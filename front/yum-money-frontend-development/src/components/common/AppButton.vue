<template>
  <button
    :class="['app-button', `app-button--${variant}`, `app-button--${size}`, { 'app-button--loading': loading }]"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <span v-if="loading" class="app-button__spinner"></span>
    <slot v-else />
  </button>
</template>

<script setup lang="ts">
interface Props {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost'
  size?: 'sm' | 'md' | 'lg'
  disabled?: boolean
  loading?: boolean
}

withDefaults(defineProps<Props>(), {
  variant: 'primary',
  size: 'md',
  disabled: false,
  loading: false
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

.app-button {
  @include button-base;
  position: relative;
  
  &--primary {
    background: $color-coral;
    color: $color-white;
    
    &:hover:not(:disabled) {
      background: $color-coral-light;
      transform: translateY(-1px);
    }
  }
  
  &--secondary {
    background: $color-cream;
    color: $color-brown;
    
    &:hover:not(:disabled) {
      background: $color-cream-dark;
      transform: translateY(-1px);
    }
  }
  
  &--outline {
    background: transparent;
    color: $color-brown;
    
    &:hover:not(:disabled) {
      background: $color-cream;
    }
  }
  
  &--ghost {
    background: transparent;
    border: none;
    color: $color-brown;
    
    &:hover:not(:disabled) {
      background: $color-gray-100;
    }
  }
  
  &--sm {
    padding: $spacing-2 $spacing-4;
    font-size: $font-size-sm;
  }
  
  &--md {
    padding: $spacing-3 $spacing-6;
    font-size: $font-size-base;
  }
  
  &--lg {
    padding: $spacing-4 $spacing-8;
    font-size: $font-size-lg;
  }
  
  &--loading {
    pointer-events: none;
  }
  
  &__spinner {
    display: inline-block;
    width: 16px;
    height: 16px;
    border: 2px solid currentColor;
    border-radius: 50%;
    border-top-color: transparent;
    animation: spin 0.6s linear infinite;
  }
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
