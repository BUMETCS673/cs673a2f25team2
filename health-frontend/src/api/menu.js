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



