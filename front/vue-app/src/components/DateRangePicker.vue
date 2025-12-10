<template>
  <div class="flex items-center gap-2">
    <input type="date" v-model="start" class="border border-border rounded p-1" />
    <span>-</span>
    <input type="date" v-model="end" class="border border-border rounded p-1" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({
  name: 'DateRangePicker',
  setup() {
    const start = ref('')
    const end = ref('')
    return { start, end }
  },
})
</script>

<style scoped></style>
