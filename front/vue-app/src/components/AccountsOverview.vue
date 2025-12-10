<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">Accounts Overview</h4>
    <div class="grid grid-cols-3 gap-3">
      <div v-for="n in 3" :key="n" class="p-3 border border-border rounded">Account {{ n }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'AccountsOverview' })
</script>

<style scoped></style>
