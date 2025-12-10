<template>
  <div class="border border-border rounded bg-card p-4">
    <div class="flex items-center gap-3">
      <div class="h-12 w-12 rounded-full bg-primary/10 flex items-center justify-center">JD</div>
      <div>
        <div class="font-medium">Jane Doe</div>
        <div class="text-xs text-muted-foreground">jane@example.com</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'Profile01' })
</script>

<style scoped></style>
