<template>
  <div :class="['app-card', { 'app-card--clickable': clickable, 'app-card--hover': hover }]" @click="handleClick">
    <slot />
  </div>
</template>

<script setup lang="ts">
interface Props {
  clickable?: boolean
  hover?: boolean
}

withDefaults(defineProps<Props>(), {
  clickable: false,
  hover: false
})

const emit = defineEmits<{
  click: [event: MouseEvent]
}>()

function handleClick(event: MouseEvent) {
  emit('click', event)
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables';
@import '@/styles/mixins';

.app-card {
  @include card;
  
  &--clickable {
    cursor: pointer;
    transition: transform $transition-base, box-shadow $transition-base;
  }
  
  &--hover:hover {
    transform: translateY(-2px);
    box-shadow: $shadow-lg;
  }
}
</style>
