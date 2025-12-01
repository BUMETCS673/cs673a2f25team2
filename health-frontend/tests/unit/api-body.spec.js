/**
 * Unit tests for Body API module
 */
import { describe, it, expect, beforeEach, vi } from 'vitest'
import bodyApi from '@/api/body'
import request from '@/api/request'

vi.mock('@/api/request', () => ({
  default: vi.fn()
}))

describe('Body API', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('uploadBodyInfo', () => {
    it('should call request to upload body info', async () => {
      const bodyData = { 
        name: 'John',
        age: 30,
        height: 175,
        weight: 70
      }
      request.mockResolvedValue({ code: 20000 })

      await bodyApi.uploadBodyInfo(bodyData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/BodyInformation',
        method: 'post',
        data: bodyData
      })
    })
  })

  describe('getBodyInfo', () => {
    it('should call request to get body info', async () => {
      request.mockResolvedValue({ code: 20000, data: {} })

      await bodyApi.getBodyInfo()

      expect(request).toHaveBeenCalledWith({
        url: '/user/getBodyInfo',
        method: 'get'
      })
    })
  })

  describe('addBodyNotes', () => {
    it('should call request to add body notes', async () => {
      const notesData = { 
        date: '2024-11-10',
        weight: 70,
        bloodSugar: 5.5
      }
      request.mockResolvedValue({ code: 20000 })

      await bodyApi.addBodyNotes(notesData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/BodyInformationNotes',
        method: 'post',
        data: notesData
      })
    })
  })

  describe('getBodyNotes', () => {
    it('should call request with user id', async () => {
      const userId = 1
      request.mockResolvedValue({ code: 20000, data: [] })

      await bodyApi.getBodyNotes(userId)

      expect(request).toHaveBeenCalledWith({
        url: `/user/getBodyNotes/${userId}`,
        method: 'get'
      })
    })
  })

  describe('updateBody', () => {
    it('should call request to update body info', async () => {
      const bodyData = { id: 1, weight: 71 }
      request.mockResolvedValue({ code: 20000 })

      await bodyApi.updateBody(bodyData)

      expect(request).toHaveBeenCalledWith({
        url: '/user/updateBody',
        method: 'post',
        data: bodyData
      })
    })
  })

  describe('deleteBody', () => {
    it('should call request to delete body info', async () => {
      const bodyId = 1
      request.mockResolvedValue({ code: 20000 })

      await bodyApi.deleteBody(bodyId)

      expect(request).toHaveBeenCalledWith({
        url: `/user/deleteBodyById/${bodyId}`,
        method: 'delete'
      })
    })
  })

  describe('getUserBodyList', () => {
    it('should call request with query parameters', async () => {
      const params = { page: 1, pageSize: 10 }
      request.mockResolvedValue({ code: 20000, data: { records: [] } })

      await bodyApi.getUserBodyList(params)

      expect(request).toHaveBeenCalledWith({
        url: '/user/getUserBodyList',
        method: 'get',
        params
      })
    })
  })
})

