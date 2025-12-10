<template>
  <div>
    <button @click="open = true" class="px-3 py-1 border border-border rounded">Add Money</button>
    <Modal :show="open" @close="open = false">
      <h3 class="text-lg font-semibold">Add Money</h3>
      <p class="text-sm text-muted-foreground">Add funds to your account</p>
      <div class="mt-3">
        <label class="block text-sm">Amount</label>
        <input v-model.number="amount" type="number" class="w-full border border-border rounded p-2" />
      </div>
      <div class="mt-3 text-right">
        <button @click="submit" class="px-3 py-1 bg-primary text-primary-foreground rounded">Add Funds</button>
      </div>
    </Modal>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import Modal from './Modal.vue'

export default defineComponent({
  name: 'AddMoneyModal',
  components: { Modal },
  setup() {
    const open = ref(false)
    const amount = ref<number | null>(null)
    function submit() {
      open.value = false
      amount.value = null
    }
    return { open, amount, submit }
  },
})
</script>

<style scoped></style>
