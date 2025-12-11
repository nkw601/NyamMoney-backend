import { defineStore } from 'pinia'
import Cookies from 'js-cookie'
import api from '@/api/axios'

// 안전한 JWT payload 디코딩 함수(atob 대체)
function decodeJwtPayload(token) {
  try {
    const base64 = token.split('.')[1]
    const padded = base64.padEnd(base64.length + (4 - (base64.length % 4)) % 4, '=')
    const json = decodeURIComponent(
      atob(padded)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    return JSON.parse(json)
  } catch (e) {
    console.warn('JWT decode failed:', e)
    return null
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    userId: null,
    loginId: null,
    nickname: null,
    isAuthenticated: false,
    loading: false,
  }),

  actions: {
    // 브라우저 새로고침 시 토큰에서 상태 복구
    setAuthFromToken() {
      const token = Cookies.get('accessToken')
      if (!token) {
        this.$resetAuth()
        return
      }

      const payload = decodeJwtPayload(token)
      if (!payload) {
        this.logout()
        return
      }

      this.userId = payload.userId
      this.loginId = payload.loginId
      this.nickname = payload.nickname
      this.isAuthenticated = true
    },

    async login({ loginId, password }) {
      this.loading = true
      try {
        const res = await api.post('/v1/auth/login', { loginId, password })

        const data = res.data
        this.userId = data.userId
        this.loginId = data.loginId
        this.nickname = data.nickname
        this.isAuthenticated = true

        Cookies.set('accessToken', data.accessToken, {
          secure: true,
          sameSite: 'strict',
        })
        Cookies.set('refreshToken', data.refreshToken, {
          secure: true,
          sameSite: 'strict',
        })
      } finally {
        this.loading = false
      }
    },

    async logout() {
      try {
        const refreshToken = Cookies.get('refreshToken')
        if (refreshToken) {
          await api.post(
            '/api/v1/auth/logout',
            null,
            {
              headers: { 'Refresh-Token': refreshToken },
            }
          )
        }
      } catch (e) {
        console.warn('Server logout failed — ignored.')
      }

      Cookies.remove('accessToken')
      Cookies.remove('refreshToken')
      this.$resetAuth()
    },

    $resetAuth() {
      this.userId = null
      this.loginId = null
      this.nickname = null
      this.isAuthenticated = false
      this.loading = false
    },
  },
})