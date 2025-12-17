<template>
  <header
    class="sticky top-0 z-40 border-b border-border bg-card/95 backdrop-blur supports-[backdrop-filter]:bg-card/60"
  >
    <div
      class="container flex h-16 items-center justify-end lg:justify-between px-4 lg:px-6 max-w-6xl mx-auto pl-16 lg:pl-6"
    >
      <!-- 브레드크럼 -->
      <div class="hidden lg:block">
        <nav class="flex items-center space-x-2">
          <RouterLink
            to="/"
            class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors"
          >
            Home
          </RouterLink>
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

      <!-- 오른쪽 아이콘 영역 -->
      <div class="flex items-center gap-3">
        <Notifications />
        <button class="p-2 rounded-md hover:bg-accent" aria-label="Toggle theme">🌓</button>

        <!-- 아바타 + 드롭다운 -->
        <div class="relative">
          <button
            class="relative h-9 w-9 rounded-full ring-offset-background focus-visible:ring-2 focus-visible:ring-primary focus-visible:ring-offset-2"
            @click="isMenuOpen = !isMenuOpen"
          >
            <div class="h-9 w-9 rounded-full border-2 border-primary/20 overflow-hidden">
              <img
                v-if="avatar"
                :src="avatar"
                :alt="displayName"
                class="w-full h-full object-cover"
              />
              <div
                v-else
                class="bg-primary text-primary-foreground font-semibold flex items-center justify-center h-full"
              >
                {{ initials }}
              </div>
            </div>
          </button>

          <div
            v-if="isMenuOpen"
            class="absolute right-0 mt-2 w-56 bg-card border border-border rounded-md shadow-md p-3 z-50"
          >
            <div class="flex flex-col space-y-1 mb-2">
              <p class="text-sm font-semibold leading-none">{{ displayName }}님, 안녕하세요.</p>
              <p class="text-xs leading-none text-muted-foreground">
                {{ email || ' ' }}
              </p>
            </div>
            <div class="border-t border-border mt-2 pt-2 space-y-1">
              <RouterLink
                :to="{ name: 'UserProfile', query: { userId: userId } }"
                class="block px-2 py-1 hover:bg-accent rounded"
                @click="closeMenu"
              >
                My Page
              </RouterLink>
              <button
                class="w-full text-left px-2 py-1 hover:bg-accent rounded"
                @click="handleLogout"
              >
                Log out
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import { defineComponent, computed, ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import Notifications from './Notifications.vue'

export default defineComponent({
  name: 'TopNav',
  components: { RouterLink, Notifications },
  setup() {
    const route = useRoute()
    const router = useRouter()
    const pathSegments = computed(() => route.path.split('/').filter(Boolean))

    const auth = useAuthStore()

    const displayName = computed(() => `${auth.nickname} (${auth.loginId})`)
    const userId = computed(() => auth.userId)

    // ✅ 이니셜은 닉네임 앞 2글자
    const initials = computed(() => auth.nickname.slice(0, 2).toUpperCase())

    function capitalize(s) {
      return s.charAt(0).toUpperCase() + s.slice(1)
    }

    const isMenuOpen = ref(false)

    const closeMenu = () => {
      isMenuOpen.value = false
    }

    const onClickOutside = (event) => {
      const target = event.target
      if (!target.closest('.relative')) {
        isMenuOpen.value = false
      }
    }

    onMounted(() => {
      window.addEventListener('click', onClickOutside)
    })

    onBeforeUnmount(() => {
      window.removeEventListener('click', onClickOutside)
    })

    const handleLogout = async () => {
      closeMenu()
      await auth.logout()
      router.push('/')
    }

    return {
      pathSegments,
      capitalize,
      isMenuOpen,
      closeMenu,
      handleLogout,
      displayName,
      initials,
      userId,
    }
  },
})
</script>
