import { portal } from './axiosInstance'
import { Node } from '@/types'

const getNodes = async (): Promise<Node[]> => {
  try {
    const nodes = await portal.get('/nodes')
    return nodes.data
  } catch (err) {
    return []
  }
}

export default {
  getNodes
}
