<template>
  <input type="range" v-bind="$attrs" class="w-full" />
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiSlider' })
</script>

<style scoped></style>
