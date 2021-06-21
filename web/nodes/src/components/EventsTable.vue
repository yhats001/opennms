<template>
  <div>
    <DataTable :value="events" showGridlines dataKey="id" :loading="loading" responsiveLayout="scroll">

        <!-- Search -->
        <template #header>
          <div class="flex-container space-between">
            <div>
              <h1>Recent Events</h1>
            </div>
            <!-- <div>
              <span class="p-input-icon-left top-30">
                <i class="pi pi-search" />
                <InputText @input="searchFilterHandler" placeholder="Search event" />
              </span>
            </div> -->
          </div>
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

        <Column field="id" header="Id">
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
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue'
import DataTable from 'primevue/datatable'
import InputText from 'primevue/inputtext'
import Column from 'primevue/column'
import Pagination from './Pagination.vue'
import { useStore } from 'vuex'
import { QueryParameters } from '../types'
import { useRoute } from 'vue-router'

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
    const queryParameters = ref({ 
      limit: 5, 
      offset: 0,
      _s: `node.id==${route.params.id}`
    } as QueryParameters)

    // const searchFilterHandler = (e: any) => {
    //   const searchQueryParam: QueryParameters = { _s: `node.id==${route.params.id}`}
    //   const updatedParams = { ...queryParameters.value, ...searchQueryParam }
    //   store.dispatch(call.value, updatedParams)
    //   queryParameters.value = updatedParams
    // }

    const updateQueryParameters = (updatedParams: QueryParameters) => queryParameters.value = updatedParams
    const events = computed(() => store.state.eventsModule.events)

    return {
      events,
      loading,
      queryParameters,
     // searchFilterHandler,
      updateQueryParameters
    }
  }
})
</script>

<style lang="scss" scoped>
  .flex-container {
    padding: 0;
    margin: 0;
    list-style: none;
    display: flex;
  }
  .space-between { 
    justify-content: space-between; 
  }
</style>
