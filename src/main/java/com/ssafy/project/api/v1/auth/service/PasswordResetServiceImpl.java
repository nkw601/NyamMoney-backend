package com.ssafy.project.api.v1.auth.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.auth.caller.PasswordResetMailCaller;
import com.ssafy.project.api.v1.auth.dto.PasswordResetSendCodeRequest;
import com.ssafy.project.api.v1.auth.dto.PasswordResetSendCodeResponse;
import com.ssafy.project.api.v1.auth.dto.PasswordResetVerifyCodeRequest;
import com.ssafy.project.api.v1.auth.dto.PasswordResetVerifyCodeResponse;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
	
	private final PasswordResetMailCaller mailCaller;
	
	public PasswordResetServiceImpl(PasswordResetMailCaller mailCaller) {
		this.mailCaller = mailCaller;
	}

	@Override
	public PasswordResetSendCodeResponse sendVerificationCode(PasswordResetSendCodeRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PasswordResetVerifyCodeResponse checkVerificationCode(PasswordResetVerifyCodeRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
}
