import API from "@/services"
import { Commit, Dispatch } from 'vuex'
import { QueryParameters } from '@/types'

interface VuexContext {
  commit: Commit,
  dispatch: Dispatch
}

const getEvents = async (context: VuexContext, queryParameters?: QueryParameters) => {
  const resp = await API.getEvents(queryParameters)
  if (resp) {
    context.commit('SAVE_EVENTS_TO_STATE', resp.event)
    context.commit('SAVE_TOTAL_COUNT', resp.totalCount)
  }
}

export default {
  getEvents
}
