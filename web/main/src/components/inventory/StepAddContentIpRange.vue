<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
  <div class="p-flex-row">
    <InputText id="start" type="text" v-model="start" placeholder="Start" class="input" />
  </div>
  <div class="p-flex-row">
    <InputText id="end" type="text" v-model="end" placeholder="End" class="input" />
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
    const start = ref()
    const end = ref()

    const setLocation = (location: MonitoringLocation) => {
      console.log(location)
    }

    return {
      setLocation,
      start,
      end
    }
  }
})

</script>

<style scoped lang="scss">
</style>
