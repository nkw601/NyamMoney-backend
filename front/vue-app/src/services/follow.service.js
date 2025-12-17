import api from '@/api/axios'

// 팔로우 요청
export function requestFollow(targetUserId) {
  return api.post(`/v1/follows/${targetUserId}`)
}

// 언팔로우
export function unfollow(targetUserId) {
  return api.delete(`/v1/follows/${targetUserId}`)
}

// 팔로우 요청 목록 (incoming/outgoing)
export function fetchFollowRequests(direction) {
  return api.get('/v1/follows/follow-requests', { params: { direction } })
}

// 팔로우 요청 승인/거절
export function acceptFollowRequest(requestId) {
  return api.patch(`/v1/follows/follow-requests/${requestId}`, { status: 'ACCEPTED' })
}
export function declineFollowRequest(requestId) {
  return api.patch(`/v1/follows/follow-requests/${requestId}`, { status: 'REJECTED' })
}

// 팔로우 요청 취소(outgoing)
export function cancelFollowRequest(requestId) {
  return api.delete(`/v1/follows/follow-requests/${requestId}`)
}

// 팔로워/팔로잉
export function fetchFollowers() {
  return api.get('/v1/follows/followers')
}
export function fetchFollowings() {
  return api.get('/v1/follows/followings')
}

// 차단/차단해제/차단목록
export function block(targetUserId) {
  return api.put(`/v1/blocks/${targetUserId}`)
}
export function unblock(targetUserId) {
  return api.delete(`/v1/blocks/${targetUserId}`)
}
export function fetchBlocks() {
  return api.get('/v1/blocks/blocks')
}

// 팔로우 상태 조회
export function fetchFollowStatus(targetUserId) {
  return api.get(`/v1/follows/${targetUserId}/status`)
}