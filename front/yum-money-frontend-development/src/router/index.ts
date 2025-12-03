import { createRouter, createWebHistory } from "vue-router"
import { useAuthStore } from "@/stores/auth.store"

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      redirect: "/dashboard",
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("@/views/LoginView.vue"),
      meta: { requiresGuest: true },
    },
    {
      path: "/signup",
      name: "Signup",
      component: () => import("@/views/SignupView.vue"),
      meta: { requiresGuest: true },
    },
    {
      path: "/dashboard",
      name: "Dashboard",
      component: () => import("@/views/DashboardView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/transactions",
      name: "Transactions",
      component: () => import("@/views/TransactionsView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/challenges",
      name: "Challenges",
      component: () => import("@/views/ChallengesView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/challenges/:id",
      name: "ChallengeDetail",
      component: () => import("@/views/ChallengeDetailView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/social",
      name: "Social",
      component: () => import("@/views/SocialFeedView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/profile/:userId?",
      name: "Profile",
      component: () => import("@/views/ProfileView.vue"),
      meta: { requiresAuth: true },
    },
  ],
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth)
  const requiresGuest = to.matched.some((record) => record.meta.requiresGuest)

  if (requiresAuth && !authStore.isAuthenticated) {
    // Redirect to login if not authenticated
    next({ name: "Login", query: { redirect: to.fullPath } })
  } else if (requiresGuest && authStore.isAuthenticated) {
    // Redirect to dashboard if already logged in
    next({ name: "Dashboard" })
  } else {
    next()
  }
})

export default router
