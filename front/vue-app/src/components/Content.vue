<template>
  <div class="prose max-w-none">
    <slot />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'Content' })
</script>

<style scoped></style>
