<template>
  <div :class="['rounded-md bg-card', wrapperClass]">
    <slot name="header"></slot>
    <div class="p-4"><slot /></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'UiCard',
  props: { wrapperClass: { type: String, default: 'border border-border' } }
})
</script>

<style scoped></style>
