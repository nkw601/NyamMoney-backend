// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Signup from '../views/users/Signup.vue'
import Settings from '../views/Settings.vue'
import Analytics from '../views/Analytics.vue'
import Organization from '../views/Organization.vue'
import Projects from '../views/Projects.vue'
import Transactions from '../views/Transactions.vue'
import Invoices from '../views/Invoices.vue'
import Payments from '../views/Payments.vue'
import Members from '../views/Members.vue'
import Permissions from '../views/Permissions.vue'
import Chat from '../views/Chat.vue'
import Meetings from '../views/Meetings.vue'
import Help from '../views/Help.vue'
import { useAuthStore } from '../stores/auth'
import Login from '../views/users/Login.vue'

const routes = [
  // 루트 → 로그인으로 리다이렉트
  { path: '/', redirect: '/login' },
  // { path: '/', name: 'RootLogin', component: Login },


  // 인증 불필요
  { path: '/login', name: 'Login', component: Login },
  { path: '/signup', name: 'Signup', component: Signup },
  { path: '/help', name: 'Help', component: Help },

  // 인증 필요한 라우트들
  { path: '/dashboard', name: 'Dashboard', component: Home, meta: { requiresAuth: true } },
  { path: '/settings', name: 'Settings', component: Settings, meta: { requiresAuth: true } },
  { path: '/analytics', name: 'Analytics', component: Analytics, meta: { requiresAuth: true } },
  { path: '/organization', name: 'Organization', component: Organization, meta: { requiresAuth: true } },
  { path: '/projects', name: 'Projects', component: Projects, meta: { requiresAuth: true } },
  { path: '/transactions', name: 'Transactions', component: Transactions, meta: { requiresAuth: true } },
  { path: '/invoices', name: 'Invoices', component: Invoices, meta: { requiresAuth: true } },
  { path: '/payments', name: 'Payments', component: Payments, meta: { requiresAuth: true } },
  { path: '/members', name: 'Members', component: Members, meta: { requiresAuth: true } },
  { path: '/permissions', name: 'Permissions', component: Permissions, meta: { requiresAuth: true } },
  { path: '/chat', name: 'Chat', component: Chat, meta: { requiresAuth: true } },
  { path: '/meetings', name: 'Meetings', component: Meetings, meta: { requiresAuth: true } },
  { path: '/boards/:boardId/posts/:postId', name:'PostDetail', component: ()=>import('../views/posts/PostDetailView.vue'), meta: { requiresAuth: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// restore()를 한 번만 호출하기 위한 플래그

let restored = false

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()

  // 앱 최초 라우팅 시에만 쿠키에서 토큰 복원
  if (!restored) {
    auth.setAuthFromToken()   // ← 여기!
    restored = true
  }

  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return next({ path: '/login' })
  }

  return next()
})

export default router