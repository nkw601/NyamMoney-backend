<template>
  <Layout>
    <div>
      <!-- 목록으로 -->
      <button class="mb-4 px-3 py-1 border rounded hover:bg-gray-100"
        @click="goList">목록으로</button>

        
        <!-- 로딩 -->
        <p v-if="loading">불러오는 중...</p>
        
        <!-- 게시글 -->
        <div v-else-if="post">
        <div v-if="post.author?.userId === authStore.userId">
        <button class="text-sm text-gray-400" @click="goEdit">수정</button>
        <button class="px-4 py-2 bg-red-500 text-white rounded" @click="handleDelete">삭제</button>
        </div>
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
import { useAuthStore } from '../../stores/auth'
import { watch } from 'vue'

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
    const authStore = useAuthStore()
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

    const goEdit = () => {
      router.push({
        name: 'postEdit',
        params: {
          boardId: props.boardId,
          postId: props.postId,
        },
      })
    }

    // 없어도 수정 내용 잘 출력됨
    // watch(
    //   ()=>props.postId,
    //   ()=>{
    //     postStore.loadPostDetail(props.boardId, props.postId)
    //   },
    //   {immediate: true}
    // )

    const handleDelete = async () => {
      if (!confirm('게시글을 삭제하시겠습니까?')) return

      await postStore.removePost(props.boardId, props.postId)

      // 삭제 후 게시판 목록으로 이동
      router.push({
        name: 'boardPosts',
        params: { boardId: props.boardId, },
      })
    }

    return {
      post,
      loading,
      goList, goEdit, authStore, handleDelete,
    }
  },
}
</script>

<style scoped>
</style>
