import { IpInterface, Node, NodeAvailability, SnmpInterface } from '@/types'
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

const SAVE_SNMP_INTERFACES_TO_STATE = (state: State, snmpInterfaces: SnmpInterface[]) => {
  state.snmpInterfaces = snmpInterfaces
}

const SAVE_SNMP_INTERFACES_TOTAL_COUNT = (state: State, snmpInterfacesTotalCount: number) => {
  state.snmpInterfacesTotalCount = snmpInterfacesTotalCount
}

const SAVE_IP_INTERFACES_TO_STATE = (state: State, ipInterfaces: IpInterface[]) => {
  state.ipInterfaces = ipInterfaces
}

const SAVE_IP_INTERFACES_TOTAL_COUNT = (state: State, ipInterfacesTotalCount: number) => {
  state.ipInterfacesTotalCount = ipInterfacesTotalCount
}

const SAVE_NODE_AVAILABILITY_TO_STATE = (state: State, availability: NodeAvailability) => {
  state.availability = availability
}

export default {
  SAVE_TOTAL_COUNT,
  SAVE_NODES_TO_STATE,
  SAVE_NODE_DETAILS_TO_STATE,
  SAVE_SNMP_INTERFACES_TO_STATE,
  SAVE_SNMP_INTERFACES_TOTAL_COUNT,
  SAVE_IP_INTERFACES_TO_STATE,
  SAVE_IP_INTERFACES_TOTAL_COUNT,
  SAVE_NODE_AVAILABILITY_TO_STATE
}
