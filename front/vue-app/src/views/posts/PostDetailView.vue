<template>
  <Layout>
    <div>
      <!-- 목록으로 -->
      <button
        class="mb-4 px-3 py-1 border rounded hover:bg-gray-100"
        @click="goList"
      >
        목록으로
      </button>

      <!-- 로딩 -->
      <p v-if="loading">불러오는 중...</p>

      <!-- 게시글 -->
      <div v-else-if="post">
        <h1 class="text-2xl font-bold mb-2">
          {{ post.title }}
        </h1>

        <p class="text-sm text-gray-500 mb-6">
          {{ post.author?.nickname }} · {{ post.createdAt }}
        </p>

        <div class="mb-8 whitespace-pre-line">
          {{ post.content }}
        </div>

        <p class="text-sm text-gray-400 mb-6">
          댓글 {{ post.commentCount }} · 좋아요 {{ post.likeCount }}
        </p>

        <!-- 댓글 영역 -->
        <CommentListView :boardId="boardId" :postId="postId" />
      </div>
    </div>
  </Layout>
</template>

<script>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { usePostStore } from '@/stores/post.store'
import { storeToRefs } from 'pinia'
import Layout from '@/components/Layout.vue'
import CommentListView from '@/views/comments/CommentListView.vue'

export default {
  components: {
    CommentListView,
  },

  props: {
    boardId: {
      type: Number,
      required: true,
    },
    postId: {
      type: Number,
      required: true,
    },
  },

  setup(props) {
    const router = useRouter()
    const route = useRoute()
    const postStore = usePostStore()
    const { post, loading } = storeToRefs(postStore)

    // 게시글 상세 조회
    onMounted(() => {
      postStore.loadPostDetail(props.boardId, props.postId)
    })

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
      post,
      loading,
      goList,
    }
  },
}
</script>

<style scoped>
</style>
