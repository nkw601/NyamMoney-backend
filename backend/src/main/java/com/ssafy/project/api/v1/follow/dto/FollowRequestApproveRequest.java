package com.ssafy.project.api.v1.follow.dto;

import com.ssafy.project.domain.follow.model.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestApproveRequest {

    private Status status; // ACCEPTED, REJECTED
}
