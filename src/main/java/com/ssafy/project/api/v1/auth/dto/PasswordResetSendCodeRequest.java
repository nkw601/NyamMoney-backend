package com.ssafy.project.api.v1.auth.dto;

import com.google.auto.value.AutoValue.Builder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Data
@Setter
public class PasswordResetSendCodeRequest {
	Long userId;
	String email;
}
