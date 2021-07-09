<template>
  <div class="p-flex-row">
    <InputText id="start" type="text" v-model="start" placeholder="Start" class="first input" />
  </div>
  <div class="p-flex-row">
    <InputText id="end" type="text" v-model="end" placeholder="End" class="input" />
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
    const start = ref()
    const end = ref()

    return {
      start,
      end
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

