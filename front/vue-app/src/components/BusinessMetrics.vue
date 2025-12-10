<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">Business Metrics</h4>
    <div class="grid grid-cols-3 gap-3">
      <div v-for="n in 3" :key="n" class="p-3 bg-white/5 rounded">Metric {{ n }}<div class="text-xl font-semibold">{{ n*10 }}%</div></div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'BusinessMetrics' })
</script>

<style scoped></style>
