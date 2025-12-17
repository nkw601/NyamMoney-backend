<template>
  <Layout>
    <div class="min-h-screen">
      <div class="max-w-6xl mx-auto p-8 space-y-8">
        <div class="grid gap-8 lg:grid-cols-[320px_1fr]">
          <!-- Profile column -->
          <div class="space-y-4">
            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="flex flex-col items-center md:items-start gap-3 text-center md:text-left">
                <UiAvatar
                  :src="profile.avatarUrl"
                  :alt="profile.nickname"
                  :name="profile.nickname"
                  size="lg"
                  className="border-2 border-border"
                />
                <div>
                  <p class="text-sm text-gray-500">@{{ profile.userId }}</p>
                  <h1 class="text-2xl font-bold leading-tight">{{ profile.nickname }}</h1>
                </div>

                <div class="flex flex-col md:flex-row w-full gap-2 mt-2">
                  <template v-if="isMyProfile">
                    <UiButton
                      class="w-full md:w-auto bg-white text-gray-700 border border-border"
                      variant="outline"
                      @click="goEdit"
                    >
                      내 정보 수정
                    </UiButton>
                  </template>

                  <template v-else>
                    <UiButton
                      class="w-full md:w-auto"
                      :variant="followButtonVariant"
                      :disabled="profile.isBlocked || loadingFollow"
                      @click="toggleFollow"
                    >
                      {{ followButtonLabel }}
                    </UiButton>

                    <UiButton
                      class="w-full md:w-auto bg-gray-100 text-gray-700"
                      variant="outline"
                      :disabled="loadingBlock"
                      @click="toggleBlock"
                    >
                      {{ profile.isBlocked ? '차단 해제' : '차단' }}
                    </UiButton>
                  </template>
                </div>
              </div>
            </UiCard>

            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="space-y-3">
                <p class="text-sm font-semibold text-gray-600">활동 통계</p>
                <div class="grid grid-cols-3 gap-2 text-center">
                  <div v-for="stat in stats" :key="stat.label" class="rounded-md bg-gray-50 py-3">
                    <p class="text-xl font-bold">{{ stat.value }}</p>
                    <p class="text-xs text-gray-500">{{ stat.label }}</p>
                  </div>
                </div>
              </div>
            </UiCard>

            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="space-y-3">
                <div class="space-y-1">
                  <p class="text-sm font-semibold text-gray-600">한 줄 소개</p>
                  <p class="text-sm text-gray-600 leading-relaxed">{{ profile.bio }}</p>
                </div>
                <div class="flex items-center gap-2 text-sm text-gray-500">
                  <span class="text-xs font-semibold text-gray-400">위치</span>
                  <span>{{ profile.location }}</span>
                </div>
                <div class="flex items-center gap-2 text-sm text-gray-500">
                  <span class="text-xs font-semibold text-gray-400">가입</span>
                  <span>{{ profile.joinedDate }}</span>
                </div>
              </div>
            </UiCard>
          </div>

          <!-- Posts + activity column -->
          <div class="space-y-6">
            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="grid gap-6 md:grid-cols-2">
                <div class="space-y-3">
                  <p class="text-sm font-semibold text-gray-700">소비내역 분석</p>
                  <div class="flex items-center gap-4">
                    <div class="relative w-36 h-36">
                      <div class="w-full h-full rounded-full" :style="{ background: boardGradient }"></div>
                      <div class="absolute inset-4 rounded-full bg-white shadow-inner flex items-center justify-center text-center text-sm font-semibold">
                        <div>
                          <p class="text-xs text-gray-500">총 게시글</p>
                          <p class="text-lg">{{ totalPosts }}</p>
                        </div>
                      </div>
                    </div>
                    <ul class="space-y-2 text-sm text-gray-600">
                      <li v-for="seg in boardSegments" :key="seg.label" class="flex items-center gap-2">
                        <span class="inline-block w-3 h-3 rounded-full" :style="{ background: seg.color }"></span>
                        <span class="w-14">{{ seg.label }}</span>
                        <span class="text-gray-500">{{ seg.percent }}%</span>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="space-y-3">
                  <p class="text-sm font-semibold text-gray-700">예산?</p>
                  <div class="flex items-center gap-4">
                    <div class="relative w-36 h-36">
                      <div class="w-full h-full rounded-full" :style="{ background: engagementGradient }"></div>
                      <div class="absolute inset-4 rounded-full bg-white shadow-inner flex items-center justify-center text-center text-sm font-semibold">
                        <div>
                          <p class="text-xs text-gray-500">총 반응</p>
                          <p class="text-lg">{{ totalEngagement }}</p>
                        </div>
                      </div>
                    </div>
                    <ul class="space-y-2 text-sm text-gray-600">
                      <li v-for="seg in engagementSegments" :key="seg.label" class="flex items-center gap-2">
                        <span class="inline-block w-3 h-3 rounded-full" :style="{ background: seg.color }"></span>
                        <span class="w-14">{{ seg.label }}</span>
                        <span class="text-gray-500">{{ seg.percent }}%</span>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </UiCard>

            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">최근 게시글</p>
                <h2 class="text-2xl font-bold">Posts</h2>
              </div>
              <span class="inline-flex items-center px-3 py-1 rounded-full bg-white border border-border text-sm shadow-sm">
                총 {{ totalPosts }}개
              </span>
            </div>

            <div class="space-y-4">
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="tab in tabs"
                  :key="tab.key"
                  @click="activeTab = tab.key"
                  :class="[
                    'px-3 py-1 rounded-full text-sm border transition-colors',
                    activeTab === tab.key
                      ? 'bg-primary text-primary-foreground border-primary'
                      : 'bg-white text-gray-600 border-border hover:bg-gray-50'
                  ]"
                  type="button"
                >
                  {{ tab.label }}
                </button>
              </div>

              <div class="space-y-3">
                <UiCard v-for="post in filteredPosts" :key="post.id" wrapperClass="border border-border bg-white shadow-sm">
                  <div class="space-y-2">
                    <div class="flex items-center justify-between text-sm text-gray-500">
                      <div class="flex items-center gap-2">
                        <span class="px-2 py-1 rounded-full bg-orange-50 text-orange-600 text-xs font-semibold">
                          {{ post.boardName }}
                        </span>
                        <span>{{ post.date }}</span>
                      </div>
                      <div class="flex items-center gap-4 text-xs">
                        <span class="flex items-center gap-1">좋아요 {{ post.likes }}</span>
                        <span class="flex items-center gap-1">댓글 {{ post.comments }}</span>
                      </div>
                    </div>
                    <h3 class="font-semibold text-lg text-gray-900">{{ post.title }}</h3>
                    <p class="text-sm text-gray-600 leading-relaxed">{{ post.excerpt }}</p>
                  </div>
                </UiCard>

                <UiCard v-if="loadingPosts" wrapperClass="border border-dashed border-border bg-white">
                  <div class="text-center text-gray-500 py-6">불러오는 중...</div>
                </UiCard>
                <UiCard v-else-if="!filteredPosts.length" wrapperClass="border border-dashed border-border bg-white">
                  <div class="text-center text-gray-500 py-6">이 카테고리에 게시글이 없습니다.</div>
                </UiCard>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import { fetchUser } from '@/services/user.service'
