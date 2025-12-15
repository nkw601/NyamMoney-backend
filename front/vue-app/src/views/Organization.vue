<template>
  <Layout>
    <div class="p-8">
      <div class="mb-6 flex gap-3">
        <button @click="goBoard(1)">자유게시판</button>
        <button @click="goBoard(2)">질문 게시판</button>
        <button @click="goBoard(3)">팁 게시판</button>
        <button @click="goBoard(4)">공지 게시판</button>
      </div>

      <router-view />
    </div>
  </Layout>
</template>

<script>
import Layout from '@/components/Layout.vue'
import BoardPosts from '@/views/boards/BoardPostsView.vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()

    const goBoard = (boardId) => {
      router.push({
        name: 'boardPosts',
        params: { boardId },
      })
    }

    return { goBoard }
  },

  components: {
    Layout,
    BoardPosts,
  },
}
</script>
