<template>
  <Layout>
  <div class="container mx-auto py-10">
    <h1 class="text-3xl font-bold mb-6">My Page</h1>

    <div>
      <div class="flex gap-2 mb-4">
        <button v-for="t in tabs" :key="t.value" @click="tab = t.value" :class="['px-3 py-1 rounded', tab===t.value? 'bg-primary text-primary-foreground':'border border-border']">{{ t.label }}</button>
      </div>

<div v-if="tab==='account'">
  <div class="border border-border rounded bg-card p-4">
    <h2 class="text-lg font-medium">내 정보</h2>

    <div class="space-y-6 mt-4">
      <!-- Login ID (read-only) -->
      <div class="grid gap-2">
        <label class="text-sm">아이디</label>
        <UiInput v-model="local.loginId" readonly />
      </div>

      <!-- Nickname -->
      <div class="grid gap-2">
        <label class="text-sm">닉네임</label>
        <UiInput v-model="local.nickname" />
      </div>

      <!-- Name -->
      <div class="grid gap-2">
        <label class="text-sm">이름</label>
        <UiInput v-model="local.name" />
      </div>

      <!-- Phone Number -->
      <div class="grid gap-2">
        <label class="text-sm">휴대폰 번호</label>
        <UiInput v-model="local.phoneNumber" type="tel" />
      </div>

      <!-- Email -->
      <div class="grid gap-2">
        <label class="text-sm">이메일</label>
        <UiInput v-model="local.email" type="email" />
      </div>

      <!-- Profile Visibility (radio) -->
      <div class="grid gap-2">
        <label class="text-sm">Profile Visibility</label>
        <div class="flex gap-4">
          <label class="flex items-center space-x-2 cursor-pointer">
            <input
              type="radio"
              value="PUBLIC"
              v-model="local.profileVisibility"
              class="w-4 h-4 text-primary focus:ring-primary"
            />
            <span class="text-sm font-medium">Public</span>
          </label>

          <label class="flex items-center space-x-2 cursor-pointer">
            <input
              type="radio"
              value="PROTECTED"
              v-model="local.profileVisibility"
              class="w-4 h-4 text-primary focus:ring-primary"
            />
            <span class="text-sm font-medium">Protected</span>
          </label>
        </div>
      </div>

      <!-- Budget -->
      <div class="grid gap-2">
        <label class="text-sm">한 달 예산</label>
        <UiInput v-model="local.monthlyBudget" type="number" />
      </div>

      <div class="grid gap-2">
        <label class="text-sm">냠 비용</label>
        <UiInput v-model="local.triggerBudget" type="number" />
      </div>

      <div class="text-right">
        <button
          @click="saveAccount"
          class="px-4 py-2 bg-primary text-primary-foreground rounded"
        >
          수정하기
        </button>
      </div>
    </div>
  </div>
