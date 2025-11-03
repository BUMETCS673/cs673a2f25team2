<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
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
      <el-col :span="6">
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
      <el-col :span="6">
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
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #F56C6C;">
              <el-icon><TrendCharts /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.todayActive }}</div>
              <div class="stat-label">Today Active</div>
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
            <p>Welcome, {{ userInfo?.username }}!</p>
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
import { ref, onMounted } from 'vue'
import { User, Document, Basketball, TrendCharts } from '@element-plus/icons-vue'

const userInfo = ref(null)
const stats = ref({
  totalUsers: 0,
  totalRecords: 0,
  totalSports: 0,
  todayActive: 0
})

onMounted(() => {
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }
  
  // 这里可以加载统计数据
  // 暂时使用模拟数据
  stats.value = {
    totalUsers: 128,
    totalRecords: 256,
    totalSports: 32,
    todayActive: 45
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  margin-bottom: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  margin-right: 15px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.welcome-content {
  padding: 20px 0;
}

.welcome-content p {
  margin-bottom: 15px;
  color: #606266;
  line-height: 1.8;
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 20px 0;
}

.quick-actions .el-button {
  width: 100% !important;
  margin: 0 !important;
}
</style>



