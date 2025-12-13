<template>
  <Layout>
    <div class="p-8">
      <BoardPosts :boardId="2" title="질문 게시판" />
    </div>
  </Layout>
</template>

<script>
import Layout from '@/components/Layout.vue'
import BoardPosts from '@/views/boards/BoardPosts.vue'

export default {
  components: {
    Layout,
    BoardPosts,
  },
}
</script>
