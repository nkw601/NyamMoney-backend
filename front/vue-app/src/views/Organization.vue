<template>
  <Layout>
    <div class="p-8">
      <BoardPosts :boardId="1" title="자유 게시판" />
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
