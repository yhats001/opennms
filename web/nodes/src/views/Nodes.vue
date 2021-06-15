<template>
  <NodesTable />
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
