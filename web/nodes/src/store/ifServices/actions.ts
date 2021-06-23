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
    // context.commit('', resp.totalCount)
    context.commit('SAVE_IF_SERVICES_TO_STATE', resp)
  }
}

export default {
  getNodeIfServices
}
