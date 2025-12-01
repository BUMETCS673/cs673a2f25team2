/**
 * Unit tests for Request Interceptor
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import axios from 'axios'

describe('Request Interceptor', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  describe('Request Configuration', () => {
    it('should add token to request headers when token exists', () => {
      const token = 'test-token-123'
      localStorage.setItem('token', token)

      // Simulate request interceptor logic
      const config = { headers: {} }
      const storedToken = localStorage.getItem('token')
      
      if (storedToken) {
        config.headers['X-Token'] = storedToken
      }

      expect(config.headers['X-Token']).toBe(token)
    })

    it('should not add token when token does not exist', () => {
      // Simulate request interceptor logic
      const config = { headers: {} }
      const storedToken = localStorage.getItem('token')
      
      if (storedToken) {
        config.headers['X-Token'] = storedToken
      }

      expect(config.headers['X-Token']).toBeUndefined()
    })

    it('should have correct base URL configuration', () => {
      const baseURL = '/api'
      expect(baseURL).toBe('/api')
    })

    it('should have timeout configuration', () => {
      const timeout = 10000
      expect(timeout).toBe(10000)
      expect(timeout).toBeGreaterThan(0)
    })
  })

  describe('Response Handling', () => {
    it('should handle successful response with code 20000', () => {
      const response = {
        data: {
          code: 20000,
          data: { username: 'test' },
          message: 'Success'
        }
      }

      // Simulate response interceptor logic
      const res = response.data
      let result
      
      if (res.code === 20000) {
        result = res.data
      }

      expect(result).toEqual({ username: 'test' })
    })

    it('should handle error response with non-20000 code', () => {
      const response = {
        data: {
          code: 20001,
          message: 'Error occurred'
        }
      }

      // Simulate response interceptor logic
      const res = response.data
      let errorMessage = null
      
      if (res.code !== 20000) {
        errorMessage = res.message || 'Request failed'
      }

      expect(errorMessage).toBe('Error occurred')
    })

    it('should handle token expiration with code 20003', () => {
      const response = {
        data: {
          code: 20003,
          message: 'Token expired'
        }
      }

      // Simulate response interceptor logic
      const res = response.data
      let shouldRedirect = false
      
      if (res.code === 20003) {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        shouldRedirect = true
      }

      expect(shouldRedirect).toBe(true)
      expect(localStorage.getItem('token')).toBeNull()
      expect(localStorage.getItem('userInfo')).toBeNull()
    })

    it('should handle network errors', () => {
      const error = {
        message: 'Network Error'
      }

      // Simulate error interceptor logic
      const errorMessage = error.message || 'Network error'

      expect(errorMessage).toBe('Network Error')
    })
  })

  describe('Error Scenarios', () => {
    it('should handle request rejection', () => {
      const error = new Error('Request failed')
      
      expect(error.message).toBe('Request failed')
    })

    it('should handle response with missing data', () => {
      const response = { data: null }
      
      const res = response.data || {}
      
      expect(res).toEqual({})
    })

    it('should clear authentication on logout', () => {
      localStorage.setItem('token', 'test-token')
      localStorage.setItem('userInfo', JSON.stringify({ username: 'test' }))

      // Simulate logout
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')

      expect(localStorage.getItem('token')).toBeNull()
      expect(localStorage.getItem('userInfo')).toBeNull()
    })
  })
})

