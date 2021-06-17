<template>
  <Paginator :first="offset" :rows="limit" :total-records="totalCount" @page="onPage($event)" :rowsPerPageOptions="[5,20,30]" />
</template>
  
<script lang="ts">
import { defineComponent, ref, computed, onMounted } from 'vue'
import Paginator, { PageState } from 'primevue/paginator'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'Pagination',
  components: {
    Paginator
  },
  props: {
    call: {
      type: String,
      required: true
    },  
    parameters: {
      type: Object,
      required: true
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const limit = ref(props.parameters.limit)
    const offset = ref(props.parameters.offset)

    onMounted(() => store.dispatch(props.call, props.parameters))

    const totalCount = computed(() => store.state.nodesModule.totalCount)

    const onPage = (event: PageState) => {
      limit.value = event.rows
      offset.value = event.rows * event.page
      const updatedParameters = { ...props.parameters, limit: limit.value, offset: offset.value }
      emit('update-query-parameters', updatedParameters)
      store.dispatch(props.call, updatedParameters)
    }

    return {
      limit,
      offset,
      totalCount,
      onPage
    }
  }
})
</script>
  