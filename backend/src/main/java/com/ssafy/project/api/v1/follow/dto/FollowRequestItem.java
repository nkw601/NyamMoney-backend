package com.ssafy.project.api.v1.follow.dto;

import java.time.LocalDateTime;

import com.ssafy.project.domain.follow.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowRequestItem {
    private Long requestId;        // followId
    private Long userId;           // 상대방 userId (incoming: followerId)
    private String nickname;       // 상대방 닉네임
    private Status status;         // PENDING
    private LocalDateTime createdAt;

}