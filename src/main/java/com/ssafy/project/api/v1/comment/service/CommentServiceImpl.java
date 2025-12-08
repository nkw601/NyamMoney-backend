package com.ssafy.project.api.v1.comment.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.comment.dto.CommentCreateRequest;
import com.ssafy.project.api.v1.comment.dto.CommentCreateResponse;
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

	@Override
	public CommentCreateResponse createComment(Long postId, Long userId, CommentCreateRequest req) {
		CommentDto dto = new CommentDto();
		dto.setPostId(postId);
		dto.setUserId(userId);
		dto.setContentMd(req.getContent());
		
		commentMapper.createComment(dto);
		return new CommentCreateResponse(dto.getCommentId());
	}

}
