import api from '@/api/axios'

// 챌린지 목록 조회
export function fetchChallengeList() {
  return api.get('/v1/challenges')}
// 챌린지 상세 조회
export function fetchChallengeDetail(challengeId) {
  return api.get(`/v1/challenges/${challengeId}`)}
// 챌린지 참여 / 재참여
export function challengeJoin(challengeId) {
  return api.post(`/v1/challenges/${challengeId}/join`)}
// 챌린지 참여 취소
export function challengeJoinCancle(challengeId) {
  return api.delete(`/v1/challenges/${challengeId}/join`)}
  // 챌린지 생성
export function createChallenge(payload) {
  return api.post('/v1/challenges', payload)}