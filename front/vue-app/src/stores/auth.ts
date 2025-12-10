import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isAuthenticated: (typeof window !== 'undefined' && !!localStorage.getItem('isAuthenticated')) || false,
    user: null as null | { id: string; name: string; email: string }
  }),
  actions: {
    login(user: { id: string; name: string; email: string }) {
      this.isAuthenticated = true
      this.user = user
      localStorage.setItem('isAuthenticated', '1')
      localStorage.setItem('user', JSON.stringify(user))
    },
    logout() {
      this.isAuthenticated = false
      this.user = null
      localStorage.removeItem('isAuthenticated')
      localStorage.removeItem('user')
    },
    restore() {
      const u = localStorage.getItem('user')
      if (u) this.user = JSON.parse(u)
      this.isAuthenticated = !!localStorage.getItem('isAuthenticated')
    }
  }
})
