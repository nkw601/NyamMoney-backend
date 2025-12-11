package com.ssafy.project.api.v1.postLike.service;

import com.ssafy.project.api.v1.postLike.dto.LikePostResponse;

public interface PostLikeService {

	LikePostResponse toggleLike(Long boardId, Long postId, Long userId);

}
