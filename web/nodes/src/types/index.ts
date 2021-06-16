
export interface NodeApiResponse {
  count: number
  node: Node[]
  offset: number
  totalCount: number
}
export interface Node {
  location: string
  type: string
  label: string
  id: string
  assetRecord: any
  categories: Category[]
  createTime: number
  foreignId: string
  foreignSource: string
  lastEgressFlow: any
  lastIngressFlow: any
}

export interface Category {
  authorizedGroups: string[]
  id: number
  name: string
}

export interface QueryParameters {
  limit?: number
  offset?: number
  _s?: string
  orderBy?: string
  order?: 'asc' | 'desc'
}
