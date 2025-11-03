<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>Personal Information</span>
          </template>
          <div class="user-info">
            <el-avatar :size="100" :src="userInfo?.avatar || ''">
              {{ userInfo?.username?.charAt(0)?.toUpperCase() }}
            </el-avatar>
            <h3 style="margin-top: 20px;">{{ userInfo?.username }}</h3>
            <p style="color: #909399; margin-top: 10px;">{{ userInfo?.email || 'Email not set' }}</p>
            <p style="color: #909399;">{{ userInfo?.phone || 'Phone number not set' }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>Change Password</span>
          </template>
          <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="150px">
            <el-form-item label="Current Password" prop="password">
              <el-input
                v-model="passwordForm.password"
                type="password"
                show-password
                placeholder="Please enter current password"
              />
            </el-form-item>
            <el-form-item label="New Password" prop="newPassword">
              <el-input
                v-model="passwordForm.newPassword"
                type="password"
                show-password
                placeholder="Please enter new password"
              />
            </el-form-item>
            <el-form-item label="Confirm New Password" prop="confirmPassword">
              <el-input
                v-model="passwordForm.confirmPassword"
                type="password"
                show-password
                placeholder="Please enter new password again"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleChangePassword" :loading="passwordLoading">
                Change Password
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <el-card style="margin-top: 20px;">
          <template #header>
            <span>Account Information</span>
          </template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="Username">{{ userInfo?.username }}</el-descriptions-item>
            <el-descriptions-item label="Email">{{ userInfo?.email || 'Not set' }}</el-descriptions-item>
            <el-descriptions-item label="Phone Number">{{ userInfo?.phone || 'Not set' }}</el-descriptions-item>
            <el-descriptions-item label="Status">
              <el-tag :type="userInfo?.status === 1 ? 'success' : 'danger'">
                {{ userInfo?.status === 1 ? 'Enabled' : 'Disabled' }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import userApi from '../api/user'

const userInfo = ref(null)
const passwordFormRef = ref(null)
const passwordLoading = ref(false)

const passwordForm = reactive({
  password: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('Passwords do not match'))
  } else {
    callback()
  }
}

const passwordRules = {
  password: [{ required: true, message: 'Please enter current password', trigger: 'blur' }],
  newPassword: [
    { required: true, message: 'Please enter new password', trigger: 'blur' },
    { min: 6, message: 'Password must be at least 6 characters', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: 'Please confirm new password', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const loadUserInfo = async () => {
  const token = localStorage.getItem('token')
  if (token) {
    try {
      const data = await userApi.getUserInfo(token)
      userInfo.value = data
      localStorage.setItem('userInfo', JSON.stringify(data))
    } catch (error) {
      ElMessage.error('Failed to get user information')
    }
  }
}

const handleChangePassword = async () => {
  if (!passwordFormRef.value) return
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      passwordLoading.value = true
      try {
        await userApi.changePassword({
          username: userInfo.value.username,
          password: passwordForm.password,
          newPassword: passwordForm.newPassword
        })
        ElMessage.success('Password changed successfully')
        passwordFormRef.value.resetFields()
      } catch (error) {
        ElMessage.error(error.message || 'Failed to change password')
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

onMounted(() => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }
  loadUserInfo()
})
</script>

<style scoped>
.profile {
  padding: 20px;
}

.user-info {
  text-align: center;
  padding: 20px 0;
}

.user-info h3 {
  margin: 10px 0;
  color: #303133;
}

.user-info p {
  margin: 5px 0;
}
</style>



