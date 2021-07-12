<template>
  <template v-for="component in controllerForms">
    <component :is="component" @setValues="setValues"></component>
  </template>

  <div p-flex-row>
    <Button 
      label="Add another type" 
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
import StepAddContentCtrl from './StepAddContentCtrl.vue'
import Button from 'primevue/button'
import { useStore } from 'vuex'

interface ControllerAPIValues {
  endpoint: string
  secret: string
  key: string
  type: string
}

export default defineComponent({
  components: {
    StepAddContentCtrl,
    Button
  },
  setup() {
    const store = useStore()

    const controllerApiFormsValues = ref([] as ControllerAPIValues[])
    const controllerForms = ref([StepAddContentCtrl])

    const addForm = () => controllerForms.value.push(StepAddContentCtrl)
    const setValues = (controllerFormValues: ControllerAPIValues) => controllerApiFormsValues.value.push(controllerFormValues)

    const test = () => { 
      // send controller api form values for testing
      // display next btn if testing successful
      store.dispatch('inventoryModule/showAddStepNextButton', true)
    }

    return {
      test,
      addForm,
      setValues,
      controllerForms
    }
  }
})

</script>

<style scoped lang="scss">
</style>
