import { defineStore } from 'pinia'
import { fetchPostsByBoard } from '@/services/board.service'

export const useBoardStore = defineStore('board', {
  state: () => ({
    posts: [],
    loading: false,
  }),

  actions: {
    async loadBoardPosts(boardId) {
      this.loading = true
      try {
        const response = await fetchPostsByBoard(boardId)
        this.posts = response.data.content
      } catch (error) {
        console.error('게시글 목록 조회 실패', error)
      } finally {
        this.loading = false
      }
    },
  },
})
