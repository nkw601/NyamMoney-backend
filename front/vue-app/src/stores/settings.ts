// src/stores/settings.ts
import { defineStore } from 'pinia'

export interface UserSettings {
  avatar: string
  fullName: string
  email: string
  phone: string
  timezone: string
  language: string
  currency: string
  dateFormat: string
  fontSize: number
  theme: 'light' | 'dark' | 'system'
  layout: 'default' | 'compact' | 'expanded'
  notifications: {
    email: boolean
    push: boolean
    sms: boolean
    accountActivity: boolean
    newFeatures: boolean
    marketing: boolean
    frequency: 'real-time' | 'daily' | 'weekly'
    quietHoursStart: string
    quietHoursEnd: string
  }
  privacy: {
    analyticsSharing: boolean
    personalizedAds: boolean
    visibility: 'public' | 'private'
    dataRetention: '6-months' | '1-year' | '2-years' | 'indefinite'
  }
}

// 디폴트 값 (초기 진입 시 / localStorage 실패 시 사용)
const defaultSettings: UserSettings = {
  avatar: '',
  fullName: 'Dollar Singh',
  email: 'dollar.singh@example.com',
  phone: '+1 (555) 123-4567',
  timezone: 'utc-8',
  language: 'en',
  currency: 'usd',
  dateFormat: 'mm-dd-yyyy',
  fontSize: 16,
  theme: 'system',
  layout: 'default',
  notifications: {
    email: true,
    push: true,
    sms: false,
    accountActivity: true,
    newFeatures: true,
    marketing: false,
    frequency: 'real-time',
    quietHoursStart: '22:00',
    quietHoursEnd: '07:00',
  },
  privacy: {
    analyticsSharing: true,
    personalizedAds: false,
    visibility: 'public',
    dataRetention: '1-year',
  },
}

// 👉 localStorage에서 읽을 때 항상 try/catch + window 체크
function loadSettings(): UserSettings {
  if (typeof window === 'undefined') {
    return defaultSettings
  }

  try {
    const raw = localStorage.getItem('userSettings')
    if (!raw) return defaultSettings

    const parsed = JSON.parse(raw)

    // 혹시 필드가 추가/변경돼도 기본값이랑 merge 되도록
    return {
      ...defaultSettings,
      ...parsed,
      notifications: {
        ...defaultSettings.notifications,
        ...(parsed.notifications || {}),
      },
      privacy: {
        ...defaultSettings.privacy,
        ...(parsed.privacy || {}),
      },
    }
  } catch (e) {
    console.warn('[settings] localStorage 읽기 실패:', e)
    return defaultSettings
  }
}

// 👉 저장도 마찬가지로 방어적으로
function saveSettings(settings: UserSettings) {
  if (typeof window === 'undefined') return

  try {
    localStorage.setItem('userSettings', JSON.stringify(settings))
  } catch (e) {
    console.warn('[settings] localStorage 저장 실패:', e)
  }
}

export const useSettingsStore = defineStore('settings', {
  state: (): { settings: UserSettings } => ({
    settings: loadSettings(),
  }),
  actions: {
    updateSettings(newSettings: Partial<UserSettings>) {
      this.settings = {
        ...this.settings,
        ...newSettings,
      }
      saveSettings(this.settings)
    },
    updateNotificationSettings(
      notificationSettings: Partial<UserSettings['notifications']>,
    ) {
      this.settings.notifications = {
        ...this.settings.notifications,
        ...notificationSettings,
      }
      saveSettings(this.settings)
    },
    updatePrivacySettings(
      privacySettings: Partial<UserSettings['privacy']>,
    ) {
      this.settings.privacy = {
        ...this.settings.privacy,
        ...privacySettings,
      }
      saveSettings(this.settings)
    },
  },
})