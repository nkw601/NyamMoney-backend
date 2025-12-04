package com.ssafy.project.api.v1.post.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.post.dto.PostDetailResponse;
import com.ssafy.project.api.v1.post.dto.PostDto;
import com.ssafy.project.api.v1.post.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
public class PostServiceImpl implements PostService {
	private final PostMapper postMapper;
	public PostServiceImpl(PostMapper postMapper) {
		this.postMapper = postMapper;
	}

	@Override
	public Long createPost(PostDto dto, Long userId) {
		dto.setUserId(userId);
		postMapper.createPost(dto, userId);
		return dto.getPostId(); // 생성된 PK 반환
	}

	@Override
	public PostDetailResponse getPostDetail(Long postId) {
		return postMapper.getPostDetail(postId);
	}

}
