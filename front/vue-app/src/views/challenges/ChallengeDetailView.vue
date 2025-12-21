<template>
  <Layout>
    <div class="p-8 max-w-3xl mx-auto">
      <button
        class="mb-6 text-sm text-gray-500 hover:underline"
        @click="goBack">← 목록으로
      </button>

      <p v-if="loading">불러오는 중...</p>

    <div v-else-if="challenge" class="rounded-xl border p-6 bg-white">
        <button
            v-if="canJoin"
            :disabled="joining"
            @click="handleJoin"
            class="inline-flex items-center
            px-3 py-1.5
            text-sm font-medium
            rounded-full
            bg-orange-400 hover:bg-orange-500
            text-white
            transition
            disabled:opacity-50"
            >
            {{ joinButtonText }}
        </button>

    <p v-else class="text-sm text-gray-400 mt-4">
    참여할 수 없는 챌린지입니다.
    </p>
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
    <button
        v-if="isCreator"
        @click="goEdit"
        class="text-sm text-gray-400"
        >
        수정
    </button>
    <button
      v-if="canDelete"
      @click="handleDelete"
      class="text-sm text-red-500 hover:text-red-600"
    >
      삭제
    </button>


    </div>
  </Layout>
</template>

<script>
import { onMounted, computed } from 'vue'
import { useRouter, useRoute, } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useChallengeStore } from '@/stores/challenge.store'
import Layout from '@/components/Layout.vue'
import { useAuthStore } from '../../stores/auth'

export default {
  components: { Layout },

  props: {
    challengeId: {
      type: Number,
      required: true,
    },
  },

  setup(props) {
    const challengeStore = useChallengeStore()
    const authStore = useAuthStore()
    const { challengeDetail: challenge, loading } = storeToRefs(challengeStore)
    const router = useRouter()
    const route = useRoute()

    const challengeId = route.params.challengeId

    onMounted(() => {
      challengeStore.loadChallengeDetail(props.challengeId)
    })

    const goBack = () => {
      router.push({ name: 'Projects'})
    }

    const canJoin = computed(() => {
        if (!challenge.value) return false
        console.log('status:', challenge.value.status)
        return ['UPCOMING', 'ACTIVE'].includes(challenge.value.status)
        
    })

    const joinButtonText = computed(() => {
        if (!challenge.value) return ''
        return challenge.value.isJoined ? '참여 취소' : '챌린지 참여'
    })

    const joinButtonClass = computed(() => {
        return challenge.value?.isJoined
        ? 'bg-gray-200 text-gray-700 hover:bg-gray-300'
        : 'bg-orange-500 text-white hover:bg-orange-600'
    })

    const handleJoin = () => {
        challengeStore.toggleJoin(challenge.value.challengeId)
    }

    const goEdit = () => {
        //const challengeId = challenge.value.challengeId
        router.push({
            name: 'challengeEdit',
            params: { challengeId },
        })
    }
    // 생성한 사람인지 체크
    const isCreator = computed(() => {
        if (!challenge.value) return false
        return challenge.value.userId === authStore.userId
    })

    const canDelete = computed(() => {
      if (!challenge.value) return false
      return (
        isCreator.value &&
        challenge.value.status === 'UPCOMING'
      )
    })

    const handleDelete = async () => {
      //const challengeId = challenge.value.challengeId
      if (!confirm('이 챌린지를 삭제하시겠습니까?\n참여자 전원에게 환불됩니다.')) {
        return
      }

      await challengeStore.deleteChallenge(challengeId)

      // 삭제 후 목록으로 이동 (back 아님)
      router.replace({ name: 'Projects' })
    }




    return {
      challenge, loading, goBack,
      canJoin, joinButtonText, joinButtonClass, handleJoin,
      goEdit, isCreator,
      canDelete, handleDelete,
    }
  },
}
</script>
