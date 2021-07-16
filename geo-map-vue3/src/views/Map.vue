<template>
  <div class="map">
    <GeoMap/>
  </div>
</template>

<script>
import GeoMap from "@/components/GeoMap.vue";

export default {
  name: "Map",
  components: {
    GeoMap,
  },
};
</script>

<style scoped>
.map {
  height: 100vh; 
  width: 100vw;
  margin: 0 auto;
}
</style>