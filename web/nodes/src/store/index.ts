import { createStore, Commit, Dispatch } from 'vuex'
import API from "@/services/nodesService"
import { Node } from '@/types'

interface VuexContext {
  commit: Commit,
  dispatch: Dispatch
}
interface State {
  nodes: Node[]
}

export default createStore({
  state: {
    nodes: []
  },

  mutations: {
    SAVE_NODES_TO_STATE: (state: State, nodes: Node[]) => {
      state.nodes = [...nodes]
    }
  },

  actions: {
    getNodes: async (context: VuexContext) => {
      const nodes = await API.getNodes()
      if (nodes) context.commit('SAVE_NODES_TO_STATE', nodes)
    }
  }
})
