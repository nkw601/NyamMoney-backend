<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth.store'

const authStore = useAuthStore()

onMounted(() => {
  authStore.restoreSession()
})
</script>

<style lang="scss">
@import '@/styles/global.scss';
</style>
