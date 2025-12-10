<template>
  <table class="w-full text-sm">
    <slot />
  </table>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiTable' })
</script>

<style scoped></style>
