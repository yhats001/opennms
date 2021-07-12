<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { MonitoringLocation } from '@/types'
import LocationsDropdown from './LocationsDropdown.vue'
import { useStore } from 'vuex'

export default defineComponent({
  components: {
    LocationsDropdown
  },
  setup() {
    const store = useStore()
    const location = ref()

    const setLocation = (selectedLocation: MonitoringLocation) => {
      location.value = selectedLocation
      // display next btn if testing successful
      store.dispatch('inventoryModule/showAddStepNextButton', true)
    }

    return {
      setLocation
    }
  }
})

</script>

<style scoped lang="scss">
</style>
