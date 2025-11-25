<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-left">
        <h2 class="admin-title">Admin Panel</h2>
        <el-breadcrumb separator="/" style="margin-left: 20px;">
          <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">Admin</el-breadcrumb-item>
          <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-icon><UserFilled /></el-icon>
            {{ userInfo?.name || userInfo?.username || 'Admin' }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout" divided>Logout</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UserFilled } from '@element-plus/icons-vue'
import userApi from '../api/user'

const route = useRoute()
const router = useRouter()
const userInfo = ref(null)

const currentTitle = computed(() => {
  return route.meta?.title || 'Admin Dashboard'
})

const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('Are you sure you want to logout?', 'Prompt', {
      confirmButtonText: 'Confirm',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }).then(async () => {
      try {
        await userApi.logout()
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        ElMessage.success('Logout successful')
        router.push('/login')
      } catch (error) {
        ElMessage.error('Logout failed')
      }
    })
  }
}

onMounted(async () => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }
  
  // Initialize backend loginUser to ensure subsequent API calls work properly
  const token = localStorage.getItem('token')
  if (token) {
    try {
      // Call getUserInfo to initialize backend loginUser member variable
      await userApi.getUserInfo(token)
    } catch (error) {
      console.error('Failed to initialize user info:', error)
    }
  }
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.header {
  background: #ffffff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  backdrop-filter: blur(10px);
}

.admin-title {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  display: inline-block;
}

.header-left {
  flex: 1;
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #606266;
  padding: 8px 16px;
  border-radius: 20px;
  transition: all 0.3s ease;
}

.user-info:hover {
  background-color: #f5f7fa;
  color: #409eff;
}

.user-info .el-icon {
  margin: 0 5px;
  transition: transform 0.3s ease;
}

.user-info:hover .el-icon {
  transform: scale(1.1);
}

.main-content {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 30px;
  min-height: calc(100vh - 60px);
  width: 100%;
}
</style>

