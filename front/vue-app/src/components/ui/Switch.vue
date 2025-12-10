<template>
  <label class="inline-flex items-center cursor-pointer">
    <input type="checkbox" v-model="model" class="sr-only" />
    <span :class="['w-10 h-6 rounded-full transition-colors', model ? 'bg-primary' : 'bg-muted']"></span>
    <slot />
  </label>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue'
export default defineComponent({
  name: 'UiSwitch',
  props: { modelValue: { type: Boolean, default: false } },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const model = ref<boolean>(props.modelValue)
    watch(model, (v) => emit('update:modelValue', v))
    return { model }
  }
})
</script>

<style scoped></style>
