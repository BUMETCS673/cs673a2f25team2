import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { title: 'Login' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
    meta: { title: 'Register' }
  },
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),
    redirect: '/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: 'Dashboard', icon: 'Odometer' }
      },
      {
        path: 'body-info',
        name: 'BodyInfo',
        component: () => import('../views/BodyInfo.vue'),
        meta: { title: 'Body Information', icon: 'User' }
      },
      {
        path: 'body-notes',
        name: 'BodyNotes',
        component: () => import('../views/BodyNotes.vue'),
        meta: { title: 'Health Records', icon: 'Document' }
      },
      {
        path: 'sport-info',
        name: 'SportInfo',
        component: () => import('../views/SportInfo.vue'),
        meta: { title: 'Sports Knowledge', icon: 'Basketball' }
      },
      {
        path: 'ai-chat',
        name: 'AiChat',
        component: () => import('../views/AiChat.vue'),
        meta: { title: 'AI Chat', icon: 'ChatDotRound' }
      },
      {
        path: 'user-management',
        name: 'UserManagement',
        component: () => import('../views/UserManagement.vue'),
        meta: { title: 'User Management', icon: 'Avatar' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue'),
        meta: { title: 'Personal Center', icon: 'UserFilled' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router



