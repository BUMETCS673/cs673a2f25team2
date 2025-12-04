<!--
  Code Composition Ratio:
  - AI Generated: 45%
  - Manual Development: 55%
  
  User profile page with password change functionality. The password validation,
  API integration, and user info management logic were manually developed with
  attention to security and user experience.
-->
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
            <span>Account Settings</span>
          </template>
          <div class="button-group">
            <el-button type="primary" size="large" @click="showPasswordDialog = true">
              Change Password
            </el-button>
            <el-button type="success" size="large" @click="handleOpenEmailDialog">
              Change Email
            </el-button>
            <el-button type="warning" size="large" @click="handleOpenPhoneDialog">
              Change Phone Number
            </el-button>
          </div>
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
            <el-descriptions-item label="Payment Status">
              <el-tag :type="paymentStatusTagType(userInfo?.paymentStatus)">
                {{ formatPaymentStatus(userInfo?.paymentStatus) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Access Expiry">
              {{ formatAccessExpiry(userInfo?.accessExpiry) }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <!-- Change Password Dialog -->
    <el-dialog
      v-model="showPasswordDialog"
      title="Change Password"
      width="500px"
      @close="handlePasswordDialogClose"
    >
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="180px">
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
      </el-form>
      <template #footer>
        <el-button @click="handlePasswordDialogClose">Cancel</el-button>
        <el-button type="primary" @click="handleChangePassword" :loading="passwordLoading">
          Confirm
        </el-button>
      </template>
    </el-dialog>

    <!-- Change Email Dialog -->
    <el-dialog
      v-model="showEmailDialog"
      title="Change Email"
      width="500px"
      @close="handleEmailDialogClose"
    >
      <el-form :model="emailForm" :rules="emailRules" ref="emailFormRef" label-width="150px">
        <el-form-item label="New Email" prop="email">
          <el-input
            v-model="emailForm.email"
            placeholder="Please enter new email"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleEmailDialogClose">Cancel</el-button>
        <el-button type="primary" @click="handleChangeEmail" :loading="emailLoading">
          Confirm
        </el-button>
      </template>
    </el-dialog>

    <!-- Change Phone Dialog -->
    <el-dialog
      v-model="showPhoneDialog"
      title="Change Phone Number"
      width="500px"
      @close="handlePhoneDialogClose"
    >
      <el-form :model="phoneForm" :rules="phoneRules" ref="phoneFormRef" label-width="170px">
        <el-form-item label="New Phone Number" prop="phone">
          <el-input
            v-model="phoneForm.phone"
            placeholder="Please enter new phone number"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handlePhoneDialogClose">Cancel</el-button>
        <el-button type="primary" @click="handleChangePhone" :loading="phoneLoading">
          Confirm
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import userApi from '../api/user'

const userInfo = ref(null)
const passwordFormRef = ref(null)
const emailFormRef = ref(null)
const phoneFormRef = ref(null)
const passwordLoading = ref(false)
const emailLoading = ref(false)
const phoneLoading = ref(false)
const showPasswordDialog = ref(false)
const showEmailDialog = ref(false)
const showPhoneDialog = ref(false)

const passwordForm = reactive({
  password: '',
  newPassword: '',
  confirmPassword: ''
})

const emailForm = reactive({
  email: ''
})

const phoneForm = reactive({
  phone: ''
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

const validateEmail = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please enter email'))
  } else {
    const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailReg.test(value)) {
      callback(new Error('Please enter a valid email format'))
    } else {
      callback()
    }
  }
}

const validatePhone = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please enter phone number'))
  } else {
    const phoneReg = /^1[3-9]\d{9}$/
    if (!phoneReg.test(value)) {
      callback(new Error('Please enter a valid phone number (11 digits, starting with 1)'))
    } else {
      callback()
    }
  }
}

const emailRules = {
  email: [{ validator: validateEmail, trigger: 'blur' }]
}

const phoneRules = {
  phone: [{ validator: validatePhone, trigger: 'blur' }]
}

const formatPaymentStatus = (status) => {
  if (!status) return 'Unpaid'
  const upper = status.toUpperCase()
  if (upper === 'ACTIVE' || upper === 'PAID') return 'Active'
  if (upper === 'EXPIRED') return 'Expired'
  return 'Unpaid'
}

const paymentStatusTagType = (status) => {
  if (!status) return 'danger'
  const upper = status.toUpperCase()
  if (upper === 'ACTIVE' || upper === 'PAID') return 'success'
  if (upper === 'EXPIRED') return 'warning'
  return 'danger'
}

const formatAccessExpiry = (expiry) => {
  if (!expiry) return 'Not set'
  try {
    return new Date(expiry).toLocaleString()
  } catch (e) {
    return expiry
  }
}

