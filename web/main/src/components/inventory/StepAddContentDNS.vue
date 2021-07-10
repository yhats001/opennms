<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
  <div class="p-flex-row">
    <InputText id="host" type="text" v-model="host" placeholder="Host" class="input" />
  </div>
  <div class="p-flex-row">
    <InputText id="zone" type="text" v-model="zone" placeholder="Zone" class="input" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'
import LocationsDropdown from './LocationsDropdown.vue'
import { MonitoringLocation } from '@/types'

export default defineComponent({
  components: {
    InputText,
    LocationsDropdown
  },
  setup() {
    const host = ref()
    const zone = ref()

    const setLocation = (location: MonitoringLocation) => {
      console.log(location)
    }

    return {
      host,
      zone,
      setLocation
    }
  }
})

</script>

<style scoped lang="scss">
</style>
