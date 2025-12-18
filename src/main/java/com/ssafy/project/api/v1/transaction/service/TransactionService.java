package com.ssafy.project.api.v1.transaction.service;

import com.ssafy.project.api.v1.transaction.dto.TransactionCreateRequest;
import com.ssafy.project.api.v1.transaction.dto.TransactionCreateResponse;
import com.ssafy.project.api.v1.transaction.dto.TransactionDetailResponse;
import com.ssafy.project.api.v1.transaction.dto.TransactionUpdateRequest;

public interface TransactionService {

	TransactionCreateResponse createTransaction(Long userId, TransactionCreateRequest req);

	TransactionDetailResponse updateTransaction(Long userId, Long transactionId, TransactionUpdateRequest req);

	void deleteTransaction(Long userId, Long transactionId);
	
}
