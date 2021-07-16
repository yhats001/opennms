<template>
  <template v-for="index in forms">
    <component :is="contains" :index="index" @setValues="setValues"></component>
  </template>

  <div p-flex-row>
    <Button
      label="Add another" 
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
import { useStore } from 'vuex'
import Button from 'primevue/button'
import StepAddContentCtrl from './StepAddContentCtrl.vue'
import StepAddContentDNS from './StepAddContentDNS.vue'
import StepAddContentIpRange from './StepAddContentIpRange.vue'

export default defineComponent({
  components: {
    Button,
    StepAddContentDNS,
    StepAddContentCtrl,
    StepAddContentIpRange
  },
  props: {
    contains: {
      type: String,
      required: true
    }
  },
  setup() {
    const store = useStore()
    const formsValues = ref([] as any)
    const forms = ref([0])

    const addForm = () => forms.value.push(forms.value.length)
    const setValues = (form: any) => {formsValues.value[form.index] = form.data; console.log(form.data)}

    const test = () => { 
      // send controller api form values for testing
      // display next btn if testing successful
      store.dispatch('inventoryModule/showAddStepNextButton', true)
    }

    return {
      test,
      addForm,
      setValues,
      forms
    }
  }
})
</script>

<style scoped lang="scss"></style>
