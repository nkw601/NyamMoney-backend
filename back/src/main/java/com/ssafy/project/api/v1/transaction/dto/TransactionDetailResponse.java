package com.ssafy.project.api.v1.transaction.dto;

import java.time.LocalDateTime;

import com.ssafy.project.api.v1.category.dto.CategoryItem;
import com.ssafy.project.domain.transaction.model.TxType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetailResponse {
    private Long transactionId;
    private TxType txType;
    private Long amount;
    private LocalDateTime occurredAt;

    private CategoryItem category;

    private String merchantName;
    private String memo;
    private String tags;

    private Boolean impulseFlag;

    private Boolean isRefund;
    private LocalDateTime canceledAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}