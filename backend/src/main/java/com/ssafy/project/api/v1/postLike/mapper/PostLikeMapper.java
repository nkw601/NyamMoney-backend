package com.ssafy.project.api.v1.postLike.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PostLikeMapper {

	Long findLike(Long postId, Long userId);

	void insertLike(Long postId, Long userId);

	void deleteLike(Long likedId);

	// 사용자가 그 게시글 좋아요 눌렀는지 여부 확인
	int existsUserLike(Long postId, Long userId);

}
