<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="start" placeholder="Start" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="end" placeholder="End" class="input" @input="setValues" />
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
    const start = ref()
    const end = ref()
    const location = ref()

    const setLocation = (selectedLocation: MonitoringLocation) => {
      location.value = selectedLocation
      setValues()
    }

    const setValues = () => context.emit('set-values', { start, end, location })

    return {
      setLocation,
      setValues,
      start,
      end
    }
  }
})

</script>

<style scoped lang="scss">
</style>
