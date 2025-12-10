<template>
  <div class="relative inline-block">
    <div @click="open = !open"><slot name="trigger" /></div>
    <div v-if="open" class="absolute z-50 bg-card border border-border rounded-md p-2 shadow mt-2">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({ name: 'UiPopover', setup() { const open = ref(false); return { open } } })
</script>

<style scoped></style>
