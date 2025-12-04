package com.ssafy.project.api.v1.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupResponse {
	private Long userId;
    private String nickname;
    private LocalDateTime createdAt;

    public UserSignupResponse(Long userId, String nickname, LocalDateTime createdAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
