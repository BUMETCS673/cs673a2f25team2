<!--
  Code Composition Ratio:
  - AI Generated: 60%
  - Manual Development: 40%
  
  Dashboard page displaying system statistics and quick actions.
  This is primarily a presentational component with mock data and
  standard layout patterns.
-->
<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #409EFF;">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalUsers }}</div>
              <div class="stat-label">Total Users</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #67C23A;">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalRecords }}</div>
              <div class="stat-label">Health Records</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #E6A23C;">
              <el-icon><Basketball /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalSports }}</div>
              <div class="stat-label">Sports Knowledge</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>Welcome to Health Management System</span>
          </template>
          <div class="welcome-content">
            <p>Welcome, {{ displayName || 'User' }}!</p>
            <p>You can manage your health information, view sports knowledge, record physical conditions, etc.</p>
            <el-button type="primary" @click="$router.push('/body-info')">
              Start Managing Health Information
            </el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>Quick Actions</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/body-notes')">
              Add Health Record
            </el-button>
            <el-button type="success" @click="$router.push('/sport-info')">
              View Sports Knowledge
            </el-button>
            <el-button type="warning" @click="$router.push('/ai-chat')">
              AI Health Assistant
            </el-button>
            <el-button type="info" @click="$router.push('/profile')">
              Personal Center
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { User, Document, Basketball } from '@element-plus/icons-vue'
import userApi from '../api/user'
import sportApi from '../api/sport'
import bodyApi from '../api/body'

const userInfo = ref(null)
const stats = ref({
  totalUsers: 0,
  totalRecords: 0,
  totalSports: 0
})

// 计算显示的用户名，优先使用username，其次使用name
const displayName = computed(() => {
  if (!userInfo.value) return ''
  return userInfo.value.username || userInfo.value.name || ''
})

onMounted(async () => {
  // 从localStorage加载用户信息
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    try {
      userInfo.value = JSON.parse(userInfoStr)
    } catch (error) {
      console.error('Failed to parse userInfo:', error)
    }
  }
  
  // 刷新用户信息以确保数据最新
  const token = localStorage.getItem('token')
  if (token) {
    try {
      const freshUserInfo = await userApi.getUserInfo(token)
      if (freshUserInfo) {
        // 更新用户信息，确保username字段存在
        const updatedUserInfo = {
          ...userInfo.value,
          ...freshUserInfo,
          username: freshUserInfo.name || userInfo.value?.username || freshUserInfo.username
        }
        userInfo.value = updatedUserInfo
        localStorage.setItem('userInfo', JSON.stringify(updatedUserInfo))
      }
    } catch (error) {
      console.error('Failed to refresh user info:', error)
    }
  }
  
  // 加载统计数据
  await loadStatistics()
})

const loadStatistics = async () => {
  // 获取用户总数
  try {
    const userListData = await userApi.getUserList({ 
      pageNo: 1, 
      pageSize: 10000,  // 获取全部数据以确保统计准确
      username: undefined,
      phone: undefined
    })
    // 如果total为0但rows有数据，使用rows的长度
    if (userListData && typeof userListData === 'object') {
      if (userListData.total !== undefined && userListData.total !== null && userListData.total > 0) {
        stats.value.totalUsers = userListData.total
      } else if (userListData.rows && userListData.rows.length > 0) {
        stats.value.totalUsers = userListData.rows.length
      }
    }
  } catch (error) {
    console.warn('Failed to load user count, using default value:', error)
  }

  // 获取运动知识总数
  try {
    const sportListData = await sportApi.getSportList({ 
      pageNo: 1, 
      pageSize: 10000,  // 获取全部数据以确保统计准确
      sportType: undefined
    })
    // 如果total为0但rows有数据，使用rows的长度
    if (sportListData && typeof sportListData === 'object') {
      if (sportListData.total !== undefined && sportListData.total !== null && sportListData.total > 0) {
        stats.value.totalSports = sportListData.total
      } else if (sportListData.rows && sportListData.rows.length > 0) {
        stats.value.totalSports = sportListData.rows.length
      }
    }
  } catch (error) {
    console.warn('Failed to load sport count, using default value:', error)
  }

  // 获取健康记录总数
  try {
    // 先尝试使用专门的计数API
    const bodyNotesCountData = await userApi.getBodyNotesCount()
    if (typeof bodyNotesCountData === 'number' && bodyNotesCountData > 0) {
      stats.value.totalRecords = bodyNotesCountData
    } else {
      // 如果计数API返回0或失败，尝试获取全部数据计算
      const bodyListData = await bodyApi.getUserBodyList({ 
        pageNo: 1, 
        pageSize: 10000 
      })
      if (bodyListData && typeof bodyListData === 'object') {
        if (bodyListData.total !== undefined && bodyListData.total !== null && bodyListData.total > 0) {
          stats.value.totalRecords = bodyListData.total
        } else if (bodyListData.rows && bodyListData.rows.length > 0) {
          stats.value.totalRecords = bodyListData.rows.length
        }
      }
    }
  } catch (error) {
    console.warn('Failed to load body notes count, using default value:', error)
    // 如果新API不存在，尝试使用旧方法
    try {
      const bodyListData = await bodyApi.getBodyList({ pageNo: 1, pageSize: 10000 })
      if (bodyListData && typeof bodyListData === 'object') {
        if (bodyListData.total !== undefined && bodyListData.total !== null && bodyListData.total > 0) {
          stats.value.totalRecords = bodyListData.total
        } else if (bodyListData.rows && bodyListData.rows.length > 0) {
          stats.value.totalRecords = bodyListData.rows.length
        }
      }
    } catch (fallbackError) {
      console.warn('Fallback method also failed:', fallbackError)
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  margin-bottom: 20px;
  transition: all 0.3s ease;
  border-radius: 12px;
  overflow: hidden;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.stat-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.stat-icon {
  width: 70px;
  height: 70px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 28px;
  margin-right: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.stat-card:hover .stat-icon {
  transform: scale(1.1) rotate(5deg);
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 8px;
  line-height: 1.2;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  font-weight: 500;
  letter-spacing: 0.5px;
}

.welcome-content {
  padding: 30px 0;
}

.welcome-content p {
  margin-bottom: 20px;
  color: #606266;
  line-height: 2;
  font-size: 15px;
}

.welcome-content :deep(.el-button) {
  margin-top: 10px;
  padding: 12px 30px;
  font-size: 15px;
  font-weight: 600;
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 30px 0;
}

.quick-actions :deep(.el-button) {
  width: 100% !important;
  margin: 0 !important;
  height: 48px;
  font-size: 15px;
  font-weight: 600;
  border-radius: 8px;
}

:deep(.el-card__header) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-bottom: 2px solid #e4e7ed;
  font-weight: 600;
  font-size: 16px;
  padding: 18px 20px;
}

:deep(.el-card__body) {
  padding: 25px;
}
</style>



