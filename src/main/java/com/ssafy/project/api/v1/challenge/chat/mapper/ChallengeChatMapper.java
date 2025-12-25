package com.ssafy.project.api.v1.challenge.chat.mapper;

import java.util.List;
import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.api.v1.challenge.chat.dto.ChallengeChatMessage;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface ChallengeChatMapper {

	void insertMessage(ChallengeChatMessage message);

	List<ChallengeChatMessage> findByChallengeId(@Param("challengeId") Long challengeId);

	List<ChallengeChatMessage> findByChallengeIdBefore(
		@Param("challengeId") Long challengeId,
		@Param("before") LocalDateTime before,
		@Param("size") int size
	);

}
