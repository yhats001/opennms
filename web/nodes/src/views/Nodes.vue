<template>
  <NodesTable />
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue'
import NodesTable from '@/components/NodesTable.vue'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'Nodes',
  components: {
    NodesTable
  },
  setup() {
    const store = useStore()
    onMounted(() => {
      console.log('Mounting Nodes.vue')
      store.dispatch('getNodes')
    })
  }
})
</script>
