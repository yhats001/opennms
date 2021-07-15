import { VuexContext } from '@/types'

const setShowCompleteButton = (context: VuexContext, bool: boolean) => {
  context.commit('SET_SHOW_COMPLETE_BUTTON', bool)
}

const showAddStepNextButton = (context: VuexContext, bool: boolean) => {
  context.commit('SET_SHOW_ADD_STEP_NEXT_BUTTON', bool)
}

const showConfigureServiceStepNextButton = (context: VuexContext, bool: boolean) => {
  context.commit('SET_SHOW_CONFIGURE_SERVICE_STEP_NEXT_BUTTON', bool)
}

const addCompletedService = (context: VuexContext, service: string) => {
  context.commit('ADD_COMPLETED_SERVICE', service)
}

export default {
  addCompletedService,
  setShowCompleteButton,
  showAddStepNextButton,
  showConfigureServiceStepNextButton
}
