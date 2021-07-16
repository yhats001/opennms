<template>
  <Row label="Location" first><LocationsDropdown @setLocation="setLocation" /></Row>
  <Row label="Start"><InputText v-model="start" class="input" @input="setValues" /></Row>
  <Row label="End"><InputText v-model="end" class="input" @input="setValues" /></Row>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'
import LocationsDropdown from './LocationsDropdown.vue'
import { MonitoringLocation } from '@/types'
import Row from '@/components/common/Row.vue'

export default defineComponent({
  components: {
    Row,
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
