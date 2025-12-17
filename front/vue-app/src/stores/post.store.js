import { defineStore } from 'pinia'
import { fetchPostDetail, createPost, updatePost, deletePost, unlikePost, likePost, } from '@/services/post.service'

export const usePostStore = defineStore('post', {
  state: () => ({
    post: null,
    loading: false,
    creating: false,
    updating: false,
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

    // 게시글 수정
    async updatePost(boardId, postId, title, content) {
      this.updating = true
      try {
        const res = await updatePost(boardId, postId, { 
          title,
          contentMd: content,
        })
        this.post = res.data
        return res.data
      } finally {
        this.updating = false
      }
    },

    // 삭제
    async removePost(boardId, postId) {
      await deletePost(boardId, postId)
      this.post = null
    },

    // 좋아요 등록, 삭제
    async toggleLike(boardId, postId) {
      if (!this.post) return

      if (this.post.liked) {
        await unlikePost(boardId, postId)
        this.post.liked = false
        this.post.likeCount--
      } else {
        await likePost(boardId, postId)
        this.post.liked = true
        this.post.likeCount++
      }
    },



  },

})
