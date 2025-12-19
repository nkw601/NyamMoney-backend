package com.ssafy.project.api.v1.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeItem;
import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeListResponse;
import com.ssafy.project.api.v1.challenge.mapper.ChallengeParticipantMapper;

@Service
public class ChallengeParticipantServiceImpl implements ChallengeParticipantService {
	private final ChallengeParticipantMapper pMapper;
	public ChallengeParticipantServiceImpl(ChallengeParticipantMapper pMapper) {
		this.pMapper = pMapper;
	}

	@Override
	public MyChallengeListResponse getMyChallenges(Long userId) {
		List<MyChallengeItem> items = pMapper.selectMyChallenges(userId);
        return new MyChallengeListResponse(items);
	}

}
