import api from '@/api/axios'

export function fetchComments(boardId, postId){
    return api.get(`/v1/boards/${boardId}/posts/${postId}/comments`)
}