import {
  getNodes,
  getNodeById,
  getNodeOutages,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityPercentage
} from './nodesService'

import { getEvents } from './eventsService'
import { getNodeIfServices } from './ifService'

export default {
  getNodes,
  getEvents,
  getNodeById,
  getNodeOutages,
  getNodeIfServices,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityPercentage
}
