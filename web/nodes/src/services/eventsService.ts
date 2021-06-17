import { portal } from './axiosInstance'
import { QueryParameters } from '@/types'
import { queryParametersHandler } from './serviceHelpers'

const endpoint = '/events'

const getEvents = async (queryParameters?: QueryParameters): Promise<any> => {
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, endpoint)
  }

  try {
    const resp = await portal.get(endpointWithQueryString)

    // no content from server
    if (resp.status === 204) {
      return []
    }

    return resp.data
  } catch (err) {
    return false
  }
}

export {
  getEvents
}
