import { portal } from './axiosInstance'
import { NodeApiResponse, QueryParameters } from '@/types'
import { queryParametersHandler } from './serviceHelpers'

const endpoint = '/nodes'

const getNodes = async (queryParameters?: QueryParameters): Promise<NodeApiResponse | false> => {
  let endpointWithQueryString = ''

  if (queryParameters) {
    endpointWithQueryString = queryParametersHandler(queryParameters, endpoint)
  }

  try {
    const resp = await portal.get(endpointWithQueryString)

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
    const resp = await portal.get(`${endpoint}/${id}`)
    return resp.data
  } catch (err) {
    return false
  }
}

export default {
  getNodes,
  getNodeById
}
