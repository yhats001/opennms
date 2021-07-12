<template>
  <template v-for="component in dnsForms">
    <component :is="component" @setValues="setValues"></component>
  </template>

  <div p-flex-row>
    <Button 
      label="Add another DNS" 
      class="p-button-raised p-button-text first input" 
      @click="addForm"
    />
  </div>

  <div p-flex-row>
    <Button 
      label="Test" 
      class="p-button-primary input" 
      @click="test"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import StepAddContentDNS from './StepAddContentDNS.vue'
import Button from 'primevue/button'
import { useStore } from 'vuex'

interface DNSFormValues {
  host: string
  zone: string
  location: string
}

export default defineComponent({
  components: {
    StepAddContentDNS,
    Button
  },
  setup() {
    const store = useStore()

    const dnsFormsValues = ref([] as DNSFormValues[])
    const dnsForms = ref([StepAddContentDNS])

    const addForm = () => dnsForms.value.push(StepAddContentDNS)
    const setValues = (dnsFormValues: DNSFormValues) => dnsFormsValues.value.push(dnsFormValues)

    const test = () => { 
      // send DNS form values for testing
      // display next btn if testing successful
      store.dispatch('inventoryModule/showAddStepNextButton', true)
    }

    return {
      test,
      addForm,
      setValues,
      dnsForms
    }
  }
})

</script>

<style scoped lang="scss">
</style>
