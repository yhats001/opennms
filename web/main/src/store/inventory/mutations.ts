import { State } from './state'

const SET_SHOW_ADD_STEP_NEXT_BUTTON = (state: State, bool: boolean) => {
  state.showAddStepNextButton = bool
}

export default {
  SET_SHOW_ADD_STEP_NEXT_BUTTON
}
