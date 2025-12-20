package com.ssafy.project.api.v1.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.project.api.v1.api.dto.ApiResponse;
import com.ssafy.project.api.v1.api.dto.MerchantItem;
import com.ssafy.project.api.v1.category.Service.CategoryService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiDataServiceImpl implements ApiDataService {

    private static final String BASE_URL = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInArea";

    private final CategoryService categoryService;
    private final ObjectProvider<VectorStore> vectorStoreProvider;
    private final ObjectMapper objectMapper;

    @Value("${data.go.service-key}")
    private String serviceKey;

    public ApiDataServiceImpl(CategoryService categoryService,
            ObjectProvider<VectorStore> vectorStoreProvider,
            ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.vectorStoreProvider = vectorStoreProvider;
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    @Async
    public void init() {
        log.info("🔥 fetchDataAndStore START");
        fetchDataAndStore();
    }

    @Override
    public void fetchDataAndStore() {

        int startKey = 10000; // 10000
        int endKey = 10001; // 10508
        int numOfRows = 1000; // 1000
        int totalDocs = 0;

        for (int key = startKey; key <= endKey; key++) {
            int pageNo = 1;

            ApiResponse first = call(key, pageNo, numOfRows);
            if (first == null || first.getBody() == null)
                continue;

            int totalCount = first.getBody().getTotalCount();
            int totalPages = (totalCount + numOfRows - 1) / numOfRows;

            ingestPage(first);
            totalDocs += first.getBody().getItems() == null ? 0 : first.getBody().getItems().size();

            for (pageNo = 2; pageNo <= totalPages; pageNo++) {
                ApiResponse page = call(key, pageNo, numOfRows);
                if (page == null || page.getBody() == null)
                    continue;
                ingestPage(page);
                totalDocs += page.getBody().getItems() == null ? 0 : page.getBody().getItems().size();
            }

            log.info("[vector-ingest] key={} ingested. totalCount={}, totalPages={}",
                    key, totalCount, totalPages);
        }

        log.info("[vector-ingest] completed. keys=[{}-{}], totalDocs={}",
                startKey, endKey, totalDocs);
    }

    /**
     * 📡 API 호출 (HttpURLConnection 방식)
     * - serviceKey 인코딩 이슈(+ -> 공백) 및 RestTemplate 401 회피 목적
     */
    private ApiResponse call(int key, int pageNo, int numOfRows) {
        HttpURLConnection conn = null;

        try {
            String cleanedKey = serviceKey == null ? "" : serviceKey.trim();
            String finalServiceKey = toQuerySafeServiceKey(cleanedKey);

            StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?")
                    .append(URLEncoder.encode("serviceKey", "UTF-8"))
                    .append("=")
                    .append(finalServiceKey);

            urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8"))
                    .append("=").append(pageNo);

            urlBuilder.append("&").append(URLEncoder.encode("numOfRows", "UTF-8"))
                    .append("=").append(numOfRows);

            urlBuilder.append("&").append(URLEncoder.encode("key", "UTF-8"))
                    .append("=").append(key);

            urlBuilder.append("&").append(URLEncoder.encode("type", "UTF-8"))
                    .append("=").append("json");

            URL url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 브라우저와 유사하게(차단/인증 오류 완화용)
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int code = conn.getResponseCode();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream(),
                            StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            br.close();

            if (code < 200 || code >= 300) {
                String maskedUrl = urlBuilder.toString().replaceAll("serviceKey=[^&]+", "serviceKey=***");
                log.warn("API call failed. httpCode={}, key={}, pageNo={}, url={}, body={}",
                        code, key, pageNo, maskedUrl, sb.toString());
                return null;
            }

            return objectMapper.readValue(sb.toString(), ApiResponse.class);

        } catch (Exception e) {
            String masked = "(url build failed)";
            try {
                masked = BASE_URL + "?serviceKey=***&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&key=" + key
                        + "&type=JSON";
            } catch (Exception ignore) {
            }

            log.warn("API call exception. key={}, pageNo={}, url={}", key, pageNo, masked, e);
            return null;

        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    /**
     * 🧠 카테고리 매핑 + 벡터스토어 적재
     */
    private void ingestPage(ApiResponse res) {

        List<MerchantItem> items = res.getBody().getItems();
        if (items == null || items.isEmpty()) return;

        log.info("📦 ingest items = {}", items.size());

        List<Document> docs = new ArrayList<>(items.size());

        for (MerchantItem item : items) {

            String mappedCategory = categoryService.mapCategoryByIndustry(
                    item.getIndsLclsNm(),
                    item.getIndsMclsNm());

            // ✅ content 최소화 (공백 나열)
            String content = String.format(
                    "%s %s %s %s",
                    safe(item.getBizesNm()),
                    safe(item.getIndsLclsNm()),
                    safe(item.getIndsMclsNm()),
                    safe(item.getIndsSclsNm())
            );

            Map<String, Object> meta = new HashMap<>();
            meta.put("category", mappedCategory);
            meta.put("indsL", item.getIndsLclsNm());
            meta.put("indsM", item.getIndsMclsNm());
            meta.put("indsS", item.getIndsSclsNm());
            meta.put("bizesId", item.getBizesId());

            docs.add(new Document(content, meta));
        }

        VectorStore vectorStore = vectorStoreProvider.getObject();

        final int batchSize = 5;      // 🔽 안정성 우선
        final long sleepMs = 250L;

        for (int start = 0; start < docs.size(); start += batchSize) {
            int end = Math.min(start + batchSize, docs.size());
            List<Document> batch = docs.subList(start, end);

            long t0 = System.currentTimeMillis();
            vectorStore.add(batch);
            long took = System.currentTimeMillis() - t0;

            log.info("🧠 vectorStore.add size={}, took={}ms", batch.size(), took);

            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**s
     * serviceKey가 이미 %2B/%3D 형태로 들어온 경우 이중 인코딩 방지.
     * - 이미 인코딩된 키: 그대로 사용
     * - 원본 키(+=/ 포함): URLEncoder로 인코딩 후 사용
     */
    private String toQuerySafeServiceKey(String key) throws Exception {
        if (key == null)
            return "";
        // 퍼센트 인코딩 패턴이 보이면 이미 인코딩된 것으로 간주
        if (key.contains("%2B") || key.contains("%2F") || key.contains("%3D") || key.contains("%25")) {
            return key;
        }
        return URLEncoder.encode(key, StandardCharsets.UTF_8);
    }
    
    private String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
