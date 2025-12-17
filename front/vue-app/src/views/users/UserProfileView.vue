<template>
  <Layout>
    <div class="bg-gray-50 min-h-screen">
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
                  <UiButton
                    class="w-full md:w-auto"
                    :variant="profile.isFollowing ? 'outline' : 'default'"
                    @click="toggleFollow"
                  >
                    {{ profile.isFollowing ? '팔로잉 중' : '팔로우' }}
                  </UiButton>
                  <UiButton
                    class="w-full md:w-auto bg-gray-100 text-gray-700"
                    variant="outline"
                    @click="toggleBlock"
                  >
                    {{ profile.isBlocked ? '차단 해제' : '차단' }}
                  </UiButton>
                </div>
              </div>
            </UiCard>

            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="space-y-3">
                <p class="text-sm font-semibold text-gray-600">활동 통계</p>
                <div class="grid grid-cols-3 gap-2 text-center">
                  <div
                    v-for="stat in stats"
                    :key="stat.label"
                    class="rounded-md bg-gray-50 py-3"
                  >
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
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">최근 게시글</p>
                <h2 class="text-2xl font-bold">Posts</h2>
              </div>
              <span class="inline-flex items-center px-3 py-1 rounded-full bg-white border border-border text-sm shadow-sm">
                총 {{ totalPosts }}개
              </span>
            </div>

            <UiCard wrapperClass="border border-border bg-white shadow-sm">
              <div class="grid gap-6 md:grid-cols-2">
                <div class="space-y-3">
                  <p class="text-sm font-semibold text-gray-700">게시판 활동</p>
                  <div class="flex items-center gap-4">
                    <div class="relative w-36 h-36">
                      <div
                        class="w-full h-full rounded-full"
                        :style="{ background: boardGradient }"
                      ></div>
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
                  <p class="text-sm font-semibold text-gray-700">반응 요약</p>
                  <div class="flex items-center gap-4">
                    <div class="relative w-36 h-36">
                      <div
                        class="w-full h-full rounded-full"
                        :style="{ background: engagementGradient }"
                      ></div>
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
                <UiCard
                  v-for="post in filteredPosts"
                  :key="post.id"
                  wrapperClass="border border-border bg-white shadow-sm"
                >
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

                <UiCard v-if="!filteredPosts.length" wrapperClass="border border-dashed border-border bg-white">
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

<script lang="ts" setup>
import { computed, reactive, ref } from 'vue'
import Layout from '../../components/Layout.vue'
import UiAvatar from '../../components/ui/Avatar.vue'
import UiButton from '../../components/ui/Button.vue'
import UiCard from '../../components/ui/Card.vue'

type Post = {
  id: number
  board: string
  boardName: string
  title: string
  excerpt: string
  date: string
  likes: number
  comments: number
}

type Stat = {
  label: string
  value: string | number
}

const profile = reactive({
  nickname: 'user_nickname',
  userId: 'user_id_123',
  avatarUrl: 'https://api.dicebear.com/7.x/thumbs/svg?seed=vue-user',
  bio: 'Just sharing my thoughts and experiences. Coffee enthusiast. Tech lover.',
  location: 'Seoul, South Korea',
  joinedDate: '2024년 3월 합류',
  isFollowing: false,
  isBlocked: false,
})

const stats: Stat[] = [
  { label: 'Posts', value: 42 },
  { label: 'Followers', value: '1.2K' },
  { label: 'Following', value: 328 },
]

const posts = ref<Post[]>([
  {
    id: 1,
    board: 'general',
    boardName: '자유',
    title: '프로필을 열었습니다!',
    excerpt: '방금 새 프로필을 설정했어요. 앞으로 이곳에서 일상과 생각들을 공유할게요.',
    date: '2024-03-15',
    likes: 24,
    comments: 5,
  },
  {
    id: 2,
    board: 'tech',
    boardName: '기술',
    title: 'Next.js 15 후기',
    excerpt: '새로 나온 Next.js 15 기능들을 살펴봤는데, 성능과 DX 모두 괜찮네요.',
    date: '2024-03-14',
    likes: 89,
    comments: 12,
  },
  {
    id: 3,
    board: 'tech',
    boardName: '기술',
    title: '스케일에 강한 앱 만들기',
    excerpt: '규모가 커져도 유지보수 가능한 패턴을 정리해봤습니다.',
    date: '2024-03-12',
    likes: 156,
    comments: 23,
  },
  {
    id: 4,
    board: 'life',
    boardName: '라이프',
    title: '강남 카페 추천',
    excerpt: '원격 근무하기 좋은 분위기의 카페를 발견했어요. 추천합니다.',
    date: '2024-03-10',
    likes: 45,
    comments: 8,
  },
  {
    id: 5,
    board: 'general',
    boardName: '자유',
    title: '주말 계획',
    excerpt: '이번 주말에 한강 나들이 갈 예정입니다. 같이 가실 분?',
    date: '2024-03-08',
    likes: 32,
    comments: 15,
  },
  {
    id: 6,
    board: 'tech',
    boardName: '기술',
    title: 'TypeScript 팁',
    excerpt: '코드가 훨씬 깔끔해지는 타입스크립트 팁 몇 가지를 공유합니다.',
    date: '2024-03-05',
    likes: 203,
    comments: 31,
  },
])

const tabs = [
  { key: 'all', label: '전체' },
  { key: 'general', label: '자유' },
  { key: 'tech', label: '기술' },
  { key: 'life', label: '라이프' },
]

const activeTab = ref('all')

const totalPosts = computed(() => posts.value.length)

const filteredPosts = computed(() => {
  if (activeTab.value === 'all') return posts.value
  return posts.value.filter((post) => post.board === activeTab.value)
})

const boardActivity = computed(() => {
  const total = posts.value.length || 1
  return tabs
    .filter((tab) => tab.key !== 'all')
    .map((tab) => {
      const count = posts.value.filter((post) => post.board === tab.key).length
      return {
        label: tab.label,
        percent: Math.round((count / total) * 100),
      }
    })
})

const engagement = computed(() => {
  const totals = posts.value.reduce(
    (acc, post) => {
      acc.likes += post.likes
      acc.comments += post.comments
      return acc
    },
    { likes: 0, comments: 0 }
  )

  return [
    { label: '좋아요', value: totals.likes, percent: 0 },
    { label: '댓글', value: totals.comments, percent: 0 },
  ]
})

const toggleFollow = () => {
  profile.isFollowing = !profile.isFollowing
}

const toggleBlock = () => {
  profile.isBlocked = !profile.isBlocked
}

const donutColors = ['#f97316', '#3b82f6', '#10b981', '#a855f7', '#f59e0b']

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
  return posts.value.reduce((acc, post) => acc + post.likes + post.comments, 0)
})

const engagementSegments = computed(() => {
  const total = totalEngagement.value || 1
  let acc = 0
  const base = [
    { label: '좋아요', value: posts.value.reduce((sum, p) => sum + p.likes, 0) },
    { label: '댓글', value: posts.value.reduce((sum, p) => sum + p.comments, 0) },
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
</script>
