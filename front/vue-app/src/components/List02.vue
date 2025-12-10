<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">List 02</h4>
    <ul class="space-y-1">
      <li v-for="n in 3" :key="n" class="flex justify-between">
        <span>Row {{ n }}</span>
        <span class="text-muted-foreground">Info</span>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'List02' })
</script>

<style scoped></style>
