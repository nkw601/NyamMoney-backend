import { defineStore } from 'pinia'
import { fetchChallengeList } from '@/services/challenge.service'

export const useChallengeStore = defineStore('challenge', {
  state: () => ({
    challenges: [],
    loading: false,
  }),

  actions: {
    async loadChallenges() {
      this.loading = true
      try {
        const res = await fetchChallengeList()
        this.challenges = res.data.items
      } catch (e) {
        console.error('챌린지 목록 조회 실패', e)
      } finally {
        this.loading = false
      }
    },


    
  },
})
