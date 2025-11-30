import request from './request'

export default {
  // Get all sport information
  getAllSportInfo() {
    return request({
      url: '/sport/getAllSportInfo',
      method: 'get'
    })
  },
  
  // Get sport list (paginated)
  getSportList(params) {
    return request({
      url: '/sport/getSportList',
      method: 'get',
      params
    })
  },
  
  // Add sport information
  addSport(data) {
    return request({
      url: '/sport/add',
      method: 'post',
      data
    })
  },
  
  // Update sport information
  updateSport(data) {
    return request({
      url: '/sport/update',
      method: 'put',
      data
    })
  },
  
  // Get sport information by ID
  getSportById(id) {
    return request({
      url: `/sport/${id}`,
      method: 'get'
    })
  },
  
  // Delete sport information
  deleteSport(id) {
    return request({
      url: `/sport/${id}`,
      method: 'delete'
    })
  }
}



