<template>
  <DataTable :value="events" showGridlines dataKey="id" :loading="loading" responsiveLayout="scroll" @sort="sort" :lazy="true">

      <template #header>
        Recent Events
      </template>

      <template #empty>
          No data found.
      </template>

      <template #loading>
          Loading data. Please wait.
      </template>

      <template #footer>
        <Pagination 
          :parameters="queryParameters" 
          @update-query-parameters="updateQueryParameters" 
          moduleName="eventsModule"
          functionName="getEvents"
          totalCountStateName="totalCount"/>
      </template>

      <Column field="id" header="Id" :sortable="true">
        <template #body="{data}">
          <router-link :to="`/event/${data.id}`">
            {{ data.id }}
          </router-link>
        </template>
      </Column>

      <Column field="createTime" header="Created">
        <template #body="{data}">
            {{data.createTime}}
        </template>
      </Column>

      <Column field="severity" header="Severity">
        <template #body="{data}">
            {{data.severity}}
        </template>
      </Column>

      <Column field="logMessage" header="Message">
        <template #body="{data}">
          <span v-html="data.logMessage"></span> 
        </template>
      </Column>
  </DataTable>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue'
import DataTable from 'primevue/datatable'
import InputText from 'primevue/inputtext'
import Column from 'primevue/column'
import Pagination from './Pagination.vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import useQueryParameters from '@/hooks/useQueryParams'

export default defineComponent({
  name: 'EventsTable',
  components: {
    DataTable,
    InputText,
    Column,
    Pagination
  },
  setup() {
    const store = useStore()
    const route = useRoute()
    const loading = ref(false)

    const { queryParameters, sort, updateQueryParameters } = useQueryParameters({ 
      limit: 5, 
      offset: 0,
      _s: `node.id==${route.params.id}`
    }, 'eventsModule/getEvents')

    const events = computed(() => store.state.eventsModule.events)

    return {
      events,
      loading,
      queryParameters,
      updateQueryParameters,
      sort
    }
  }
})
</script>
