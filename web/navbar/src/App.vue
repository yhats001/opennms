<template>
  <Menubar>
    <template #start>
        <Logo />
    </template>

    <template #end>
        <a href="/opennms/" class="button">Return to previous UI</a>
    </template>
  </Menubar>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Menubar from 'primevue/menubar'
import Logo from './assets/LogoLightBG.vue'

export default defineComponent({
  components: {
    Menubar,
    Logo
  }
})
</script>