import {
  requestFollow,
  unfollow,
  cancelFollowRequest,
  fetchFollowStatus,
  block,
  unblock,
  fetchFollowers,
  fetchFollowings,
} from '@/services/follow.service'
import { fetchBoards, fetchPostsByBoard } from '@/services/board.service'
import { useAuthStore } from '@/stores/auth'
import Layout from '../../components/Layout.vue'
import UiAvatar from '../../components/ui/Avatar.vue'
import UiButton from '../../components/ui/Button.vue'
import UiCard from '../../components/ui/Card.vue'

const toStr = (v) => (v === null || v === undefined ? '' : String(v))

const profile = reactive({
  nickname: '',
  userId: '',
  avatarUrl: '',
  bio: '',
  location: '',
  joinedDate: '',
  followStatus: 'NONE',     // 'NONE' | 'PENDING' | 'ACCEPTED'
  followRequestId: '',      // PENDING일 때 취소용
  isBlocked: false
})

const auth = useAuthStore()
const { userId: myUserId } = storeToRefs(auth)
const route = useRoute()
const router = useRouter()

const targetUserId = computed(() => {
  const fromRoute = route.query.userId
  const own = myUserId.value ? String(myUserId.value) : ''
  return (fromRoute ? String(fromRoute) : '') || own || profile.userId
})

const isMyProfile = computed(() => {
  if (!profile.userId || !myUserId.value) return false
  return String(profile.userId) === String(myUserId.value)
})

const followButtonLabel = computed(() => {
  if (profile.isBlocked) return '차단됨'
  if (profile.followStatus === 'PENDING') return '신청 중'
  if (profile.followStatus === 'ACCEPTED') return '팔로잉'
  return '팔로우'
})

const followButtonVariant = computed(() => {
  if (profile.followStatus === 'PENDING' || profile.followStatus === 'ACCEPTED') return 'outline'
  return 'default'
})

const loadingFollow = ref(false)
const loadingBlock = ref(false)

