<template>
  <div class="relative">
    <button @click="isOpen = !isOpen" class="p-2 rounded-md hover:bg-accent">
      🔔
      <span class="absolute top-0 right-0 h-2 w-2 bg-red-500 rounded-full" />
    </button>

    <div v-if="isOpen" class="absolute right-0 mt-2 w-96 z-50">
      <div class="border border-border rounded-md bg-card shadow">
        <div class="flex items-center justify-between p-3 border-b border-border">
          <h4 class="text-sm font-medium">Notifications</h4>
          <button @click="isOpen = false" class="p-1">✕</button>
        </div>
        <div class="p-3 max-h-[400px] overflow-auto space-y-4">
          <div v-for="n in notifications" :key="n.id" class="border rounded-md p-3 bg-white/5">
            <div class="flex items-start space-x-4">
              <div :class="['p-2 rounded-full bg-opacity-10', n.color]">
                <span class="inline-block">{{ n.emoji }}</span>
              </div>
              <div class="flex-1 space-y-1">
                <p class="text-sm font-medium leading-none">{{ n.title }}</p>
                <p class="text-sm text-muted-foreground">{{ n.message }}</p>
                <p class="text-xs text-muted-foreground">{{ n.date }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from 'vue'

export default defineComponent({
  name: 'Notifications',
  setup() {
    const isOpen = ref(false)
    const notifications = reactive([
      { id: 1, title: 'New Feature', message: 'Check out our new budget tracking tool!', date: '2023-07-15', emoji: 'ℹ️', color: 'text-blue-500' },
      { id: 2, title: 'Account Alert', message: 'Unusual activity detected on your account.', date: '2023-07-14', emoji: '⚠️', color: 'text-yellow-500' },
      { id: 3, title: 'Payment Due', message: 'Your credit card payment is due in 3 days.', date: '2023-07-13', emoji: '💳', color: 'text-red-500' },
      { id: 4, title: 'Investment Update', message: 'Your investment portfolio has grown by 5% this month.', date: '2023-07-12', emoji: '📈', color: 'text-green-500' },
      { id: 5, title: 'New Offer', message: "You're eligible for a new savings account with higher interest!", date: '2023-07-11', emoji: '🎁', color: 'text-purple-500' },
    ])

    return { isOpen, notifications }
  },
})
</script>

<style scoped></style>
