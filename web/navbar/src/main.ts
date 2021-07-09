import { h, createApp } from 'vue'
import singleSpaVue from 'single-spa-vue'
import PrimeVue from 'primevue/config'
import store from './store'

import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

import App from './App.vue'

const vueLifecycles = singleSpaVue({
  createApp,
  appOptions: {
    render() {
      return h(App, {})
    },
  },
  handleInstance: (app) => {
    app.use(PrimeVue)
    app.use(store)
  }
})

export const bootstrap = vueLifecycles.bootstrap
export const mount = vueLifecycles.mount
export const unmount = vueLifecycles.unmount
