package com.ssafy.project.api.v1.auth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.project.api.v1.auth.dto.TokenRefreshResponse;
import com.ssafy.project.api.v1.auth.refreshToken.dto.RefreshTokenDto;
import com.ssafy.project.api.v1.auth.refreshToken.mapper.RefreshTokenMapper;
import com.ssafy.project.api.v1.user.dto.UserDto;
import com.ssafy.project.api.v1.user.mapper.UserMapper;
import com.ssafy.project.security.jwt.JWTUtil;

import io.jsonwebtoken.Claims;

@Service
public class AuthServiceImpl implements AuthService {
	private final RefreshTokenMapper rMapper;
    private final UserMapper uMapper;
    private final JWTUtil jwtUtil;

	public AuthServiceImpl(RefreshTokenMapper rMapper,UserMapper uMapper, JWTUtil jwtUtil) {
		this.rMapper = rMapper;
		this.uMapper = uMapper;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	@Transactional
	public void logout(Long userId) {
		rMapper.deleteByUserId(userId);
	}

	// Refresh Token으로 access token 재발급 받기 
	@Override
	@Transactional
	public TokenRefreshResponse refresh(String refreshToken) {
		Claims claims  = jwtUtil.getClaims(refreshToken);
		
		Long userId = claims.get("userId", Long.class);
		String loginId = claims.get("loginId", String.class);
        
        // 저장된 토큰인지 확인 
        RefreshTokenDto rToken = rMapper.findByTokenHash(refreshToken);
        if (rToken == null || !rToken.getUserId().equals(userId)) {
            throw new IllegalArgumentException("유효하지 않은 refresh token 입니다.");
        }
        
        // 만료 여부 확인 
        if (rToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            // 이미 만료된 토큰 → DB에서도 정리
            rMapper.deleteByUserId(userId);
            throw new IllegalArgumentException("refresh token 이 만료되었습니다. 다시 로그인 해주세요.");
        }
        
        // 새로운 accessToken 발급
        UserDto user = uMapper.findById(userId);
        if(user == null) {
        	rMapper.deleteByUserId(userId);
        	throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        
        String newAccessToken = jwtUtil.createAccessToken(user);
        
        return new TokenRefreshResponse(newAccessToken, refreshToken);
	}
	
}
