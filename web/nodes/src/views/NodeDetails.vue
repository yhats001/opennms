<template>
  <div class="p-grid top-30 page">
    <div class="p-col-6">
      <InterfacesTabsVue />
    </div>
    <div class="p-col-6">
      <EventsTable />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import EventsTable from '@/components/EventsTable.vue'
import InterfacesTabsVue from '@/components/InterfacesTabs.vue'

export default defineComponent({
  name: 'NodeDetails',
  components: {
    EventsTable,
    InterfacesTabsVue
  }
})
</script>
