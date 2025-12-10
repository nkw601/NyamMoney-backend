<template>
  <div class="border border-border rounded-md bg-card p-4">
    <div class="flex items-center justify-between pb-2 mb-2">
      <h3 class="text-md font-medium">Savings Goals</h3>
      <button class="border border-border rounded-md p-1" aria-label="Add new savings goal">＋</button>
    </div>
    <div class="space-y-4">
      <div v-for="goal in savingsGoals" :key="goal.name" class="space-y-2">
        <div class="flex justify-between text-sm">
          <span>{{ goal.name }}</span>
          <span>${{ goal.current.toLocaleString() }} / ${{ goal.target.toLocaleString() }}</span>
        </div>
        <div class="w-full bg-muted/20 rounded-full h-2 overflow-hidden">
          <div class="bg-primary h-2" :style="{ width: goalPercentage(goal) + '%' }"></div>
        </div>
        <p class="text-xs text-right text-muted-foreground">{{ goalPercentage(goal).toFixed(1) }}% complete</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from 'vue'

export default defineComponent({
  name: 'SavingsGoals',
  setup() {
    const savingsGoals = reactive([
      { name: 'Emergency Fund', current: 10000, target: 25000 },
      { name: 'Vacation', current: 3000, target: 5000 },
      { name: 'New Car', current: 15000, target: 35000 },
    ])

    function goalPercentage(goal: { current: number; target: number }) {
      return (goal.current / goal.target) * 100
    }

    return { savingsGoals, goalPercentage }
  },
})
</script>

<style scoped></style>
