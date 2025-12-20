import api from '@/api/axios'

// 챌린지 목록 조회
export function fetchChallengeList() {
  return api.get('/v1/challenges')
}
