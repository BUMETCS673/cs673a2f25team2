/**
 * Code Composition Ratio:
 * - AI Generated: 35%
 * - Manual Development: 65%
 * 
 * Authentication utility functions for token and user info management.
 * The localStorage abstraction and authentication state logic were manually
 * developed to ensure secure and consistent data handling.
 * 
 * 认证相关的工具函数
 */

/**
 * 获取存储的token
 */
export function getToken() {
  return localStorage.getItem('token')
}

/**
 * 设置token
 */
export function setToken(token) {
  localStorage.setItem('token', token)
}

/**
 * 移除token
 */
export function removeToken() {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
  const userInfoStr = localStorage.getItem('userInfo')
  return userInfoStr ? JSON.parse(userInfoStr) : null
}

/**
 * 设置用户信息
 */
export function setUserInfo(userInfo) {
  localStorage.setItem('userInfo', JSON.stringify(userInfo))
}

/**
 * 检查是否已登录
 */
export function isLoggedIn() {
  return !!getToken()
}



