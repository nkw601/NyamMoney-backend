<template>
  <Layout>
    <div class="p-6 grid grid-cols-12 gap-6">
      <div class="col-span-12">
        <h1 class="text-2xl font-semibold">Analytics</h1>
      </div>

      <div class="col-span-8 space-y-4">
        <BusinessMetrics />
        <FinancialChart />
      </div>

      <div class="col-span-4 space-y-4">
        <AccountsOverview />
        <RecentTransactions />
      </div>
    </div>
  </Layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Layout from '../components/Layout.vue'
import BusinessMetrics from '../components/BusinessMetrics.vue'
import FinancialChart from '../components/FinancialChart.vue'
import AccountsOverview from '../components/AccountsOverview.vue'
import RecentTransactions from '../components/RecentTransactions.vue'

export default defineComponent({
  components: { Layout, BusinessMetrics, FinancialChart, AccountsOverview, RecentTransactions }
})
</script>

<style scoped></style>
