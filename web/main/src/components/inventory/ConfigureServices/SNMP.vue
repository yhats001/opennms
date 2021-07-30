<template>
<div class="p-grid">
  <div class="p-col">
    <Row first><h3>SNMP</h3></Row>
    <Row label="v1/v2c community string"><InputText type="text" v-model="v1v2" class="input" @input="setValues"/></Row>
    <Row label="Timeout"><InputText type="text" v-model="timeout" class="input" @input="setValues" /></Row>
    <Row label="Retry"><InputText type="text" v-model="retry" class="input" @input="setValues" /></Row>

    <ShowHideBox label="Advanced options">
      <Row first label="Security level">
        <Dropdown v-model="securityLevel" @change="setValues" class="input" :options="['noAuthNoPriv','authNoPriv', 'authPriv']"/>
      </Row>

      <!-- add filter -->
      <ServiceFilter @setValues="setValues" />
    </ShowHideBox>
  </div>
  <div class="p-col">
    <ResponseTable />
  </div>
</div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, ComputedRef } from 'vue'
import InputText from 'primevue/inputtext'
import Dropdown from 'primevue/dropdown'
import Row from '@/components/common/Row.vue'
import ShowHideBox from '@/components/common/ShowHideBox.vue'
import ServiceFilter from './ServiceFilter.vue'
import ResponseTable from './ResponseTable.vue'
import { SNMPDetectRequestConfig } from '@/types'

export default defineComponent({
  components: {
    Row,
    Dropdown,
    InputText,
    ShowHideBox,
    ServiceFilter,
    ResponseTable
  },
  emits: ['set-values'],
  props: {
    index: {
      type: Number,
      required: true
    }
  },
  setup(props, context) {
    // advanced options
    const securityLevel = ref('noAuthNoPriv')

    // form
    const v1v2 = ref()
    const timeout = ref()
    const retry = ref()

    const data: ComputedRef<SNMPDetectRequestConfig> = computed(() => ({ 
      timeout: timeout.value, 
      retry: retry.value, 
      communityString: v1v2.value,
      securityLevel: securityLevel.value
    }))

    const setValues = (filterValues: any) => context.emit('set-values', { index: props.index, data: {...data.value, ...filterValues} })

    return {
      v1v2,
      retry,
      timeout,
      securityLevel,
      setValues
    }
  }
})
</script>

<style scoped lang="scss"></style>
