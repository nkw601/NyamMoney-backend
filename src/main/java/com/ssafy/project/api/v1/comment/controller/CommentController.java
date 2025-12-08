package com.ssafy.project.api.v1.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.comment.dto.CommentDetailResponse;
import com.ssafy.project.api.v1.comment.service.CommentService;

@RestController
@RequestMapping("/api/boards/{boardId}/posts/{postId}/comments")
public class CommentController {
	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/{commentId}")
	public CommentDetailResponse getCommnet(@PathVariable Long boardId,
											@PathVariable Long postId,
											@PathVariable Long commentId) {
		return commentService.getComment(boardId, postId, commentId);
	}
}
