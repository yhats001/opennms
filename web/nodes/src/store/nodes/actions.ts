import API from "@/services"
import { Commit, Dispatch } from 'vuex'
import { Node, QueryParameters } from '@/types'

interface VuexContext {
  commit: Commit,
  dispatch: Dispatch
}

const getNodes = async (context: VuexContext, queryParameters?: QueryParameters) => {
  const resp = await API.getNodes(queryParameters)
  if (resp) {
    context.commit('SAVE_TOTAL_COUNT', resp.totalCount)
    context.commit('SAVE_NODES_TO_STATE', resp.node)
  }
}

const getNodeById = async (context: VuexContext, node: Node) => {
  const resp = await API.getNodeById(node.id)
  if (resp) {
    context.commit('SAVE_NODE_DETAILS_TO_STATE', resp)
  }
}

export default {
  getNodes,
  getNodeById
}
