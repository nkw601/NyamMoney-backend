package com.ssafy.project.api.v1.brand.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.brand.mapper.BrandMapper;

@Service
public class BrandServiceImpl implements BrandService {
	
	private final BrandMapper brandMapper;
	
	public BrandServiceImpl(BrandMapper brandMapper) {
		this.brandMapper = brandMapper;
	}
	
	@Override
	public String findBrand(String merchantName) {
		// 특수문자 제거 (\\w는 알파벳과 숫자를 포함한 모든 단어 문자, \\s 공백 문자, 가-힣 한글)
        String normalizedMerchantName = merchantName.replaceAll("[^\\w가-힣]", "").trim();
		
        String brandName = brandMapper.findBrand(normalizedMerchantName);
        
        return brandName != null ? brandName : merchantName;
	}

}
