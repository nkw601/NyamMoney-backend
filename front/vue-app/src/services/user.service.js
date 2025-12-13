import api from '@/api/axios'

export const userService = {
  checkLoginId: (loginId) =>
    api.get('/v1/users/check-loginId', { params: { loginId } }),

  checkNickname: (nickname) =>
    api.get('/v1/users/check-nickname', { params: { nickname } }),

  signup: (body) => api.post('/v1/users/signup', body),
}