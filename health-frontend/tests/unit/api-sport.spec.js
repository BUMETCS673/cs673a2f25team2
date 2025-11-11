/**
 * Unit tests for Sport API module
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import sportApi from '@/api/sport'
import request from '@/api/request'

vi.mock('@/api/request', () => ({
  default: vi.fn()
}))

describe('Sport API', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('getAllSportInfo', () => {
    it('should call request to get all sports', async () => {
      request.mockResolvedValue({ code: 20000, data: [] })

      await sportApi.getAllSportInfo()

      expect(request).toHaveBeenCalledWith({
        url: '/sport/getAllSportInfo',
        method: 'get'
      })
    })
  })

  describe('getSportList', () => {
    it('should call request with pagination parameters', async () => {
      const params = { page: 1, pageSize: 10 }
      request.mockResolvedValue({ code: 20000, data: { records: [] } })

      await sportApi.getSportList(params)

      expect(request).toHaveBeenCalledWith({
        url: '/sport/getSportList',
        method: 'get',
        params
      })
    })
  })

  describe('addSport', () => {
    it('should call request to add sport', async () => {
      const sportData = { 
        sportType: 'Running',
        suitableTime: 'Morning'
      }
      request.mockResolvedValue({ code: 20000 })

      await sportApi.addSport(sportData)

      expect(request).toHaveBeenCalledWith({
        url: '/sport/add',
        method: 'post',
        data: sportData
      })
    })
  })

  describe('updateSport', () => {
    it('should call request to update sport', async () => {
      const sportData = { id: 1, sportType: 'Swimming' }
      request.mockResolvedValue({ code: 20000 })

      await sportApi.updateSport(sportData)

      expect(request).toHaveBeenCalledWith({
        url: '/sport/update',
        method: 'put',
        data: sportData
      })
    })
  })

  describe('getSportById', () => {
    it('should call request with sport id', async () => {
      const sportId = 1
      request.mockResolvedValue({ code: 20000, data: {} })

      await sportApi.getSportById(sportId)

      expect(request).toHaveBeenCalledWith({
        url: `/sport/${sportId}`,
        method: 'get'
      })
    })
  })

  describe('deleteSport', () => {
    it('should call request to delete sport', async () => {
      const sportId = 1
      request.mockResolvedValue({ code: 20000 })

      await sportApi.deleteSport(sportId)

      expect(request).toHaveBeenCalledWith({
        url: `/sport/${sportId}`,
        method: 'delete'
      })
    })
  })
})

