import api from '@/api/axios'

export function signup(body) {
  return api.post('/v1/users/signup', body)
}

export function checkLoginId(loginId) {
  return api.get('/v1/users/check-loginId', { params: { loginId } })
}

export function checkNickname(nickname) {
  return api.get('/v1/users/check-nickname', { params: { nickname }})
}

export function fetchMe() {
  return api.get('/v1/users/me')
}

export function updateUser(userId, body) {
  return api.patch(`/v1/users/${userId}`, body)
}

export function updatePassword(userId, body) {
  return api.patch(`/v1/users/${userId}/password`, body)
}

export function deleteUser(userId) {
  return api.delete(`/v1/users/${userId}`)
}

export function fetchUser(userId) {
  return api.get(`v1/users/${userId}`)
}

export const fetchUserPosts = ({ userId, cursor = null, size = 5 }) => {
  const params = {}
  if (cursor) params.cursor = cursor
  if (size) params.size = size

  return api.get(`/v1/users/${userId}/posts`, { params })
}