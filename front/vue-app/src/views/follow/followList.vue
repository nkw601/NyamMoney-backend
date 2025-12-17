<template>
  <Layout>
    <div class="container mx-auto py-10">
      <h1 class="text-3xl font-bold mb-6">Follows</h1>

      <!-- Tabs -->
      <div class="flex gap-2 mb-6">
        <button
          v-for="t in tabs"
          :key="t.value"
          @click="tab = t.value"
          :class="[
            'px-3 py-1 rounded',
            tab === t.value ? 'bg-primary text-primary-foreground' : 'border border-border bg-white'
          ]"
          type="button"
        >
          {{ t.label }}
        </button>
      </div>

      <!-- Loading -->
      <div v-if="loading" class="text-sm text-gray-500 bg-white rounded p-4 border border-border">
        Loading...
      </div>

      <!-- =======================
           Follow Requests (INCOMING)
      ======================== -->
      <div v-else-if="tab === 'requests'" class="border border-border rounded bg-card p-4">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold">팔로우 요청</h2>
          <span class="text-sm text-gray-500">Pending {{ incomingRequests.length }}</span>
        </div>

        <p v-if="!incomingRequests.length" class="text-sm text-gray-500">
          나에게 들어온 팔로우 요청이 없습니다.
        </p>

        <ul v-else class="space-y-3">
          <li
            v-for="req in incomingRequests"
            :key="req.requestId"
            class="p-4 rounded bg-white border border-border flex items-center justify-between gap-4"
          >
            <div class="flex items-center gap-3">
              <div class="h-10 w-10 rounded-full bg-orange-100 text-orange-700 font-semibold flex items-center justify-center overflow-hidden">
                <span>{{ (req.nickname || 'U').charAt(0) }}</span>
              </div>

              <div>
                <p class="font-semibold">{{ req.nickname }}</p>
                <p class="text-xs text-gray-400">요청일 {{ req.createdAt }}</p>
              </div>
            </div>

            <div class="flex items-center gap-2">
              <button
                class="px-3 py-1 rounded bg-orange-100 text-gray-700 text-sm"
                type="button"
                @click="declineIncoming(req.requestId)"
              >
                거절
              </button>
              <button
                class="px-3 py-1 rounded bg-orange-500 text-white text-sm hover:bg-orange-600"
                type="button"
                @click="acceptIncoming(req.requestId)"
              >
                수락
              </button>
              <button
                class="px-3 py-1 rounded border border-border text-sm"
                type="button"
                @click="goProfile(req.userId)"
              >
                프로필
              </button>
            </div>
          </li>
        </ul>
      </div>

      <!-- =======================
           Followers
      ======================== -->
      <div v-else-if="tab === 'followers'" class="border border-border rounded bg-card p-4">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold">팔로워</h2>
          <span class="text-sm text-gray-500">{{ followers.length }} people</span>
        </div>

        <p v-if="!followers.length" class="text-sm text-gray-500">
          아직 팔로워가 없습니다.
        </p>

        <ul v-else class="space-y-3">
          <li
            v-for="u in followers"
            :key="u.userId"
            class="p-4 rounded bg-white border border-border flex items-center justify-between gap-4"
          >
            <div class="flex items-center gap-3">
              <div class="h-10 w-10 rounded-full bg-orange-100 text-orange-700 font-semibold flex items-center justify-center overflow-hidden">
                <span>{{ (u.nickname || 'U').charAt(0) }}</span>
              </div>
              <div>
                <p class="font-semibold">{{ u.nickname }}</p>
                <p class="text-xs text-gray-400">@{{ u.userId }}</p>
              </div>
            </div>

            <button
              class="px-3 py-1 rounded border border-border text-sm hover:bg-orange-50"
              type="button"
              @click="goProfile(u.userId)"
            >
              프로필
            </button>
          </li>
        </ul>
      </div>

      <!-- =======================
           Followings (OUTGOING PENDING + ACCEPTED)
      ======================== -->
      <div v-else-if="tab === 'followings'" class="border border-border rounded bg-card p-4 space-y-8">
        <div class="flex items-center justify-between">
          <h2 class="text-lg font-semibold">팔로잉</h2>
          <span class="text-sm text-gray-500">
            신청중 {{ outgoingRequests.length }} · 팔로잉 {{ followings.length }}
          </span>
        </div>

        <!-- Outgoing requests -->
        <section class="space-y-3">
          <h3 class="text-sm font-semibold text-gray-700">팔로우 요청 중</h3>

          <p v-if="!outgoingRequests.length" class="text-sm text-gray-500">
            신청 중인 팔로우가 없습니다.
          </p>

          <ul v-else class="space-y-3">
            <li
              v-for="req in outgoingRequests"
              :key="req.requestId"
              class="p-4 rounded border border-border flex items-center justify-between gap-4"
            >
              <div class="flex items-center gap-3">
                <div class="h-10 w-10 rounded-full bg-orange-100 text-orange-700 font-semibold flex items-center justify-center overflow-hidden">
                  <span>{{ (req.nickname || 'U').charAt(0) }}</span>
                </div>

                <div>
                  <p class="font-semibold flex items-center gap-2">
                    {{ req.nickname }}
                    <span class="text-xs px-2 py-0.5 rounded-full border border-border bg-white text-gray-600">
                      PENDING
                    </span>
                  </p>
                  <p class="text-xs text-gray-400">신청일 {{ req.createdAt }}</p>
                </div>
              </div>

              <div class="flex items-center gap-2">
                <button
                  class="px-3 py-1 rounded bg-white border border-border text-sm"
                  type="button"
                  @click="goProfile(req.userId)"
                >
                  프로필
                </button>
                <button
                  class="px-3 py-1 rounded bg-orange-100 text-gray-700 text-sm "
                  type="button"
                  @click="cancelOutgoing(req.requestId)"
                >
                  신청 취소
                </button>
              </div>
            </li>
          </ul>
        </section>

        <!-- Accepted followings -->
        <section class="space-y-3">
          <h3 class="text-sm font-semibold text-gray-700">팔로잉 중</h3>

          <p v-if="!followings.length" class="text-sm text-gray-500">
            팔로잉 중인 사용자가 없습니다.
          </p>

          <ul v-else class="space-y-3">
            <li
              v-for="u in followings"
              :key="u.userId"
              class="p-4 rounded bg-white border border-border flex items-center justify-between gap-4"
            >
              <div class="flex items-center gap-3">
                <div class="h-10 w-10 rounded-full bg-orange-100 text-orange-700 font-semibold flex items-center justify-center overflow-hidden">
                  <span>{{ (u.nickname || 'U').charAt(0) }}</span>
                </div>
                <div>
                  <p class="font-semibold">{{ u.nickname }}</p>
                  <p class="text-xs text-gray-400">@{{ u.userId }}</p>
                </div>
              </div>

              <div class="flex items-center gap-2">
                <button
                  class="px-3 py-1 rounded border border-border text-sm "
                  type="button"
                  @click="goProfile(u.userId)"
                >
                  프로필
                </button>
                <button
                  class="px-3 py-1 rounded bg-orange-100 text-gray-700 text-sm"
                  type="button"
                  @click="doUnfollow(u.userId)"
                >
                  언팔로우
                </button>
              </div>
            </li>
          </ul>
        </section>
      </div>

      <!-- =======================
           Blocks
      ======================== -->
      <div v-else class="border border-border rounded bg-card p-4">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold">차단 목록</h2>
          <span class="text-sm text-gray-500">{{ blocks.length }} people</span>
        </div>

        <p v-if="!blocks.length" class="text-sm text-gray-500">
          차단한 사용자가 없습니다.
        </p>

        <ul v-else class="space-y-3">
          <li
            v-for="u in blocks"
            :key="u.userId"
            class="p-4 rounded bg-white border border-border flex items-center justify-between gap-4"
          >
            <div class="flex items-center gap-3">
              <div class="h-10 w-10 rounded-full bg-orange-100 text-orange-700 font-semibold flex items-center justify-center overflow-hidden">
                <span>{{ (u.nickname || 'U').charAt(0) }}</span>
              </div>
              <div>
                <p class="font-semibold">{{ u.nickname }}</p>
                <p class="text-xs text-gray-400">@{{ u.userId }}</p>
              </div>
            </div>

            <div class="flex items-center gap-2">
              <button
                class="px-3 py-1 rounded border border-border text-sm "
                type="button"
                @click="goProfile(u.userId)"
              >
                프로필
              </button>

              <!-- ✅ 차단 해제 버튼 -->
              <button
                class="px-3 py-1 rounded bg-orange-100 text-gray-700 text-sm "
                type="button"
                :disabled="unblockingId === String(u.userId)"
                @click="doUnblock(u.userId)"
              >
                {{ unblockingId === String(u.userId) ? '해제 중...' : '차단 해제' }}
              </button>
            </div>
          </li>
        </ul>
      </div>

    </div>
  </Layout>
