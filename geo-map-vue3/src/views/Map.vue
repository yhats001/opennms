<template>
  <splitpanes class="default-theme" horizontal style="height: 1000px">
    <pane min-size="1" max-size="60">
      <div class="leaflet-map">
        <LeafletMap />
      </div>
    </pane>
    <pane>
      <div class="nodes-grid">
        <NodesGrid />
      </div>
    </pane>
  </splitpanes>
</template>

<script>
import LeafletMap from "@/components/LeafletMap.vue";
import NodesGrid from "@/components/NodesGrid.vue";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

export default {
  name: "Map",
  components: {
    LeafletMap,
    Splitpanes,
    Pane,
    NodesGrid,
  },
};
</script>

<style scoped>
</style>