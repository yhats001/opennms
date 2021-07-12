<template>
<div>
  <Dropdown
    ref="childRef"
    v-model="searchStr"
    :options="results"
    optionLabel="label"
    optionGroupLabel="label"
    optionGroupChildren="results"
    placeholder="Search"
    type="text"
    class="search"
    @input="search"
    @change="selectItem"
    :loading="loading"
    editable>

    <template #optiongroup="slotProps">
        <div>
          <div>{{slotProps.option.label}}</div>
        </div>
    </template>
  </Dropdown>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue'
import { debounce } from 'lodash'
import { useStore } from 'vuex'
import Dropdown from 'primevue/dropdown'
import { useRouter } from 'vue-router'

export default defineComponent({
  components: {
    Dropdown
  },
  setup() {
    const router = useRouter()
    const store = useStore()

    // data
    const searchStr = ref()
    const loading = ref(false)
    const childRef = ref()

    // methods
    const openDropdown = () => childRef.value.show()

    const selectItem = ({ value }: { value: { url: string } }) => {
      // parse selected item url and redirect
      const path = value.url.split('?')[1].split('=')
      router.push(`/${path[0]}/${path[1]}`)
    }

    const search = debounce(async (e) => {
      loading.value = true
      await store.dispatch('searchModule/search', e.target.value)
      loading.value = false

      openDropdown()
    }, 600)

    // computed
    const results = computed(() => store.state.searchModule.searchResults)

    return {
      search,
      results,
      loading,
      childRef,
      searchStr,
      selectItem
    }
  }
})
</script>

<style lang="scss" scoped>
  .search {
    width: 500px
  }
</style>
