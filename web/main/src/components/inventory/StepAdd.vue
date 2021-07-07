<template>
  <Card>
    <template v-slot:title>
      Do you have any managed or preconfigured information about your network?
    </template>
    <template v-slot:content>
      <Content />
    </template>
    <template v-slot:footer>
      <div class="p-grid p-nogutter p-justify-between">
        <i></i>
        <Button class="p-button-primary" label="This looks good" @click="$emit('next-page', { pageIndex: 0 })" icon="pi pi-angle-right" iconPos="right" />
      </div>
    </template>
  </Card>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Card from 'primevue/card'
import Button from 'primevue/button'
import Content from './StepAddContent.vue'

export default defineComponent({
  components: {
    Card,
    Button,
    Content
  },
  emits: ['next-page']
})

</script>

<style scoped lang="scss">
</style>
