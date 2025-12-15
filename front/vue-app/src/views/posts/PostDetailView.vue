<template>
  <Layout>
    <div class="p-8">
      <p v-if="loading">불러오는 중...</p>

      <div v-if="post">
        <div class="mb-4">
          <button class="px-3 py-1 border rounded" @click="goList">목록으로</button>
        </div>
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
      <CommentListView :boardId="boardId" :postId="postId" />
      <CommentForm :boardId="boardId" :postId="postId" />
    </div>
  </Layout>

</template>

<script>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { usePostStore } from '@/stores/post.store'
import { storeToRefs } from 'pinia'
import { useCommentStore } from '@/stores/comment.store'
import Layout from '@/components/Layout.vue'
import CommentListView from '../comments/CommentListView.vue'
import CommentForm from '../../components/comments/CommentForm.vue'

export default {
  components: { Layout, CommentListView, CommentForm },

  setup() {
    const route = useRoute()
    const router = useRouter()
    const postStore = usePostStore()
    const commentStore = useCommentStore()
    const { post, loading } = storeToRefs(postStore)

    const boardId = route.params.boardId
    const postId = route.params.postId

    onMounted(() => {
        const { boardId, postId } = route.params
        postStore.loadPostDetail(boardId, postId)
        console.log('params', route.params)
    })

    onMounted(()=>{
      commentStore.loadComments(boardId, postId, 0)
    })

    // 목록으로
    const goList = ()=>{
      router.push(`/boards/${route.params.boardId}`)
    }

    return {
      post, loading, boardId, postId, goList,
    }
  },
}
</script>
