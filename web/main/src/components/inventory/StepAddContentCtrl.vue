<template>
  <div class="p-flex-row first input">
    <LocationsDropdown @setLocation="setLocation" />
  </div>
  <div class="p-flex-row">
    <InputText id="endpoint" type="text" v-model="endpoint" placeholder="Endpoint" class="input" />
  </div>
  <div class="p-flex-row">
    <InputText id="key" type="text" v-model="key" placeholder="Key" class="input" />
  </div>
  <div class="p-flex-row">
    <InputText id="secret" type="text" v-model="secret" placeholder="Secret" class="input"  />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'
import LocationsDropdown from './/LocationsDropdown.vue'
import { MonitoringLocation } from '@/types'

export default defineComponent({
  components: {
    InputText,
    LocationsDropdown
  },
  setup() {
    const endpoint = ref()
    const secret = ref()
    const key = ref()

    const setLocation = (location: MonitoringLocation) => {
      console.log(location)
    }

    return {
      setLocation,
      endpoint,
      secret,
      key
    }
  }
})

</script>

<style scoped lang="scss">
</style>
