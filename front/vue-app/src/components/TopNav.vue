<template>
  <header class="sticky top-0 z-40 border-b border-border bg-card/95 backdrop-blur supports-[backdrop-filter]:bg-card/60">
    <div class="container flex h-16 items-center justify-between px-4 md:px-6 max-w-6xl mx-auto">
      <div class="hidden md:block">
        <nav class="flex items-center space-x-2">
          <RouterLink to="/" class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">Home</RouterLink>
          <template v-for="(segment, index) in pathSegments" :key="segment">
            <span class="text-muted-foreground/50">•</span>
            <RouterLink
              :to="'/' + pathSegments.slice(0, index + 1).join('/')"
              class="text-sm font-medium text-foreground hover:text-primary transition-colors"
            >
              {{ capitalize(segment) }}
            </RouterLink>
          </template>
        </nav>
      </div>
      <div class="flex items-center gap-3">
        <!-- 알림 버튼 -->
        <Notifications></Notifications>
        <!-- 테마 토글 버튼 -->
        <button class="p-2 rounded-md hover:bg-accent" aria-label="Toggle theme">🌓</button>

        <!-- 사용자 드롭다운 -->
        <div class="relative">
          <details class="relative">
            <summary class="list-none">
              <button class="relative h-9 w-9 rounded-full ring-offset-background focus-visible:ring-2 focus-visible:ring-primary focus-visible:ring-offset-2">
                <div class="h-9 w-9 rounded-full border-2 border-primary/20 overflow-hidden">
                  <img v-if="settings.avatar" :src="settings.avatar" :alt="settings.fullName" class="w-full h-full object-cover" />
                  <div v-else class="bg-primary text-primary-foreground font-semibold flex items-center justify-center h-full">{{ initials }}</div>
                </div>
              </button>
            </summary>
            <div class="absolute right-0 mt-2 w-56 bg-card border border-border rounded-md shadow-md p-3">
              <div class="flex flex-col space-y-1 mb-2">
                <p class="text-sm font-semibold leading-none">{{ settings.fullName }}</p>
                <p class="text-xs leading-none text-muted-foreground">{{ settings.email }}</p>
              </div>
              <div class="border-t border-border mt-2 pt-2 space-y-1">
                <RouterLink to="/settings" class="block px-2 py-1 hover:bg-accent rounded">Profile</RouterLink>
                <RouterLink to="/settings" class="block px-2 py-1 hover:bg-accent rounded">Settings</RouterLink>
                <button class="w-full text-left px-2 py-1 hover:bg-accent rounded">Log out</button>
              </div>
            </div>
          </details>
        </div>
      </div>
    </div>
  </header>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { useSettingsStore } from '../stores/settings'
import Notifications from './Notifications.vue';

export default defineComponent({
  name: 'TopNav',
  components: { RouterLink, Notifications },
  setup() {
    const route = useRoute()
    const pathSegments = computed(() => route.path.split('/').filter(Boolean))

    const settingsStore = useSettingsStore()
    const settings = settingsStore.settings

    const initials = computed(() =>
      settings.fullName
        .split(' ')
        .map((n) => n[0])
        .join('')
    )

    function capitalize(s: string) {
      return s.charAt(0).toUpperCase() + s.slice(1)
    }

    return { pathSegments, settings, initials, capitalize }
  }
})
</script>

<style scoped>
</style>
