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
            <input id="loginId" v-model="form.loginId" @input="onLoginIdInput" type="text" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Choose a login ID" />
            <p
              v-if="loginIdCheck.message || loginIdCheck.loading"
              class="text-xs mt-1"
              :class="loginIdCheck.available ? 'text-green-600' : 'text-red-500'"
            >
              {{ loginIdCheck.loading ? 'ID 확인 중...' : loginIdCheck.message }}
            </p>
          </div>
          <div class="space-y-2">
            <label class="text-sm font-medium" for="nickname">Nickname *</label>
            <input id="nickname" v-model="form.nickname" @input="onNicknameInput" type="text" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Your display name" />
            <p
              v-if="nicknameCheck.message || nicknameCheck.loading"
              class="text-xs mt-1"
              :class="nicknameCheck.available ? 'text-green-600' : 'text-red-500'"
            >
              {{ nicknameCheck.loading ? '닉네임 확인 중...' : nicknameCheck.message }}
            </p>
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
            <label class="text-sm font-medium" for="passwordConfirm">Confirm Password *</label>
            <input id="passwordConfirm" v-model="form.passwordConfirm" type="password" required class="h-11 w-full border border-border rounded px-2 py-1" placeholder="Confirm your password" />
            <p
              v-if="passwordsMatch !== null"
              class="text-xs mt-1"
              :class="passwordsMatch ? 'text-green-600' : 'text-red-500'"
            >
              {{ passwordsMatch ? '비밀번호가 일치합니다.' : '비밀번호가 일치하지 않습니다.' }}
            </p>
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

<script>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import {
  signup as signupApi,
  checkLoginId as checkLoginIdApi,
  checkNickname as checkNicknameApi,
} from '@/services/user.service'

export default {
  name: 'SignupView',

  setup() {
    const router = useRouter()

    const loading = ref(false)
    const errorMessage = ref('')

    // ------------------------
    // 디바운스 유틸
    // ------------------------
    function debounce(fn, delay) {
      let timer = null
      return (...args) => {
        clearTimeout(timer)
        timer = setTimeout(() => fn(...args), delay)
      }
    }

    // ------------------------
    // 중복 체크 상태
    // ------------------------
    const loginIdCheck = reactive({
      loading: false,
      available: null, // true | false | null
      message: '',
    })

    const nicknameCheck = reactive({
      loading: false,
      available: null,
      message: '',
    })

    // ------------------------
    // 폼 데이터
    // ------------------------
    const form = reactive({
      loginId: '',
      password: '',
      passwordConfirm: '',
      nickname: '',
      email: '',
      monthlyBudget: '',
      triggerBudget: '',
      profileVisibility: 'PUBLIC',
    })

    const passwordsMatch = computed(() => {
      if (!form.password || !form.passwordConfirm) return null
      return form.password === form.passwordConfirm
    })

    // ------------------------
    // 로그인 ID 중복 체크 (서비스 호출로 변경)
    // ------------------------
    const checkLoginId = async () => {
      const value = form.loginId.trim()
      if (!value) {
        loginIdCheck.loading = false
        loginIdCheck.available = null
        loginIdCheck.message = ''
        return
      }

      try {
        const { data } = await checkLoginIdApi(value)

        loginIdCheck.available = data.available
        loginIdCheck.message = data.available
          ? '사용 가능한 ID입니다.'
          : '이미 존재하는 ID입니다.'
      } catch (e) {
        loginIdCheck.available = null
        loginIdCheck.message = 'ID 확인 중 오류가 발생했습니다.'
      } finally {
        loginIdCheck.loading = false
      }
    }

    const debouncedCheckLoginId = debounce(checkLoginId, 300)

    const onLoginIdInput = () => {
      loginIdCheck.loading = true
      loginIdCheck.available = null
      loginIdCheck.message = ''
      debouncedCheckLoginId()
    }

    // ------------------------
    // 닉네임 중복 체크 (서비스 호출로 변경)
    // ------------------------
    const checkNickname = async () => {
      const value = form.nickname.trim()
      if (!value) {
        nicknameCheck.loading = false
        nicknameCheck.available = null
        nicknameCheck.message = ''
        return
      }

      try {
        const { data } = await checkNicknameApi(value)

        nicknameCheck.available = data.available
        nicknameCheck.message = data.available
          ? '사용 가능한 닉네임입니다.'
          : '이미 존재하는 닉네임입니다.'
      } catch (e) {
        nicknameCheck.available = null
        nicknameCheck.message = '닉네임 확인 중 오류가 발생했습니다.'
      } finally {
        nicknameCheck.loading = false
      }
    }

    const debouncedCheckNickname = debounce(checkNickname, 400)

    const onNicknameInput = () => {
      nicknameCheck.loading = true
      nicknameCheck.available = null
      nicknameCheck.message = ''
      debouncedCheckNickname()
    }

    // ------------------------
    // 회원가입 요청 (서비스 호출로 변경)
    // ------------------------
    const submit = async () => {
      errorMessage.value = ''

      if (passwordsMatch.value === false) {
        alert('비밀번호가 일치하지 않습니다.')
        return
      }

      if (loginIdCheck.available === false) {
        alert('이미 사용 중인 로그인 ID입니다.')
        return
      }
      if (nicknameCheck.available === false) {
        alert('이미 사용 중인 닉네임입니다.')
        return
      }

      loading.value = true
      try {
        const body = {
          loginId: form.loginId,
          password: form.password,
          passwordConfirm: form.passwordConfirm,
          nickname: form.nickname,
          email: form.email || null,
          monthlyBudget: form.monthlyBudget ? Number(form.monthlyBudget) : null,
          triggerBudget: form.triggerBudget ? Number(form.triggerBudget) : null,
          profileVisibility: form.profileVisibility,
        }

        console.log('[SIGNUP] request:', body)

        await signupApi(body)

        alert('회원가입이 완료되었습니다!')
        router.push('/login')
      } catch (err) {
        console.error('[SIGNUP] error:', err)
        const msg =
          err?.response?.data?.message || '회원가입 실패. 입력값을 다시 확인해주세요.'
        errorMessage.value = msg
        alert(msg)
      } finally {
        loading.value = false
      }
    }

    return {
      form,
      loading,
      errorMessage,
      loginIdCheck,
      nicknameCheck,
      passwordsMatch,
      onLoginIdInput,
      onNicknameInput,
      submit,
    }
  },
}
</script>

<style scoped></style>
