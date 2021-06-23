<template>
  init graph
</template>
  
<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import useQueryParameters from '@/hooks/useQueryParams'

export default defineComponent({
  name: 'Node Availability Graph',
  components: {
  },
  setup() {
    const call = 'ifServicesModule/getNodeIfServices'
    const store = useStore()
    const route = useRoute()
    const { queryParameters } = useQueryParameters({
      //_s: `node.id==${route.params.id}`
    }, call)

    onMounted(() => {
      store.dispatch(call, queryParameters.value)
    })
   
    return {
      queryParameters
    }
  }
})
</script>
  