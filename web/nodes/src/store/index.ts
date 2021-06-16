import { createStore, Commit, Dispatch } from 'vuex'
import API from "@/services/nodesService"
import { Node, QueryParameters } from '@/types'

interface VuexContext {
  commit: Commit,
  dispatch: Dispatch
}
interface State {
  nodes: Node[]
  totalCount: number
}

export default createStore({
  state: {
    nodes: [],
    totalCount: 0
  },

  mutations: {
    SAVE_TOTAL_COUNT: (state: State, totalCount: number) => {
      state.totalCount = totalCount
    },
    SAVE_NODES_TO_STATE: (state: State, nodes: Node[]) => {
      state.nodes = [...nodes]
    }
  },

  actions: {
    getNodes: async (context: VuexContext, queryParameters?: QueryParameters) => {
      const resp = await API.getNodes(queryParameters)
      if (resp) {
        context.commit('SAVE_TOTAL_COUNT', resp.totalCount)
        context.commit('SAVE_NODES_TO_STATE', resp.node)
      }
    }
  }
})
