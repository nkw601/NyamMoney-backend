<template>
  <div class="relative inline-block">
    <select v-bind="$attrs" :class="'border border-border rounded px-2 py-1 bg-input text-foreground'">
      <slot />
    </select>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiSelect' })
</script>

<style scoped></style>
