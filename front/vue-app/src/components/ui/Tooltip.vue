<template>
  <div class="relative inline-block" @mouseenter="show = true" @mouseleave="show = false">
    <slot name="trigger" />
    <div v-if="show" class="absolute z-50 bg-card border border-border text-sm rounded-md p-2 shadow">
      <slot name="content">{{ content }}</slot>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({
  name: 'UiTooltip',
  props: { content: { type: String, default: '' } },
  setup() { const show = ref(false); return { show } }
})
</script>

<style scoped></style>
