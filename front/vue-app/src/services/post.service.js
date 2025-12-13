import api from '@/api/axios'

export function fetchPostDetail(boardId, postId) {
  return api.get(`/v1/boards/${boardId}/posts/${postId}`)
}
