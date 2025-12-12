import api from '@/api/axios'

/**
 * 게시판별 게시글 목록 조회
 * @param {number} boardId
 */
export function fetchPostsByBoard(boardId) {
  return api.get(`/v1/boards/${boardId}/post`)
}
