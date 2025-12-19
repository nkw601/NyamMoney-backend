package com.ssafy.project.api.v1.challenge.service;

import com.ssafy.project.api.v1.challenge.dto.challenge.ChallengeCreateRequest;
import com.ssafy.project.api.v1.challenge.dto.challenge.ChallengeCreateResponse;
import com.ssafy.project.api.v1.challenge.dto.challenge.ChallengeListResponse;
import com.ssafy.project.api.v1.challenge.dto.challenge.ChallengeUpdateRequest;

import jakarta.validation.Valid;

public interface ChallengeService {

	ChallengeListResponse getChallengeList();

	ChallengeCreateResponse createChallenge(@Valid ChallengeCreateRequest request);

	void updateChallenge(Long challengeId, @Valid ChallengeUpdateRequest request);

	void deleteChallenge(Long challengeId);

}
