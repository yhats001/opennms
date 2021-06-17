import { Node } from '@/types'

export interface State {
  nodes: Node[]
  totalCount: number,
  node: Node
}

const state: State = {
  nodes: [],
  node: {} as Node,
  totalCount: 0
}

export default () => state
