<template>
  <div class="border border-border rounded bg-card p-4">
    <h4 class="font-medium mb-2">Upcoming Events</h4>
    <ul class="space-y-2">
      <li v-for="i in 3" :key="i" class="flex justify-between items-center">
        <div>
          <div class="font-medium">Event {{ i }}</div>
          <div class="text-xs text-muted-foreground">Date info</div>
        </div>
        <div class="text-sm text-muted-foreground">Location</div>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({ name: 'UpcomingEvents' })
</script>

<style scoped></style>
