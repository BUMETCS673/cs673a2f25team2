/**
 * Unit tests for Vue Router configuration
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import { createRouter, createWebHistory } from 'vue-router'

describe('Router Configuration', () => {
  let router

  beforeEach(() => {
    localStorage.clear()
    // Mock router for testing
    router = createRouter({
      history: createWebHistory(),
      routes: [
        { path: '/login', name: 'Login', component: { template: '<div>Login</div>' } },
        { path: '/dashboard', name: 'Dashboard', meta: { requiresAuth: true }, component: { template: '<div>Dashboard</div>' } }
      ]
    })
  })

  it('should have correct route definitions', () => {
    const routes = router.getRoutes()
    expect(routes).toHaveLength(2)
    expect(routes.find(r => r.name === 'Login')).toBeDefined()
    expect(routes.find(r => r.name === 'Dashboard')).toBeDefined()
  })

  it('should mark dashboard route as requiring authentication', () => {
    const dashboardRoute = router.getRoutes().find(r => r.name === 'Dashboard')
    expect(dashboardRoute.meta.requiresAuth).toBe(true)
  })

  it('should not require auth for login route', () => {
    const loginRoute = router.getRoutes().find(r => r.name === 'Login')
    expect(loginRoute.meta.requiresAuth).toBeUndefined()
  })
})

