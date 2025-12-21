package com.ssafy.project.api.v1.transaction.dto;

import java.time.LocalDateTime;

import com.ssafy.project.domain.transaction.model.TxType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionUpdateParam {

    private Long userId;
    private Long transactionId;

    private TxType txType;
    private Long amount;
    private LocalDateTime occurredAt;

    private Long categoryId;

    private String merchantNameRaw;
    private String memo;

    private String tags;
    private Boolean impulseFlag;

    private Boolean isRefund;
    private LocalDateTime canceledAt;

    public static TransactionUpdateParam from(Long userId, Long transactionId, TransactionUpdateRequest req) {
        TransactionUpdateParam p = new TransactionUpdateParam();
        p.userId = userId;
        p.transactionId = transactionId;

        p.txType = req.getTxType();
        p.amount = req.getAmount();
        p.occurredAt = req.getOccurredAt();

        p.categoryId = req.getCategoryId();

        p.merchantNameRaw = req.getMerchantName();
        p.memo = req.getMemo();

        // tags: null이면 미수정, 빈 리스트면 빈 문자열로 갱신
        if (req.getTags() != null) {
            p.tags = req.getTags().isEmpty() ? "" : String.join(",", req.getTags());
        }

        p.impulseFlag = req.getImpulseFlag();

        p.isRefund = req.getIsRefund();
        p.canceledAt = req.getCanceledAt();
        return p;
    }
}