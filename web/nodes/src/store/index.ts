import { createStore } from 'vuex'

// store modules
import nodesModule from './nodes'
import eventsModule from './events'


export default createStore({
  modules: {
    nodesModule,
    eventsModule
  }
})
