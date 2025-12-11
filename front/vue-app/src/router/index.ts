import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Settings from '../views/Settings.vue'
import Analytics from '../views/Analytics.vue'
import Signup from '../views/Signup.vue'
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

const routes = [
  { path: '', name: 'Login', component: Login },
  { path: '/', name: 'Login', component: Login },
  { path: '/login', name: 'Login', component: Login },
  { path: '/dashboard', name: 'Dashboard', component: Home, meta: { requiresAuth: true } },
  { path: '/settings', name: 'Settings', component: Settings, meta: { requiresAuth: true } }
  ,{ path: '/analytics', name: 'Analytics', component: Analytics, meta: { requiresAuth: true } }
  ,{ path: '/signup', name: 'Signup', component: Signup }
  ,{ path: '/organization', name: 'Organization', component: Organization, meta: { requiresAuth: true } }
  ,{ path: '/projects', name: 'Projects', component: Projects, meta: { requiresAuth: true } }
  ,{ path: '/transactions', name: 'Transactions', component: Transactions, meta: { requiresAuth: true } }
  ,{ path: '/invoices', name: 'Invoices', component: Invoices, meta: { requiresAuth: true } }
  ,{ path: '/payments', name: 'Payments', component: Payments, meta: { requiresAuth: true } }
  ,{ path: '/members', name: 'Members', component: Members, meta: { requiresAuth: true } }
  ,{ path: '/permissions', name: 'Permissions', component: Permissions, meta: { requiresAuth: true } }
  ,{ path: '/chat', name: 'Chat', component: Chat, meta: { requiresAuth: true } }
  ,{ path: '/meetings', name: 'Meetings', component: Meetings, meta: { requiresAuth: true } }
  ,{ path: '/help', name: 'Help', component: Help }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()
  auth.restore()
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return next({ path: '/' })
  }
  // if (to.path === '/' && auth.isAuthenticated) {
  //   return next({ path: '/dashboard' })
  // }
  return next()
})

export default router
