<template>
  <label :for="forAttr" class="text-sm font-medium"><slot /></label>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'UiLabel',
  props: { forAttr: { type: String, default: '' } }
})
</script>

<style scoped></style>
