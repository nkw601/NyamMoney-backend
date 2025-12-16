package com.ssafy.project.api.v1.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.follow.dto.FollowCreateResponse;
import com.ssafy.project.api.v1.follow.service.FollowService;
import com.ssafy.project.security.auth.UserPrincipal;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {
	private final FollowService followService;
	
	public FollowController(FollowService followService) {
		this.followService = followService;
	}
	
	@PostMapping("/{targetUserId}")
	public ResponseEntity<FollowCreateResponse> createFollow(@PathVariable long targetUserId, @AuthenticationPrincipal UserPrincipal principal) {
		Long userId = principal.getUserId();
		
		FollowCreateResponse res = followService.createFollow(userId, targetUserId);
		return ResponseEntity.ok(res);
	}
}
