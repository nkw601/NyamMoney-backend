import { defineStore } from 'pinia'
import { fetchPostDetail, createPost, updatePost, deletePost,toggleLike, } from '@/services/post.service'

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

      // 🔥 1. UI 즉시 반영 (optimistic)
      const prevLiked = this.post.liked
      const prevCount = this.post.likeCount

      this.post.liked = !prevLiked
      this.post.likeCount += this.post.liked ? 1 : -1

      try {
        // 2️⃣ 서버에 실제 토글 요청
        await toggleLike(boardId, postId)

        // 3️⃣ 백그라운드 동기화 (깜빡임 없음)
        const res = await fetchPostDetail(boardId, postId)
        this.post = res.data
      } catch (error) {
        // ❌ 실패 시 롤백
        this.post.liked = prevLiked
        this.post.likeCount = prevCount

        console.error('좋아요 토글 실패', error)
      }
    },





  },

})
