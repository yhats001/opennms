<template>
  <Menubar :model="items" />
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Menubar from 'primevue/menubar'

export default defineComponent({
  components: {
    Menubar
  },
  data() {
    return {
      items: [
        {
          label: 'Return to previous UI',
          icon: 'pi pi-fw pi-arrow-left'
        }
      ]
    }
  }
})
</script>

<style>
</style>
