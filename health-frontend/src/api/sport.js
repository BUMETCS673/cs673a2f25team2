/**
 * Code Composition Ratio:
 * - AI Generated: 70%
 * - Manual Development: 30%
 * 
 * Sports information API service module. Simple CRUD operations following
 * standard RESTful patterns with minimal custom logic.
 */
import request from './request'

export default {
  // 获取所有运动信息
  getAllSportInfo() {
    return request({
      url: '/sport/getAllSportInfo',
      method: 'get'
    })
  },
  
  // 获取运动列表（分页）
  getSportList(params) {
    return request({
      url: '/sport/getSportList',
      method: 'get',
      params
    })
  },
  
  // 添加运动信息
  addSport(data) {
    return request({
      url: '/sport/add',
      method: 'post',
      data
    })
  },
  
  // 更新运动信息
  updateSport(data) {
    return request({
      url: '/sport/update',
      method: 'put',
      data
    })
  },
  
  // 根据ID获取运动信息
  getSportById(id) {
    return request({
      url: `/sport/${id}`,
      method: 'get'
    })
  },
  
  // 删除运动信息
  deleteSport(id) {
    return request({
      url: `/sport/${id}`,
      method: 'delete'
    })
  }
}



