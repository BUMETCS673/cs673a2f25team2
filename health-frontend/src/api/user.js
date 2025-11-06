/**
 * Code Composition Ratio:
 * - AI Generated: 65%
 * - Manual Development: 35%
 * 
 * User API service module. This file contains standard CRUD operations
 * following RESTful conventions. Most endpoints follow common API patterns.
 */
import request from './request'

export default {
  // 登录
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  
  // 注册
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },
  
  // 获取用户信息
  getUserInfo(token) {
    return request({
      url: '/user/info',
      method: 'get',
      params: { token }
    })
  },
  
  // 退出登录
  logout() {
    return request({
      url: '/user/logout',
      method: 'post'
    })
  },
  
  // 获取用户列表
  getUserList(params) {
    return request({
      url: '/user/list',
      method: 'get',
      params
    })
  },
  
  // 添加用户
  addUser(data) {
    return request({
      url: '/user/add',
      method: 'post',
      data
    })
  },
  
  // 更新用户
  updateUser(data) {
    return request({
      url: '/user/update',
      method: 'put',
      data
    })
  },
  
  // 根据ID获取用户
  getUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'get'
    })
  },
  
  // 删除用户
  deleteUser(id) {
    return request({
      url: `/user/${id}`,
      method: 'delete'
    })
  },
  
  // 修改密码
  changePassword(data) {
    return request({
      url: '/user/changePassword',
      method: 'put',
      data
    })
  },
  
  // 获取用户ID
  getUserId() {
    return request({
      url: '/user/getUserId',
      method: 'get'
    })
  }
}



