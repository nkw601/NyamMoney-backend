package com.ssafy.project.api.v1.auth.refreshToken.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.project.api.v1.auth.refreshToken.dto.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {
	int insertRefreshToken(RefreshTokenDto token);
	
	int deleteByUserId(@Param("userId") Long userId);
	
    RefreshTokenDto findByTokenHash(@Param("tokenHash") String tokenHash);

}
