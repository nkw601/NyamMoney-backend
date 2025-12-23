package com.ssafy.project.api.v1.auth.caller;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PasswordResetMailCaller {
	public String makeCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1_000_000);
        return String.format("%06d", num);
    }

    public void send(String to, String userId, String code) {

    	
    }
}
