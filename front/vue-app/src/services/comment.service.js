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

// 댓글 수정
export function updateComment(boardId, postId, commentId, content) {
  return api.patch(`/v1/boards/${boardId}/posts/${postId}/comments/${commentId}`, { content })
}

// 댓글 삭제
export function deleteComment(boardId, postId, commentId) {
    return api.delete(`/v1/boards/${boardId}/posts/${postId}/comments/${commentId}`)
}
