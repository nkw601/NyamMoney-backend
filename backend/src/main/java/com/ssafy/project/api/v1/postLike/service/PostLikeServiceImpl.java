package com.ssafy.project.api.v1.postLike.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.post.mapper.PostMapper;
import com.ssafy.project.api.v1.postLike.dto.LikePostResponse;
import com.ssafy.project.api.v1.postLike.mapper.PostLikeMapper;

@Service
public class PostLikeServiceImpl implements PostLikeService {
	private final PostLikeMapper postLikeMapper;
	private final PostMapper postMapper;
	public PostLikeServiceImpl(PostLikeMapper postLikeMapper, PostMapper postMapper) {
		this.postLikeMapper = postLikeMapper;
		this.postMapper = postMapper;
	}

	@Override
	public LikePostResponse toggleLike(Long boardId, Long postId, Long userId) {
		
		// 좋아요 중복 체크
		Long likedId = postLikeMapper.findLike(postId, userId);
		
		boolean likeState;
		int likesCount;
		
		if (likedId == null) {
            // 2. 좋아요 등록
            postLikeMapper.insertLike(postId, userId);
            postMapper.increaseLike(postId);
            likeState = true;
        } else {
            // 3. 좋아요 취소
            postLikeMapper.deleteLike(likedId);
            postMapper.decreaseLike(postId);
            likeState = false;
        }

        // 현재 좋아요 개수 조회
        likesCount = postMapper.getLikesCount(postId);

        return new LikePostResponse(postId, likeState, likesCount);
	}


}
