<template>
  <div class="p-grid page">
    <div class="p-col-12">
      <Breadcrumb :home="home" :model="items">
          <template #item="{item}">
            <a :href="item.to">{{item.label}}</a>
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
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import EventsTable from '@/components/EventsTable.vue'
import InterfacesTabsVue from '@/components/InterfacesTabs.vue'
import NodeAvailabilityGraphVue from '@/components/NodeAvailabilityGraph.vue'
import Breadcrumb from 'primevue/breadcrumb';
import Panel from 'primevue/panel'

export default defineComponent({
  name: 'NodeDetails',
  components: {
    EventsTable,
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
      {label: 'Node Details'},
    ]);

    return { home, items }
  }
})
</script>

<style lang="scss" scoped>
  .node-interfaces {
    margin-top: 20px;
  }
</style>
