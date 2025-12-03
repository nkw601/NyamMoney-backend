<template>
  <div class="signup-view">
    <div class="signup-container">
      <div class="signup-header">
        <img src="/logo.png" alt="YumMoney" class="logo" />
        <h1>Join YumMoney</h1>
        <p>Start your lucky finance journey</p>
      </div>

      <form @submit.prevent="handleSignup" class="signup-form">
        <AppInput
          v-model="formData.loginId"
          label="Login ID"
          placeholder="Choose a login ID"
          required
          :error="errors.loginId"
        />

        <AppInput
          v-model="formData.email"
          type="email"
          label="Email"
          placeholder="your@email.com"
          required
          :error="errors.email"
        />

        <AppInput
          v-model="formData.nickname"
          label="Nickname"
          placeholder="How should we call you?"
          required
          :error="errors.nickname"
        />

        <AppInput
          v-model="formData.password"
          type="password"
          label="Password"
          placeholder="Create a strong password"
          required
          :error="errors.password"
        />

        <AppInput
          v-model="confirmPassword"
          type="password"
          label="Confirm Password"
          placeholder="Confirm your password"
          required
          :error="errors.confirmPassword"
        />

        <AppButton
          type="submit"
          variant="primary"
          size="lg"
          :loading="isLoading"
          class="signup-button"
        >
          Create Account
        </AppButton>

        <p class="login-link">
          Already have an account?
          <router-link to="/login">Sign in</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth.store'
import AppInput from '@/components/common/AppInput.vue'
import AppButton from '@/components/common/AppButton.vue'

const router = useRouter()
const authStore = useAuthStore()

const formData = ref({
  loginId: '',
  email: '',
  nickname: '',
  password: ''
})
const confirmPassword = ref('')
const isLoading = ref(false)
const errors = ref<Record<string, string>>({})

function validateForm(): boolean {
  errors.value = {}

  if (!formData.value.loginId) {
    errors.value.loginId = 'Login ID is required'
  }
  if (!formData.value.email) {
    errors.value.email = 'Email is required'
  } else if (!/\S+@\S+\.\S+/.test(formData.value.email)) {
    errors.value.email = 'Email is invalid'
  }
  if (!formData.value.nickname) {
    errors.value.nickname = 'Nickname is required'
  }
  if (!formData.value.password) {
    errors.value.password = 'Password is required'
  } else if (formData.value.password.length < 6) {
    errors.value.password = 'Password must be at least 6 characters'
  }
  if (!confirmPassword.value) {
    errors.value.confirmPassword = 'Please confirm your password'
  } else if (formData.value.password !== confirmPassword.value) {
    errors.value.confirmPassword = 'Passwords do not match'
  }

  return Object.keys(errors.value).length === 0
}

async function handleSignup() {
  if (!validateForm()) return

  isLoading.value = true
  try {
    await authStore.signup(formData.value)
    router.push('/dashboard')
  } catch (error: any) {
    errors.value.loginId = error.response?.data?.message || 'Signup failed. Please try again.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables';
@import '@/styles/mixins';

.signup-view {
  min-height: 100vh;
  @include flex-center;
  padding: $spacing-4;
  background: linear-gradient(135deg, $color-cream 0%, $color-off-white 100%);
}

.signup-container {
  width: 100%;
  max-width: 420px;
  background: $color-white;
  border-radius: $radius-lg;
  border: 3px solid $color-brown;
  box-shadow: $shadow-xl;
  padding: $spacing-8;
}

.signup-header {
  text-align: center;
  margin-bottom: $spacing-8;

  .logo {
    width: 80px;
    height: 80px;
    margin-bottom: $spacing-4;
  }

  h1 {
    font-size: $font-size-3xl;
    color: $color-brown;
    margin-bottom: $spacing-2;
  }

  p {
    color: $color-gray-600;
    margin-bottom: 0;
  }
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: $spacing-4;
}

.signup-button {
  width: 100%;
  margin-top: $spacing-2;
}

.login-link {
  text-align: center;
  font-size: $font-size-sm;
  color: $color-gray-600;
  margin-bottom: 0;

  a {
    color: $color-coral;
    font-weight: $font-weight-semibold;
    
    &:hover {
      color: $color-coral-light;
    }
  }
}
</style>
