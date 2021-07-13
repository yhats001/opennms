import { VuexContext } from '@/types'

const showAddStepNextButton = (context: VuexContext, bool: boolean) => {
  context.commit('SET_SHOW_ADD_STEP_NEXT_BUTTON', bool)
}

export default {
  showAddStepNextButton
}
