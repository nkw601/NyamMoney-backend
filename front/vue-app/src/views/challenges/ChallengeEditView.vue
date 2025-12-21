<template>
  <Layout>
    <div class="p-8 max-w-xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">챌린지 수정</h1>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <!-- 제목 -->
        <div>
          <label class="block text-sm font-medium mb-1">제목</label>
          <input
            v-model="form.title"
            type="text"
            class="w-full border rounded px-3 py-2"
          />
        </div>

        <!-- 설명 -->
        <div>
          <label class="block text-sm font-medium mb-1">설명</label>
          <textarea
            v-model="form.description"
            rows="3"
            class="w-full border rounded px-3 py-2"
          />
        </div>

        <!-- 기간 -->
        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-medium mb-1">시작일</label>
            <input
              v-model="form.startDate"
              type="date"
              class="w-full border rounded px-3 py-2"
            />
          </div>

          <div>
            <label class="block text-sm font-medium mb-1">종료일</label>
            <input
              v-model="form.endDate"
              type="date"
              class="w-full border rounded px-3 py-2"
            />
          </div>
        </div>

        <!-- 버튼 -->
        <button
          type="submit"
          :disabled="updating"
          class="inline-flex items-center
                 px-3 py-1.5
                 text-sm font-medium
                 rounded-full
                 bg-yummoney-primary text-black
                 hover:bg-yummoney-primaryHover
                 transition
                 disabled:opacity-50"
        >
          수정 저장
        </button>
      </form>
    </div>
  </Layout>
</template>

<script>
import { reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useChallengeStore } from '@/stores/challenge.store'
import Layout from '@/components/Layout.vue'

export default {
  components: { Layout },

  setup() {
    const route = useRoute()
    const router = useRouter()
    const store = useChallengeStore()
    const { challengeDetail, updating } = storeToRefs(store)

    const challengeId = route.params.challengeId

    const form = reactive({
      title: '',
      description: '',
      startDate: '',
      endDate: '',
    })

    onMounted(async () => {
      if (!store.challengeDetail) {
        await store.loadChallengeDetail(challengeId)
      }

      const c = store.challengeDetail
      form.title = c.title
      form.description = c.description
      form.startDate = c.startDate
      form.endDate = c.endDate
    })

    const handleSubmit = async () => {
      const payload = {
        title: form.title,
        description: form.description,
        startDate: form.startDate,
        endDate: form.endDate,
      }

      if (form.title) payload.title = form.title
      if (form.description) payload.description = form.description
      if (form.startDate) payload.startDate = form.startDate
      if (form.endDate) payload.endDate = form.endDate

      await store.updateChallenge(challengeId, payload)

      // 수정 후 상세로
      router.replace({
        name: 'challengeDetail',
        params: { challengeId },
      })
    }

    return {
      form,
      handleSubmit,
      updating,
    }
  },
}
</script>
