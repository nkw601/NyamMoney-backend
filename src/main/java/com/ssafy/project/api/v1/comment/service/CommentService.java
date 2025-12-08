package com.ssafy.project.api.v1.comment.service;

import com.ssafy.project.api.v1.comment.dto.CommentDetailResponse;

public interface CommentService {

	CommentDetailResponse getComment(Long boardId, Long postId, Long commentId);
}
