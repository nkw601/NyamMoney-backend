import { defineStore } from 'pinia'
import { fetchPostDetail } from '@/services/post.service'

export const usePostStore = defineStore('post', {
  state: () => ({
    post: null,
    loading: false,
  }),

  actions: {
    async loadPostDetail(boardId, postId) {
      this.loading = true
      this.post = null
      try {
        const response = await fetchPostDetail(boardId, postId)
        this.post = response.data
      } catch (error) {
        console.error('게시글 상세 조회 실패', error)
      } finally {
        this.loading = false
      }
    },
  },
})
