<template>
  <div class="p-flex-row">
    <InputText id="host" type="text" v-model="host" placeholder="Host" class="first input" />
  </div>
  <div class="p-flex-row">
    <InputText id="zone" type="text" v-model="zone" placeholder="Zone" class="input" />
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
    const host = ref()

    const zone = ref()

    return {
      host,
      zone
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

