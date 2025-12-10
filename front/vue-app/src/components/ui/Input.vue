<template>
  <input v-bind="$attrs" :class="classes" />
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'UiInput',
  props: {
    variant: { type: String, default: 'default' }
  },
  setup() {
    const classes = computed(() => 'border border-border rounded-md px-2 py-1 bg-input text-foreground')
    return { classes }
  }
})
</script>

<style scoped></style>
