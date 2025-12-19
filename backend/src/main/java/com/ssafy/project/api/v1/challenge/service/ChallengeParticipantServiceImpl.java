package com.ssafy.project.api.v1.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.project.api.v1.challenge.dto.participant.ChallengeJoinResponse;
import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeItem;
import com.ssafy.project.api.v1.challenge.dto.participant.MyChallengeListResponse;
import com.ssafy.project.api.v1.challenge.mapper.ChallengeMapper;
import com.ssafy.project.api.v1.challenge.mapper.ChallengeParticipantMapper;
import com.ssafy.project.domain.challenge.model.ChallengeStatus;

@Service
public class ChallengeParticipantServiceImpl implements ChallengeParticipantService {
	private final ChallengeMapper cMapper;
	private final ChallengeParticipantMapper pMapper;
	public ChallengeParticipantServiceImpl(ChallengeMapper cMapper, ChallengeParticipantMapper pMapper) {
		this.cMapper = cMapper;
		this.pMapper = pMapper;
	}

	@Override
	public MyChallengeListResponse getMyChallenges(Long userId) {
		List<MyChallengeItem> items = pMapper.selectMyChallenges(userId);
        return new MyChallengeListResponse(items);
	}

	@Override
	public ChallengeJoinResponse joinChallenge(Long challengeId, Long userId) {
		// 챌린지 상태 검증
        ChallengeStatus status = cMapper.selectStatus(challengeId);
        if (status == null) {
            throw new IllegalArgumentException("존재하지 않는 챌린지입니다.");
        }

        if (status!=ChallengeStatus.UPCOMING && status!=ChallengeStatus.ACTIVE) {
            throw new IllegalStateException("진행 예정이거나 진행 중인 챌린지만 참여할 수 있습니다.");
        }

        // 중복 참여 방지
        int exists = pMapper.existsParticipant(challengeId, userId);
        if (exists > 0) {
            throw new IllegalStateException("이미 참여한 챌린지입니다.");
        }

        // 참여 처리
        pMapper.insertParticipant(challengeId, userId);

        // 응답 생성
        return new ChallengeJoinResponse(challengeId, userId, "JOINED", 0.0);
	}

}
