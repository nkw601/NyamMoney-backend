<template>
  <div class="flex items-center gap-2">
    <button @click="setMode('default')" class="px-2 py-1 border border-border rounded">Default</button>
    <button @click="setMode('compact')" class="px-2 py-1 border border-border rounded">Compact</button>
    <button @click="setMode('expanded')" class="px-2 py-1 border border-border rounded">Expanded</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useSettingsStore } from '../stores/settings'

export default defineComponent({
  name: 'ModeToggle',
  setup() {
    const store = useSettingsStore()
    function setMode(mode: 'default' | 'compact' | 'expanded') {
      store.updateSettings({ layout: mode })
    }
    return { setMode }
  },
})
</script>

<style scoped></style>
