import { createStore } from 'vuex'
import nodesModule from './nodes'


export default createStore({
  modules: {
    nodesModule
  }
})
