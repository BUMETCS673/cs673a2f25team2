import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import HealthRecords from './components/HealthRecords.vue'
import CreateRecord from './components/CreateRecord.vue'
import { getToken } from './api/client'

const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/records' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/records', component: HealthRecords, meta: { requireAuth: true } },
  { path: '/records/new', component: CreateRecord, meta: { requireAuth: true } },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to) => {
  const needAuth = (to.meta as any)?.requireAuth
  if (needAuth && !getToken()) {
    return '/login'
  }
})
