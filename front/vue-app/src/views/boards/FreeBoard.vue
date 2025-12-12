<template>
  <div>
    <h1 class="text-xl font-bold mb-4">자유게시판</h1>

    <p v-if="loading">게시글을 불러오는 중...</p>

    <ul v-else>
      <li
        v-for="post in posts"
        :key="post.postId"
        class="mb-3 p-4 rounded bg-white shadow"
      >
        <h2 class="font-semibold">{{ post.title }}</h2>
        <p class="text-sm text-gray-500">
          {{ post.authorNickname }} · {{ post.createdAt }}
        </p>
      </li>
    </ul>
  </div>
</template>

<script>
import { onMounted, watch } from 'vue'
import { useBoardStore } from '@/stores/board.store'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

export default {
  setup() {
    const boardStore = useBoardStore()
    const authStore = useAuthStore()

    const { posts, loading } = storeToRefs(boardStore)
    const { isAuthenticated } = storeToRefs(authStore)

    // 로그인 상태가 true가 되는 순간 API 호출
    watch(isAuthenticated, (loggedIn) => {
      if (loggedIn) {
        boardStore.loadBoardPosts(1)
      }
    }, { immediate: true })

    return {
      posts,
      loading,
    }
  },
}
</script>
