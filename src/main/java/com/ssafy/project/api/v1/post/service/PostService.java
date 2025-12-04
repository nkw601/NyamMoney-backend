package com.ssafy.project.api.v1.post.service;

import com.ssafy.project.api.v1.post.dto.PostDetailResponse;
import com.ssafy.project.api.v1.post.dto.PostDto;

public interface PostService {

	Long createPost(PostDto dto, Long userId);

	PostDetailResponse getPostDetail(Long postId);

}
