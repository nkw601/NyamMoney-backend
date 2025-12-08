package com.ssafy.project.api.v1.comment.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.comment.dto.CommentDetailResponse;
import com.ssafy.project.api.v1.comment.dto.CommentDto;
import com.ssafy.project.api.v1.comment.mapper.CommentMapper;

import lombok.AllArgsConstructor;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public CommentDetailResponse getComment(Long boardId, Long postId, Long commentId) {
		return commentMapper.getComment(postId, commentId);
	}

}
