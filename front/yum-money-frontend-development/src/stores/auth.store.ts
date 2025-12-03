import { defineStore } from "pinia"
import { ref, computed } from "vue"
import { authService } from "@/services/auth.service"
import type { User } from "@/types"

export const useAuthStore = defineStore("auth", () => {
  // State
  const user = ref<User | null>(null)
  const accessToken = ref<string | null>(localStorage.getItem("accessToken"))
  const refreshToken = ref<string | null>(localStorage.getItem("refreshToken"))
  const isLoading = ref(false)

  // Getters
  const isAuthenticated = computed(() => !!accessToken.value && !!user.value)

  // Actions
  async function login(loginId: string, password: string) {
    isLoading.value = true
    try {
      const response = await authService.login({ loginId, password })

      // Store tokens
      accessToken.value = response.accessToken
      refreshToken.value = response.refreshToken
      user.value = response.user

      localStorage.setItem("accessToken", response.accessToken)
      localStorage.setItem("refreshToken", response.refreshToken)

      return response.user
    } catch (error) {
      console.error("[v0] Login failed:", error)
      throw error
    } finally {
      isLoading.value = false
    }
  }

  async function signup(userData: {
    loginId: string
    email: string
    password: string
    nickname: string
  }) {
    isLoading.value = true
    try {
      const response = await authService.signup(userData)

      // Store tokens
      accessToken.value = response.accessToken
      refreshToken.value = response.refreshToken
      user.value = response.user

      localStorage.setItem("accessToken", response.accessToken)
      localStorage.setItem("refreshToken", response.refreshToken)

      return response.user
    } catch (error) {
      console.error("[v0] Signup failed:", error)
      throw error
    } finally {
      isLoading.value = false
    }
  }

  async function logout() {
    isLoading.value = true
    try {
      await authService.logout()
    } catch (error) {
      console.error("[v0] Logout error:", error)
    } finally {
      // Clear local state regardless
      user.value = null
      accessToken.value = null
      refreshToken.value = null
      localStorage.removeItem("accessToken")
      localStorage.removeItem("refreshToken")
      isLoading.value = false
    }
  }

  async function restoreSession() {
    const token = localStorage.getItem("accessToken")
    if (!token) return

    isLoading.value = true
    try {
      const currentUser = await authService.getCurrentUser()
      user.value = currentUser
    } catch (error) {
      console.error("[v0] Session restore failed:", error)
      // Clear invalid session
      logout()
    } finally {
      isLoading.value = false
    }
  }

  return {
    // State
    user,
    accessToken,
    isLoading,

    // Getters
    isAuthenticated,

    // Actions
    login,
    signup,
    logout,
    restoreSession,
  }
})

// Default export for flexibility
export default useAuthStore
