import { defineStore } from 'pinia'
import { fetchComments } from '@/services/comment.service'

export const useCommentStore = defineStore('comment', {
  state: () => ({
    comments: [],
    loading: false,
  }),

  actions: {
    async loadComments(boardId, postId) {
      this.loading = true
      try {
        const response = await fetchComments(boardId, postId)
        this.comments = response.data.content
  console.log('comments response', response.data)
      } catch (error) {
        console.error('댓글 조회 실패', error)
      } finally {
        this.loading = false
      }
    },
  },
})
