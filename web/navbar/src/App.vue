<template>
  <div class="menubar">
    <div>
      <Logo @click="returnHomeHandler" />
    </div>
    <div>
      <Search />
    </div>
    <div>
      <Button @click="returnHandler" class="p-button-md p-button-primary">Return to previous UI</Button>
    </div>
  </div>
  <div class="sidebar">
    <Link to="/#/inventory">Inventory</Link>
    <Link to="/">Nodes</Link>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Button from 'primevue/button'
import Logo from './assets/LogoDarkBG.vue'
import Link from './components/Link.vue'
import Search from './components/Search.vue'

export default defineComponent({
  components: {
    Button,
    Search,
    Logo,
    Link
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
  $linkColor: #14D1DF;
  $menuColor: #0A0C1B;
  .menubar {
    display: flex;
    justify-content: space-between;
    background: $menuColor;
    height: 65px;
    padding: 12px
  }
  .sidebar {
    height: 100vh;
    width: 230px;
    background: grey;
    position: relative;
    float: left;
    background: $menuColor;
    color: rgba($color: $linkColor, $alpha: 0.8);
  }
</style>