</div>

      <div v-if="tab==='security'" class="space-y-4">
        <div class="border border-border rounded bg-card p-4">
          <h2 class="text-lg font-medium">Security Settings</h2>
          <div class="space-y-4 mt-4">
            <div class="grid gap-2">
              <label class="text-sm">Current Password</label>
              <UiInput type="password" />
            </div>
            <div class="grid gap-2">
              <label class="text-sm">New Password</label>
              <UiInput type="password" />
            </div>
            <div class="flex items-center space-x-2">
              <UiSwitch v-model:modelValue="twoFactor" />
              <label class="text-sm">Enable Two-Factor Authentication</label>
            </div>
            <div class="text-right">
              <button class="px-4 py-2 bg-primary text-primary-foreground rounded">Save Security Settings</button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="tab==='preferences'">
        <div class="border border-border rounded bg-card p-4">
          <h2 class="text-lg font-medium">Preferences</h2>
          <div class="space-y-4 mt-4">
            <div class="grid md:grid-cols-2 gap-4">
              <div>
                <label class="text-sm">Language</label>
                <UiSelect v-model="local.language">
                  <option value="en">English</option>
                  <option value="es">Español</option>
                </UiSelect>
              </div>
              <div>
                <label class="text-sm">Currency</label>
                <UiSelect v-model="local.currency">
                  <option value="usd">USD</option>
                  <option value="eur">EUR</option>
                </UiSelect>
              </div>
            </div>
            <div class="text-right">
              <button class="px-4 py-2 bg-primary text-primary-foreground rounded">Save Preferences</button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="tab==='notifications'">
        <div class="border border-border rounded bg-card p-4">
          <h2 class="text-lg font-medium">Notification Settings</h2>
          <div class="space-y-4 mt-4">
            <div class="grid md:grid-cols-2 gap-4">
              <div>
                <div class="flex items-center space-x-2">
                  <UiCheckbox v-model="notif.email" />
                  <label class="text-sm">Email Notifications</label>
                </div>
                <div class="flex items-center space-x-2">
                  <UiCheckbox v-model="notif.push" />
                  <label class="text-sm">Push Notifications</label>
                </div>
                <div class="flex items-center space-x-2">
                  <UiCheckbox v-model="notif.sms" />
                  <label class="text-sm">SMS Notifications</label>
                </div>
              </div>
              <div>
                <label class="text-sm">Notification Frequency</label>
                <UiSelect v-model="notif.frequency">
                  <option value="real-time">Real-time</option>
                  <option value="daily">Daily</option>
                </UiSelect>
              </div>
            </div>
            <div class="text-right">
              <button @click="saveNotifications" class="px-4 py-2 bg-primary text-primary-foreground rounded">Save Notification Settings</button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="tab==='privacy'">
        <div class="border border-border rounded bg-card p-4">
          <h2 class="text-lg font-medium">Privacy Settings</h2>
          <div class="space-y-4 mt-4">
            <div class="flex items-center justify-between">
              <label class="text-sm">Share analytics data</label>
              <UiSwitch v-model:modelValue="priv.analyticsSharing" />
            </div>
            <div class="flex items-center justify-between">
              <label class="text-sm">Allow personalized ads</label>
              <UiSwitch v-model:modelValue="priv.personalizedAds" />
            </div>
            <div class="text-right">
              <button @click="savePrivacy" class="px-4 py-2 bg-primary text-primary-foreground rounded">Save Privacy Settings</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </Layout>
</template>
<script>
import { defineComponent, reactive, ref, onMounted } from 'vue'
import Layout from '../../components/Layout.vue'
import UiInput from '../../components/ui/Input.vue'
import UiSelect from '../../components/ui/Select.vue'
import UiSwitch from '../../components/ui/Switch.vue'
import UiCheckbox from '../../components/ui/Checkbox.vue'
import { fetchMe } from '@/services/user.service'

export default defineComponent({
  name: 'SettingsView',
  components: { Layout, UiInput, UiSelect, UiSwitch, UiCheckbox },
  setup() {
    const loading = ref(false)

    // ✅ UserDetailResponse 기준 local 상태
    const local = reactive({
      userId: null,
      loginId: '',
      nickname: '',
      name: '',
      phoneNumber: '',
      email: '',
      monthlyBudget: null,
      triggerBudget: null,
      createdAt: null,
      upDatedAt: null,
      profileVisibility: 'PUBLIC',
      shareLevel: null,
      role: null,
    })

    const tabs = [
      { value: 'account', label: 'Account' },
      { value: 'security', label: 'Security' },
      { value: 'preferences', label: 'Preferences' },
      { value: 'notifications', label: 'Notifications' },
      { value: 'privacy', label: 'Privacy' },
    ]
    const tab = ref('account')

    // 알림/프라이버시 탭은 아직 백엔드 연동 전 → 로컬 유지
    const notif = reactive({
      email: false,
      push: false,
      sms: false,
      frequency: 'real-time',
    })

    const priv = reactive({
      analyticsSharing: false,
      personalizedAds: false,
    })

    // ✅ 내 정보 로드
    const loadMe = async () => {
      loading.value = true
      try {
        const { data } = await fetchMe()
        Object.assign(local, data)
        console.log('[ME] data:', data)
      } catch (err) {
        console.log('status', err?.response?.status)
        console.log('body', err?.response?.data)
      } finally {
        loading.value = false
      }
    }

    onMounted(() => {
      loadMe()
    })

    // 아직 수정 API가 없으므로 임시 처리
    function saveAccount() {
      alert('계정 정보 수정 API가 아직 없습니다.')
    }

    function saveNotifications() {
      alert('Notification settings saved')
    }

    function savePrivacy() {
      alert('Privacy settings saved')
    }

    return {
      loading,
      tabs,
      tab,
      local,
      notif,
      priv,
      saveAccount,
      saveNotifications,
      savePrivacy,
    }
  },
})
</script>
<style scoped></style>