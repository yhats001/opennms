<template>
  <Card>
    <template v-slot:title>
      What services do you want to find/monitor?
    </template>
    <template v-slot:content>
      <div class="p-d-flex p-flex-column p-flex-md-row">
        <StepConfigureServiceBtnContainer />
      </div>
      <div class="p-d-flex p-flex-column p-flex-md-row">
        
      </div>
    </template>
    <template v-slot:footer>
      <div class="p-grid p-nogutter p-justify-between">
        <Button class="p-button-primary" label="Back" @click="$emit('prev-page', { pageIndex: 1 })" icon="pi pi-angle-left" />
        <Button class="p-button-primary" label="Next" @click="$emit('next-page', { pageIndex: 1 })" icon="pi pi-angle-right" iconPos="right" />
      </div>
    </template>
  </Card>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Card from 'primevue/card'
import Button from 'primevue/button'
import StepConfigureServiceBtnContainer from './StepConfigureServiceBtnContainer.vue'

export default defineComponent({
  components: {
    Card,
    Button,
    StepConfigureServiceBtnContainer
  },
  emits: ['next-page', 'prev-page']
})

</script>

<style scoped lang="scss"></style>
