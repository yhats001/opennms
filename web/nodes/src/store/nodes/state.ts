import { IpInterface, Node, NodeAvailability, SnmpInterface } from '@/types'

export interface State {
  nodes: Node[]
  totalCount: number
  node: Node
  snmpInterfaces: SnmpInterface[]
  snmpInterfacesTotalCount: number
  ipInterfaces: IpInterface[]
  ipInterfacesTotalCount: number,
  availability: NodeAvailability
}

const state: State = {
  nodes: [],
  node: {} as Node,
  totalCount: 0,
  snmpInterfaces: [],
  snmpInterfacesTotalCount: 0,
  ipInterfaces: [],
  ipInterfacesTotalCount: 0,
  availability: {} as NodeAvailability
}

export default () => state
