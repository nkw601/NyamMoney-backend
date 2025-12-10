<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-accent via-background to-muted p-4 py-8">
    <div class="w-full max-w-2xl shadow-xl border-2 bg-card rounded-lg">
      <div class="text-center space-y-4 py-6">
        <div class="flex justify-center">
          <div class="w-20 h-20 bg-gradient-to-br from-primary to-secondary rounded-full flex items-center justify-center shadow-lg">
            <span class="text-3xl">🐱</span>
          </div>
        </div>
        <div class="text-3xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">Join YumMoney</div>
        <div class="text-base text-muted-foreground">Create your account and start managing your finances with ease</div>
      </div>
      <form @submit.prevent="submit" class="space-y-5 px-8 pb-8">
        <div class="grid md:grid-cols-2 gap-4">
          <div class="space-y-2">
            <label class="text-sm font-medium" for="loginId">Login ID *</label>
            <input id="loginId" v-model="form.loginId" type="text" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Choose a login ID" />
          </div>
          <div class="space-y-2">
            <label class="text-sm font-medium" for="nickname">Nickname *</label>
            <input id="nickname" v-model="form.nickname" type="text" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Your display name" />
          </div>
        </div>
        <div class="space-y-2">
          <label class="text-sm font-medium" for="email">Email Address *</label>
          <input id="email" v-model="form.email" type="email" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="your@email.com" />
        </div>
        <div class="grid md:grid-cols-2 gap-4">
          <div class="space-y-2">
            <label class="text-sm font-medium" for="password">Password *</label>
            <input id="password" v-model="form.password" type="password" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Create a password" />
          </div>
          <div class="space-y-2">
            <label class="text-sm font-medium" for="confirmPassword">Confirm Password *</label>
            <input id="confirmPassword" v-model="form.confirmPassword" type="password" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Confirm your password" />
          </div>
        </div>
        <div class="space-y-3 pt-2">
          <h3 class="text-sm font-semibold text-foreground flex items-center gap-2">
            <span class="text-primary">👤</span>
            Profile Visibility *
          </h3>
          <div class="flex gap-4">
            <label class="flex items-center space-x-2 cursor-pointer">
              <input type="radio" value="PUBLIC" v-model="form.profileVisibility" class="w-4 h-4 text-primary focus:ring-primary" />
              <span class="text-sm font-medium">Public</span>
            </label>
            <label class="flex items-center space-x-2 cursor-pointer">
              <input type="radio" value="PROTECTED" v-model="form.profileVisibility" class="w-4 h-4 text-primary focus:ring-primary" />
              <span class="text-sm font-medium">Protected</span>
            </label>
          </div>
          <p class="text-xs text-muted-foreground">Public profiles can be viewed by anyone, protected profiles are only visible to approved connections</p>
        </div>
        <div class="pt-2 pb-1">
          <h3 class="text-sm font-semibold text-foreground mb-3 flex items-center gap-2">
            <span class="text-primary">💰</span>
            Budget Settings
          </h3>
          <div class="grid md:grid-cols-2 gap-4">
            <div class="space-y-2">
              <label class="text-sm font-medium" for="monthlyBudget">Monthly Budget *</label>
              <input id="monthlyBudget" v-model="form.monthlyBudget" type="number" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="500000" />
              <p class="text-xs text-muted-foreground">Your total monthly spending limit</p>
            </div>
            <div class="space-y-2">
              <label class="text-sm font-medium" for="triggerBudget">Alert Threshold *</label>
              <input id="triggerBudget" v-model="form.triggerBudget" type="number" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="100000" />
              <p class="text-xs text-muted-foreground">Get notified when spending exceeds this amount</p>
            </div>
          </div>
        </div>
        <div class="flex flex-col space-y-4 pt-4">
          <button type="submit" class="w-full h-11 text-base font-semibold shadow-md hover:shadow-lg transition-shadow bg-primary text-primary-foreground rounded" :disabled="loading">
            {{ loading ? 'Creating Account...' : 'Create Account' }}
          </button>
          <div class="text-center text-sm text-muted-foreground">
            Already have an account?
            <router-link to="/login" class="text-primary hover:text-primary/80 font-semibold">Sign in</router-link>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  name: 'SignupView',
  setup() {
    const router = useRouter()
    const loading = ref(false)
    const form = reactive({
      loginId: '',
      password: '',
      confirmPassword: '',
      nickname: '',
      email: '',
      monthlyBudget: '',
      triggerBudget: '',
      profileVisibility: 'PUBLIC',
    })

    function submit() {
      if (form.password !== form.confirmPassword) {
        alert('Passwords do not match!')
        return
      }
      loading.value = true
      setTimeout(() => {
        loading.value = false
        alert('Account created — please sign in')
        router.push('/login')
      }, 1000)
    }

    return { form, loading, submit }
  }
})
</script>

<style scoped></style>
