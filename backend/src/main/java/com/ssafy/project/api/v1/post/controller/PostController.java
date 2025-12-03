package com.ssafy.project.api.v1.post.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.post.dto.PostDto;
import com.ssafy.project.api.v1.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/post")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@PostMapping
    public Long createPost(
            @PathVariable Long boardId,
            @RequestBody PostDto dto,
            @AuthenticationPrincipal(expression = "userId") Long userId) {
        dto.setBoardId(boardId);
        return postService.createPost(dto, userId);
    }

}
