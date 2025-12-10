<template>
  <div class="shadow-sm border-border/50 border rounded-md bg-card p-4">
    <div class="flex items-center justify-between mb-2">
      <h3 class="text-sm font-semibold text-foreground">Recent Transactions</h3>
    </div>
    <div class="space-y-4">
      <div
        v-for="transaction in transactions.slice(0,3)"
        :key="transaction.id"
        class="flex items-center p-2 rounded-lg hover:bg-accent/50 transition-colors"
      >
        <div class="flex-1">
          <p class="text-sm font-medium text-foreground">{{ transaction.name }}</p>
          <p class="text-xs text-muted-foreground mt-0.5">{{ transaction.date }}</p>
        </div>
        <div class="flex items-center gap-1">
          <span :class="['text-sm font-semibold', transaction.type === 'income' ? 'text-primary' : 'text-secondary']">
            {{ transaction.type === 'income' ? '+' : '-' }}${{ Math.abs(transaction.amount).toFixed(2) }}
          </span>
          <span class="h-4 w-4">
            {{ transaction.type === 'income' ? '⬆️' : '⬇️' }}
          </span>
        </div>
      </div>
    </div>
    <button class="w-full mt-4 shadow-sm bg-transparent border border-border rounded-md py-2">View All Transactions</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from 'vue'

export default defineComponent({
  name: 'RecentTransactions',
  setup() {
    const transactions = reactive([
      { id: 1, name: 'Amazon.com', amount: -129.99, date: '2023-07-15', type: 'expense' },
      { id: 2, name: 'Whole Foods Market', amount: -89.72, date: '2023-07-10', type: 'expense' },
      { id: 3, name: 'Netflix Subscription', amount: -15.99, date: '2023-07-05', type: 'expense' },
      { id: 4, name: 'Freelance Payment', amount: 750, date: '2023-07-12', type: 'income' },
      { id: 5, name: 'Gas Station', amount: -45.5, date: '2023-07-18', type: 'expense' },
    ])

    return { transactions }
  },
})
</script>

<style scoped></style>
