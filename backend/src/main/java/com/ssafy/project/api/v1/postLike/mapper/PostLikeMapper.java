package com.ssafy.project.api.v1.postLike.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PostLikeMapper {

	Long findLike(Long postId, Long userId);

	void insertLike(Long postId, Long userId);

	void deleteLike(Long likedId);

}
