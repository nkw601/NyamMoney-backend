package com.ssafy.project.api.v1.post.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.post.dto.PostDto;
import com.ssafy.project.api.v1.post.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostMapper postMapper;

	public Long createPost(PostDto dto, Long userId) {
		dto.setUserId(userId);
		postMapper.createPost(dto, userId);
		return dto.getPostId(); // 생성된 PK 반
		
	}

}
