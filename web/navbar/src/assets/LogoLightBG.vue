<template>
  <img :src="logo" class="img"/>
</template>

<script>
export default {
  data() {
    return {
      logo: require('./OpenNMS_Horizontal-Logo_Light-BG.svg')
    }
  }
}
</script>

<style>
  .img {
    widows: 8em;
    height: 2em;
  }
</style>
