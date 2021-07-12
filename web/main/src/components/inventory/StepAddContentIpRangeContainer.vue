<template>
  <template v-for="component in ipRangeForms">
    <component :is="component" @setValues="setValues"></component>
  </template>

  <div p-flex-row>
    <Button 
      label="Add another range" 
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
import StepAddContentIpRange from './StepAddContentIpRange.vue'
import Button from 'primevue/button'
import { useStore } from 'vuex'

interface IPRangeFormValues {
  start: string
  end: string
  location: string
}

export default defineComponent({
  components: {
    StepAddContentIpRange,
    Button
  },
  setup() {
    const store = useStore()

    const ipRangeFormsValues = ref([] as IPRangeFormValues[])
    const ipRangeForms = ref([StepAddContentIpRange])

    const addForm = () => ipRangeForms.value.push(StepAddContentIpRange)
    const setValues = (dnsFormValues: IPRangeFormValues) => ipRangeFormsValues.value.push(dnsFormValues)

    const test = () => { 
      // send DNS form values for testing
      // display next btn if testing successful
      store.dispatch('inventoryModule/showAddStepNextButton', true)
    }

    return {
      test,
      addForm,
      setValues,
      ipRangeForms
    }
  }
})

</script>

<style scoped lang="scss">
</style>
