/**
 * Code Composition Ratio:
 * - AI Generated: 75%
 * - Manual Development: 25%
 * 
 * Menu API service module. Simple API wrapper with single endpoint
 * following standard framework patterns.
 */
import request from './request'

export default {
  // Get all menus
  getAllMenu() {
    return request({
      url: '/menu',
      method: 'get'
    })
  }
}



