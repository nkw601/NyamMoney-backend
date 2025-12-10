<template>
  <div class="border border-border rounded-md bg-card p-4">
    <h3 class="text-lg font-medium mb-2">Financial Overview</h3>
    <div class="h-[220px] w-full">
      <svg viewBox="0 0 600 200" preserveAspectRatio="none" class="w-full h-full">
        <!-- grid lines -->
        <g stroke="#eee">
          <line v-for="i in 4" :key="i" :y1="(i-1)*50" :y2="(i-1)*50" x1="0" x2="600" />
        </g>
        <!-- expenses line -->
        <polyline :points="expensesPoints" fill="none" stroke="#82ca9d" stroke-width="2" />
        <!-- income line -->
        <polyline :points="incomePoints" fill="none" stroke="#8884d8" stroke-width="2" />
        <!-- labels -->
        <g class="text-xs" fill="#666">
          <text v-for="(d, i) in data" :key="d.month" :x="(i*100)+20" y="195">{{ d.month }}</text>
        </g>
      </svg>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'FinancialChart',
  setup() {
    const data = [
      { month: 'Jan', income: 2000, expenses: 1800 },
      { month: 'Feb', income: 2200, expenses: 1900 },
      { month: 'Mar', income: 2400, expenses: 2000 },
      { month: 'Apr', income: 2600, expenses: 2200 },
      { month: 'May', income: 2800, expenses: 2400 },
      { month: 'Jun', income: 3000, expenses: 2600 },
    ]

    // map values to svg points (600x200 viewBox)
    function mapPoints(key: 'income' | 'expenses') {
      const max = Math.max(...data.map((d) => d.income), ...data.map((d) => d.expenses))
      return data
        .map((d, i) => {
          const x = i * 100 + 20
          const y = 180 - (d[key] / max) * 150
          return `${x},${y}`
        })
        .join(' ')
    }

    const incomePoints = computed(() => mapPoints('income'))
    const expensesPoints = computed(() => mapPoints('expenses'))

    return { data, incomePoints, expensesPoints }
  },
})
</script>

<style scoped></style>
