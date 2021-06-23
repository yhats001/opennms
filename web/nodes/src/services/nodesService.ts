import { v2, rest } from './axiosInstance'
import { NodeApiResponse, SnmpInterfaceApiResponse, QueryParameters, IpInterfaceApiResponse } from '@/types'
import { queryParametersHandler } from './serviceHelpers'
import dayjs from 'dayjs'

const endpoint = '/nodes'

const getNodes = async (queryParameters?: QueryParameters): Promise<NodeApiResponse | false> => {
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, endpoint)
  }

  try {
    const resp = await v2.get(endpointWithQueryString || endpoint)

    // no content from server
    if (resp.status === 204) {
      return { node: [], totalCount: 0, count: 0, offset: 0 } 
    }

    return resp.data
  } catch (err) {
    return false
  }
}

const getNodeById = async (id: string): Promise<any> => {
  try {
    const resp = await v2.get(`${endpoint}/${id}`)
    return resp.data
  } catch (err) {
    return false
  }
}

const getNodeSnmpInterfaces = async (id: string, queryParameters?: QueryParameters): Promise<SnmpInterfaceApiResponse | false> => {
  const snmpInterfaceEndpoint = `${endpoint}/${id}/snmpinterfaces`
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, snmpInterfaceEndpoint)
  }

  try {
    const resp = await v2.get(`${endpointWithQueryString || snmpInterfaceEndpoint}`)

    // no content from server
    if (resp.status === 204) {
      return { snmpInterface: [], totalCount: 0, count: 0, offset: 0 }
    }

    return resp.data
  } catch (err) {
    return false
  }
}

const getNodeIpInterfaces = async (id: string, queryParameters?: QueryParameters): Promise<IpInterfaceApiResponse | false> => {
  const ipInterfaceEndpoint = `${endpoint}/${id}/ipinterfaces`
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, ipInterfaceEndpoint)
  }

  try {
    const resp = await v2.get(`${endpointWithQueryString || ipInterfaceEndpoint}`)

    // no content from server
    if (resp.status === 204) {
      return { ipInterface: [], totalCount: 0, count: 0, offset: 0 }
    }

    return resp.data
  } catch (err) {
    return false
  }
}

const getNodeAvailabilityImage = async (id: string, ipAddress: string, service: string): Promise<any> => {
  const now = dayjs()
  const startTime = now.subtract(1, 'day').unix()
  const endTime = now.unix()

  try {
    const resp = await rest.get(`/timeline/html/${id}/${ipAddress}/${service}/${startTime}/${endTime}`)

    return resp.data
  } catch (err) {
    return false
  }
}

const getNodeAvailabilityPercentage = async (id: string): Promise<any> => {
  try {
    const resp = await rest.get(`/availabilityRestService/nodes/${id}`)

    return resp.data
  } catch (err) {
    return false
  }
}

export {
  getNodes,
  getNodeById,
  getNodeIpInterfaces,
  getNodeSnmpInterfaces,
  getNodeAvailabilityImage,
  getNodeAvailabilityPercentage
}
