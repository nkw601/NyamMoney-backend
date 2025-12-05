package com.ssafy.project.api.v1.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.post.dto.PostCreateRequest;
import com.ssafy.project.api.v1.post.dto.PostCreateResponse;
import com.ssafy.project.api.v1.post.dto.PostDetailResponse;
import com.ssafy.project.api.v1.post.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/post")
public class PostController {
	private final PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}
//	
//	@PostMapping
//    public Long createPost(
//            @PathVariable Long boardId,
//            @RequestBody PostDto dto,
//            @AuthenticationPrincipal(expression = "userId") Long userId) {
//        dto.setBoardId(boardId);
//        return postService.createPost(dto, userId);
//    }
	
	@GetMapping("/{postId}")
	public PostDetailResponse getPostDetail(@PathVariable Long postId, @PathVariable Long boardId) {
		return postService.getPostDetail(postId);
		
	}
	
	@PostMapping
	public PostCreateResponse createPost(@PathVariable Long boardId, 
			@Valid @RequestBody PostCreateRequest dto,
			@AuthenticationPrincipal(expression = "uesrId") Long userId) {
		return postService.createPost(boardId, userId, dto);
	}

}
