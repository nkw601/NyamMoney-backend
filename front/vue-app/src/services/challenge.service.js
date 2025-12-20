import api from '@/api/axios'

// 챌린지 목록 조회
export function fetchChallengeList() {
  return api.get('/v1/challenges')
}
// 챌린지 상세 조회
export function fetchChallengeDetail(challengeId) {
  return api.get(`/v1/challenges/${challengeId}`)
}

