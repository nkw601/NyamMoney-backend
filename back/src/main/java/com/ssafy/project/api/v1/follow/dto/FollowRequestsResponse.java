package com.ssafy.project.api.v1.follow.dto;

import java.util.List;

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
public class FollowRequestsResponse {
	private String direction; // incoming / outgoing
	private long totalCount;
	private List<FollowRequestItem> requests;
}
