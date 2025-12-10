<template>
  <div class="min-h-screen flex items-center justify-center bg-background text-foreground">
    <div class="w-full max-w-lg p-6">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({ name: 'AuthLayout' })
</script>

<style scoped></style>
