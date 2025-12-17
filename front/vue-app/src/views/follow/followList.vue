<template>
  <Layout>
    <div class="p-8 min-h-screen">
      <h1 class="text-2xl font-bold mb-6">Follows</h1>

      <div class="grid gap-8 lg:grid-cols-1">
        <section class="space-y-3">
          <div class="flex items-center justify-between">
            <h2 class="text-lg font-semibold">Follow Requests</h2>
            <span class="text-sm text-gray-500">Pending {{ followRequests.length }}</span>
          </div>

          <p v-if="loading" class="text-sm text-gray-500 bg-white rounded p-4 shadow">Loading...</p>
          <p v-else-if="!followRequests.length" class="text-sm text-gray-500 bg-white rounded p-4 shadow">
            No follow requests.
          </p>

          <ul v-else>
            <li
              v-for="request in followRequests"
              :key="request.id"
              class="mb-3 p-4 rounded bg-white shadow flex items-center justify-between gap-4"
            >
              <div class="flex items-center gap-4">
                <div class="h-12 w-12 rounded-full overflow-hidden bg-orange-100 text-orange-700 font-semibold flex items-center justify-center">
                  <img
                    v-if="request.profileUrl"
                    :src="request.profileUrl"
                    :alt="request.nickname"
                    class="h-full w-full object-cover"
                  />
                  <span v-else>{{ request.nickname.charAt(0) }}</span>
                </div>
                <div>
                  <p class="font-semibold">{{ request.nickname }}</p>
                  <p class="text-sm text-gray-500">@{{ request.id }}</p>
                  <p class="text-xs text-gray-400">Requested at {{ request.requestedAt }}</p>
                </div>
              </div>

              <div class="flex items-center gap-2">
                <button
                  class="px-3 py-1 rounded bg-gray-100 text-gray-700 text-sm hover:bg-gray-200"
                  @click="declineRequest(request.id)"
                >
                  Decline
                </button>
                <button
                  class="px-3 py-1 rounded bg-orange-500 text-white text-sm hover:bg-orange-600"
                  @click="acceptRequest(request.id)"
                >
                  Accept
                </button>

                <button class="px-3 py-1 border border-gray-200 rounded text-sm hover:bg-gray-50">
                  View profile
                </button>
              </div>
            </li>
          </ul>
        </section>

        <section class="space-y-3">
          <div class="flex items-center justify-between">
            <h2 class="text-lg font-semibold">Followers</h2>
            <span class="text-sm text-gray-500">{{ followers.length }} people</span>
          </div>

          <p v-if="loading" class="text-sm text-gray-500 bg-white rounded p-4 shadow">Loading...</p>
          <p v-else-if="!followers.length" class="text-sm text-gray-500 bg-white rounded p-4 shadow">
            No followers yet.
          </p>

          <ul v-else>
            <li
              v-for="follower in followers"
              :key="follower.id"
              class="mb-3 p-4 rounded bg-white shadow flex items-center justify-between gap-4"
            >
              <div class="flex items-center gap-4">
                <div class="h-12 w-12 rounded-full overflow-hidden bg-orange-100 text-orange-700 font-semibold flex items-center justify-center">
                  <img
                    v-if="follower.profileUrl"
                    :src="follower.profileUrl"
                    :alt="follower.nickname"
                    class="h-full w-full object-cover"
                  />
                  <span v-else>{{ follower.nickname.charAt(0) }}</span>
                </div>
                <div>
                  <p class="font-semibold">{{ follower.nickname }}</p>
                  <p class="text-sm text-gray-500">@{{ follower.id }}</p>
                </div>
              </div>

              <button
                class="px-3 py-1 border border-gray-200 rounded text-sm hover:bg-gray-50"
                @click="goProfile(follower.id)"
              >
                View profile
              </button>
            </li>
          </ul>
        </section>
      </div>
    </div>
  </Layout>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '../../components/Layout.vue'
import {
  fetchFollowers,
  fetchFollowRequests,
  acceptFollowRequest,
  declineFollowRequest,
} from '@/services/follow.service'

type UserCard = {
  id: string
  nickname: string
  profileUrl?: string
  requestedAt?: string
}

export default defineComponent({
  name: 'FollowList',
  components: { Layout },
  setup() {
    const router = useRouter()
    const followRequests = ref<UserCard[]>([])
    const followers = ref<UserCard[]>([])
    const loading = ref(false)

    const normalizeRequest = (item: any): UserCard => ({
      id: item?.requestId ?? item?.userId ?? item?.id ?? '',
      nickname: item?.nickname ?? item?.name ?? 'User',
      profileUrl: item?.profileImageUrl ?? item?.profileUrl ?? item?.avatarUrl ?? '',
      requestedAt: item?.requestedAt ?? item?.createdAt ?? '',
    })

    const normalizeFollower = (item: any): UserCard => ({
      id: item?.userId ?? item?.id ?? '',
      nickname: item?.nickname ?? item?.name ?? 'User',
      profileUrl: item?.profileImageUrl ?? item?.profileUrl ?? item?.avatarUrl ?? '',
    })

    const loadData = async () => {
      loading.value = true
      try {
        const [reqRes, followerRes] = await Promise.all([
          fetchFollowRequests('incoming'),
          fetchFollowers(),
        ])


        const reqData = reqRes?.data ?? reqRes
        const followerData = followerRes?.data ?? followerRes

        followRequests.value = (reqData?.requests ?? reqData?.items ?? []).map(normalizeRequest)
        followers.value = (followerData?.items ?? []).map(normalizeFollower)

        console.log('reqData', reqData)
        console.log('followerData', followerData)
      } catch (err) {
        console.error('Failed to load follow lists', err)
      } finally {
        loading.value = false
      }
    }


    const acceptRequest = async (id: string) => {
      if (!id) return
      try {
        await acceptFollowRequest(id)
        await loadData()
      } catch (err) {
        console.error('Accept follow request failed', err)
      }
    }

    const declineRequest = async (id: string) => {
      if (!id) return
      try {
        await declineFollowRequest(id)
        followRequests.value = followRequests.value.filter((req) => req.id !== id)
      } catch (err) {
        console.error('Decline follow request failed', err)
      }
    }

    const goProfile = (userId: string) => {
      if (!userId) return
      router.push({ name: 'UserProfile', query: { userId } })
    }

    loadData()

    return {
      followRequests,
      followers,
      loading,
      acceptRequest,
      declineRequest,
      goProfile,
    }
  },
})
</script>
