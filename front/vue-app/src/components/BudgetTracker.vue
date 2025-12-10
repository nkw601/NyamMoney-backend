<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">Budget Tracker</h4>
    <p class="text-sm text-muted-foreground">Monthly budget spend vs limit</p>
    <div class="mt-3 space-y-2">
      <div v-for="n in 3" :key="n">
        <div class="flex justify-between text-sm">
          <span>Category {{ n }}</span>
          <span>$ {{ (n*100).toFixed(0) }}</span>
        </div>
        <div class="w-full bg-muted/20 rounded-full h-2 overflow-hidden">
          <div class="bg-secondary h-2" :style="{ width: `${n*20}%` }"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'BudgetTracker' })
</script>

<style scoped></style>
