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

export const useSettingsStore = defineStore('settings', {
  state: (): { settings: UserSettings } => ({
    settings: (typeof window !== 'undefined' && localStorage.getItem('userSettings'))
      ? JSON.parse(localStorage.getItem('userSettings') as string)
      : defaultSettings,
  }),
  actions: {
    updateSettings(newSettings: Partial<UserSettings>) {
      this.settings = { ...this.settings, ...newSettings }
      localStorage.setItem('userSettings', JSON.stringify(this.settings))
    },
    updateNotificationSettings(notificationSettings: Partial<UserSettings['notifications']>) {
      this.settings.notifications = { ...this.settings.notifications, ...notificationSettings }
      localStorage.setItem('userSettings', JSON.stringify(this.settings))
    },
    updatePrivacySettings(privacySettings: Partial<UserSettings['privacy']>) {
      this.settings.privacy = { ...this.settings.privacy, ...privacySettings }
      localStorage.setItem('userSettings', JSON.stringify(this.settings))
    },
  },
})
