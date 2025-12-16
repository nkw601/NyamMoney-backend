package com.ssafy.project.api.v1.follow.dto;

import java.time.LocalDateTime;

public class FollowCreateResponse {
	Long followId;
	Long followerId;
	Long followeeId;
	String status; // 'pending', 'accepted', 'rejected', 'blocked'
    private LocalDateTime createdAt;

}
