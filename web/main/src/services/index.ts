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
import { getLocations } from './locationsService'

export default {
  getNodes,
  getEvents,
  getNodeById,
  getLocations,
  getNodeOutages,
  getNodeIfServices,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityPercentage
}
