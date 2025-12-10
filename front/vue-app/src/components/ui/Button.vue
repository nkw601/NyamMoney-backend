<template>
  <button :class="classes" v-bind="$attrs"><slot /></button>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'UiButton',
  props: {
    variant: { type: String, default: 'default' },
    size: { type: String, default: 'md' }
  },
  setup(props) {
    const classes = computed(() => {
      const base = 'inline-flex items-center justify-center rounded-md font-medium';
      const variants: any = {
        default: 'bg-primary text-primary-foreground',
        ghost: 'bg-transparent',
        outline: 'bg-transparent border border-border'
      }
      const sizes: any = {
        sm: 'px-2 py-1 text-sm',
        md: 'px-3 py-2 text-base',
        lg: 'px-4 py-2 text-lg'
      }
      return [base, variants[props.variant] || variants.default, sizes[props.size] || sizes.md].join(' ')
    })

    return { classes }
  }
})
</script>

<style scoped></style>
