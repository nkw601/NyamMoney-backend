<template>
  <div>
    <h1 class="text-xl font-bold mb-4">{{ title }}</h1>

    <button
      class="mb-4 px-4 py-2 bg-orange-500 text-white rounded"
      @click="goNewPost"
    >
      글쓰기
    </button>

    <p v-if="loading">게시글을 불러오는 중...</p>

    <ul v-else>
      <li
        v-for="post in posts"
        :key="post.postId"
        @click="goDetail(post.postId)"
        class="mb-3 p-4 rounded bg-white shadow cursor-pointer"
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
import { useRouter } from 'vue-router'
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
    const router = useRouter()

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

    const goDetail = (postId) => {
      router.push({
        name: 'postDetail',
        params: {
          boardId: props.boardId,
          postId,
        },
      })
    }

    const goNewPost = () => {
      router.push({
        name: 'newPost',
        params: {
          boardId: props.boardId,
        },
      })
    }

    return {
      posts,
      loading,
      goDetail,
      goNewPost,
    }
  },
}
</script>
