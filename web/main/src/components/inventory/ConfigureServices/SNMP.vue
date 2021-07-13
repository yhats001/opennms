<template>
  <div class="p-flex-row first input">
    <h3>SNMP</h3>
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="v1v2" placeholder="v1/v2c community string" class="input" @input="setValues"/>
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="timeout" placeholder="Timeout" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="retry" placeholder="Retry" class="input" @input="setValues" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'

export default defineComponent({
  components: {
    InputText
  },
  emits: ['set-values'],
  props: {
    index: {
      type: Number,
      required: true
    }
  },
  setup(props, context) {
    const v1v2 = ref()
    const timeout = ref()
    const retry = ref()

    const data = ref({ timeout, retry, v1v2 })

    const setValues = () => context.emit('set-values', { index: props.index, data })

    return {
      v1v2,
      retry,
      timeout,
      setValues
    }
  }
})
</script>

<style scoped lang="scss"></style>
