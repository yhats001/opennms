import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import Nodes from '../views/Nodes.vue'
import NodeDetails from '../views/NodeDetails.vue'
import Inventory from '../views/Inventory.vue'
import StepAdd from '../components/inventory/StepAdd.vue'
import StepSchedule from '../components/inventory/StepSchedule.vue'
import StepConfigure from '../components/inventory/StepConfigure.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '',
    name: 'Nodes',
    component: Nodes
  },
  {
    path: '/node/:id',
    name: 'Node Details',
    component: NodeDetails
  },
  {
    path: '/inventory',
    name: 'Inventory',
    component: Inventory,
    children: [
      {
        path: '', // use base /inventory path
        component: StepAdd
      },
      {
        path: 'configure',
        component: StepConfigure
      },
      {
        path: 'schedule',
        component: StepSchedule
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*', // catch other paths and redirect
    redirect: "/"
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
