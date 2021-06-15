import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Nodes from '../views/Nodes.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Nodes',
    component: Nodes
  },
  {
    path: '/nodes:id',
    name: 'Node Details',
    component: () => import('../views/NodeDetails.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
