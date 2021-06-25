<template>
  <img :src="logo" class="img"/>
</template>

<script>
export default {
  data() {
    return {
      logo: require('./OpenNMS_Horizontal-Logo_Dark-BG.svg')
    }
  }
}
</script>

<style>
  .img {
    width: 8em;
    height: 2em;
    cursor: pointer;
  }
</style>
