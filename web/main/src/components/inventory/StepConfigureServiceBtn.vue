<template>
  <Button 
    :label="serviceName"
    class="p-button-raised p-button-text service"
    :class="{'selected' : selectedServices.includes(serviceName)}"
    @click="$emit('select-service', serviceName)"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Button from 'primevue/button'

export default defineComponent({
  components: {
    Button
  },
  emits:['select-service'],
  props: {
    serviceName: {
      type: String,
      required: true
    },
    selectedServices: {
      type: Array,
      required: true,
      default: []
    }
  }
})
</script>

<style scoped lang="scss">
  .service {
    margin-right: 10px;
  }
</style>
