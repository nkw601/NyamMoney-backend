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
          <h2 class="text-lg font-medium">Account Settings</h2>
          <div class="space-y-6 mt-4">
            <div>
              <label class="text-sm">Current Avatar</label>
              <div class="flex items-center space-x-4 mt-2">
                <UiAvatar :src="local.avatar" :name="local.fullName" className="h-20 w-20" />
              </div>
            </div>

            <div>
              <label class="text-sm">Choose a new avatar</label>
              <div class="flex gap-4 overflow-x-auto pb-2 mt-2">
                <div v-for="(a, i) in defaultAvatars" :key="i" class="shrink-0">
                  <div @click="local.avatar = a" :class="['h-20 w-20 rounded-lg cursor-pointer', local.avatar===a ? 'ring-2 ring-primary' : '']">
                    <img :src="a" class="w-full h-full object-cover rounded-lg" />
                  </div>
                </div>
              </div>
            </div>

            <div class="grid gap-2">
              <label class="text-sm">Full Name</label>
              <UiInput v-model="local.fullName" />
            </div>

            <div class="grid gap-2">
              <label class="text-sm">Email</label>
              <UiInput v-model="local.email" type="email" />
            </div>

            <div class="grid gap-2">
              <label class="text-sm">Phone Number</label>
              <UiInput v-model="local.phone" type="tel" />
            </div>

            <div class="grid gap-2">
              <label class="text-sm">Timezone</label>
              <UiSelect v-model="local.timezone">
                <option value="utc-8">Pacific Time (UTC-8)</option>
                <option value="utc-5">Eastern Time (UTC-5)</option>
                <option value="utc+0">Greenwich Mean Time (UTC+0)</option>
                <option value="utc+9">Japan Standard Time (UTC+9)</option>
              </UiSelect>
            </div>

            <div class="text-right">
              <button @click="saveAccount" class="px-4 py-2 bg-primary text-primary-foreground rounded">Save Account Settings</button>
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
import UiAvatar from '../../components/ui/Avatar.vue'
import { fetchMe } from '@/services/user.service'

export default defineComponent({
  name: 'SettingsView',
  components: { Layout, UiInput, UiSelect, UiSwitch, UiCheckbox, UiAvatar },
  setup() {
    const loading = ref(false)

    // ✅ UserDetailResponse 기준 로컬 상태
    const local = reactive({
      userId: null,
      loginId: '',
      nickname: '',
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
    const twoFactor = ref(false)

    // 기존 UI에서 쓰던 notif/priv가 필요하면 유지 (백엔드 연동 전까지는 로컬로)
    const notif = reactive({ email: false, push: false, sms: false, frequency: 'real-time' })
    const priv = reactive({ analyticsSharing: false, personalizedAds: false })

    const defaultAvatars = [
      'https://hebbkx1anhila5yf.public.blob.vercel-storage.com/9439775.jpg-4JVJWOjPksd3DtnBYJXoWHA5lc1DU9.jpeg',
      'https://hebbkx1anhila5yf.public.blob.vercel-storage.com/375238645_11475210.jpg-lU8bOe6TLt5Rv51hgjg8NT8PsDBmvN.jpeg',
    ]

    // ✅ 로그인 유저 상세정보 로드
    const loadMe = async () => {
      loading.value = true
      try {
        const { data } = await fetchMe()
        Object.assign(local, data)
      } finally {
        loading.value = false
      }
    }

    onMounted(() => {
      loadMe()
    })

    // 저장 버튼을 누를 때 백엔드 수정 API가 없다면 일단 alert만 (또는 버튼 숨김)
    function saveAccount() {
      // TODO: PUT/PATCH /v1/users/me 같은 API가 생기면 여기서 호출
      alert('저장 API가 아직 없으면, 지금은 조회만 가능합니다.')
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
      defaultAvatars,
      saveAccount,
      saveNotifications,
      savePrivacy,
      twoFactor,
    }
  },
})
</script>

<style scoped></style>