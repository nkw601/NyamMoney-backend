<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">List 03</h4>
    <ul class="space-y-1">
      <li v-for="n in 4" :key="n" class="flex justify-between">
        <span>Entry {{ n }}</span>
        <span class="text-muted-foreground">Details</span>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'List03' })
</script>

<style scoped></style>
