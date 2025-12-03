<template>
  <div class="login-view">
    <div class="login-container">
      <div class="login-header">
        <img src="/logo.png" alt="YumMoney" class="logo" />
        <h1>어서 오세요!</h1>
        <p>당신의 지갑을 냠 하려면 로그인하세요!</p>
      </div>

      <form @submit.prevent="handleLogin" class="login-form">
        <AppInput
          v-model="loginId"
          label="아이디"
          placeholder="아이디를 입력하세요."
          required
          :error="errors.loginId"
        />

        <AppInput
          v-model="password"
          type="password"
          label="비밀번호"
          placeholder="비밀번호를 입력하세요"
          required
          :error="errors.password"
        />

        <AppButton
          type="submit"
          variant="primary"
          size="lg"
          :loading="isLoading"
          class="login-button"
        >
          Sign In
        </AppButton>

        <p class="signup-link">
          Don't have an account?
          <router-link to="/signup">Sign up</router-link>
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

const loginId = ref('')
const password = ref('')
const isLoading = ref(false)
const errors = ref<{ loginId?: string; password?: string }>({})

async function handleLogin() {
  // Clear previous errors
  errors.value = {}

  // Validate
  if (!loginId.value) {
    errors.value.loginId = 'Login ID is required'
    return
  }
  if (!password.value) {
    errors.value.password = 'Password is required'
    return
  }

  isLoading.value = true
  try {
    await authStore.login(loginId.value, password.value)
    router.push('/dashboard')
  } catch (error: any) {
    errors.value.loginId = error.response?.data?.message || 'Login failed. Please try again.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables';
@import '@/styles/mixins';

.login-view {
  min-height: 100vh;
  @include flex-center;
  padding: $spacing-4;
  background: linear-gradient(135deg, $color-cream 0%, $color-off-white 100%);
}

.login-container {
  width: 100%;
  max-width: 420px;
  background: $color-white;
  border-radius: $radius-lg;
  border: 3px solid $color-brown;
  box-shadow: $shadow-xl;
  padding: $spacing-8;
}

.login-header {
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

.login-form {
  display: flex;
  flex-direction: column;
  gap: $spacing-5;
}

.login-button {
  width: 100%;
  margin-top: $spacing-2;
}

.signup-link {
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
