/**
 * Authentication related utility functions
 */

/**
 * Get stored token
 */
export function getToken() {
  return localStorage.getItem('token')
}

/**
 * Set token
 */
export function setToken(token) {
  localStorage.setItem('token', token)
}

/**
 * Remove token
 */
export function removeToken() {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
}

/**
 * Get user information
 */
export function getUserInfo() {
  const userInfoStr = localStorage.getItem('userInfo')
  return userInfoStr ? JSON.parse(userInfoStr) : null
}

/**
 * Set user information
 */
export function setUserInfo(userInfo) {
  localStorage.setItem('userInfo', JSON.stringify(userInfo))
}

/**
 * Check if logged in
 */
export function isLoggedIn() {
  return !!getToken()
}



