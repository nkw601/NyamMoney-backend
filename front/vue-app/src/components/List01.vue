<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">List 01</h4>
    <ul class="space-y-1">
      <li v-for="n in 5" :key="n" class="flex justify-between">
        <span>Item {{ n }}</span>
        <span class="text-muted-foreground">Meta</span>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'List01' })
</script>

<style scoped></style>
