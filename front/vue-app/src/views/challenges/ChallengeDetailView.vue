<template>
  <Layout>
    <div class="p-8 max-w-3xl mx-auto">
      <button
        class="mb-6 text-sm text-gray-500 hover:underline"
        @click="goBack"
      >
        ← 목록으로
      </button>

      <p v-if="loading">불러오는 중...</p>

      <div v-else-if="challenge" class="rounded-xl border p-6 bg-white">
        <h1 class="text-2xl font-bold mb-2">
          {{ challenge.title }}
        </h1>

        <p class="text-gray-600 mb-4">
          {{ challenge.description }}
        </p>

        <p class="text-sm text-gray-500 mb-2">
          기간: {{ challenge.startDate }} ~ {{ challenge.endDate }}
        </p>

        <p class="text-sm mb-4">
          참여자 {{ challenge.participantCount }}명
        </p>

        <span
          v-if="challenge.isJoined"
          class="inline-block px-3 py-1 text-sm rounded-full
                 bg-green-100 text-green-700"
        >
          참여 중
        </span>
      </div>
    </div>
  </Layout>
</template>

<script>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useChallengeStore } from '@/stores/challenge.store'
import Layout from '@/components/Layout.vue'

export default {
  components: { Layout },

  props: {
    challengeId: {
      type: Number,
      required: true,
    },
  },

  setup(props) {
    const router = useRouter()
    const store = useChallengeStore()
    const { challengeDetail: challenge, loading } = storeToRefs(store)

    onMounted(() => {
      store.loadChallengeDetail(props.challengeId)
    })

    const goBack = () => {
      router.back()
    }

    return {
      challenge,
      loading,
      goBack,
    }
  },
}
</script>