// --- Profile & Relationship loaders ---
const loadProfile = async () => {
  if (!targetUserId.value) return
  try {
    const res = await fetchUser(String(targetUserId.value))
    const data = res?.data ?? res

    profile.userId = toStr(data?.userId ?? targetUserId.value)
    profile.nickname = data?.nickname ?? ''
    profile.avatarUrl = data?.profileImageUrl ?? data?.avatarUrl ?? ''
    profile.bio = data?.bio ?? ''
    profile.location = data?.location ?? ''
    profile.joinedDate = data?.createdAt ?? data?.joinedDate ?? ''
  } catch (err) {
    console.error('Failed to load profile', err)
  }
}


const loadRelationship = async () => {
  if (!targetUserId.value) return
  // 내 프로필이면 관계 조회 의미 없음
  if (String(targetUserId.value) === String(myUserId.value || '')) {
    profile.followStatus = 'NONE'
    profile.followRequestId = ''
    profile.isBlocked = false
    return
  }

  try {
    const res = await fetchFollowStatus(String(targetUserId.value))
    const data = res?.data ?? res

    // ✅ 백엔드 응답
    // { status: "NONE"|"PENDING"|"ACCEPTED"|"BLOCKED", requestId: 123 }
    const status = (data?.status ?? 'NONE').toUpperCase()

    profile.isBlocked = status === 'BLOCKED'
    profile.followStatus = status === 'PENDING' || status === 'ACCEPTED' ? status : 'NONE'
    profile.followRequestId = data?.requestId ? String(data.requestId) : ''
  } catch (err) {
    console.error('Failed to load follow status', err)
    profile.isBlocked = false
    profile.followStatus = 'NONE'
    profile.followRequestId = ''
  }
}

// 팔로워 / 팔로잉 수
const loadFollowCounts = async () => {
  try {
    const [followersRes, followingsRes] = await Promise.all([
      fetchFollowers(),
      fetchFollowings()
    ])

    const followersData = followersRes?.data ?? followersRes
    const followingsData = followingsRes?.data ?? followingsRes

    stats.value[1].value = followersData?.totalCount ?? 0
    stats.value[2].value = followingsData?.totalCount ?? 0
  } catch (err) {
    console.error('Failed to load follow counts', err)
    stats.value[1].value = 0
    stats.value[2].value = 0
  }
}

watch(
  targetUserId,
  async (id) => {
    if (!id) return
    profile.userId = String(id)
    await loadProfile()
    await loadRelationship()
    await loadBoards()
    await loadFollowCounts()
  },
  { immediate: true }
)

// --- Follow / Block actions ---
const toggleFollow = async () => {
  if (loadingFollow.value) return
  if (profile.isBlocked) return
  if (!profile.userId || isMyProfile.value) return

  loadingFollow.value = true
  try {
    if (profile.followStatus === 'NONE') {
      // 팔로우 신청
      const res = await requestFollow(profile.userId)
      const data = res?.data ?? res

      // createFollow 응답: { followId, status: "PENDING"|"ACCEPTED", ... }
      const next = (data?.status ?? 'PENDING').toUpperCase()
      profile.followStatus = next === 'ACCEPTED' ? 'ACCEPTED' : 'PENDING'
      profile.followRequestId = data?.followId ? String(data.followId) : ''

      // 서버가 requestId를 followId로 주면 여기로 충분
    } else if (profile.followStatus === 'PENDING') {
      // 신청 취소
      if (!profile.followRequestId) {
        await loadRelationship()
        return
      }
      await cancelFollowRequest(profile.followRequestId)
      profile.followStatus = 'NONE'
      profile.followRequestId = ''
    } else if (profile.followStatus === 'ACCEPTED') {
      // 언팔
      await unfollow(profile.userId)
      profile.followStatus = 'NONE'
      profile.followRequestId = ''
    }
  } catch (err) {
    console.error('follow toggle failed', err)
    await loadRelationship()
  } finally {
    loadingFollow.value = false
  }
}

const toggleBlock = async () => {
  if (loadingBlock.value) return
  if (!profile.userId || isMyProfile.value) return

  loadingBlock.value = true
  try {
    if (profile.isBlocked) {
      await unblock(profile.userId)
      profile.isBlocked = false
      await loadRelationship()
    } else {
      await block(profile.userId)
      profile.isBlocked = true
      // 차단하면 팔로우 관계는 끊긴다고 했으니 프론트도 정리
      profile.followStatus = 'NONE'
      profile.followRequestId = ''
    }
  } catch (err) {
    console.error('block toggle failed', err)
    await loadRelationship()
  } finally {
    loadingBlock.value = false
  }
}

