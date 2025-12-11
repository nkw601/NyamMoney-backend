package com.ssafy.project.api.v1.postLike.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.postLike.dto.LikePostResponse;
import com.ssafy.project.api.v1.postLike.service.PostLikeServiceImpl;

@RestController
@RequestMapping("/api/v1/boards/{boards}/posts/{postId}/like")
public class PostLikeController {
	private PostLikeServiceImpl postLikeServiceImpl;
	public PostLikeController(PostLikeServiceImpl postLikeServiceImpl) {
		this.postLikeServiceImpl = postLikeServiceImpl;
	}
	
	@PostMapping
	public ResponseEntity<LikePostResponse> likePost(){
		
	}

}
