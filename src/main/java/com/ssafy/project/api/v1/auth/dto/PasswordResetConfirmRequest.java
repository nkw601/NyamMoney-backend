package com.ssafy.project.api.v1.auth.dto;

import com.google.auto.value.AutoValue.Builder;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Data
@Setter
public class PasswordResetConfirmRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String newPassword;
}
