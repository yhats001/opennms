import { IPRange, SNMPDetectRequest, VuexContext } from '@/types'
import API from '../../services'

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

const scanIPRanges = async (context: VuexContext, ipRanges: IPRange[]) => {
  const successfulResp = await API.scanIPRanges(ipRanges) 

  if (successfulResp) {
    context.commit('SAVE_IP_RANGE_SCAN_RESPONSE', successfulResp)
  }

  return successfulResp
}

const detectSNMPAvailable = async (context: VuexContext, SNMPDetectRequestObjects: SNMPDetectRequest[]) => {
  const successfulResp = await API.detectSNMPAvailable(SNMPDetectRequestObjects) 

  if (successfulResp) {
    context.commit('SAVE_SNMP_DETECT_RESPONSE', successfulResp)
  }

  return successfulResp
}

export default {
  scanIPRanges,
  detectSNMPAvailable,
  addCompletedService,
  setShowCompleteButton,
  showAddStepNextButton,
  showConfigureServiceStepNextButton
}
