<template>
  <template v-for="service of services">
    <component 
      is="StepConfigureServiceBtn" 
      :serviceName="service"
      :selectedServices="selectedServices"
      @selectService="selectService(service)"
    />
  </template>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import StepConfigureServiceBtn from './StepConfigureServiceBtn.vue';

export default defineComponent({
  components: {
    StepConfigureServiceBtn
  },
  setup() {
    const selectedServices = ref([] as string[])
    const services = [
      'SNMP',
      'HTTP-S',
      'SSH',
      'ICMP',
      'WinRM'
    ]

    const selectService = (service: string) => {
      const idx = selectedServices.value.indexOf(service)

      if (idx !== -1) {
        selectedServices.value.splice(idx, 1)
        return
      }

      selectedServices.value.push(service)
    }

    return {
      services,
      selectedServices,
      selectService
    }
  }
})
</script>

<style scoped lang="scss"></style>
