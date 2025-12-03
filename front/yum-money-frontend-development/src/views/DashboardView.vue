<template>
  <div class="dashboard-view">
    <h1>Dashboard (Coming Soon)</h1>
    <p>Welcome, {{ authStore.user?.nickname }}!</p>
    <AppButton @click="handleLogout" variant="outline">Logout</AppButton>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth.store'
import AppButton from '@/components/common/AppButton.vue'

const router = useRouter()
const authStore = useAuthStore()

async function handleLogout() {
  await authStore.logout()
  router.push('/login')
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables';

.dashboard-view {
  padding: $spacing-8;
}
</style>
