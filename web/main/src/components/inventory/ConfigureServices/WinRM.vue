<template>
  <div class="p-flex-row first input">
    <h3>WinRM / WSMan</h3>
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="username" placeholder="Username" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="password" placeholder="Password" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="timeout" placeholder="Timeout" class="input" @input="setValues" />
  </div>
  <div class="p-flex-row">
    <InputText type="text" v-model="retry" placeholder="Retry" class="input" @input="setValues" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'

export default defineComponent({
  components: {
    InputText
  },
  emits: ['set-values'],
  props: {
    index: {
      type: Number,
      required: true
    }
  },
  setup(props, context) {
    const timeout = ref()
    const retry = ref()
    const username = ref()
    const password = ref()

    const data = ref({ timeout, retry, username, password })

    const setValues = () => context.emit('set-values', { index: props.index, data })

    return {
      retry,
      timeout,
      username,
      password,
      setValues
    }
  }
})
</script>

<style scoped lang="scss"></style>
