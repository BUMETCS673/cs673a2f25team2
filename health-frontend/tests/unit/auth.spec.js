/**
 * Unit tests for authentication utilities
 */
import { describe, it, expect, beforeEach } from 'vitest'
import { getToken, setToken, removeToken, isLoggedIn, getUserInfo, setUserInfo } from '../../src/utils/auth'

describe('Auth Utils', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  describe('Token Management', () => {
    it('should store and retrieve token', () => {
      const token = 'test-token-123'
      setToken(token)
      expect(getToken()).toBe(token)
    })

    it('should return null when no token exists', () => {
      expect(getToken()).toBeNull()
    })

    it('should remove token from localStorage', () => {
      setToken('test-token')
      removeToken()
      expect(getToken()).toBeNull()
    })

    it('should remove userInfo when removing token', () => {
      setToken('test-token')
      setUserInfo({ username: 'test' })
      removeToken()
      expect(getUserInfo()).toBeNull()
    })
  })

  describe('Login Status', () => {
    it('should return false when not logged in', () => {
      expect(isLoggedIn()).toBe(false)
    })

    it('should return true when token exists', () => {
      setToken('token')
      expect(isLoggedIn()).toBe(true)
    })
  })

  describe('User Info Management', () => {
    it('should store and retrieve user info', () => {
      const userInfo = {
        username: 'testuser',
        email: 'test@example.com',
        role: 'user'
      }
      setUserInfo(userInfo)
      expect(getUserInfo()).toEqual(userInfo)
    })

    it('should return null when no user info exists', () => {
      expect(getUserInfo()).toBeNull()
    })

    it('should handle invalid JSON gracefully', () => {
      localStorage.setItem('userInfo', 'invalid-json')
      expect(getUserInfo()).toBeNull()
    })
  })
})

