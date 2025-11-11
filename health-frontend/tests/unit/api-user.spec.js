/**
 * Unit tests for User API module
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import userApi from '@/api/user'
import request from '@/api/request'

// Mock the request module
vi.mock('@/api/request', () => ({
  default: vi.fn()
}))

describe('User API', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('login', () => {
    it('should call request with correct parameters', async () => {
      const mockData = { username: 'test', password: 'test123' }
      request.mockResolvedValue({ code: 20000, data: { token: 'mock-token' } })

      await userApi.login(mockData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/login',
        method: 'post',
        data: mockData
      })
    })
  })

  describe('register', () => {
    it('should call request with correct parameters', async () => {
      const mockData = { username: 'newuser', password: 'pass123', email: 'test@test.com' }
      request.mockResolvedValue({ code: 20000 })

      await userApi.register(mockData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/register',
        method: 'post',
        data: mockData
      })
    })
  })

  describe('getUserInfo', () => {
    it('should call request with token parameter', async () => {
      const token = 'test-token'
      request.mockResolvedValue({ code: 20000, data: { username: 'test' } })

      await userApi.getUserInfo(token)

      expect(request).toHaveBeenCalledWith({
        url: '/user/info',
        method: 'get',
        params: { token }
      })
    })
  })

  describe('getUserList', () => {
    it('should call request with query parameters', async () => {
      const params = { page: 1, pageSize: 10 }
      request.mockResolvedValue({ code: 20000, data: [] })

      await userApi.getUserList(params)

      expect(request).toHaveBeenCalledWith({
        url: '/user/list',
        method: 'get',
        params
      })
    })
  })

  describe('updateUser', () => {
    it('should call request with user data', async () => {
      const userData = { id: 1, username: 'updated' }
      request.mockResolvedValue({ code: 20000 })

      await userApi.updateUser(userData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/update',
        method: 'put',
        data: userData
      })
    })
  })

  describe('deleteUser', () => {
    it('should call request with user id', async () => {
      const userId = 1
      request.mockResolvedValue({ code: 20000 })

      await userApi.deleteUser(userId)

      expect(request).toHaveBeenCalledWith({
        url: `/user/${userId}`,
        method: 'delete'
      })
    })
  })

  describe('changePassword', () => {
    it('should call request with password data', async () => {
      const passwordData = { 
        username: 'test',
        password: 'old',
        newPassword: 'new'
      }
      request.mockResolvedValue({ code: 20000 })

      await userApi.changePassword(passwordData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/changePassword',
        method: 'put',
        data: passwordData
      })
    })
  })
})

