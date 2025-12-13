import api from '@/api/axios'

// 댓글 목록 조회
export function fetchComments(boardId, postId, page=0, size=10){
    return api.get(`/v1/boards/${boardId}/posts/${postId}/comments`,{
        params: { page, size }
    })
}

// 댓글 작성
export function createComment(boardId, postId, content) {
  return api.post(`/v1/boards/${boardId}/posts/${postId}/comments`, {
    content,
  })
}