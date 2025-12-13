<template>
  <input
    v-bind="$attrs"
    :class="classes"
    :value="modelValue"
    @input="onInput"
  />
</template>

<script>
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'UiInput',
  inheritAttrs: false,
  props: {
    modelValue: { type: [String, Number], default: '' },
    variant: { type: String, default: 'default' },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const classes = computed(
      () => 'border border-border rounded-md px-2 py-1 bg-input text-foreground'
    )

    const onInput = (e) => {
      emit('update:modelValue', e.target.value)
    }

    return { classes, onInput }
  },
})
</script>

<style scoped></style>