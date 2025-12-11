<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-accent via-background to-muted p-4">
    <div class="w-full max-w-md shadow-xl border-2 bg-card rounded-lg">
      <!-- 상단 인트로 -->
      <div class="text-center space-y-4 py-6">
        <div class="flex justify-center">
          <div class="w-24 h-24 bg-gradient-to-br from-primary to-secondary rounded-full flex items-center justify-center shadow-lg">
            <span class="text-4xl">🐱</span>
          </div>
        </div>
        <div class="text-3xl font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
          YumMoney
        </div>
        <div class="text-base text-muted-foreground">
          다시 오신 걸 환영해요!  
          오늘도 소비를 잘 기록해볼까요?
        </div>
      </div>

      <!-- 로그인 폼 -->
      <form @submit.prevent="submit" class="space-y-4 px-8 pb-8">
        <!-- 로그인 ID -->
        <div class="space-y-2">
          <label class="text-sm font-medium" for="loginId">로그인 ID</label>
          <input
            id="loginId"
            v-model="loginId"
            type="text"
            required
            class="h-11 w-full border border-border rounded px-2 py-1"
            placeholder="로그인에 사용할 ID를 입력하세요"
          />
        </div>

        <!-- 비밀번호 -->
        <div class="space-y-2">
          <label class="text-sm font-medium" for="password">비밀번호</label>
          <input
            id="password"
            v-model="password"
            type="password"
            required
            class="h-11 w-full border border-border rounded px-2 py-1"
            placeholder="비밀번호를 입력하세요"
          />
        </div>

        <!-- 아이디 기억하기 + 비밀번호 찾기 -->
        <div class="flex items-center justify-between pt-1">
          <label class="flex items-center gap-2 text-sm text-muted-foreground cursor-pointer">
            <input
              type="checkbox"
              v-model="rememberLoginId"
              class="w-4 h-4 border border-border rounded"
            />
            <span>아이디 기억하기</span>
          </label>

          <router-link
            to="#"
            class="text-sm text-primary hover:text-primary/80 font-medium"
          >
            비밀번호를 잊으셨나요?
          </router-link>
        </div>

        <!-- 에러 메시지 -->
        <p v-if="errorMessage" class="text-sm text-red-500 mt-1">
          {{ errorMessage }}
        </p>

        <!-- 버튼 / 회원가입 링크 -->
        <div class="flex flex-col space-y-4 pt-4">
          <button
            type="submit"
            class="w-full h-11 text-base font-semibold shadow-md hover:shadow-lg transition-shadow bg-primary text-primary-foreground rounded"
            :disabled="loading"
          >
            {{ loading ? '로그인 중...' : '로그인' }}
          </button>
          <div class="text-center text-sm text-muted-foreground">
            아직 계정이 없으신가요?
            <router-link
              to="/signup"
              class="text-primary hover:text-primary/80 font-semibold"
            >
              회원가입
            </router-link>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

export default {
  name: 'LoginView',

  setup() {
    const router = useRouter()
    const auth = useAuthStore()

    const loginId = ref('')
    const password = ref('')
    const rememberLoginId = ref(false)
    const loading = ref(false)
    const errorMessage = ref('')

    // 처음 들어올 때 localStorage에 저장된 loginId 복원
    onMounted(() => {
      const saved = localStorage.getItem('savedLoginId')
      if (saved) {
        loginId.value = saved
        rememberLoginId.value = true
      }
    })

    const submit = async () => {
      errorMessage.value = ''

      if (!loginId.value || !password.value) {
        errorMessage.value = '로그인 ID와 비밀번호를 모두 입력해주세요.'
        return
      }

      loading.value = true
      try {
        // 🔐 백엔드 로그인 API 호출 (authStore에서 처리)
        await auth.login({
          loginId: loginId.value,
          password: password.value,
        })

        // ✅ 아이디 기억하기 처리
        if (rememberLoginId.value) {
          localStorage.setItem('savedLoginId', loginId.value)
        } else {
          localStorage.removeItem('savedLoginId')
        }

        // ✅ 로그인 성공 시 대시보드로 이동
        router.push('/dashboard')
      } catch (err) {
        console.error('[LOGIN] error:', err)
        const msg =
          err?.response?.data?.message ||
          '로그인에 실패했습니다. 아이디/비밀번호를 다시 확인해주세요.'
        errorMessage.value = msg
      } finally {
        loading.value = false
      }
    }

    return {
      loginId,
      password,
      rememberLoginId,
      loading,
      errorMessage,
      submit,
    }
  },
}
</script>

<style scoped></style>