import { defineStore } from 'pinia'
import { fetchChallengeList, fetchChallengeDetail, challengeJoin, challengeJoinCancle, 
    createChallenge, } from '@/services/challenge.service'

export const useChallengeStore = defineStore('challenge', {
  state: () => ({
    challenges: [],
    challengeDetail: null,
    loading: false,
    joining: false,
  }),

  actions: {
    async loadChallenges() {
      this.loading = true
      this.challengeDetail = null
      try {
        const res = await fetchChallengeList()
        this.challenges = res.data.items
      } catch (e) {
        console.error('챌린지 목록 조회 실패', e)
      } finally {
        this.loading = false
      }
    },

    async loadChallengeDetail(challengeId) {
      this.loading = true
      this.challengeDetail = null
      try {
        const res = await fetchChallengeDetail(challengeId)
        this.challengeDetail = res.data
      } finally {
        this.loading = false
      }
    },
    // 참여 / 참여취소 분기
    async toggleJoin(challengeId) {
      if (this.joining || !this.challengeDetail) return
      this.joining = true

      try {
        if (this.challengeDetail.isJoined) {
          await challengeJoinCancel(challengeId)
        } else {
          await challengeJoin(challengeId)
        }

        // 항상 상세 재조회로 상태 동기화
        await this.loadChallengeDetail(challengeId)
      } catch (e) {
        console.error('챌린지 참여/취소 실패', e)
      } finally {
        this.joining = false
      }
    },
    // 챌린지 생성
    async createChallenge(payload) {
      this.creating = true
      try {
        const res = await createChallenge(payload)
        return res.data.challengeId // 생성된 ID 반환
      } catch (e) {
        console.error('챌린지 생성 실패', e)
        throw e
      } finally {
        this.creating = false
      }
    },


  },
})
