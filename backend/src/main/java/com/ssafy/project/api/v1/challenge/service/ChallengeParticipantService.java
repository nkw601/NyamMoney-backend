package com.ssafy.project.api.v1.challenge.service;

import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeListResponse;

public interface ChallengeParticipantService {

	MyChallengeListResponse getMyChallenges(Long userId);

}
