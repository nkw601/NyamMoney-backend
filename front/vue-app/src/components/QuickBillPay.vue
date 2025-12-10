<template>
  <div class="border border-border rounded p-4 bg-card">
    <h3 class="font-semibold">Quick Bill Pay</h3>
    <p class="text-sm text-muted-foreground">Pay bills quickly.</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({})
</script>

<style scoped></style>
<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">Quick Bill Pay</h4>
    <div class="space-y-2">
      <button class="w-full border border-border rounded px-3 py-2">Pay Electricity</button>
      <button class="w-full border border-border rounded px-3 py-2">Pay Water</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'QuickBillPay' })
</script>

<style scoped></style>
