export interface State {
  completedServices: string[]
  showCompleteButton: boolean
  showAddStepNextButton: boolean
  showConfigureServiceStepNextButton: boolean
}

const state: State = {
  completedServices: [],
  showCompleteButton: false,
  showAddStepNextButton: false,
  showConfigureServiceStepNextButton: false
}

export default state
