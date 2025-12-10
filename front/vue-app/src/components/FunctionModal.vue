<template>
  <div>
    <button @click="open = true" class="px-3 py-1 border border-border rounded">Open</button>
    <Modal :show="open" @close="open = false">
      <h3 class="text-lg font-semibold">Function</h3>
      <p class="text-sm text-muted-foreground">Arbitrary function modal</p>
    </Modal>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import Modal from './Modal.vue'

export default defineComponent({
  name: 'FunctionModal',
  components: { Modal },
  setup() {
    const open = ref(false)
    return { open }
  },
})
</script>

<style scoped></style>
