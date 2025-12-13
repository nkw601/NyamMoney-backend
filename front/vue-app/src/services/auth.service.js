import api from '@/api/axios'

export const authService = {
  login: (body) => api.post('/v1/auth/login', body),

  logout: (refreshToken) =>
    api.post('/v1/auth/logout', null, {
      headers: { 'Refresh-Token': refreshToken },
    }),

  refresh: (refreshToken) =>
    api.post('/v1/auth/refresh', null, {
      headers: { 'Refresh-Token': refreshToken },
    }),
}