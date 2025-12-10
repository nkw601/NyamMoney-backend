<template>
  <div v-if="show" class="fixed bottom-4 right-4 bg-card border border-border rounded p-3 shadow">
    <div class="text-sm">{{ message }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'UiToast',
  props: { show: { type: Boolean, default: false }, message: { type: String, default: '' } }
})
</script>

<style scoped></style>
