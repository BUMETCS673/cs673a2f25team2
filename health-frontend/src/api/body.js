import request from './request'

export default {
  // Upload body information
  uploadBodyInfo(data) {
    return request({
      url: '/user/BodyInformation',
      method: 'post',
      data
    })
  },
  
  // Get body information
  getBodyInfo() {
    return request({
      url: '/user/getBodyInfo',
      method: 'get'
    })
  },
  
  // Add body record
  addBodyNotes(data) {
    return request({
      url: '/user/BodyInformationNotes',
      method: 'post',
      data
    })
  },
  
  // Get body record list
  getBodyNotes(id) {
    return request({
      url: `/user/getBodyNotes/${id}`,
      method: 'get'
    })
  },
  
  // Get body information list (paginated)
  getBodyList(params) {
    return request({
      url: '/user/getBodyList',
      method: 'get',
      params
    })
  },
  
  // Get body information by ID
  getBodyById(id) {
    return request({
      url: `/user/getBodyById/${id}`,
      method: 'get'
    })
  },
  
  // Update body information
  updateBody(data) {
    return request({
      url: '/user/updateBody',
      method: 'post',
      data
    })
  },
  
  // Delete body information
  deleteBody(id) {
    return request({
      url: `/user/deleteBodyById/${id}`,
      method: 'delete'
    })
  },
  
  // Get user body record list
  getUserBodyList(params) {
    return request({
      url: '/user/getUserBodyList',
      method: 'get',
      params
    })
  },
  
  // Get user body record by ID
  getUserBodyById(notesid) {
    return request({
      url: `/user/getUserBodyById/${notesid}`,
      method: 'get'
    })
  },
  
  // Update user body record
  updateUserBody(data) {
    return request({
      url: '/user/updateUserBody',
      method: 'post',
      data
    })
  },
  
  // Delete user body record
  deleteUserBody(notesid) {
    return request({
      url: `/user/deleteUserBodyById/${notesid}`,
      method: 'delete'
    })
  }
}



