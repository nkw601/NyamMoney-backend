<template>
  <div class="border border-border rounded-md bg-card p-4">
    <div class="mb-3">
      <h3 class="text-lg font-medium">Quick Actions</h3>
    </div>
    <div class="grid gap-4">
      <ActionDialog title="Add Funds" description="Add funds to your account" actionText="Add Funds" />
      <ActionDialog title="Send Money" description="Send money to another account" actionText="Send Money" />
      <ActionDialog title="Top Up" description="Top up your account" actionText="Top Up" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

const ActionDialog = defineComponent({
  name: 'ActionDialog',
  props: {
    title: String,
    description: String,
    actionText: String,
  },
  setup(props) {
    const open = ref(false)
    const amount = ref<number | null>(null)

    function submit() {
      // placeholder action — in real app, wire up form submit
      open.value = false
      amount.value = null
      // could emit or call API
    }

    return { open, amount, submit, props }
  },
  template: `
    <div>
      <button @click="open = true" class="w-full text-left border border-border rounded-md px-3 py-2">{{ props.title }}</button>
      <div v-if="open" class="fixed inset-0 flex items-center justify-center z-50">
        <div class="bg-black/30 absolute inset-0" @click="open = false"></div>
        <div class="bg-card border border-border rounded-md p-4 z-10 w-full max-w-md">
          <h3 class="text-lg font-semibold">{{ props.title }}</h3>
          <p class="text-sm text-muted-foreground">{{ props.description }}</p>
          <div class="grid gap-4 py-4">
            <label class="flex items-center gap-2">
              <span class="w-24 text-right text-sm">Amount</span>
              <input v-model.number="amount" type="number" placeholder="Enter amount" class="flex-1 border border-border rounded-md px-2 py-1" />
            </label>
          </div>
          <div class="flex justify-end gap-2">
            <button @click="open = false" class="px-3 py-1 border border-border rounded">Cancel</button>
            <button @click="submit" class="px-3 py-1 bg-primary text-primary-foreground rounded">{{ props.actionText }}</button>
          </div>
        </div>
      </div>
    </div>
  `,
})

export default defineComponent({
  name: 'QuickActions',
  components: { ActionDialog },
})
</script>

<style scoped></style>
