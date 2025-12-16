<template>
  <Layout>
    <div class="p-8 max-w-3xl mx-auto">
      <h1 class="text-xl font-bold mb-6">게시글 수정</h1>

      <p v-if="loading">불러오는 중...</p>

      <div v-else>
        <!-- 제목 -->
        <input
          v-model="title"
          type="text"
          class="w-full p-3 border rounded mb-4"
        />

        <!-- 내용 -->
        <textarea
          v-model="content"
          rows="10"
          class="w-full p-3 border rounded resize-none mb-6"
        />

        <div class="flex justify-end gap-2">
          <button class="px-4 py-2 border rounded" @click="goDetail">
            취소
          </button>

          <button
            class="px-4 py-2 bg-orange-500 text-white rounded disabled:opacity-50"
            :disabled="updating"
            @click="submit"
          >
            {{ updating ? '수정 중...' : '저장' }}
          </button>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { usePostStore } from '@/stores/post.store'
import { storeToRefs } from 'pinia'
import Layout from '@/components/Layout.vue'

export default {
  components: { Layout },

  props: {
    boardId: Number,
    postId: Number,
  },

  setup(props) {
    const router = useRouter()
    const postStore = usePostStore()
    const { post, loading, updating } = storeToRefs(postStore)

    const title = ref('')
    const content = ref('')

    onMounted(async () => {
      await postStore.loadPostDetail(props.boardId, props.postId)
      title.value = post.value.title
      content.value = post.value.content
    })

    const submit = async () => {
      const result = await postStore.updatePost(
        props.boardId,
        props.postId,
        title.value,
        content.value
      )

      router.push({
        name: 'postDetail',
        params: {
          boardId: result.boardId,
          postId: result.postId,
        },
      })
    }

    const goDetail = () => {
      router.push({
        name: 'postDetail',
        params: {
          boardId: props.boardId,
          postId: props.postId,
        },
      })
    }

    return {
      title,
      content,
      loading,
      updating,
      submit,
      goDetail,
    }
  },
}
</script>
