<template>
  <div class="mt-8">
    <h2 class="font-semibold mb-4">
      댓글 {{ comments.length }}
    </h2>

    <p v-if="loading">댓글을 불러오는 중...</p>
    <p v-else-if="comments.length===0">아직 댓글이 없습니다.</p>
    <ul v-else>
      <li
        v-for="comment in comments"
        :key="comment.commentId"
        class="mb-3 p-3 rounded bg-gray-50"
      >
        <p class="text-sm font-medium">
          {{ comment.author?.nickname || '익명' }}
        </p>
        <p class="text-sm">{{ comment.content }}</p>
        <p class="text-xs text-gray-400">
          {{ comment.createdAt }}
        </p>
      </li>
    </ul>
  </div>
</template>

<script>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useCommentStore } from '@/stores/comment.store'

export default {
  props: {
    boardId: {
      type: [Number, String],
      required: true,
    },
    postId: {
      type: [Number, String],
      required: true,
    },
  },

  setup(props) {
    const commentStore = useCommentStore()
    const { comments, loading } = storeToRefs(commentStore)

    onMounted(() => {
      commentStore.loadComments(props.boardId, props.postId)
      console.log('CommentList props', props.boardId, props.postId)
    })

    return {
      comments,
      loading,
    }
  },
}
</script>
