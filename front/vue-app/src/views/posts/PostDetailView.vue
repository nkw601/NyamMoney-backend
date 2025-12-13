<template>
  <Layout>
    <div class="p-8">
      <p v-if="loading">불러오는 중...</p>

      <div v-if="post">
        <h1 class="text-2xl font-bold mb-2">{{ post.title }}</h1>

        <p class="text-sm text-gray-500 mb-4">
          {{ post.author?.nickname }} · {{ post.createdAt }}
        </p>

        <div class="mb-6">
          {{ post.content }}
        </div>

        <p class="text-sm text-gray-400">
          댓글 {{ post.commentCount }} · 좋아요 {{ post.likeCount }}
        </p>
      </div>
      <CommentListView
        :boardId="boardId"
        :postId="postId"
      />
    </div>
  </Layout>

</template>

<script>
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { usePostStore } from '@/stores/post.store'
import { storeToRefs } from 'pinia'
import Layout from '@/components/Layout.vue'
import CommentListView from '../comments/CommentListView.vue'

export default {
  components: { Layout, CommentListView },

  setup() {
    const route = useRoute()
    const postStore = usePostStore()
    const { post, loading } = storeToRefs(postStore)

    const boardId = route.params.boardId
    const postId = route.params.postId

    onMounted(() => {
        const { boardId, postId } = route.params
        postStore.loadPostDetail(boardId, postId)
        console.log('params', route.params)
    })

    return {
      post,
      loading,
      boardId,
      postId,
    }
  },
}
</script>
