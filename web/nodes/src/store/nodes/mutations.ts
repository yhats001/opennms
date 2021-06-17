import { Node } from '@/types'
import { State } from './state'

const SAVE_TOTAL_COUNT = (state: State, totalCount: number) => {
  state.totalCount = totalCount
}

const SAVE_NODES_TO_STATE = (state: State, nodes: Node[]) => {
  state.nodes = [...nodes]
}

const SAVE_NODE_DETAILS_TO_STATE = (state: State, node: Node) => {
  state.node = node
}
export default {
  SAVE_TOTAL_COUNT,
  SAVE_NODES_TO_STATE,
  SAVE_NODE_DETAILS_TO_STATE
}
