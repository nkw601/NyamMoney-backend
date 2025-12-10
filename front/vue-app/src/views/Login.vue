<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-accent via-background to-muted p-4">
    <div class="w-full max-w-md shadow-xl border-2 bg-card rounded-lg">
      <div class="text-center space-y-4 py-6">
        <div class="flex justify-center">
          <div class="w-24 h-24 bg-gradient-to-br from-primary to-secondary rounded-full flex items-center justify-center shadow-lg">
            <span class="text-4xl">🐱</span>
          </div>
        </div>
        <div class="text-3xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">YumMoney</div>
        <div class="text-base text-muted-foreground">Welcome back! Sign in to manage your finances</div>
      </div>
      <form @submit.prevent="submit" class="space-y-4 px-8 pb-8">
        <div class="space-y-2">
          <label class="text-sm font-medium" for="loginId">Login ID</label>
          <input id="loginId" v-model="loginId" type="text" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Enter your login ID" />
        </div>
        <div class="space-y-2">
          <label class="text-sm font-medium" for="password">Password</label>
          <input id="password" v-model="password" type="password" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Enter your password" />
        </div>
        <div class="flex justify-end">
          <router-link to="#" class="text-sm text-primary hover:text-primary/80 font-medium">Forgot password?</router-link>
        </div>
        <div class="flex flex-col space-y-4 pt-4">
          <button type="submit" class="w-full h-11 text-base font-semibold shadow-md hover:shadow-lg transition-shadow bg-primary text-primary-foreground rounded" :disabled="loading">
            {{ loading ? 'Signing in...' : 'Sign In' }}
          </button>
          <div class="text-center text-sm text-muted-foreground">
            Don't have an account?
            <router-link to="/signup" class="text-primary hover:text-primary/80 font-semibold">Sign up</router-link>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

export default defineComponent({
  name: 'LoginView',
  setup() {
    const loginId = ref('')
    const password = ref('')
    const loading = ref(false)
    const router = useRouter()
    const auth = useAuthStore()

    function submit() {
      loading.value = true
      setTimeout(() => {
        loading.value = false
        auth.login({ id: '1', name: loginId.value, email: loginId.value })
        router.push('/dashboard')
      }, 1000)
    }

    return { loginId, password, loading, submit }
  }
})
</script>

<style scoped></style>
