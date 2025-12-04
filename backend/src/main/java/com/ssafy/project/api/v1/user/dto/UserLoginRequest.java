package com.ssafy.project.api.v1.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
