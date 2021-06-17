<template>
  Node details
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import API from '../services/nodesService'

export default defineComponent({
  name: 'NodeDetails',
  components: {

  },
  setup() {
    const route = useRoute()

    onMounted(() => {
      API.getNodeById(route.params.id as string)
    })
  }
})
</script>
