<template>
  <Menubar>
    <template #start>
      <Logo @click="returnHomeHandler" />
    </template>

    <template #end>
      <Button @click="returnHandler" class="p-button-sm">Return to previous UI</Button>
    </template>
  </Menubar>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Menubar from 'primevue/menubar'
import Button from 'primevue/button'
import Logo from './assets/LogoDarkBG.vue'

export default defineComponent({
  components: {
    Menubar,
    Button,
    Logo
  },
  setup() {
    const returnHandler = () => window.location.href = '/opennms/'
    const returnHomeHandler = () => window.location.href = '/'

    return {
      returnHandler,
      returnHomeHandler
    }
  }
})
</script>

<style lang="scss" scoped>
  .p-menubar {
    background: #0A0C1B !important;
    border: none !important;
    border-radius: 0px !important;
    height: 65px !important;
  }
  .p-button {
    background: rgba($color: #14D1DF, $alpha: 0.5);
    border: none;

    &:hover {
      background: rgba($color: #14D1DF, $alpha: 0.8) !important;
    }
  }
</style>
