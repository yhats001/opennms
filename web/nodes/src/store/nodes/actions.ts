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

const getNodeSnmpInterfaces = async (context: VuexContext, payload: { id: string, queryParameters?: QueryParameters }) => {
  const resp = await API.getNodeSnmpInterfaces(payload.id, payload.queryParameters)
  if (resp) {
    context.commit('SAVE_SNMP_INTERFACES_TO_STATE', resp.snmpInterface)
    context.commit('SAVE_SNMP_INTERFACES_TOTAL_COUNT', resp.totalCount)
  }
}

const getNodeIpInterfaces = async (context: VuexContext, payload: { id: string, queryParameters?: QueryParameters }) => {
  const resp = await API.getNodeIpInterfaces(payload.id, payload.queryParameters)
  if (resp) {
    context.commit('SAVE_IP_INTERFACES_TO_STATE', resp.ipInterface)
    context.commit('SAVE_IP_INTERFACES_TOTAL_COUNT', resp.totalCount)
  }
}

const getNodeAvailabilityPercentage = async (context: VuexContext, id: string) => {
  const availability = await API.getNodeAvailabilityPercentage(id)
  if (availability) {
    context.commit('SAVE_NODE_AVAILABILITY_TO_STATE', availability)
  }
}

export default {
  getNodes,
  getNodeById,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityPercentage
}
