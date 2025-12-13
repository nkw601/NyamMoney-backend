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