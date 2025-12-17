package com.ssafy.project.api.v1.user.dto;

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
public class UserPostCursorRequest {
//	private Long boardId;
	private String cursor;
	private Integer size;
}
