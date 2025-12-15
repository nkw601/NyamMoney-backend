<template>
  <Layout>
    <div class="p-8 max-w-3xl mx-auto">
      <h1 class="text-xl font-bold mb-6">{{ boardTitle }} 글쓰기</h1>

      <!-- 제목 -->
      <div class="mb-4">
        <input
          v-model="title"
          type="text"
          placeholder="제목을 입력하세요"
          class="w-full p-3 border rounded"
        />
      </div>

      <!-- 내용 -->
      <div class="mb-6">
        <textarea
          v-model="content"
          rows="10"
          placeholder="내용을 입력하세요"
          class="w-full p-3 border rounded resize-none"
        />
      </div>

      <!-- 버튼 영역 -->
      <div class="flex justify-end gap-2">
        <button
          class="px-4 py-2 border rounded"
          @click="goList"
        >
          목록으로
        </button>

        <button
          class="px-4 py-2 bg-orange-500 text-white rounded disabled:opacity-50"
          :disabled="creating"
          @click="submit"
        >
          {{ creating ? '작성 중...' : '등록' }}
        </button>
      </div>
    </div>
  </Layout>
</template>

<script>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { usePostStore } from '@/stores/post.store'
import { storeToRefs } from 'pinia'
import Layout from '@/components/Layout.vue'

export default {
  components: { Layout },

  props: {
    boardId: {
      type: Number,
      required: true,
    },
  },

  setup(props) {
    const router = useRouter()
    const postStore = usePostStore()
    const { creating } = storeToRefs(postStore)

    const title = ref('')
    const content = ref('')

    // 게시판 이름 매핑
    const boardTitle = computed(() => {
      const map = {
        1: '자유게시판',
        2: 'Projects 게시판',
        3: 'Transactions 게시판',
        4: 'Invoices 게시판',
      }
      return map[props.boardId] || '게시판'
    })

    // 게시글 작성
    const submit = async () => {
      if (!title.value.trim() || !content.value.trim()) {
        alert('제목과 내용을 입력해주세요.')
        return
      }

      const result = await postStore.submitPost(
        props.boardId,
        title.value,
        content.value
      )

      // 작성한 게시글 상세로 이동
      router.push({
        name: 'postDetail',
        params: {
          boardId: result.boardId,
          postId: result.postId,
        },
      })
    }

    // 목록으로 이동
    const goList = () => {
      router.push({
        name: 'boardPosts',
        params: {
          boardId: props.boardId,
        },
      })
    }

    return {
      title,
      content,
      creating,
      boardTitle,
      submit,
      goList,
    }
  },
}
</script>

<style scoped>
</style>
