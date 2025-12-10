<template>
  <div class="w-full bg-muted/20 rounded-full h-2 overflow-hidden">
    <div class="bg-primary h-2" :style="{ width: value + '%' }"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'UiProgress',
  props: { value: { type: Number, required: true } }
})
</script>

<style scoped></style>
