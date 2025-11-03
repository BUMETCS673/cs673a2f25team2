<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <h2>User Registration</h2>
        </div>
      </template>
      <el-form
        :model="registerForm"
        :rules="rules"
        ref="registerFormRef"
        label-width="0"
      >
        <el-form-item prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="Please enter username"
            size="large"
            :prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="Please enter password"
            size="large"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item prop="email">
          <el-input
            v-model="registerForm.email"
            placeholder="Please enter email"
            size="large"
            :prefix-icon="Message"
          />
        </el-form-item>
        <el-form-item prop="phone">
          <el-input
            v-model="registerForm.phone"
            placeholder="Please enter phone number"
            size="large"
            :prefix-icon="Phone"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleRegister"
            :loading="loading"
            size="large"
            style="width: 100%"
          >
            Register
          </el-button>
        </el-form-item>
        <el-form-item>
          <div class="login-link">
            <el-link type="primary" @click="$router.push('/login')">
              Already have an account? Login now
            </el-link>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, Message, Phone } from '@element-plus/icons-vue'
import userApi from '../api/user'

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  username: '',
  password: '',
  email: '',
  phone: ''
})

const validatePhone = (rule, value, callback) => {
  const phoneReg = /^1[3-9]\d{9}$/
  if (!value) {
    callback(new Error('Please enter phone number'))
  } else if (!phoneReg.test(value)) {
      callback(new Error('Please enter a valid phone number'))
  } else {
    callback()
  }
}

const validateEmail = (rule, value, callback) => {
  const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!value) {
    callback(new Error('Please enter email'))
  } else if (!emailReg.test(value)) {
      callback(new Error('Please enter a valid email format'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
  password: [{ required: true, message: 'Please enter password', trigger: 'blur' }],
  email: [{ validator: validateEmail, trigger: 'blur' }],
  phone: [{ validator: validatePhone, trigger: 'blur' }]
}

const handleRegister = async () => {
  if (!registerFormRef.value) return
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await userApi.register(registerForm)
        ElMessage.success('Registration successful, please login')
        router.push('/login')
      } catch (error) {
        ElMessage.error(error.message || 'Registration failed')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-card {
  width: 400px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.card-header {
  text-align: center;
}

.card-header h2 {
  color: #333;
  font-size: 24px;
  font-weight: 500;
}

.login-link {
  width: 100%;
  text-align: center;
}
</style>



