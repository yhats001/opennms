<template>
  <div class="p-grid page">
    <div class="p-col-12">
      <h1>Identify your network inventory</h1>
      <Steps />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Steps from '../components/inventory/Steps.vue'

export default defineComponent({
  name: 'Node Inventory',
  components: {
    Steps
  }
})
</script>
