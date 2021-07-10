<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
</template>

<script lang="ts">
import { MonitoringLocation } from '@/types'
import { defineComponent } from 'vue'
import LocationsDropdown from './LocationsDropdown.vue'

export default defineComponent({
  components: {
    LocationsDropdown
  },
  setup() {
    const setLocation = (location: MonitoringLocation) => {
      console.log(location)
    }

    return {
      setLocation
    }
  }
})

</script>

<style scoped lang="scss">
</style>
