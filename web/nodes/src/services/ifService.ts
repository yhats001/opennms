import { v2, rest } from './axiosInstance'
import { QueryParameters } from '@/types'
import { queryParametersHandler } from './serviceHelpers'

const endpoint = '/ifservices'

const getNodeIfServices = async (queryParameters?: QueryParameters): Promise<any> => {
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, endpoint)
  }

  try {
    const resp = await v2.get(endpointWithQueryString || endpoint)

    if (resp.status === 204) {
      return { service: [], totalCount: 0, count: 0, offset: 0 } 
    }

    return resp.data
  } catch (err) {
    return false
  }
}

export {
  getNodeIfServices
}
