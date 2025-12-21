<template>
  <Layout>
    <div class="p-8">
      <ChallengeListView />
    </div>
  </Layout>
</template>

<script>
import Layout from '@/components/Layout.vue'
import ChallengeListView from './challenges/ChallengeListView.vue';

export default {
  components: {
    Layout,
    ChallengeListView,
  },
}
</script>
