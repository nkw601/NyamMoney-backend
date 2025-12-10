<template>
  <label class="inline-flex items-center gap-2">
    <input type="checkbox" v-bind="$attrs" class="border border-border rounded" />
    <slot />
  </label>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UiCheckbox' })
</script>

<style scoped></style>
