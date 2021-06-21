<template>
  <div>
    <DataTable :value="snmpInterfaces" showGridlines dataKey="id" :loading="loading" responsiveLayout="scroll">

        <template #header>
          <div class="flex-container space-between">
            <div>
              <h1>SNMP Interfaces</h1>
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
            :payload="payload"
            :parameters="queryParameters" 
            @update-query-parameters="updateQueryParameters" 
            moduleName="nodesModule"
            functionName="getNodeSnmpInterfaces"
            totalCountStateName="snmpInterfacesTotalCount"/>
        </template>

        <Column field="idIndex" header="SNMP ifIndex">
          <template #body="{data}">
            {{ data.ifIndex }}
          </template>
        </Column>

        <Column field="ifDescr" header="SNMP ifDescr">
          <template #body="{data}">
              {{ data.idDescr || 'N/A' }}
          </template>
        </Column>

        <Column field="ifName" header="SNMP ifName">
          <template #body="{data}">
              {{ data.ifName || 'N/A' }}
          </template>
        </Column>

        <Column field="ifAlias" header="SNMP ifAlias">
          <template #body="{data}">
            {{ data.ifAlias || 'N/A' }}
          </template>
        </Column>

        <Column field="ifSpeed" header="SNMP ifSpeed">
          <template #body="{data}">
            <span v-html="data.ifSpeed"></span> 
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
import { useRoute } from 'vue-router'
import { QueryParameters } from '../types'

export default defineComponent({
  name: 'SNMP Interfaces Table',
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
    } as QueryParameters)
    const payload = ref({ id: route.params.id, queryParameters})

    // const searchFilterHandler = (e: any) => {
    //   const searchQueryParam: QueryParameters = { _s: `node.id==${route.params.id}`}
    //   const updatedParams = { ...queryParameters.value, ...searchQueryParam }
    //   store.dispatch(call.value, updatedParams)
    //   queryParameters.value = updatedParams
    // }

    const updateQueryParameters = (updatedParams: QueryParameters) => queryParameters.value = updatedParams
    const snmpInterfaces = computed(() => store.state.nodesModule.snmpInterfaces)

    return {
      loading,
      snmpInterfaces,
      queryParameters,
      payload,
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
