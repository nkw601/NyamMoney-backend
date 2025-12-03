import api from "./api"
import type { User, ApiResponse } from "@/types"

interface LoginRequest {
  loginId: string
  password: string
}

interface SignupRequest {
  loginId: string
  email: string
  password: string
  nickname: string
}

interface AuthResponse {
  accessToken: string
  refreshToken: string
  user: User
}

export const authService = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const { data } = await api.post<ApiResponse<AuthResponse>>("/auth/login", credentials)
    return data.data
  },

  async signup(userData: SignupRequest): Promise<AuthResponse> {
    const { data } = await api.post<ApiResponse<AuthResponse>>("/auth/signup", userData)
    return data.data
  },

  async logout(): Promise<void> {
    await api.post("/auth/logout")
    localStorage.removeItem("accessToken")
    localStorage.removeItem("refreshToken")
  },

  async refreshToken(refreshToken: string): Promise<string> {
    const { data } = await api.post<ApiResponse<{ accessToken: string }>>("/auth/refresh", {
      refreshToken,
    })
    return data.data.accessToken
  },

  async getCurrentUser(): Promise<User> {
    const { data } = await api.get<ApiResponse<User>>("/auth/me")
    return data.data
  },
}

export default authService
