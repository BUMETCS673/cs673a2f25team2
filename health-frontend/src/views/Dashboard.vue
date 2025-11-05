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



