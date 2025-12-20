<template>
  <Layout>
    <div>
      <h1 class="text-2xl font-bold mb-6">챌린지</h1>
<!-- 헤더 영역 -->
      <div class="flex justify-between items-center mb-6">
        <!-- 챌린지 생성 버튼 -->
        <button
          @click="goCreate"
          class="inline-flex items-center
                 px-3 py-1.5
                 text-sm font-medium
                 rounded-full
                 border border-red-500
                 bg-yummoney-primary text-black
                 hover:bg-yummoney-primaryHover
                 transition">+ 챌린지 생성</button>
      </div>

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
          :class="statusStyleMap[c.status]?.border"
          @click="goDetail(c.challengeId)"
        >
          <!-- 🔥 상태 뱃지 -->
          <span
            class="inline-block mb-2 px-2 py-1 text-xs font-medium rounded-full"
            :class="statusStyleMap[c.status]?.badge"
          >
            {{ statusStyleMap[c.status]?.label }}
          </span>

          <!-- 제목 -->
          <h2 class="font-semibold text-lg mb-2">
            {{ c.title }}
          </h2>

          <!-- 설명 -->
          <p class="text-sm text-gray-600 mb-4 line-clamp-2">
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

            <!-- 참여 중 표시 (개인 상태) -->
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

export default {
  setup() {
    const router = useRouter()
    const challengeStore = useChallengeStore()
    const { challenges, loading } = storeToRefs(challengeStore)

    // 🔥 상태별 UI 스타일 매핑
    const statusStyleMap = {
      UPCOMING: {
        badge: 'bg-blue-100 text-blue-700',
        border: 'hover:border-blue-400',
        label: '곧 시작',
      },
      ACTIVE: {
        badge: 'bg-green-100 text-green-700',
        border: 'hover:border-green-500',
        label: '진행 중',
      },
      ENDED: {
        badge: 'bg-gray-100 text-gray-500',
        border: 'hover:border-gray-300',
        label: '종료됨',
      },
      CLOSED: {
        badge: 'bg-gray-100 text-gray-500',
        border: 'hover:border-gray-300',
        label: '마감됨',
      },
    }

    onMounted(() => {
      challengeStore.loadChallenges()
    })

    const goDetail = (challengeId) => {
      router.push({
        name: 'challengeDetail',
        params: { challengeId },
      })
    }
    // 🔥 챌린지 생성 이동
    const goCreate = () => {
        router.push({
        name: 'challengeCreate',
        })
    }

    return {
      challenges, loading, goDetail,
      statusStyleMap,
      goCreate,
    }
  },
}
</script>
