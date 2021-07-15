<template>
  <div class="p-d-flex p-flex-column p-flex-md-row">
    <template v-for="service of services">
      <component 
        is="StepConfigureServiceBtn" 
        :serviceName="service"
        :selectedServices="selectedServices"
        :disableService="disableServiceSelection"
        @selectService="selectService(service)"
      />
    </template>
  </div>
  <div class="p-flex-row first" v-if="showConfigureServicesBtn">
    <Button
      class="p-button-secondary" 
      label="Configure" 
      @click="configureServices"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import StepConfigureServiceBtn from './StepConfigureServiceBtn.vue';
import Button from 'primevue/button'

export default defineComponent({
  components: {
    StepConfigureServiceBtn,
    Button
  },
  emits: ['configure-services'],
  setup(_, context) {
    const showConfigureServicesBtn = ref(false)
    const disableServiceSelection = ref(false)
    const selectedServices = ref([] as string[])
    const services = [
      'SNMP',
      'HTTPS',
      'SSH',
      'ICMP',
      'WinRM'
    ]

    const selectService = (service: string) => {
      const idx = selectedServices.value.indexOf(service)

      if (idx !== -1) {
        selectedServices.value.splice(idx, 1)
      } else {
        selectedServices.value.push(service)
      }

      showConfigureServicesBtn.value = Boolean(selectedServices.value.length)
    }

    const configureServices = () => {
      context.emit('configure-services', selectedServices.value)
      showConfigureServicesBtn.value = false
      disableServiceSelection.value = true
    }

    return {
      services,
      selectedServices,
      showConfigureServicesBtn,
      disableServiceSelection,
      configureServices,
      selectService
    }
  }
})
</script>

<style scoped lang="scss"></style>
