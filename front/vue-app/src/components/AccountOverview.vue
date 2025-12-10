<template>
  <div class="border border-border rounded-md bg-card p-4">
    <div class="mb-3">
      <h3 class="text-lg font-medium">Account Overview</h3>
    </div>
    <div>
      <div class="text-2xl font-semibold mb-4">${{ totalBalance.toFixed(2) }}</div>
      <div class="space-y-2">
        <div v-for="account in accounts" :key="account.name" class="flex justify-between items-center">
          <span class="text-sm text-muted-foreground">{{ account.name }}</span>
          <span class="font-medium">${{ account.balance.toFixed(2) }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, computed } from 'vue'

export default defineComponent({
  name: 'AccountOverview',
  setup() {
    const accounts = reactive([
      { name: 'Checking', balance: 5240.23 },
      { name: 'Savings', balance: 12750.89 },
      { name: 'Investment', balance: 7890.45 },
    ])

    const totalBalance = computed(() => accounts.reduce((sum, a) => sum + a.balance, 0))

    return { accounts, totalBalance }
  },
})
</script>

<style scoped></style>
