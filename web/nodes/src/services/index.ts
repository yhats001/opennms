import {
  getNodes,
  getNodeById, 
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityImage,
  getNodeAvailabilityPercentage 
} from './nodesService'

import { getEvents } from './eventsService'
import { getNodeIfServices } from './ifService'

export default {
  getNodes,
  getEvents,
  getNodeById,
  getNodeIfServices,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityImage,
  getNodeAvailabilityPercentage
}
