<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation"/>
  </div>
  <div class="p-flex-row">
    <InputText id="host" type="text" v-model="host" placeholder="Host" class="input" @input="setValues"/>
  </div>
  <div class="p-flex-row">
    <InputText id="zone" type="text" v-model="zone" placeholder="Zone" class="input" @input="setValues" />
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
  emits: ['set-values'],
  setup(_, context) {
    const host = ref()
    const zone = ref()
    const location = ref()

    const setLocation = (selectedLocation: MonitoringLocation) => {
      location.value = selectedLocation
      setValues()
    }

    const setValues = () => context.emit('set-values', { host, zone, location })

    return {
      host,
      zone,
      setValues,
      setLocation
    }
  }
})

</script>

<style scoped lang="scss">
</style>
