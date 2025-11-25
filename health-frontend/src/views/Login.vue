<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="card-header">
          <h2>Health Management System</h2>
        </div>
      </template>
      <el-tabs v-model="activeTab" class="login-tabs">
        <el-tab-pane label="User Login" name="user">
          <el-form
            :model="loginForm"
            :rules="rules"
            ref="loginFormRef"
            label-width="0"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="Please enter username"
                size="large"
                :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="Please enter password"
                size="large"
                :prefix-icon="Lock"
                @keyup.enter="handleLogin"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click="handleLogin"
                :loading="loading"
                size="large"
                style="width: 100%"
              >
                Login
              </el-button>
            </el-form-item>
            <el-form-item>
              <div class="register-link">
                <el-link type="primary" @click="$router.push('/register')">
                  No account? Register now
                </el-link>
              </div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="Admin Login" name="admin">
          <el-form
            :model="adminLoginForm"
            :rules="rules"
            ref="adminLoginFormRef"
            label-width="0"
          >
            <el-form-item prop="username">
              <el-input
                v-model="adminLoginForm.username"
                placeholder="Please enter admin username"
                size="large"
                :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="adminLoginForm.password"
                type="password"
                placeholder="Please enter admin password"
                size="large"
                :prefix-icon="Lock"
                @keyup.enter="handleAdminLogin"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click="handleAdminLogin"
                :loading="loading"
                size="large"
                style="width: 100%"
              >
                Admin Login
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import userApi from '../api/user'

const router = useRouter()
const loginFormRef = ref(null)
const adminLoginFormRef = ref(null)
const loading = ref(false)
const activeTab = ref('user')

const loginForm = reactive({
  username: '',
  password: ''
})

const adminLoginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
  password: [{ required: true, message: 'Please enter password', trigger: 'blur' }]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const data = await userApi.login(loginForm)
        localStorage.setItem('token', data.token)
        localStorage.setItem('userInfo', JSON.stringify(data))
        
        // Get user info to check role
        const token = data.token
        const userInfo = await userApi.getUserInfo(token)
        localStorage.setItem('userInfo', JSON.stringify({ ...data, ...userInfo }))
        
        ElMessage.success('Login successful')
        router.push('/dashboard')
      } catch (error) {
        ElMessage.error(error.message || 'Login failed')
      } finally {
        loading.value = false
      }
    }
  })
}

const handleAdminLogin = async () => {
  if (!adminLoginFormRef.value) return
  await adminLoginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const data = await userApi.login(adminLoginForm)
        localStorage.setItem('token', data.token)
        
        // Get user info to check role
        const token = data.token
        const userInfo = await userApi.getUserInfo(token)
        
        // Check if user is admin
        const roles = userInfo.roles || []
        if (!roles.includes('admin')) {
          ElMessage.error('Access denied. Admin privileges required.')
          localStorage.removeItem('token')
          localStorage.removeItem('userInfo')
          return
        }
        
        localStorage.setItem('userInfo', JSON.stringify({ ...data, ...userInfo }))
        ElMessage.success('Admin login successful')
        router.push('/admin/dashboard')
      } catch (error) {
        ElMessage.error(error.message || 'Admin login failed')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}

.login-container::before {
  content: '';
  position: absolute;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 50px 50px;
  animation: float 20s infinite linear;
  opacity: 0.3;
}

@keyframes float {
  0% { transform: translate(0, 0); }
  100% { transform: translate(-50px, -50px); }
}

.login-card {
  width: 420px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  border-radius: 16px;
  overflow: hidden;
  position: relative;
  z-index: 1;
  animation: slideUp 0.5s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  padding: 30px;
}

.login-card :deep(.el-card__body) {
  padding: 40px;
  background: #fff;
}

.card-header {
  text-align: center;
}

.card-header h2 {
  color: #fff;
  font-size: 28px;
  font-weight: 600;
  letter-spacing: 1px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.login-card :deep(.el-form-item) {
  margin-bottom: 24px;
}

.login-card :deep(.el-input__wrapper) {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border-radius: 8px;
  padding: 12px 15px;
}

.login-card :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
}

.login-card :deep(.el-button--primary) {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.login-card :deep(.el-button--primary:hover) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
}

.register-link {
  width: 100%;
  text-align: center;
  margin-top: 10px;
}

.register-link :deep(.el-link) {
  font-size: 14px;
}

.login-tabs {
  margin-top: 10px;
}

.login-tabs :deep(.el-tabs__header) {
  margin-bottom: 20px;
}

.login-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 500;
  padding: 0 30px;
}

.login-tabs :deep(.el-tabs__active-bar) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-tabs :deep(.el-tabs__item.is-active) {
  color: #667eea;
}
</style>



