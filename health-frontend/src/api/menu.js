import request from './request'

export default {
  // 获取所有菜单
  getAllMenu() {
    return request({
      url: '/menu',
      method: 'get'
    })
  }
}



