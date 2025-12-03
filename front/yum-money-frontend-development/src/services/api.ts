import axios, { type AxiosInstance, type AxiosError } from "axios"

// Create axios instance
const api: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
})

// Request interceptor - add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken")
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

// Response interceptor - handle token refresh
api.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as any

    // If 401 and not already retried
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true

      const refreshToken = localStorage.getItem("refreshToken")
      if (!refreshToken) {
        // No refresh token, redirect to login
        localStorage.clear()
        window.location.href = "/login"
        return Promise.reject(error)
      }

      try {
        // Attempt to refresh token
        const response = await axios.post(
          `${import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api"}/auth/refresh`,
          { refreshToken },
        )

        const { accessToken } = response.data
        localStorage.setItem("accessToken", accessToken)

        // Retry original request with new token
        originalRequest.headers.Authorization = `Bearer ${accessToken}`
        return api(originalRequest)
      } catch (refreshError) {
        // Refresh failed, clear storage and redirect
        localStorage.clear()
        window.location.href = "/login"
        return Promise.reject(refreshError)
      }
    }

    return Promise.reject(error)
  },
)

export default api