const loadUserInfo = async () => {
  const token = localStorage.getItem('token')
  if (token) {
    try {
      const data = await userApi.getUserInfo(token)
      if (data) {
        userInfo.value = {
          ...data,
          username: data.name || data.username || ''
        }
        // 如果有用户ID，尝试获取完整信息（包括email、phone、status）
        if (data.id) {
          try {
            const fullData = await userApi.getUserById(data.id)
            if (fullData) {
              userInfo.value = {
                ...userInfo.value,
                ...fullData,
                username: data.name || data.username || fullData.username || ''
              }
            }
          } catch (error) {
            // 如果获取失败，使用已有数据
            console.warn('Failed to get full user data:', error)
          }
        }
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
      }
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
        handlePasswordDialogClose()
      } catch (error) {
        ElMessage.error(error.message || 'Failed to change password')
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

const handlePasswordDialogClose = () => {
  showPasswordDialog.value = false
  if (passwordFormRef.value) {
    passwordFormRef.value.resetFields()
  }
  passwordForm.password = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
}

const handleOpenEmailDialog = () => {
  emailForm.email = userInfo.value?.email || ''
  showEmailDialog.value = true
}

const handleEmailDialogClose = () => {
  showEmailDialog.value = false
  if (emailFormRef.value) {
    emailFormRef.value.resetFields()
  }
  emailForm.email = ''
}

const handleOpenPhoneDialog = () => {
  phoneForm.phone = userInfo.value?.phone || ''
  showPhoneDialog.value = true
}

const handlePhoneDialogClose = () => {
  showPhoneDialog.value = false
  if (phoneFormRef.value) {
    phoneFormRef.value.resetFields()
  }
  phoneForm.phone = ''
}

const handleChangeEmail = async () => {
  if (!emailFormRef.value) return
  await emailFormRef.value.validate(async (valid) => {
    if (valid) {
      emailLoading.value = true
      try {
        const userId = userInfo.value?.id
        if (!userId) {
          ElMessage.error('User ID not found')
          emailLoading.value = false
          return
        }
        
        // 获取用户的完整信息（包括角色），以保持角色不变
        let userData = null
        try {
          userData = await userApi.getUserById(userId)
        } catch (error) {
          console.warn('Failed to get user details')
        }
        
        const updateData = {
          id: userId,
          username: userInfo.value.username || userData?.username,
          email: emailForm.email,
          phone: userInfo.value.phone || userData?.phone || null,
          status: userInfo.value.status !== undefined ? userInfo.value.status : (userData?.status !== undefined ? userData.status : 1),
          avatar: userInfo.value.avatar || userData?.avatar || null
        }
        
        if (userData && userData.roleIdList) {
          updateData.roleIdList = userData.roleIdList
        }
        
        await userApi.updateUser(updateData)
        ElMessage.success('Email updated successfully')
        handleEmailDialogClose()
        await loadUserInfo()
        localStorage.setItem('bodyInfoUpdated', 'true')
      } catch (error) {
        ElMessage.error(error.message || 'Failed to update email')
      } finally {
        emailLoading.value = false
      }
    }
  })
}

const handleChangePhone = async () => {
  if (!phoneFormRef.value) return
  await phoneFormRef.value.validate(async (valid) => {
    if (valid) {
      phoneLoading.value = true
      try {
        const userId = userInfo.value?.id
        if (!userId) {
          ElMessage.error('User ID not found')
          phoneLoading.value = false
          return
        }
        
        // 获取用户的完整信息（包括角色），以保持角色不变
        let userData = null
        try {
          userData = await userApi.getUserById(userId)
        } catch (error) {
          console.warn('Failed to get user details')
        }
        
        const updateData = {
          id: userId,
          username: userInfo.value.username || userData?.username,
          email: userInfo.value.email || userData?.email || null,
          phone: phoneForm.phone,
          status: userInfo.value.status !== undefined ? userInfo.value.status : (userData?.status !== undefined ? userData.status : 1),
          avatar: userInfo.value.avatar || userData?.avatar || null
        }
        
        if (userData && userData.roleIdList) {
          updateData.roleIdList = userData.roleIdList
        }
        
        await userApi.updateUser(updateData)
        ElMessage.success('Phone number updated successfully')
        handlePhoneDialogClose()
        await loadUserInfo()
        localStorage.setItem('bodyInfoUpdated', 'true')
      } catch (error) {
        ElMessage.error(error.message || 'Failed to update phone number')
      } finally {
        phoneLoading.value = false
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
  padding: 0;
}

.user-info {
  text-align: center;
  padding: 30px 0;
}

.user-info :deep(.el-avatar) {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  border: 4px solid #fff;
  transition: all 0.3s ease;
}

.user-info :deep(.el-avatar):hover {
  transform: scale(1.05);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.3);
}

.user-info h3 {
  margin: 20px 0 10px;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}

.user-info p {
  margin: 8px 0;
  color: #909399;
  font-size: 14px;
}

:deep(.el-card__header) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-bottom: 2px solid #e4e7ed;
  font-weight: 600;
  font-size: 16px;
  padding: 18px 20px;
}

:deep(.el-card__body) {
  padding: 30px;
}

:deep(.el-form-item__label) {
  font-weight: 600;
  color: #606266;
}

:deep(.el-input__wrapper) {
  border-radius: 6px;
}

:deep(.el-button) {
  border-radius: 6px;
  font-weight: 500;
  padding: 10px 25px;
}

:deep(.el-descriptions) {
  margin-top: 10px;
}

:deep(.el-descriptions__label) {
  font-weight: 600;
  color: #606266;
}

:deep(.el-descriptions__content) {
  color: #303133;
}

:deep(.el-tag) {
  border-radius: 4px;
  font-weight: 500;
  padding: 0 12px;
  height: 26px;
  line-height: 26px;
}

:deep(.el-dialog) {
  border-radius: 12px;
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 20px 25px;
  margin: 0;
  border-radius: 12px 12px 0 0;
}

:deep(.el-dialog__title) {
  color: #fff;
  font-weight: 600;
  font-size: 18px;
}

:deep(.el-dialog__headerbtn .el-dialog__close) {
  color: #fff;
  font-size: 20px;
}

:deep(.el-dialog__body) {
  padding: 30px;
}

:deep(.el-dialog__footer) {
  padding: 20px 30px;
  border-top: 1px solid #e4e7ed;
}

.button-group {
  padding: 20px 0;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.button-group :deep(.el-button) {
  width: 100%;
  margin: 0;
}
</style>



