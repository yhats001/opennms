<template>
  <div class="p-flex-row">
    <InputText id="endpoint" type="text" v-model="endpoint" placeholder="Endpoint" class="first input" />
  </div>
  <div class="p-flex-row">
    <InputText id="key" type="text" v-model="key" placeholder="Key" class="input" />
  </div>
  <div class="p-flex-row">
    <InputText id="secret" type="text" v-model="secret" placeholder="Secret" class="input"  />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'

export default defineComponent({
  components: {
    InputText,
  },
  setup() {
    const endpoint = ref()
    const secret = ref()
    const key = ref()

    return {
      endpoint,
      secret,
      key
    }
  }
})

</script>

<style scoped lang="scss">
  .input {
    margin-top: 5px;
    width: 370px;
  }
  .first {
    margin-top: 30px;
  }
</style>

