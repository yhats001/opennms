<template>
  <div class="p-d-flex p-flex-column p-flex-md-row">
    <h3>Name this batch/set</h3>
  </div>
  <div class="p-d-flex p-flex-column p-flex-md-row">
    <InputText id="batch-name" v-model="batchName" />
  </div>
  <div class="p-d-flex p-flex-column p-flex-md-row">
    <h3>When do you want to run this batch?</h3>
  </div>
  <div class="p-d-flex p-flex-column p-flex-md-row">
    <Button 
      label="Now"
      class="p-button-raised p-button-text now-btn"
      :class="{'bg-tertiaty-sky-blue' : selected === now}"
      @click="showNowForm"
    />
    <Button 
      label="Schedule for later"
      class="p-button-raised p-button-text"
      :class="{'bg-tertiaty-sky-blue' : selected === later}"
      @click="showLaterForm"
    />
  </div>
  <div v-if="selected === now">
    <StepScheduleContentNow />
  </div>
  <div v-if="selected === later">
    <StepScheduleContentLater />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import StepScheduleContentNow from './StepScheduleContentNow.vue'
import StepScheduleContentLater from './StepScheduleContentLater.vue'

export default defineComponent({
  components: {
    Button,
    InputText,
    StepScheduleContentNow,
    StepScheduleContentLater
  },
  setup() {
    const now = 'now'
    const later = 'later'
    const selected = ref()
    const batchName = ref('defaultName')

    const showNowForm = () => {
      selected.value = now
    }

    const showLaterForm = () => {
      selected.value = later
    }

    return {
      now,
      later,
      selected,
      batchName,
      showNowForm,
      showLaterForm
    }
  }
})

</script>

<style scoped lang="scss">
  .now-btn {
    margin-right: 10px;
  }
</style>
