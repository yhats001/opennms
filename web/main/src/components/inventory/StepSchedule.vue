<template>
  <Card>
    <template v-slot:title>
      Give this batch/set a name
    </template>
    <template v-slot:content>
      Test content
    </template>
    <template v-slot:footer>
      <div class="p-grid p-nogutter p-justify-between">
        <Button class="p-button-primary" label="Back" @click="$emit('prev-page', { pageIndex: 2 })" icon="pi pi-angle-left" />
        <Button label="Complete" @click="$emit('complete')" icon="pi pi-check" iconPos="right" class="p-button-success"/>
      </div>
    </template>
  </Card>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Card from 'primevue/card'
import Button from 'primevue/button'

export default defineComponent({
  components: {
    Card,
    Button
  },
  emits: ['complete', 'prev-page']
})

</script>

<style scoped lang="scss">

</style>
