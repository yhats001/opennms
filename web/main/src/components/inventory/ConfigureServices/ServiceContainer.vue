<template>
  <template v-for="index in forms">
    <component :is="service" :index="index" @setValues="setValues"></component>
  </template>

  <div p-flex-row>
    <Button
      :label="label" 
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
import Button from 'primevue/button'
import WinRM from './WinRM.vue'
import HTTP from './http.vue'
import ICMP from './ICMP.vue'
import SNMP from './SNMP.vue'
import SSH from './SSH.vue'

export default defineComponent({
  components: {
    Button,
    WinRM,
    HTTP,
    ICMP,
    SNMP,
    SSH
  },
  props: {
    service: {
      type: String,
      required: true
    },
    label: {
      type: String,
      required: true
    }
  },
  setup() {
    const formsValues = ref([] as any)
    const forms = ref([0])

    const addForm = () => forms.value.push(forms.value.length)
    const setValues = (form: any) => formsValues.value[form.index] = form.data

    const test = () => { 
      console.log(formsValues.value)
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
