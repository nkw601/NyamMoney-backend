import { defineStore } from 'pinia'
import { fetchPostDetail, createPost, } from '@/services/post.service'

export const usePostStore = defineStore('post', {
  state: () => ({
    post: null,
    loading: false,
    creating: false,
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

    // 게시물 작성
    async submitPost(boardId, title, content) {
      if (!title.trim() || !content.trim()) return

      this.creating = true
      try {
        const response = await createPost(boardId, title, content)

        // response 명세 그대로 반환
        return response.data
      } catch (error) {
        console.error('게시글 작성 실패', error)
        throw error
      } finally {
        this.creating = false
      }
    },



  },

})
