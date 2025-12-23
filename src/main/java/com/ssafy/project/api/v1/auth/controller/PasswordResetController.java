package com.ssafy.project.api.v1.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.api.v1.auth.dto.PasswordResetSendCodeRequest;
import com.ssafy.project.api.v1.auth.dto.PasswordResetSendCodeResponse;
import com.ssafy.project.api.v1.auth.dto.PasswordResetVerifyCodeRequest;
import com.ssafy.project.api.v1.auth.dto.PasswordResetVerifyCodeResponse;
import com.ssafy.project.api.v1.auth.service.PasswordResetService;


@RestController
@RequestMapping("/api/v1/auth/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    
    public PasswordResetController(PasswordResetService passwordResetService) {
    	this.passwordResetService = passwordResetService;
    }
    
    @PostMapping("/code")
    public ResponseEntity<PasswordResetSendCodeResponse> sendCode(
            @RequestBody PasswordResetSendCodeRequest req
    ) {
        return ResponseEntity.ok(
                passwordResetService.sendVerificationCode(req)
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<PasswordResetVerifyCodeResponse> verify(
            @RequestBody PasswordResetVerifyCodeRequest req
    ) {
        return ResponseEntity.ok(
                passwordResetService.checkVerificationCode(req)
        );
    }

}