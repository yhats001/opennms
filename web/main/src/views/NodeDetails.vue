<template>
  <div class="p-grid page">
    <div class="p-col-12">
      <Breadcrumb :home="home" :model="items">
        <template #item="{item}">
          <router-link :to="item.to">{{item.label}}</router-link>
        </template>
      </Breadcrumb>
    </div>
    <div class="p-col-6">
      <NodeAvailabilityGraphVue />
      <Panel header="Node Interfaces" class="node-interfaces">
        <InterfacesTabsVue />
      </Panel>
    </div>
    <div class="p-col-6">
      <EventsTable />
      <OutagesTable class="outages-table" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import EventsTable from '@/components/nodes/EventsTable.vue'
import OutagesTable from '@/components/nodes/OutagesTable.vue'
import InterfacesTabsVue from '@/components/nodes/InterfacesTabs.vue'
import NodeAvailabilityGraphVue from '@/components/nodes/NodeAvailabilityGraph.vue'
import Breadcrumb from 'primevue/breadcrumb';
import Panel from 'primevue/panel'

export default defineComponent({
  name: 'NodeDetails',
  components: {
    EventsTable,
    OutagesTable,
    InterfacesTabsVue,
    NodeAvailabilityGraphVue,
    Breadcrumb,
    Panel
  },
  setup() {
    const home = ref({
        label: 'Nodes', 
        to: '/',
    });
    const items = ref([
      {label: 'Node Details', to: '#'},
    ]);

    return { home, items }
  }
})
</script>

<style lang="scss" scoped>
  .node-interfaces,
  .outages-table {
    margin-top: 20px;
  }
</style>