const goEdit = () => {
  router.push('/me')
}

// --- Stats / posts (기존 로직 유지) ---
const stats = ref([
  { label: 'Posts', value: 0 },
  { label: 'Followers', value: 0 },
  { label: 'Following', value: 0 }
])

const posts = ref([])
const boards = ref([])
const loadingPosts = ref(false)

const tabs = computed(() => [
  { key: 'all', label: '전체' },
  ...boards.value.map((b) => ({ key: String(b.boardId), label: b.name }))
])

const activeTab = ref('all')

const totalPosts = computed(() => posts.value.length)

const filteredPosts = computed(() => {
  if (activeTab.value === 'all') return posts.value
  return posts.value.filter((post) => String(post.board) === String(activeTab.value))
})

const donutColors = ['#f97316', '#3b82f6', '#10b981', '#a855f7', '#f59e0b']

const boardActivity = computed(() => {
  const total = totalPosts.value || 1
  const source =
    boards.value.length > 0
      ? boards.value.map((b) => ({ label: b.name, count: b.postCount || 0 }))
      : tabs.value
          .filter((tab) => tab.key !== 'all')
          .map((tab) => ({
            label: tab.label,
            count: posts.value.filter((post) => post.board === tab.key).length
          }))

  return source.map((item) => ({
    label: item.label,
    percent: Math.round(((item.count || 0) / total) * 100)
  }))
})

const boardSegments = computed(() => {
  let acc = 0
  return boardActivity.value.map((item, idx) => {
    const deg = item.percent * 3.6
    const start = acc
    const end = acc + deg
    acc = end
    return { ...item, start, end, color: donutColors[idx % donutColors.length] }
  })
})

const boardGradient = computed(() => {
  if (!boardSegments.value.length) return '#e5e7eb 0deg 360deg'
  return boardSegments.value.map((seg) => `${seg.color} ${seg.start}deg ${seg.end}deg`).join(', ')
})

const totalEngagement = computed(() => {
  return posts.value.reduce((acc, post) => acc + (post.likes || 0) + (post.comments || 0), 0)
})

const engagementSegments = computed(() => {
  const total = totalEngagement.value || 1
  let acc = 0
  const base = [
    { label: '좋아요', value: posts.value.reduce((sum, p) => sum + (p.likes || 0), 0) },
    { label: '댓글', value: posts.value.reduce((sum, p) => sum + (p.comments || 0), 0) }
  ]
  return base.map((item, idx) => {
    const percent = Math.round((item.value / total) * 100)
    const deg = percent * 3.6
    const start = acc
    const end = acc + deg
    acc = end
    return { ...item, percent, start, end, color: donutColors[idx % donutColors.length] }
  })
})

const engagementGradient = computed(() => {
  if (!engagementSegments.value.length) return '#e5e7eb 0deg 360deg'
  return engagementSegments.value.map((seg) => `${seg.color} ${seg.start}deg ${seg.end}deg`).join(', ')
})

const loadPosts = async () => {
  loadingPosts.value = true
  try {
    if (activeTab.value === 'all') {
      const results = await Promise.all(boards.value.map((b) => fetchPostsByBoard(b.boardId)))
      const merged = results.flatMap((res, idx) =>
        (res?.data ?? []).map((p) => ({
          id: p.postId ?? p.id,
          board: String(boards.value[idx].boardId),
          boardName: boards.value[idx].name,
          title: p.title ?? '',
          excerpt: p.content ?? '',
          date: p.createdAt ?? '',
          likes: p.likeCount ?? 0,
          comments: p.commentCount ?? 0
        }))
      )
      posts.value = merged
    } else {
      const boardId = Number(activeTab.value)
      const res = await fetchPostsByBoard(boardId)
      posts.value =
        ((res?.data ?? [])).map((p) => ({
          id: p.postId ?? p.id,
          board: String(boardId),
          boardName: boards.value.find((b) => b.boardId === boardId)?.name ?? '',
          title: p.title ?? '',
          excerpt: p.content ?? '',
          date: p.createdAt ?? '',
          likes: p.likeCount ?? 0,
          comments: p.commentCount ?? 0
        })) ?? []
    }
    stats[0].value = posts.value.length
  } catch (err) {
    console.error('Failed to load posts', err)
    posts.value = []
    stats[0].value = 0
  } finally {
    loadingPosts.value = false
  }
}

const loadBoards = async () => {
  try {
    const res = await fetchBoards()
    boards.value = res?.data?.items ?? []
    await loadPosts()
  } catch (err) {
    console.error('Failed to load boards', err)
  }
}

watch(activeTab, () => {
  loadPosts()
})
</script>
