<template>
  <div class="mt-8">
    <h2 class="font-semibold mb-4">
      댓글 {{ totalElements }}개  ·  {{ page + 1 }} / {{ totalPages }} 페이지
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

    <!-- ⭐ 페이지네이션 -->
    <div
      v-if="totalPages > 1"
      class="flex justify-center gap-2 mt-6">
      <button v-for="p in totalPages" :key="p"
        @click="goPage(p - 1)"
        :disabled="loading"
        class="px-3 py-1 border rounded"
        :class="{
          'bg-orange-500 text-white': page === p - 1,
        }"
      >
        {{ p }}
      </button>
    </div>
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
    const { comments, loading, page, totalPages, totalElements } = storeToRefs(commentStore)

    onMounted(() => {
      commentStore.loadComments(props.boardId, props.postId)
      console.log('CommentList props', props.boardId, props.postId)
    })

    const goPage = (p)=>{
      commentStore.loadComments(props.boardId, props.postId, p)
    }

    return {
      comments,
      loading,
      page,
      totalPages,
      totalElements,
      goPage,
    }
  },
}
</script>
