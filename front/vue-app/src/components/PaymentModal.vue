<template>
  <div>
    <button @click="open = true" class="px-3 py-1 border border-border rounded">Payment</button>
    <Modal :show="open" @close="open = false">
      <h3 class="text-lg font-semibold">Payment</h3>
      <p class="text-sm text-muted-foreground">Make a payment</p>
    </Modal>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import Modal from './Modal.vue'

export default defineComponent({
  name: 'PaymentModal',
  components: { Modal },
  setup() {
    const open = ref(false)
    return { open }
  },
})
</script>

<style scoped></style>
