package com.ssafy.project.api.v1.postLike.service;

import org.apache.ibatis.javassist.NotFoundException;
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

	@Override
	public LikePostResponse getLikeInfo(Long boardId, Long postId, Long userId) throws NotFoundException {
		Integer exists = postMapper.existsPost(boardId, postId); // 게시글 존재 여부
		if(exists==null || exists==0) {
			throw new NotFoundException("게시글을 찾을 수 없습니다.");
		}
		int likeCount = postMapper.getLikesCount(postId); // 좋아요 총 개수
		boolean likeState = postLikeMapper.existsUserLike(postId, userId)==1; // 내가 눌렀는지 여부
		
		return new LikePostResponse(postId, likeState, likeCount);
	}


}
