import { createStore } from 'vuex'

// store modules
import nodesModule from './nodes'
import eventsModule from './events'
import inventoryModule from './inventory'
import locationsModule from './locations'
import ifServicesModule from './ifServices'
import spinnerModule from './spinner'

export default createStore({
  modules: {
    nodesModule,
    eventsModule,
    inventoryModule,
    locationsModule,
    ifServicesModule,
    spinnerModule
  }
})
