<template>
  <Layout>
    <div>
      <h1 class="text-2xl font-bold mb-6">챌린지</h1>

      <p v-if="loading">불러오는 중...</p>

      <!-- 카드 그리드 -->
      <div
        v-else
        class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
      >
        <div
          v-for="c in challenges"
          :key="c.challengeId"
          class="rounded-xl border bg-white p-5 shadow-sm
                 hover:-translate-y-1 transition cursor-pointer"
          @click="goDetail(c.challengeId)"
        >
          <!-- 제목 -->
          <h2 class="font-semibold text-lg mb-2">
            {{ c.title }}
          </h2>

          <!-- 설명 -->
          <p class="text-sm text-gray-600 mb-4 line-clamp-2 ">
            {{ c.description }}
          </p>

          <!-- 기간 -->
          <p class="text-xs text-gray-500 mb-2">
            {{ c.startDate }} ~ {{ c.endDate }}
          </p>

          <!-- 하단 영역 -->
          <div class="flex justify-between items-center mt-4">
            <span class="text-sm text-gray-500">
              참여자 {{ c.participantCount ?? 0 }}명
            </span>

            <span
              v-if="c.isJoined"
              class="text-xs px-2 py-1 rounded-full bg-green-100 text-green-700"
            >
              참여 중
            </span>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>


<script>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useChallengeStore } from '@/stores/challenge.store'
import { useRouter } from 'vue-router'
//import Layout from '@/components/Layout.vue'

export default {
  components: {  },

  setup() {
    const router = useRouter()
    const challengeStore = useChallengeStore()
    const { challenges, loading } = storeToRefs(challengeStore)

    onMounted(() => {
      challengeStore.loadChallenges()
    })

    const goDetail = (challengeId) => {
      router.push({
        name: 'challengeDetail',
        params: { challengeId },
      })
    }

    return {
      challenges,
      loading,
      goDetail,
    }
  },
}
</script>

