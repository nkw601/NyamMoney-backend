package com.ssafy.project.api.v1.postLike.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LikePostResponse {
	private Long PostId;
	private Long userId;
	private LocalDateTime likedAt;

}
