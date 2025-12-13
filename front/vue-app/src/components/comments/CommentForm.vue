<template>
  <div class="mt-6">
    <textarea
      v-model="content"
      rows="3"
      placeholder="댓글을 입력하세요"
      class="w-full p-3 border rounded resize-none"
       />

    <div class="mt-2 text-right">
      <button
        @click="submit"
        :disabled="creating"
        class="px-4 py-2 bg-orange-500 text-white rounded disabled:opacity-50"
      >
        {{ creating ? '등록 중...' : '댓글 등록' }}
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useCommentStore } from '@/stores/comment.store'
import { storeToRefs } from 'pinia'

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
    const { creating } = storeToRefs(commentStore)

    const content = ref('')

    const submit = async () => {
        console.log('댓글 등록 버튼 클릭')
      await commentStore.submitComment(props.boardId, props.postId, content.value)
      content.value = '' // 입력창 초기화
    }

    return {
      content,
      submit,
      creating,
    }
  },
}
</script>
