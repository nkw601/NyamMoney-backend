package com.ssafy.project.api.v1.postLike.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.ssafy.project.api.v1.postLike.dto.LikePostResponse;

public interface PostLikeService {

	LikePostResponse toggleLike(Long boardId, Long postId, Long userId);

	LikePostResponse getLikeInfo(Long boardId, Long postId, Long userId) throws NotFoundException;

}
