import { IPRangeResponse, SNMPDetectResponse } from "@/types"

export interface State {
  completedServices: string[]
  showCompleteButton: boolean
  showAddStepNextButton: boolean
  showConfigureServiceStepNextButton: boolean
  ipRangeResponses: IPRangeResponse[]
  snmpDetectResponses: SNMPDetectResponse[]
}

const state: State = {
  completedServices: [],
  showCompleteButton: false,
  showAddStepNextButton: false,
  showConfigureServiceStepNextButton: false,
  ipRangeResponses: [],
  snmpDetectResponses: []
}

export default state
