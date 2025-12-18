package com.ssafy.project.api.v1.transaction.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.transaction.dto.TransactionCreateRequest;
import com.ssafy.project.api.v1.transaction.dto.TransactionCreateResponse;
import com.ssafy.project.api.v1.transaction.dto.TransactionDetailResponse;
import com.ssafy.project.api.v1.transaction.dto.TransactionUpdateRequest;
import com.ssafy.project.api.v1.transaction.service.TransactionService;
import com.ssafy.project.security.auth.UserPrincipal;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping
	public ResponseEntity<TransactionCreateResponse> createTransaction(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TransactionCreateRequest req) {
		Long userId = principal.getUserId();
		if (req.getImpulseFlag() == null) {
			req.setImpulseFlag(Boolean.FALSE);
		}
		
		TransactionCreateResponse res = transactionService.createTransaction(userId, req);
		
		return ResponseEntity.ok(res);
	}
	
	@PatchMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailResponse> updateTransaction(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long transactionId,
            @RequestBody TransactionUpdateRequest req
    ) {
        Long userId = principal.getUserId();
        TransactionDetailResponse res = transactionService.updateTransaction(userId, transactionId, req);
        return ResponseEntity.ok(res);
    }
	
	@DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long transactionId
    ) {
        Long userId = principal.getUserId();
        transactionService.deleteTransaction(userId, transactionId);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDetailResponse> getTransactionDetail(
	        @AuthenticationPrincipal UserPrincipal principal,
	        @PathVariable Long transactionId
	) {
	    Long userId = principal.getUserId();
	    
	    TransactionDetailResponse res = transactionService.getTransactionDetail(userId, transactionId)
	    return ResponseEntity.ok(res);
	}
}
