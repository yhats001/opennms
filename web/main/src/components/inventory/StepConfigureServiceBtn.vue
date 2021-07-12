<template>
  <Button 
    :label="serviceName"
    class="p-button-raised p-button-text button"
    :class="{'selected' : selected}"
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
    selected: {
      type: Boolean,
      required: true,
      default: false
    }
  }
})
</script>

<style scoped lang="scss"></style>
