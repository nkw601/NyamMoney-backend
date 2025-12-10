<template>
  <div class="relative inline-block">
    <div @click="open = !open"><slot name="trigger" /></div>
    <div v-if="open" class="absolute right-0 mt-2 w-48 bg-card border border-border rounded-md shadow z-50">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({ name: 'UiDropdownMenu', setup() { const open = ref(false); return { open } } })
</script>

<style scoped></style>
