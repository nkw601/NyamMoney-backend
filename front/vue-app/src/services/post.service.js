import api from '@/api/axios'

export function fetchPostDetail(boardId, postId) {
  return api.get(`/v1/boards/${boardId}/posts/${postId}`)
}

export function createPost(boardId, title, content) {
  return api.post(`/v1/boards/${boardId}/posts`, {
    title, contentMd: content,
  })
}

// 수정
export const updatePost = (boardId, postId, data) => {
  return api.patch(`/v1/boards/${boardId}/posts/${postId}`, data)
}

// 삭제
export function deletePost(boardId, postId) {
  return api.delete(`/v1/boards/${boardId}/posts/${postId}`)
}

// 게시글 좋아요
export function likePost(boardId, postId) {
  return api.get(`/v1/boards/${boardId}/posts/${postId}/like`)
}
// 좋아요 취소
export function unlikePost(boardId, postId) {
  return api.post(`/v1/boards/${boardId}/posts/${postId}/like`)
}