<template>
  <div>
    <button
      class="lg:hidden fixed top-4 left-4 z-50 p-2 bg-background rounded-lg shadow-lg border border-border"
      @click="isMobileOpen = !isMobileOpen"
      aria-label="Toggle sidebar"
    >
      ☰
    </button>

    <div
      v-if="isMobileOpen"
      class="fixed inset-0 z-10 bg-black/30 lg:hidden"
      @click="isMobileOpen = false"
    ></div>

    <div
  :class="[
  // 공통
  'flex flex-col bg-card border-r border-border transition-all duration-300 ease-in-out',

  'fixed inset-y-0 left-0 z-20 lg:sticky lg:top-0 lg:h-screen',

  'w-72',

  // 반응형
  isCollapsed ? 'lg:w-[72px]' : 'lg:w-72',

  // 모바일 햄버거
  isMobileOpen ? 'translate-x-0 shadow-2xl' : '-translate-x-full lg:translate-x-0'
]"
>
      <div class="border-b border-border bg-gradient-to-br from-primary/5 to-secondary/5">
        <div :class="['flex h-16 items-center gap-3 px-4', isCollapsed && 'justify-center px-2']">
          <template v-if="!isCollapsed">
            <RouterLink to="/" class="flex items-center gap-2 font-bold">
              <div class="h-8 w-8 rounded-full bg-primary/10 flex items-center justify-center">
                <span class="text-xl">🐱</span>
              </div>
              <span class="text-lg bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">YumMoney</span>
            </RouterLink>
          </template>
          <button
            class="ml-auto h-8 w-8 hover:bg-accent"
            @click="toggleCollapse"
            aria-label="Toggle collapse"
          >
            <span :class="['h-4 w-4 transition-transform', isCollapsed ? 'rotate-180' : '']">◀</span>
            <span class="sr-only">{{ isCollapsed ? 'Expand' : 'Collapse' }} Sidebar</span>
          </button>
        </div>
      </div>

      <div class="flex-1 overflow-auto">
        <nav class="flex-1 space-y-1 px-3 py-4">
          <template v-for="item in navigation" :key="item.name">
            <div class="group relative">
              <RouterLink
                :to="item.href"
                class="flex items-center rounded-lg px-3 py-2.5 text-sm font-medium transition-all duration-200"
                :class="isActive(item.href) ? 'bg-primary text-primary-foreground shadow-sm' : 'text-muted-foreground hover:bg-accent hover:text-accent-foreground'"
              >
                <span :class="['h-4 w-4', (!isCollapsed || isMobileOpen) && 'mr-3']">
                  {{ item.icon }}
                </span>
                <span v-if="!isCollapsed || isMobileOpen">
                  {{ item.name }}
                </span>
              </RouterLink>
              <span
                v-if="isCollapsed && !isMobileOpen"
                class="absolute left-full top-1/2 -translate-y-1/2 ml-2 px-2 py-1 bg-card border border-border rounded text-xs text-muted-foreground opacity-0 group-hover:opacity-100 pointer-events-none z-50 whitespace-nowrap"
              >
                {{ item.name }}
              </span>
            </div>
          </template>
        </nav>
      </div>

      <div class="border-t border-border p-3 bg-muted/30">
        <nav class="space-y-1">
          <template v-for="item in bottomNavigation" :key="item.name">
            <RouterLink :to="item.href" class="flex items-center rounded-lg px-3 py-2.5 text-sm font-medium text-muted-foreground hover:bg-accent hover:text-accent-foreground">
              <span class="mr-3">{{ item.icon }}</span>
              <span>{{ item.name }}</span>
            </RouterLink>
          </template>
        </nav>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

export default defineComponent({
  name: 'Sidebar',
  components: { RouterLink },
  setup() {
    const route = useRoute()
    const isCollapsed = ref(false)
    const isMobileOpen = ref(false)

    const navigation = [
      { name: 'Dashboard', href: '/dashboard', icon: '🏠' },
      { name: 'Analytics', href: '/analytics', icon: '📊' },
      { name: 'Organization', href: '/organization', icon: '🏢' },
      { name: 'Projects', href: '/projects', icon: '📁' },
      { name: 'Transactions', href: '/transactions', icon: '💼' },
      { name: 'Invoices', href: '/invoices', icon: '🧾' },
      { name: 'Payments', href: '/payments', icon: '💳' },
      { name: 'Members', href: '/members', icon: '👥' },
      { name: 'Permissions', href: '/permissions', icon: '🛡️' },
      { name: 'Chat', href: '/chat', icon: '💬' },
      { name: 'Follows', href: '/follows', icon: '🎥' },
    ]

    const bottomNavigation = [
      { name: 'Help', href: '/help', icon: '❓' }
    ]

    function toggleCollapse() {
      isCollapsed.value = !isCollapsed.value
    }

    function isActive(href: string) {
      return route.path === href
    }

    return { navigation, bottomNavigation, isCollapsed, isMobileOpen, toggleCollapse, isActive }
  }
})
</script>

<style scoped>
</style>

