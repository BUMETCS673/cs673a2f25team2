import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// Request interceptor
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['X-Token'] = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// Response interceptor
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 20000) {
      return res.data
    } else {
      ElMessage.error(res.message || 'Request failed')
      if (res.code === 20003) {
        // Token expired, redirect to login
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        router.push('/login')
      }
      return Promise.reject(new Error(res.message || 'Request failed'))
    }
  },
  error => {
    ElMessage.error(error.message || 'Network error')
    return Promise.reject(error)
  }
)

export default request



