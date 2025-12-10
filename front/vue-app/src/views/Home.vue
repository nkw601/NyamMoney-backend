<template>
  <Layout>
    <div class="p-6 space-y-10">

      <h1 class="text-3xl font-bold">Dashboard</h1>

      <!-- 1번 섹션 -->
      <div class="space-y-3">
        <h2 class="text-xl font-semibold">주제 1</h2>
        <section class="grid gap-6 md:grid-cols-2 xl:grid-cols-3">
          <AccountOverview />
          <RecentTransactions />
          <QuickBillPay />
        </section>
      </div>

      <!-- 2번 섹션 -->
      <div class="space-y-3">
        <h2 class="text-xl font-semibold">주제 2</h2>
        <section class="grid gap-6 lg:grid-cols-2">
          <FinancialChart />
          <BudgetTracker />
        </section>
      </div>

      <!-- 3번 섹션 -->
      <div class="space-y-3">
        <h2 class="text-xl font-semibold">주제 3</h2>
        <section class="grid gap-6 lg:grid-cols-2">
          <SavingsGoals />
          <AccountsOverview />
        </section>
      </div>

      <!-- 4번 섹션 -->
      <div class="space-y-3">
        <h2 class="text-xl font-semibold">주제 4</h2>
        <BusinessMetrics />
      </div>

    </div>
  </Layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Layout from '../components/Layout.vue'
import AccountOverview from '../components/AccountOverview.vue'
import QuickActions from '../components/QuickActions.vue'
import RecentTransactions from '../components/RecentTransactions.vue'
import FinancialChart from '../components/FinancialChart.vue'
import BudgetTracker from '../components/BudgetTracker.vue'
import SavingsGoals from '../components/SavingsGoals.vue'
import AccountsOverview from '../components/AccountsOverview.vue'
import BusinessMetrics from '../components/BusinessMetrics.vue'

export default defineComponent({
  components: {
    Layout,
    AccountOverview,
    QuickActions,
    RecentTransactions,
    FinancialChart,
    BudgetTracker,
    SavingsGoals,
    AccountsOverview,
    BusinessMetrics,
  },
})
</script>

<style scoped>
</style>
