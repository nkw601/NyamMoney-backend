<template>
  <div>
    <div class="flex gap-2 mb-2">
      <button v-for="t in tabs" :key="t" @click="select(t)" :class="['px-3 py-1 rounded', active===t? 'bg-primary text-primary-foreground':'' ]">{{ t }}</button>
    </div>
    <div>
      <slot :name="active" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({
  name: 'UiTabs',
  props: { tabs: { type: Array as () => string[], default: () => [] } },
  setup(props) {
    const active = ref(props.tabs[0] || '')
    function select(t: string) { active.value = t }
    return { active, select }
  }
})
</script>

<style scoped></style>
