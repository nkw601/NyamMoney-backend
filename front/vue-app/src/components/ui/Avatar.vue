<template>
  <div :class="['inline-flex items-center justify-center rounded-full overflow-hidden', sizeClass, className]">
    <img v-if="src" :src="src" :alt="alt" class="w-full h-full object-cover" />
    <div v-else class="w-full h-full flex items-center justify-center bg-primary text-primary-foreground font-semibold">{{ initials }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'UiAvatar',
  props: {
    src: { type: String, default: '' },
    alt: { type: String, default: '' },
    name: { type: String, default: '' },
    size: { type: String, default: 'md' },
    className: { type: String, default: '' }
  },
  setup(props) {
    const initials = computed(() => (props.name || 'U').split(' ').map(s => s[0]).join(''))
    const sizeClass = computed(() => ({ sm: 'w-8 h-8', md: 'w-9 h-9', lg: 'w-12 h-12' }[props.size] || 'w-9 h-9'))
    return { initials, sizeClass }
  }
})
</script>

<style scoped></style>
