<template>
  <Layout>
    <div class="p-8 min-h-screen">
      <h1 class="text-2xl font-bold mb-6">Follows</h1>

      <div class="grid gap-8 lg:grid-cols-1">
        <section class="space-y-3">
          <div class="flex items-center justify-between">
            <h2 class="text-lg font-semibold">팔로우 요청</h2>
            <span class="text-sm text-gray-500">요청 수: {{ followRequests.length }}개</span>
          </div>

          <p
            v-if="!followRequests.length"
            class="text-sm text-gray-500 bg-white rounded p-4 shadow"
          >
            팔로우 요청이 없습니다.
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
                  <p class="text-xs text-gray-400">요청 시간 {{ request.requestedAt }}</p>
                </div>
              </div>

              <div class="flex items-center gap-2">
                <button
                  class="px-3 py-1 rounded bg-gray-100 text-gray-700 text-sm hover:bg-gray-200"
                  @click="declineRequest(request.id)"
                >
                  거절
                </button>
                <button
                  class="px-3 py-1 rounded bg-orange-500 text-white text-sm hover:bg-orange-600"
                  @click="acceptRequest(request.id)"
                >
                  수락
                </button>
              </div>
            </li>
          </ul>
        </section>

        <section class="space-y-3">
          <div class="flex items-center justify-between">
            <h2 class="text-lg font-semibold">팔로워</h2>
            <span class="text-sm text-gray-500">{{ followers.length }}명</span>
          </div>

          <p
            v-if="!followers.length"
            class="text-sm text-gray-500 bg-white rounded p-4 shadow"
          >
            팔로워가 존재하지 않습니다.
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

              <button class="px-3 py-1 border border-gray-200 rounded text-sm hover:bg-gray-50">
                차단하기
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
import Layout from '../../components/Layout.vue'

type UserCard = {
  id: string
  nickname: string
  profileUrl?: string
  requestedAt?: string
  daysFollowing?: number
}

export default defineComponent({
  name: 'Meetings',
  components: { Layout },
  setup() {
    const followRequests = ref<UserCard[]>([
      {
        id: 'minsu_92',
        nickname: '123',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=minsu',
        requestedAt: '12:34',
      },
      {
        id: 'hanna.dev',
        nickname: 'ㅁㄴㅇㄹ',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=hanna',
        requestedAt: '123:12',
      },
      {
        id: 'travel_woo',
        nickname: 'travel_woo',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=woo',
        requestedAt: '12:12',
      },
    ])

    const followers = ref<UserCard[]>([
      {
        id: 'cafe_latte',
        nickname: 'cafe_latte',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=latte',
      },
      {
        id: 'runner_june',
        nickname: 'runner_june',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=june',
      },
      {
        id: 'coding_dobi',
        nickname: 'coding_dobi',
        profileUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=dobi',
      },
    ])

    const acceptRequest = (id: string) => {
      const accepted = followRequests.value.find((req) => req.id === id)
      if (!accepted) return

      followRequests.value = followRequests.value.filter((req) => req.id !== id)
      followers.value = [
        {
          id: accepted.id,
          nickname: accepted.nickname,
          profileUrl: accepted.profileUrl,
        },
        ...followers.value,
      ]
    }

    const declineRequest = (id: string) => {
      followRequests.value = followRequests.value.filter((req) => req.id !== id)
    }

    return {
      followRequests,
      followers,
      acceptRequest,
      declineRequest,
    }
  },
})
</script>
