<template>
  <DataTable :value="nodes" showGridlines dataKey="id" :loading="loading" responsiveLayout="scroll" class="top-30">

      <!-- Search -->
      <template #header>
        <div class="flex-container space-between">
          <div>
            <h1>Nodes</h1>
          </div>
          <div>
            <span class="p-input-icon-left top-30">
              <i class="pi pi-search" />
              <InputText @input="searchFilterHandler" placeholder="Search node label" />
            </span>
          </div>
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
          moduleName="nodesModule"
          functionName="getNodes"
          totalCountStateName="totalCount"/>
      </template>

      <Column field="label" header="Label" style="min-width:12rem">
        <template #body="{data}">
          <router-link :to="`/node/${data.id}`">
            {{ data.label }}
          </router-link>
        </template>
      </Column>

      <Column field="location" header="Location" style="min-width:12rem">
        <template #body="{data}">
            {{data.location}}
        </template>
      </Column>

      <Column field="foreignSource" header="Foreign Source" style="min-width:12rem">
        <template #body="{data}">
            {{data.foreignSource}}
        </template>
      </Column>

      <Column field="foreignId" header="Foreign Id" style="min-width:12rem">
        <template #body="{data}">
            {{data.foreignId}}
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
import { QueryParameters } from '../types'

export default defineComponent({
  name: 'NodesTable',
  components: {
    DataTable,
    InputText,
    Column,
    Pagination
  },
  setup() {
    const store = useStore()
    const loading = ref(false)
    const queryParameters = ref({ 
      limit: 5, 
      offset: 0,
      orderBy: 'label'
    } as QueryParameters)

    const searchFilterHandler = (e: any) => {
      const searchQueryParam: QueryParameters = { _s: `node.label==${e.target.value}*`}
      const updatedParams = { ...queryParameters.value, ...searchQueryParam }
      store.dispatch('nodesModule/getNodes', updatedParams)
      queryParameters.value = updatedParams
    }

    const updateQueryParameters = (updatedParams: QueryParameters) => queryParameters.value = updatedParams

    const nodes = computed(() => store.state.nodesModule.nodes)

    return {
      nodes,
      loading,
      queryParameters,
      searchFilterHandler,
      updateQueryParameters
    }
  }
})
</script>
