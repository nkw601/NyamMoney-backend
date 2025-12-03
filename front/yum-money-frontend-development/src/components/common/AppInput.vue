<template>
  <div class="app-input">
    <label v-if="label" :for="inputId" class="app-input__label">
      {{ label }}
      <span v-if="required" class="app-input__required">*</span>
    </label>
    <div class="app-input__wrapper">
      <input
        :id="inputId"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :required="required"
        class="app-input__field"
        @input="handleInput"
        @blur="handleBlur"
      />
    </div>
    <p v-if="error" class="app-input__error">{{ error }}</p>
    <p v-else-if="hint" class="app-input__hint">{{ hint }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  modelValue: string | number
  type?: string
  label?: string
  placeholder?: string
  disabled?: boolean
  required?: boolean
  error?: string
  hint?: string
}

const props = withDefaults(defineProps<Props>(), {
  type: 'text',
  disabled: false,
  required: false
})

const emit = defineEmits<{
  'update:modelValue': [value: string | number]
  blur: []
}>()

const inputId = computed(() => `input-${Math.random().toString(36).substr(2, 9)}`)

function handleInput(event: Event) {
  const target = event.target as HTMLInputElement
  emit('update:modelValue', target.value)
}

function handleBlur() {
  emit('blur')
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables';

.app-input {
  display: flex;
  flex-direction: column;
  gap: $spacing-2;
  
  &__label {
    font-size: $font-size-sm;
    font-weight: $font-weight-semibold;
    color: $color-brown;
  }
  
  &__required {
    color: $color-error;
  }
  
  &__wrapper {
    position: relative;
  }
  
  &__field {
    width: 100%;
    padding: $spacing-3 $spacing-4;
    border: 2px solid $color-brown;
    border-radius: $radius-md;
    font-size: $font-size-base;
    font-family: $font-family-base;
    background: $color-white;
    color: $color-brown-dark;
    transition: border-color $transition-base;
    
    &::placeholder {
      color: $color-gray-400;
    }
    
    &:focus {
      outline: none;
      border-color: $color-coral;
    }
    
    &:disabled {
      background: $color-gray-100;
      cursor: not-allowed;
      opacity: 0.6;
    }
  }
  
  &__error {
    font-size: $font-size-sm;
    color: $color-error;
  }
  
  &__hint {
    font-size: $font-size-sm;
    color: $color-gray-500;
  }
}
</style>
