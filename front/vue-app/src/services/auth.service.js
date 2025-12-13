import api from '@/api/axios'

export function login(body) {
  return api.post('/v1/auth/login', body)
}

export function logout(refreshToken) {
  return api.post('/v1/auth/logout', null, {
    headers: { 'Refresh-Token': refreshToken },
  })
}

/**
 * accessToken 재발급
 * (보통 store나 axios interceptor에서만 사용)
 * @param {string} refreshToken
 */
export function refresh(refreshToken) {
  return api.post('/v1/auth/refresh', null, {
    headers: { 'Refresh-Token': refreshToken },
  })
}