</template>

<script>
import { defineComponent, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '../../components/Layout.vue'
import {
  fetchFollowers,
  fetchFollowings,
  fetchFollowRequests,
  acceptFollowRequest,
  declineFollowRequest,
  cancelFollowRequest,
  unfollow,
  fetchBlocks,
  unblock
} from '@/services/follow.service'

const toStr = (v) => (v === null || v === undefined ? '' : String(v))

export default defineComponent({
  name: 'FollowListView',
  components: { Layout },
  setup() {
    const router = useRouter()

    const tabs = [
      { value: 'requests', label: '팔로우 요청' },
      { value: 'followers', label: '팔로워' },
      { value: 'followings', label: '팔로잉' },
      { value: 'blocks', label: '차단' }
    ]
    const tab = ref('requests')
    const loading = ref(false)

    const incomingRequests = ref([])
    const followers = ref([])
    const outgoingRequests = ref([])
    const followings = ref([])
    const blocks = ref([])

    const unblockingId = ref('')

    const normalizeRequestItem = (item) => ({
      requestId: toStr(item?.requestId ?? item?.followId ?? item?.id ?? ''),
      userId: toStr(item?.userId ?? item?.followerId ?? item?.followeeId ?? ''),
      nickname: item?.nickname ?? item?.followerNickname ?? item?.followeeNickname ?? 'User',
      createdAt: item?.createdAt ?? item?.requestedAt ?? ''
    })

    const normalizeUserItem = (item) => ({
      userId: toStr(item?.userId ?? item?.id ?? ''),
      nickname: item?.nickname ?? item?.name ?? 'User'
    })

    const goProfile = (userId) => {
      if (!userId) return
      router.push({ name: 'UserProfile', query: { userId: String(userId) } })
    }

    const loadIncoming = async () => {
      const res = await fetchFollowRequests('incoming')
      const data = res?.data ?? res
      incomingRequests.value = (data?.requests ?? data?.items ?? []).map(normalizeRequestItem)
    }

    const loadFollowers = async () => {
      const res = await fetchFollowers()
      const data = res?.data ?? res
      followers.value = (data?.items ?? []).map(normalizeUserItem)
    }

    const loadFollowings = async () => {
      const [reqRes, followingRes] = await Promise.all([
        fetchFollowRequests('outgoing'),
        fetchFollowings()
      ])

      const reqData = reqRes?.data ?? reqRes
      outgoingRequests.value = (reqData?.requests ?? reqData?.items ?? []).map(normalizeRequestItem)

      const followingData = followingRes?.data ?? followingRes
      followings.value = (followingData?.items ?? []).map(normalizeUserItem)
    }

    const loadBlocks = async () => {
      const res = await fetchBlocks()
      const data = res?.data ?? res
      blocks.value = (data?.items ?? []).map(normalizeUserItem)
    }

    const loadByTab = async () => {
      loading.value = true
      try {
        if (tab.value === 'requests') await loadIncoming()
        else if (tab.value === 'followers') await loadFollowers()
        else if (tab.value === 'followings') await loadFollowings()
        else await loadBlocks()
      } catch (e) {
        console.error('Failed to load follow list view', e)
        if (tab.value === 'requests') incomingRequests.value = []
        else if (tab.value === 'followers') followers.value = []
        else if (tab.value === 'followings') {
          outgoingRequests.value = []
          followings.value = []
        } else blocks.value = []
      } finally {
        loading.value = false
      }
    }

    const acceptIncoming = async (requestId) => {
      if (!requestId) return
      try {
        await acceptFollowRequest(String(requestId))
        await loadIncoming()
      } catch (e) {
        console.error('acceptIncoming failed', e)
      }
    }

    const declineIncoming = async (requestId) => {
      if (!requestId) return
      try {
        await declineFollowRequest(String(requestId))
        incomingRequests.value = incomingRequests.value.filter((x) => x.requestId !== String(requestId))
      } catch (e) {
        console.error('declineIncoming failed', e)
      }
    }

    const cancelOutgoing = async (requestId) => {
      if (!requestId) return
      try {
        await cancelFollowRequest(String(requestId))
        outgoingRequests.value = outgoingRequests.value.filter((x) => x.requestId !== String(requestId))
      } catch (e) {
        console.error('cancelOutgoing failed', e)
      }
    }

    const doUnfollow = async (targetUserId) => {
      if (!targetUserId) return
      try {
        await unfollow(String(targetUserId))
        followings.value = followings.value.filter((x) => x.userId !== String(targetUserId))
      } catch (e) {
        console.error('doUnfollow failed', e)
      }
    }

    // ✅ 차단 해제
    const doUnblock = async (targetUserId) => {
      if (!targetUserId) return
      const id = String(targetUserId)
      if (unblockingId.value) return

      unblockingId.value = id
      try {
        await unblock(id)
        blocks.value = blocks.value.filter((x) => String(x.userId) !== id)
      } catch (e) {
        console.error('doUnblock failed', e)
      } finally {
        unblockingId.value = ''
      }
    }

    watch(tab, () => {
      loadByTab()
    }, { immediate: true })

    return {
      tabs,
      tab,
      loading,
      incomingRequests,
      followers,
      outgoingRequests,
      followings,
      blocks,
      unblockingId,
      acceptIncoming,
      declineIncoming,
      cancelOutgoing,
      doUnfollow,
      doUnblock,
      goProfile
    }
  }
})
</script>

<style scoped></style>
