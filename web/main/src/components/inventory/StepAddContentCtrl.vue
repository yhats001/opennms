<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
  <div class="p-flex-row input">
    <TypesDropdown @setType="setType" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="endpoint" placeholder="Endpoint" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="key" placeholder="Key" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="secret" placeholder="Secret" class="input" @input="setValues"  />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'
import TypesDropdown from './TypeDropdown.vue'
import LocationsDropdown from './LocationsDropdown.vue'
import { MonitoringLocation } from '@/types'

export default defineComponent({
  components: {
    InputText,
    TypesDropdown,
    LocationsDropdown
  },
  emits: ['set-values'],
  setup(_, context) {
    const endpoint = ref()
    const secret = ref()
    const key = ref()
    const type = ref()
    const location = ref()

    const setLocation = (selectedLocation: MonitoringLocation) => {
      location.value = selectedLocation
      setValues()
    }

    const setType = (selectedType: { id: string, name: string }) => type.value = selectedType.name
    const setValues = () => context.emit('set-values', { endpoint, secret, key, type })

    return {
      setLocation,
      setValues,
      setType,
      endpoint,
      secret,
      key
    }
  }
})

</script>

<style scoped lang="scss">
</style>
