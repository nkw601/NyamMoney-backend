<template>
  <Layout>
    <div class="p-8">
      <!-- 여기서 자유게시판을 보여준다 -->
      <FreeBoard />
    </div>
  </Layout>
</template>

<script>
import Layout from '@/components/Layout.vue'
import FreeBoard from '@/views/boards/FreeBoard.vue'

export default {
  components: {
    Layout,
    FreeBoard,
  },
}
</script>
