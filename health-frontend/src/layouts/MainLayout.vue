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
        <el-menu-item index="/ai-chat">
          <el-icon><ChatDotRound /></el-icon>
          <span>AI Chat</span>
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
}

.sidebar {
  background-color: #304156;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #2b3a4a;
  color: #fff;
}

.logo h2 {
  font-size: 18px;
  font-weight: 500;
}

.sidebar-menu {
  border-right: none;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
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
}

.user-info .el-icon {
  margin: 0 5px;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>


