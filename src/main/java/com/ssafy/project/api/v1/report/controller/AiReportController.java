package com.ssafy.project.api.v1.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.report.dto.MonthlyReportResponse;
import com.ssafy.project.api.v1.report.service.AiReportServiceImpl;
import com.ssafy.project.security.auth.UserPrincipal;

@RestController
@RequestMapping("/api/v1/ai/report")
public class AiReportController {
	private final AiReportServiceImpl aiService;
	public AiReportController(AiReportServiceImpl aiService) {
		this.aiService = aiService;
	}
	
	@GetMapping("/monthly")
	public ResponseEntity<MonthlyReportResponse> getMonthlyAnalysis(
			@AuthenticationPrincipal UserPrincipal principal,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month
			) {
		Long userId = principal.getUserId();
		
		MonthlyReportResponse response = aiService.getMonthlyAnalysis(userId, year, month);
		
		return ResponseEntity.ok(response);
	}
}
