<template>
  <div class="flex-container space-between">
    <div>
      <SnmpInterfacesTable />
    </div>
    <div>
      <EventsTable />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import EventsTable from '@/components/EventsTable.vue'
import SnmpInterfacesTable from '@/components/SnmpInterfacesTable.vue'

export default defineComponent({
  name: 'NodeDetails',
  components: {
    EventsTable,
    SnmpInterfacesTable
  }
})
</script>
