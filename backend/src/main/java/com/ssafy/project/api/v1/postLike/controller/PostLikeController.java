package com.ssafy.project.api.v1.postLike.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.postLike.dto.LikePostResponse;
import com.ssafy.project.api.v1.postLike.service.PostLikeService;
import com.ssafy.project.security.auth.UserPrincipal;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/posts/{postId}/like")
public class PostLikeController {
	private final PostLikeService postLikeService;
	public PostLikeController(PostLikeService postLikeService) {
		this.postLikeService = postLikeService;
	}
	
	@PostMapping
	public ResponseEntity<LikePostResponse> toggleLike(@PathVariable Long boardId, @PathVariable Long postId){
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext()
														.getAuthentication()
														.getPrincipal();
		Long userId = principal.getUserId();
		LikePostResponse response = postLikeService.toggleLike(boardId, postId, userId);
		return ResponseEntity.ok(response);
	}

}
