<template>
  <!-- 화면 높이에 딱 맞게, 바깥은 스크롤 안 되게 고정 -->
  <div class="h-screen bg-background text-foreground flex overflow-hidden">
    <!-- 왼쪽 사이드바 -->
    <Sidebar />

    <!-- 오른쪽 영역: 이 안에서만 스크롤 -->
    <div class="flex-1 flex flex-col">
      <TopNav />
      <main class="flex-1 overflow-y-auto overflow-x-hidden bg-background">
        <slot />
      </main>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Sidebar from './Sidebar.vue'
import TopNav from './TopNav.vue'

export default defineComponent({
  components: { Sidebar, TopNav },
})
</script>

<style scoped>
</style>