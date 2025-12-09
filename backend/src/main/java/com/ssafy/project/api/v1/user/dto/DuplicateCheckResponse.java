package com.ssafy.project.api.v1.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuplicateCheckResponse {
    private boolean available;  // true면 사용 가능 
    private String field;       // "nickname" 또는 "loginId"
    private String value;       // 사용자 입력 값
}