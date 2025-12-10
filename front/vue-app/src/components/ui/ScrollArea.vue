<template>
  <div class="overflow-auto" :style="style"><slot /></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiScrollArea', props: { style: { type: String, default: '' } } })
</script>

<style scoped></style>
