<template>
  <Menubar>
    <template #start>
        <Logo />
    </template>

    <template #end>
        <Button>Return to previous UI</Button>
    </template>
  </Menubar>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Menubar from 'primevue/menubar'
import Button from 'primevue/button'
import Logo from './assets/LogoLightBG.vue'

export default defineComponent({
  components: {
    Menubar,
    Button,
    Logo
  }
})
</script>

