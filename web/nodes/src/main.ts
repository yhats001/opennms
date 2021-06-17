import { h, createApp } from 'vue'
import singleSpaVue from 'single-spa-vue'
import PrimeVue from 'primevue/config'

import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

import App from './App.vue'
import router from './router'
import store from '@/store'

// test
import Nodes from './views/Nodes.vue'

const vueLifecycles = singleSpaVue({
  createApp,
  appOptions: {
    render() {
      return h(App, {})
    },
  },
  handleInstance: (app) => {
    app.use(router)
    app.use(store)
    app.use(PrimeVue)
  }
})

export const bootstrap = vueLifecycles.bootstrap
export const mount = vueLifecycles.mount
export const unmount = vueLifecycles.unmount
