import request from './request'

export default {
  // Login
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  
  // Register
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },
  
  // Get user information
  getUserInfo(token) {
    return request({
      url: '/user/info',
      method: 'get',
      params: { token }
    })
  },
  
  // Logout
  logout() {
    return request({
      url: '/user/logout',
      method: 'post'
    })
  },
  
  // Get user list
  getUserList(params) {
    return request({
      url: '/user/list',
      method: 'get',
      params
    })
  },
  
  // Add user
  addUser(data) {
    return request({
      url: '/user/add',
      method: 'post',
      data
    })
  },
  
  // Update user
  updateUser(data) {
    return request({
      url: '/user/update',
      method: 'put',
      data
    })
  },
  
  // Get user by ID
  getUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'get'
    })
  },
  
  // Delete user
  deleteUser(id) {
    return request({
      url: `/user/${id}`,
      method: 'delete'
    })
  },
  
  // Change password
  changePassword(data) {
    return request({
      url: '/user/changePassword',
      method: 'put',
      data
    })
  },
  
  // Get user ID
  getUserId() {
    return request({
      url: '/user/getUserId',
      method: 'get'
    })
  }
}



