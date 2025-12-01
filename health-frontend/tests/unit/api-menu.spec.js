/**
 * Unit tests for Menu API module
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import menuApi from '@/api/menu'
import request from '@/api/request'

vi.mock('@/api/request', () => ({
  default: vi.fn()
}))

describe('Menu API', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('getAllMenu', () => {
    it('should call request to get all menus', async () => {
      const mockMenus = [
        { id: 1, name: 'Dashboard', path: '/dashboard' },
        { id: 2, name: 'Users', path: '/users' }
      ]
      request.mockResolvedValue({ code: 20000, data: mockMenus })

      const result = await menuApi.getAllMenu()

      expect(request).toHaveBeenCalledWith({
        url: '/menu',
        method: 'get'
      })
      expect(result).toEqual({ code: 20000, data: mockMenus })
    })

    it('should handle empty menu list', async () => {
      request.mockResolvedValue({ code: 20000, data: [] })

      const result = await menuApi.getAllMenu()

      expect(result.data).toEqual([])
    })
  })
})

