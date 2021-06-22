import { IpInterface, Node, SnmpInterface } from '@/types'

export interface State {
  nodes: Node[]
  totalCount: number
  node: Node
  snmpInterfaces: SnmpInterface[]
  snmpInterfacesTotalCount: number
  ipInterfaces: IpInterface[]
  ipInterfacesTotalCount: number
}

const state: State = {
  nodes: [],
  node: {} as Node,
  totalCount: 0,
  snmpInterfaces: [],
  snmpInterfacesTotalCount: 0,
  ipInterfaces: [],
  ipInterfacesTotalCount: 0
}

export default () => state
