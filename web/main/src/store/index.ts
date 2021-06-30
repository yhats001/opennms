import { createStore } from 'vuex'

// store modules
import nodesModule from './nodes'
import eventsModule from './events'
import ifServicesModule from './ifServices'


export default createStore({
  modules: {
    nodesModule,
    eventsModule,
    ifServicesModule
  }
})
