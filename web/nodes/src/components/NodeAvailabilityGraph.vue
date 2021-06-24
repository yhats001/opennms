<template>
  <Panel header="Availability (last 24 hours)">

    <div class="p-grid">
      <div class="p-col-12 p-sm-4 p-md-3">
        Availability
      </div>
      <div class="p-col-9" ref="timeline"></div>

      <template v-for="ipAddress of ipAddresses">

        <div class="p-col-12 p-sm-4 p-md-2">
          {{ ipAddress }}
        </div>
        <div class="p-col-10">
          <img :src="`${baseUrl}/opennms/rest/timeline/header/${startTime}/${endTime}/${width}`" :data-imgsrc="`/opennms/rest/timeline/header/${startTime}/${endTime}/`">
        </div>


        <template v-for="service of services">
          <template v-if="service.ipAddress === ipAddress">
            <div class="p-col-12 p-sm-4 p-md-2">
              {{ service.serviceName }}
            </div>
            <div class="p-col-10">
              <img :src="`${baseUrl}/opennms/rest/timeline/image/${nodeId}/${service.ipAddress}/${service.serviceName}/${startTime}/${endTime}/${width}`">
            </div>
          </template>
        </template>
        
      </template>
    </div>

  </Panel>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import useQueryParameters from '@/hooks/useQueryParams'
import { IfService } from '@/types'
import API from '@/services'
import dayjs from 'dayjs'
import Panel from 'primevue/panel'
import { debounce } from 'lodash'

export default defineComponent({
  name: 'Node Availability Graph',
  components: {
    Panel
  },
  setup() {
    // @ts-ignore
    const baseUrl = ref(process.env.VUE_APP_BASE_URL || '')
    const store = useStore()
    const route = useRoute()
    const nodeId = ref(route.params.id as string)
    const call = 'ifServicesModule/getNodeIfServices'
    const { queryParameters } = useQueryParameters({ "node.id": nodeId.value}, call)
    const now = dayjs()
    const startTime = ref(now.subtract(1, 'day').unix())
    const endTime = ref(now.unix())
    const width = ref(200)
    const services = ref([] as IfService[])
    const availabilityPercentage = ref()
    const ipAddresses = ref()
    const timeline = ref(null)

    const recalculateWidth = () => {
      // @ts-ignore
      width.value = timeline.value.clientWidth -20
    }
    
    onMounted(async () => {
      services.value = await store.dispatch(call, queryParameters.value)
      availabilityPercentage.value = await API.getNodeAvailabilityPercentage(nodeId.value)
      ipAddresses.value = Array.from(
        new Set(
          services.value.map((service: IfService) => service.ipAddress)
        )
      )

      recalculateWidth()
      window.addEventListener("resize", debounce(recalculateWidth, 100));
    })

    onUnmounted(() => window.removeEventListener("resize", recalculateWidth))

    return {
      width,
      nodeId,
      baseUrl,
      endTime,
      services,
      timeline,
      startTime,
      ipAddresses,
      queryParameters,
      availabilityPercentage
    }
  }
})
</script>
