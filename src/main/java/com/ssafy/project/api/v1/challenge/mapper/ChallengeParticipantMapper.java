package com.ssafy.project.api.v1.challenge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeItem;

@Mapper
public interface ChallengeParticipantMapper {

	List<MyChallengeItem> selectMyChallenges(Long userId);

}
