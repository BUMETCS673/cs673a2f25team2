/**
 * Code Composition Ratio:
 * - AI Generated: 65%
 * - Manual Development: 35%
 * 
 * Body information API service module. Standard CRUD operations for
 * body data and health records following RESTful API conventions.
 */
import request from './request'

export default {
  // 上传身体信息
  uploadBodyInfo(data) {
    return request({
      url: '/user/BodyInformation',
      method: 'post',
      data
    })
  },
  
  // 获取身体信息
  getBodyInfo() {
    return request({
      url: '/user/getBodyInfo',
      method: 'get'
    })
  },
  
  // 添加身体记录
  addBodyNotes(data) {
    return request({
      url: '/user/BodyInformationNotes',
      method: 'post',
      data
    })
  },
  
  // 获取身体记录列表
  getBodyNotes(id) {
    return request({
      url: `/user/getBodyNotes/${id}`,
      method: 'get'
    })
  },
  
  // 获取身体信息列表（分页）
  getBodyList(params) {
    return request({
      url: '/user/getBodyList',
      method: 'get',
      params
    })
  },
  
  // 根据ID获取身体信息
  getBodyById(id) {
    return request({
      url: `/user/getBodyById/${id}`,
      method: 'get'
    })
  },
  
  // 更新身体信息
  updateBody(data) {
    return request({
      url: '/user/updateBody',
      method: 'post',
      data
    })
  },
  
  // 删除身体信息
  deleteBody(id) {
    return request({
      url: `/user/deleteBodyById/${id}`,
      method: 'delete'
    })
  },
  
  // 获取用户身体记录列表
  getUserBodyList(params) {
    return request({
      url: '/user/getUserBodyList',
      method: 'get',
      params
    })
  },
  
  // 根据ID获取用户身体记录
  getUserBodyById(notesid) {
    return request({
      url: `/user/getUserBodyById/${notesid}`,
      method: 'get'
    })
  },
  
  // 更新用户身体记录
  updateUserBody(data) {
    return request({
      url: '/user/updateUserBody',
      method: 'post',
      data
    })
  },
  
  // 删除用户身体记录
  deleteUserBody(notesid) {
    return request({
      url: `/user/deleteUserBodyById/${notesid}`,
      method: 'delete'
    })
  }
}



