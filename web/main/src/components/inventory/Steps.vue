<template>
  <div class="steps-container">
    <Steps :model="items" :readonly="true" />
  </div>
  <div class="router-container">
    <router-view v-slot="{Component}" @prevPage="prevPage($event)" @nextPage="nextPage($event)" @complete="complete">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
    </router-view>
  </div>
</template>

<script lang="ts">
import { ref, defineComponent } from 'vue'
import { useRouter } from 'vue-router'
import Steps from 'primevue/steps'

export default defineComponent({
  components: {
    Steps
  },
  setup() {
    const router = useRouter()
    const items = ref([
        {
            label: 'Add Nodes',
            to: "/inventory"
        },
        {
            label: 'Configure Services',
            to: "/inventory/configure",
        },
        {
            label: 'Schedule',
            to: "/inventory/schedule",
        }
    ])

    const nextPage = (event: any) => {
      router.push(items.value[event.pageIndex + 1].to);
    }
    const prevPage = (event: any) => {
      router.push(items.value[event.pageIndex - 1].to);
    }
    const complete = () => {
      console.log('complete')
      router.push('/')
    }

    return { 
      items, 
      nextPage, 
      prevPage, 
      complete 
    }
  }
})
</script>

<style scoped lang="scss">
:deep(.p-card-body) {
  padding: 2rem;
}
.steps-container {
  max-width: 500px;
  padding: 20px;
}
.router-container {
  padding: 20px;
}
</style>
