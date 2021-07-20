<template>
  <div class="geo-map">
    <l-map
      v-model:zoom="zoom"
      :zoomAnimation="true"
      :center="openNMSHeadQuarter"
    >
      <l-tile-layer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"  
        attribution= '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
      ></l-tile-layer>
      <l-control-layers />

      <l-marker :lat-lng="markerlatlng">
        <l-icon :icon-url="iconUrl" :icon-size="iconSize" />
        <l-popup>
          popup
        </l-popup>
        <l-tooltip>
          tooltip
        </l-tooltip>
      </l-marker>

      <l-polyline
        :lat-lngs="[
          [35.849613, -78.794882],
          [47.342596, -1.328731],
          [47.241487, -1.190568],
          [47.234787, -1.358337],
        ]"
        color="blue"
        :weight="3"
      ></l-polyline>
    </l-map>
  </div>
</template>
<script>
import {
  LMap,
  LTileLayer,
  LMarker,
  LIcon,
  LControlLayers,
  LTooltip,
  LPopup,
  LPolyline,
} from "@vue-leaflet/vue-leaflet";
import "leaflet/dist/leaflet.css";
import NodesService from '@/services/NodesService.js'

export default {
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LIcon,
    LControlLayers,
    LTooltip,
    LPopup,
    LPolyline,
  },
  data() {
    return {
      zoom: 4,
      openNMSHeadQuarter:[35.849613, -78.794882],
      iconWidth: 25,
      iconHeight: 25,
      nodes: null
    };
  },
  computed: {
    iconUrl() {
      return require('../assets/node.png');
    },
    iconSize() {
      return [this.iconWidth, this.iconHeight];
    },
    markerlatlng(){
      return [35.849613, -78.794882]
    }
  },
  methods: {
    
  },

  created() {
    NodesService.getNodes()
        .then(response => {
          this.nodes = response.data
          console.log(response.data)
        })
        .catch(error => {
          console.log("Here is the error meassage")
          console.log(error)
        })
  }

  
};
</script>

<style scoped>
.geo-map {
  height: 80vh; 
}
</style>

