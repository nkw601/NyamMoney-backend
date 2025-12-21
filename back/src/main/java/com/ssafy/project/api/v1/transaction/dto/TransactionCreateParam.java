package com.ssafy.project.api.v1.transaction.dto;

import java.time.LocalDateTime;

import com.ssafy.project.domain.transaction.model.TxType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateParam {
    private Long transactionId; // DB가 채움
    private Long userId;

    private TxType txType;
    private Long amount;
    private LocalDateTime occurredAt;
    private Long categoryId;

    private String merchantNameRaw; // DB: merchant_name_raw
    private String memo;

    private String tags;            // DB: VARCHAR(255)
    private Boolean isRefund;
    private LocalDateTime canceledAt;
    private Boolean impulseFlag;

    public static TransactionCreateParam from(Long userId, TransactionCreateRequest req) {
        TransactionCreateParam p = new TransactionCreateParam();
        p.userId = userId;
        p.txType = req.getTxType();
        p.amount = req.getAmount();
        p.occurredAt = req.getOccurredAt();
        p.categoryId = req.getCategoryId();
        p.merchantNameRaw = req.getMerchantName();
        p.memo = req.getMemo();

        // List<String> -> "a,b,c"
        if (req.getTags() == null || req.getTags().isEmpty()) {
            p.tags = null;
        } else {
            p.tags = String.join(",", req.getTags());
        }

        p.isRefund = req.getIsRefund();
        p.canceledAt = req.getCanceledAt();
        p.impulseFlag = req.getImpulseFlag() != null ? req.getImpulseFlag() : Boolean.FALSE;
        return p;
    }
}
