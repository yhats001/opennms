import API from "@/services"
import { Commit, Dispatch } from 'vuex'
import { QueryParameters } from '@/types'

interface VuexContext {
  commit: Commit,
  dispatch: Dispatch
}

const getNodeIfServices = async (context: VuexContext, queryParameters?: QueryParameters) => {
  const resp = await API.getNodeIfServices(queryParameters)
  if (resp) {
    context.commit('SAVE_IF_SERVICES_TO_STATE', resp['monitored-service'])
    context.commit('SAVE_IF_SERVICES_TOTAL_COUNT', resp.totalCount)
    return resp['monitored-service']
  }
}

export default {
  getNodeIfServices
}
