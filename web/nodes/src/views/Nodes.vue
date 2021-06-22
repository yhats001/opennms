<template>
  <div class="page">
    <NodesTable />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import NodesTable from '@/components/NodesTable.vue'

export default defineComponent({
  name: 'Nodes',
  components: {
    NodesTable
  }
})
</script>
