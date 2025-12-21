<template>
  <Layout>
    <div class="p-8 max-w-xl mx-auto">
      <h1 class="text-2xl font-bold mb-6">챌린지 생성</h1>
      <button
        class="mb-6 text-sm text-gray-500 hover:underline"
        @click="goBack">← 목록으로
      </button>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <!-- 제목 -->
        <div>
          <label class="block text-sm font-medium mb-1">제목</label>
          <input
            v-model="form.title"
            type="text"
            required
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
              required
              class="w-full border rounded px-3 py-2"
            />
          </div>

          <div>
            <label class="block text-sm font-medium mb-1">종료일</label>
            <input
              v-model="form.endDate"
              type="date"
              required
              class="w-full border rounded px-3 py-2"
            />
          </div>
        </div>

        <!-- 예산 -->
        <div>
          <label class="block text-sm font-medium mb-1">예산</label>
          <input
            v-model.number="form.budgetLimit"
            type="number"
            required
            min="0"
            class="w-full border rounded px-3 py-2"
          />
        </div>

        <!-- 버튼 -->
        <button
          type="submit"
          :disabled="creating"
          class="inline-flex items-center
                 px-3 py-1.5
                 text-sm font-medium
                 rounded-full
                 bg-yummoney-primary text-black
                 hover:bg-yummoney-primaryHover
                 transition
                 disabled:opacity-50">챌린지 생성
        </button>
      </form>
    </div>
  </Layout>
</template>

<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useChallengeStore } from '@/stores/challenge.store'
import Layout from '@/components/Layout.vue'

export default {
  components: { Layout },

  setup() {
    const router = useRouter()
    const store = useChallengeStore()
    const { creating } = storeToRefs(store)

    const form = reactive({
      title: '',
      description: '',
      startDate: '',
      endDate: '',
      budgetLimit: null,
    })

    const handleSubmit = async () => {
      const payload = {
        title: form.title,
        description: form.description || null,
        startDate: form.startDate,
        endDate: form.endDate,
        budgetLimit: form.budgetLimit,
      }

      const challengeId = await store.createChallenge(payload)

      // 생성 후 상세 페이지 이동
      router.push({
        name: 'challengeDetail',
        params: { challengeId },
      })
    }

    const goBack = () => {
      router.push({ name: 'Projects'})
    }

    return {
      form,
      handleSubmit,
      creating, goBack,
    }
  },
}
</script>
