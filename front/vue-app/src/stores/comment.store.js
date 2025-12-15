import { defineStore } from 'pinia'
import { fetchComments, createComment, updateComment, deleteComment, } from '@/services/comment.service'

export const useCommentStore = defineStore('comment', {
  state: () => ({
    comments: [],
    loading: false,
    creating: false,

    page: 0,
    size: 10,
    totalPages: 0,
    totalElements: 0,
  }),

  actions: {
    async loadComments(boardId, postId, page=0) {
      this.loading = true
      try {
        const response = await fetchComments(boardId, postId, page, this.size)
        this.comments = response.data.content
        this.page = response.data.page
        this.totalPages = response.data.totalPages
        this.totalElements = response.data.totalElements
        //console.log('comments response', response.data)
        //console.log('댓글 조회 응답 전체', response.data)
      } catch (error) {
        console.error('댓글 조회 실패', error)
      } finally {
        this.loading = false
      }
    },
    // 댓글 작성
    async submitComment(boardId, postId, content) {
        if (!content.trim()) return
  
        this.creating = true
        try {
          await createComment(boardId, postId, content)
  
          // 가장 안전한 방식: 다시 조회
          await this.loadComments(boardId, postId)
        } catch (error) {
          console.error('댓글 작성 실패', error)
        } finally {
          this.creating = false
        }
      },

      // 댓글 수정
      async updateComment(boardId, postId, commentId, content) {
      if (!content.trim()) return

      try {
        await updateComment(boardId, postId, commentId, content)

        await this.loadComments(boardId, postId, this.page)
      } catch (error) {
        console.error('댓글 수정 실패', error)
      }
    },

    // 댓글 삭제
    async deleteComment(boardId, postId, commentId) {
      try {
        await deleteComment(boardId, postId, commentId)

        await this.loadComments(boardId, postId, this.page)
      } catch (error) {
        console.error('댓글 삭제 실패', error)
      }
    },


  },
})
