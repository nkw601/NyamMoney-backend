import api from '@/api/axios'

export function fetchComments(boardId, postId){
    return api.get(`/v1/boards/${boardId}/posts/${postId}/comments`)
}


export function createComment(boardId, postId, content) {
  return api.post(`/v1/boards/${boardId}/posts/${postId}/comments`, {
    content,
  })
}