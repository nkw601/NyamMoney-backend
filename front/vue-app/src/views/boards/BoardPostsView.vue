<template>
  <div>
    <h1 class="text-xl font-bold mb-4">{{ title }}</h1>

    <p v-if="loading">게시글을 불러오는 중...</p>

    <ul v-else>
      <li
        v-for="post in posts"
        :key="post.postId"
        class="mb-3 p-4 rounded bg-white shadow"
      >
        <h2 class="font-semibold">{{ post.title }}</h2>
        <p class="text-sm text-gray-500">
          댓글 {{ post.commentCount }} · 좋아요 {{ post.likeCount }}
        </p>
        <p class="text-xs text-gray-400">
          {{ post.createdAt }}
        </p>
      </li>
    </ul>
  </div>
</template>

<script>
import { watch } from 'vue'
import { useBoardStore } from '@/stores/board.store'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

export default {
  props: {
    boardId: {
      type: Number,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
  },

  setup(props) {
    const boardStore = useBoardStore()
    const authStore = useAuthStore()

    const { posts, loading } = storeToRefs(boardStore)
    const { isAuthenticated } = storeToRefs(authStore)

    watch(
      () => isAuthenticated.value,
      (loggedIn) => {
        if (loggedIn) {
          boardStore.loadBoardPosts(props.boardId)
        }
      },
      { immediate: true }
    )

    return {
      posts,
      loading,
    }
  },
}
</script>
