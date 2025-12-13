<template>
  <div class="min-h-full bg-background text-foreground flex">
    <!-- 사이드바: 반응형/sticky는 Sidebar.vue 안에서 처리 -->
    <Sidebar />

    <!-- 오른쪽 영역 -->
    <div class="flex-1 flex flex-col">
      <TopNav />
      <main class="flex-1 bg-background">
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