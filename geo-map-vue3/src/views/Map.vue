<template>
  <div class="map">
    <LeafletMap/>
  </div>
</template>

<script>
import LeafletMap from "@/components/LeafletMap.vue";

export default {
  name: "Map",
  components: {
    LeafletMap,
  },
};
</script>

<style scoped>

</style>