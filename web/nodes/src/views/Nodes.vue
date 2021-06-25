<template>
  <div class="p-grid page">
    <div class="p-col-12">
      <NodesTable />
    </div>
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
