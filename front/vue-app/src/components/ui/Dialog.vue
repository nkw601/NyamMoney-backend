<template>
  <div v-if="show" class="fixed inset-0 flex items-center justify-center z-50">
    <div class="absolute inset-0 bg-black/30" @click="$emit('close')"></div>
    <div class="bg-card border border-border rounded p-4 z-10 w-full max-w-lg">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiDialog', props: { show: { type: Boolean, required: true } } })
</script>

<style scoped></style>
