<template>
  <el-container class="layout-container">
    <el-aside width="200px" class="sidebar">
      <div class="logo">
        <h2>Health Management</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="sidebar-menu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>Dashboard</span>
        </el-menu-item>
        <el-menu-item index="/body-info">
          <el-icon><User /></el-icon>
          <span>Body Information</span>
        </el-menu-item>
        <el-menu-item index="/body-notes">
          <el-icon><Document /></el-icon>
          <span>Health Records</span>
        </el-menu-item>
        <el-menu-item index="/sport-info">
          <el-icon><Basketball /></el-icon>
          <span>Sports Knowledge</span>
        </el-menu-item>
        <el-menu-item index="/user-management">
          <el-icon><Avatar /></el-icon>
          <span>User Management</span>
        </el-menu-item>
        <el-menu-item index="/profile">
          <el-icon><UserFilled /></el-icon>
          <span>Personal Center</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><UserFilled /></el-icon>
              {{ userInfo?.username || 'User' }}
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">Personal Center</el-dropdown-item>
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
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import userApi from '../api/user'

const route = useRoute()
const router = useRouter()
const userInfo = ref(null)

const activeMenu = computed(() => route.path)

const currentTitle = computed(() => {
  return route.meta?.title || 'Health Management'
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
  } else if (command === 'profile') {
    router.push('/profile')
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
      // If failed, can choose to redirect to login page
      // router.push('/login')
    }
  }
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.sidebar {
  background: linear-gradient(180deg, #2c3e50 0%, #34495e 100%);
  overflow: hidden;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
}

.logo {
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: relative;
  overflow: hidden;
}

.logo::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  animation: shimmer 3s infinite;
}

@keyframes shimmer {
  0% { left: -100%; }
  100% { left: 100%; }
}

.logo h2 {
  font-size: 20px;
  font-weight: 600;
  letter-spacing: 1px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.sidebar-menu {
  border-right: none;
  height: calc(100vh - 70px);
  overflow-y: auto;
  padding: 10px 0;
}

.sidebar-menu::-webkit-scrollbar {
  width: 6px;
}

.sidebar-menu::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
}

.sidebar-menu::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.sidebar-menu::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
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

.header-left {
  flex: 1;
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
}
</style>


