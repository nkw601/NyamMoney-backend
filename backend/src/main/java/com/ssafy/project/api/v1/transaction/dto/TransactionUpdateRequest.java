package com.ssafy.project.api.v1.transaction.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.project.domain.transaction.model.TxType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionUpdateRequest {

    private TxType txType;
    private Long amount;
    private LocalDateTime occurredAt;

    private Long categoryId;

    private String merchantName;     // DB: merchant_name_raw
    private String memo;

    private List<String> tags;       // -> "a,b,c"로 저장
    private Boolean impulseFlag;

    private Boolean isRefund;
    private LocalDateTime canceledAt;
}