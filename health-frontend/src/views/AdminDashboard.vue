<template>
  <div class="admin-dashboard">
    <!-- 统计面板 -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card class="stat-card stat-card-primary">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.totalUsers }}</div>
              <div class="stat-label">Total Users</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card stat-card-success">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><CircleCheck /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.activeUsers }}</div>
              <div class="stat-label">Active Users</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card stat-card-warning">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><Warning /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.inactiveUsers }}</div>
              <div class="stat-label">Inactive Users</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card stat-card-info">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><TrendCharts /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.todayNewUsers }}</div>
              <div class="stat-label">Today New Users</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <template #header>
        <div class="card-header">
          <span>Admin Dashboard - User Management</span>
          <div class="header-actions">
            <el-input
              v-model="searchForm.username"
              placeholder="Username"
              style="width: 150px; margin-right: 10px;"
              clearable
              @keyup.enter="handleSearch"
            />
            <el-input
              v-model="searchForm.email"
              placeholder="Email"
              style="width: 150px; margin-right: 10px;"
              clearable
              @keyup.enter="handleSearch"
            />
            <el-input
              v-model="searchForm.phone"
              placeholder="Phone Number"
              style="width: 150px; margin-right: 10px;"
              clearable
              @keyup.enter="handleSearch"
            />
            <el-select
              v-model="searchForm.status"
              placeholder="Status"
              style="width: 120px; margin-right: 10px;"
              clearable
            >
              <el-option label="All" value="" />
              <el-option label="Enabled" :value="1" />
              <el-option label="Disabled" :value="0" />
            </el-select>
            <el-button type="primary" @click="handleSearch" :icon="Search">Search</el-button>
            <el-button @click="resetSearch" :icon="Refresh">Reset</el-button>
            <el-button type="success" @click="showDialog = true" :icon="Plus">Add User</el-button>
            <el-button type="warning" @click="handleExport" :icon="Download">Export</el-button>
            <el-button @click="loadData" :icon="RefreshRight" circle />
          </div>
        </div>
      </template>

      <!-- 批量操作栏 -->
      <div v-if="selectedUsers.length > 0" class="batch-actions">
        <span class="batch-info">Selected: {{ selectedUsers.length }} users</span>
        <el-button size="small" type="success" @click="handleBatchEnable" :icon="CircleCheck">Enable</el-button>
        <el-button size="small" type="warning" @click="handleBatchDisable" :icon="Warning">Disable</el-button>
        <el-button size="small" type="danger" @click="handleBatchDelete" :icon="Delete">Delete</el-button>
        <el-button size="small" @click="selectedUsers = []" :icon="Close">Clear</el-button>
      </div>

      <el-table 
        :data="filteredTableData" 
        style="width: 100%" 
        v-loading="loading"
        @selection-change="handleSelectionChange"
        stripe
        border
        :default-sort="{ prop: 'id', order: 'ascending' }"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="id" label="ID" width="80" sortable align="center" />
        <el-table-column prop="avatar" label="Avatar" width="90" align="center">
          <template #default="scope">
            <el-avatar :src="scope.row.avatar" :size="40" v-if="scope.row.avatar" />
            <el-avatar :size="40" v-else>
              <el-icon><UserFilled /></el-icon>
            </el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="Username" min-width="140" sortable show-overflow-tooltip />
        <el-table-column prop="email" label="Email" min-width="200" show-overflow-tooltip>
          <template #default="scope">
            <span v-if="scope.row.email">{{ scope.row.email }}</span>
            <span v-else style="color: #c0c4cc;">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="Phone" min-width="140" show-overflow-tooltip>
          <template #default="scope">
            <span v-if="scope.row.phone">{{ scope.row.phone }}</span>
            <span v-else style="color: #c0c4cc;">-</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="Status" width="110" sortable align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'" effect="dark">
              {{ scope.row.status === 1 ? 'Enabled' : 'Disabled' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Operations" width="420" fixed="right" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="info" size="small" @click="handleViewDetails(scope.row)" :icon="View" text>Details</el-button>
              <el-button type="primary" size="small" @click="handleEdit(scope.row)" :icon="Edit" text>Edit</el-button>
              <el-button type="warning" size="small" @click="handleChangePassword(scope.row)" :icon="Lock" text>Password</el-button>
              <el-button type="warning" size="small" @click="handleViewHealth(scope.row)" :icon="Document" text>Health</el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope.row)" :icon="Delete" text>Delete</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :total="filteredTotal"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- 用户详情对话框 -->
    <el-dialog
      v-model="showDetailsDialog"
      title="User Details"
      width="900px"
      @close="closeDetailsDialog"
    >
      <el-tabs v-model="detailsTab">
        <el-tab-pane label="Basic Info" name="basic">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="ID">{{ userDetails.id }}</el-descriptions-item>
            <el-descriptions-item label="Username">{{ userDetails.username }}</el-descriptions-item>
            <el-descriptions-item label="Email">{{ userDetails.email || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Phone">{{ userDetails.phone || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Status">
              <el-tag :type="userDetails.status === 1 ? 'success' : 'danger'">
                {{ userDetails.status === 1 ? 'Enabled' : 'Disabled' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Avatar">
              <el-avatar :src="userDetails.avatar" v-if="userDetails.avatar" />
              <span v-else>None</span>
            </el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="Body Information" name="body">
          <div v-if="bodyInfoLoading" style="text-align: center; padding: 40px;">
            <el-icon class="is-loading" :size="30"><Loading /></el-icon>
            <p>Loading body information...</p>
          </div>
          <el-empty v-else-if="!bodyInfo" description="No body information available" />
          <el-descriptions v-else :column="2" border>
            <el-descriptions-item label="Name">{{ bodyInfo.name || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Age">{{ bodyInfo.age || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Gender">{{ bodyInfo.gender || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Height">{{ bodyInfo.height ? bodyInfo.height + ' cm' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Weight">{{ bodyInfo.weight ? bodyInfo.weight + ' kg' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Blood Sugar">{{ bodyInfo.bloodSugar || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Blood Pressure">{{ bodyInfo.bloodPressure || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Blood Lipid">{{ bodyInfo.bloodLipid || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Heart Rate">{{ bodyInfo.heart_rate ? bodyInfo.heart_rate + ' bpm' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Vision">{{ bodyInfo.vision || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Sleep Duration">{{ bodyInfo.sleep_duration ? bodyInfo.sleep_duration + ' hours' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Sleep Quality">{{ bodyInfo.sleep_quality || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Smoking">
              <el-tag :type="bodyInfo.smoking ? 'danger' : 'success'">
                {{ bodyInfo.smoking ? 'Yes' : 'No' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Drinking">
              <el-tag :type="bodyInfo.drinking ? 'warning' : 'success'">
                {{ bodyInfo.drinking ? 'Yes' : 'No' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Exercise">
              <el-tag :type="bodyInfo.exercise ? 'success' : 'info'">
                {{ bodyInfo.exercise ? 'Yes' : 'No' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Food Types">{{ bodyInfo.food_types || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Water Consumption">{{ bodyInfo.water_consumption ? bodyInfo.water_consumption + ' ml' : 'N/A' }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="Health Records" name="records">
          <div v-if="healthRecordsLoading" style="text-align: center; padding: 40px;">
            <el-icon class="is-loading" :size="30"><Loading /></el-icon>
            <p>Loading health records...</p>
          </div>
          <el-empty v-else-if="healthRecords.length === 0" description="No health records available" />
          <el-table v-else :data="healthRecords" style="width: 100%" border>
            <el-table-column prop="Date" label="Date" width="180">
              <template #default="scope">
                {{ formatDate(scope.row.Date) }}
              </template>
            </el-table-column>
            <el-table-column prop="name" label="Name" width="120" />
            <el-table-column prop="age" label="Age" width="80" />
            <el-table-column prop="gender" label="Gender" width="80" />
            <el-table-column prop="height" label="Height" width="100">
              <template #default="scope">
                {{ scope.row.height ? scope.row.height + ' cm' : 'N/A' }}
              </template>
            </el-table-column>
            <el-table-column prop="weight" label="Weight" width="100">
              <template #default="scope">
                {{ scope.row.weight ? scope.row.weight + ' kg' : 'N/A' }}
              </template>
            </el-table-column>
            <el-table-column prop="bloodSugar" label="Blood Sugar" width="120" />
            <el-table-column prop="bloodPressure" label="Blood Pressure" width="130" />
            <el-table-column prop="heart_rate" label="Heart Rate" width="110">
              <template #default="scope">
                {{ scope.row.heart_rate ? scope.row.heart_rate + ' bpm' : 'N/A' }}
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <!-- 健康数据管理对话框 -->
    <el-dialog
      v-model="showHealthDialog"
      title="User Health Data Management"
      width="1200px"
      @close="closeHealthDialog"
    >
      <el-tabs v-model="healthTab">
        <el-tab-pane label="Body Information" name="body">
          <div v-if="healthBodyInfoLoading" style="text-align: center; padding: 40px;">
            <el-icon class="is-loading" :size="30"><Loading /></el-icon>
            <p>Loading body information...</p>
          </div>
          <el-empty v-else-if="!healthBodyInfo" description="No body information available" />
          <el-descriptions v-else :column="2" border>
            <el-descriptions-item label="Name">{{ healthBodyInfo.name || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Age">{{ healthBodyInfo.age || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Gender">{{ healthBodyInfo.gender || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Height">{{ healthBodyInfo.height ? healthBodyInfo.height + ' cm' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Weight">{{ healthBodyInfo.weight ? healthBodyInfo.weight + ' kg' : 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Blood Sugar">{{ healthBodyInfo.bloodSugar || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Blood Pressure">{{ healthBodyInfo.bloodPressure || 'N/A' }}</el-descriptions-item>
            <el-descriptions-item label="Heart Rate">{{ healthBodyInfo.heart_rate ? healthBodyInfo.heart_rate + ' bpm' : 'N/A' }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="Health Records" name="records">
          <div v-if="healthRecordsLoading" style="text-align: center; padding: 40px;">
            <el-icon class="is-loading" :size="30"><Loading /></el-icon>
            <p>Loading health records...</p>
          </div>
          <el-empty v-else-if="healthRecords.length === 0" description="No health records available" />
          <el-table v-else :data="healthRecords" style="width: 100%" border>
            <el-table-column prop="Date" label="Date" width="180">
              <template #default="scope">
                {{ formatDate(scope.row.Date) }}
              </template>
            </el-table-column>
            <el-table-column prop="name" label="Name" width="120" />
            <el-table-column prop="age" label="Age" width="80" />
            <el-table-column prop="weight" label="Weight" width="100">
              <template #default="scope">
                {{ scope.row.weight ? scope.row.weight + ' kg' : 'N/A' }}
              </template>
            </el-table-column>
            <el-table-column prop="bloodSugar" label="Blood Sugar" width="120" />
            <el-table-column prop="bloodPressure" label="Blood Pressure" width="130" />
            <el-table-column prop="heart_rate" label="Heart Rate" width="110">
              <template #default="scope">
                {{ scope.row.heart_rate ? scope.row.heart_rate + ' bpm' : 'N/A' }}
              </template>
            </el-table-column>
            <el-table-column label="Operations" width="150" fixed="right">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleEditHealthRecord(scope.row)" :icon="Edit">Edit</el-button>
                <el-button size="small" type="danger" @click="handleDeleteHealthRecord(scope.row)" :icon="Delete">Delete</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <!-- 编辑健康记录对话框 -->
    <el-dialog
      v-model="showEditHealthRecordDialog"
      title="Edit Health Record"
      width="600px"
      @close="closeEditHealthRecordDialog"
    >
      <el-form :model="healthRecordForm" label-width="150px" v-if="healthRecordForm">
        <el-form-item label="Date">
          <el-date-picker
            v-model="healthRecordForm.Date"
            type="datetime"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="Name">
          <el-input v-model="healthRecordForm.name" />
        </el-form-item>
        <el-form-item label="Age">
          <el-input-number v-model="healthRecordForm.age" :min="0" :max="150" />
        </el-form-item>
        <el-form-item label="Gender">
          <el-select v-model="healthRecordForm.gender" style="width: 100%">
            <el-option label="Male" value="男" />
            <el-option label="Female" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="Height (cm)">
          <el-input-number v-model="healthRecordForm.height" :min="0" :precision="1" />
        </el-form-item>
        <el-form-item label="Weight (kg)">
          <el-input-number v-model="healthRecordForm.weight" :min="0" :precision="1" />
        </el-form-item>
        <el-form-item label="Blood Sugar">
          <el-input-number v-model="healthRecordForm.bloodSugar" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="Blood Pressure">
          <el-input v-model="healthRecordForm.bloodPressure" />
        </el-form-item>
        <el-form-item label="Heart Rate (bpm)">
          <el-input-number v-model="healthRecordForm.heart_rate" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditHealthRecordDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handleSaveHealthRecord" :loading="savingHealthRecord">Save</el-button>
      </template>
    </el-dialog>

    <!-- Add/Edit User Dialog -->
    <el-dialog
      v-model="showDialog"
      :title="editingId ? 'Edit User' : 'Add User'"
      width="500px"
      @close="resetForm"
    >
      <el-form :model="form" :rules="getRules()" ref="formRef" label-width="120px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" :disabled="!!editingId" />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="Phone Number" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="Password" prop="password" v-if="!editingId">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="Status" prop="status">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="Enabled" :value="1" />
            <el-option label="Disabled" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="Avatar URL">
          <el-input v-model="form.avatar" placeholder="Please enter avatar URL" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">Confirm</el-button>
      </template>
    </el-dialog>

    <!-- Change Password Dialog -->
    <el-dialog
      v-model="showPasswordDialog"
      title="Reset User Password"
      width="500px"
      @close="resetPasswordForm"
    >
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="140px">
        <el-form-item label="Username">
          <el-input v-model="passwordForm.username" disabled />
        </el-form-item>
        <el-form-item label="New Password" prop="newPassword">
          <el-input 
            v-model="passwordForm.newPassword" 
            type="password" 
            show-password 
            placeholder="Enter new password (min 6 characters)" 
          />
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input 
            v-model="passwordForm.confirmPassword" 
            type="password" 
            show-password 
            placeholder="Confirm new password" 
          />
        </el-form-item>
        <el-alert
          title="Note: You will be prompted to enter the current password for security verification."
          type="warning"
          :closable="false"
          style="margin-top: 10px; margin-bottom: 10px;"
        />
      </el-form>
      <template #footer>
        <el-button @click="showPasswordDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handlePasswordSubmit" :loading="changingPassword">Reset Password</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  UserFilled,
  CircleCheck,
  Warning,
  TrendCharts,
  Search,
  Refresh,
  Plus,
  Download,
  RefreshRight,
  Delete,
  Close,
  View,
  Edit,
  Document,
  Loading,
  Lock
} from '@element-plus/icons-vue'
import userApi from '../api/user'
import bodyApi from '../api/body'

const tableData = ref([])
const userRolesMap = ref({})
const loading = ref(false)
const submitting = ref(false)
const showDialog = ref(false)
const editingId = ref(null)
const formRef = ref(null)
const pageNo = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 统计数据
const statistics = reactive({
  totalUsers: 0,
  activeUsers: 0,
  inactiveUsers: 0,
  todayNewUsers: 0
})

// 批量操作
const selectedUsers = ref([])

// 用户详情对话框
const showDetailsDialog = ref(false)
const detailsTab = ref('basic')
const userDetails = ref({})
const bodyInfo = ref(null)
const bodyInfoLoading = ref(false)
const healthRecords = ref([])
const healthRecordsLoading = ref(false)
const currentViewingUserId = ref(null)

// 健康数据对话框
const showHealthDialog = ref(false)
const healthTab = ref('body')
const healthBodyInfo = ref(null)
const healthBodyInfoLoading = ref(false)

// 编辑健康记录对话框
const showEditHealthRecordDialog = ref(false)
const healthRecordForm = ref(null)
const savingHealthRecord = ref(false)

// 修改密码对话框
const showPasswordDialog = ref(false)
const passwordFormRef = ref(null)
const changingPassword = ref(false)
const passwordForm = reactive({
  username: '',
  newPassword: '',
  confirmPassword: ''
})

const searchForm = reactive({
  username: '',
  email: '',
  phone: '',
  status: ''
})

const form = reactive({
  username: '',
  email: '',
  phone: '',
  password: '',
  status: 1,
  avatar: '',
  roleIdList: [3]
})

// 过滤非管理员用户
const filteredTableData = computed(() => {
  let filtered = tableData.value.filter(user => {
    const roleIds = userRolesMap.value[user.id] || []
    return !roleIds.includes(1)
  })

  // 应用搜索过滤
  if (searchForm.username) {
    filtered = filtered.filter(user => 
      user.username?.toLowerCase().includes(searchForm.username.toLowerCase())
    )
  }
  if (searchForm.email) {
    filtered = filtered.filter(user => 
      user.email?.toLowerCase().includes(searchForm.email.toLowerCase())
    )
  }
  if (searchForm.phone) {
    filtered = filtered.filter(user => 
      user.phone?.includes(searchForm.phone)
    )
  }
  if (searchForm.status !== '') {
    filtered = filtered.filter(user => user.status === searchForm.status)
  }

  return filtered
})

const filteredTotal = computed(() => {
  return filteredTableData.value.length
})

const validatePhone = (rule, value, callback) => {
  if (!value) {
    callback()
  } else {
    const phoneReg = /^1[3-9]\d{9}$/
    if (!phoneReg.test(value)) {
      callback(new Error('Please enter a valid phone number'))
    } else {
      callback()
    }
  }
}

const validateEmail = (rule, value, callback) => {
  if (!value) {
    callback()
  } else {
    const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailReg.test(value)) {
      callback(new Error('Please enter a valid email format'))
    } else {
      callback()
    }
  }
}

const getRules = () => {
  const baseRules = {
    username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
    email: [{ validator: validateEmail, trigger: 'blur' }],
    phone: [{ validator: validatePhone, trigger: 'blur' }],
    status: [{ required: true, message: 'Please select status', trigger: 'change' }]
  }
  
  if (!editingId.value) {
    baseRules.password = [{ required: true, message: 'Please enter password', trigger: 'blur' }]
  }
  
  return baseRules
}

// 计算统计数据
const calculateStatistics = () => {
  const filtered = tableData.value.filter(user => {
    const roleIds = userRolesMap.value[user.id] || []
    return !roleIds.includes(1)
  })
  
  statistics.totalUsers = filtered.length
  statistics.activeUsers = filtered.filter(u => u.status === 1).length
  statistics.inactiveUsers = filtered.filter(u => u.status === 0).length
  
  // 计算今日新增用户（简化处理，实际应该根据创建时间）
  const today = new Date().toDateString()
  statistics.todayNewUsers = 0 // 由于没有创建时间字段，暂时设为0
}

const loadData = async () => {
  loading.value = true
  try {
    // 获取所有用户数据（使用较大的pageSize以确保获取所有数据用于统计）
    const data = await userApi.getUserList({
      pageNo: 1,
      pageSize: 1000,
      username: searchForm.username || undefined,
      phone: searchForm.phone || undefined
    })
    
    tableData.value = data.rows || []
    total.value = data.total || 0
    
    // 加载角色信息
    const rolePromises = tableData.value.map(async (user) => {
      try {
        const userData = await userApi.getUserById(user.id)
        userRolesMap.value[user.id] = userData.roleIdList || []
      } catch (error) {
        console.error(`Failed to load roles for user ${user.id}:`, error)
        userRolesMap.value[user.id] = []
      }
    })
    
    await Promise.all(rolePromises)
    calculateStatistics()
  } catch (error) {
    ElMessage.error('Failed to load data')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNo.value = 1
  loadData()
}

const resetSearch = () => {
  searchForm.username = ''
  searchForm.email = ''
  searchForm.phone = ''
  searchForm.status = ''
  handleSearch()
}

const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  editingId.value = null
  Object.keys(form).forEach(key => {
    if (key === 'status') {
      form[key] = 1
    } else if (key === 'roleIdList') {
      form[key] = [3]
    } else {
      form[key] = ''
    }
  })
}

const handleEdit = async (row) => {
  editingId.value = row.id
  try {
    const data = await userApi.getUserById(row.id)
    Object.assign(form, data)
    form.password = ''
    showDialog.value = true
  } catch (error) {
    ElMessage.error('Failed to load data')
  }
}

const handleDelete = async (row) => {
  ElMessageBox.confirm('Are you sure to delete this user?', 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      await userApi.deleteUser(row.id)
      ElMessage.success('Deleted successfully')
      loadData()
    } catch (error) {
      ElMessage.error('Failed to delete')
    }
  })
}

// 修改密码
const handleChangePassword = async (row) => {
  passwordForm.username = row.username
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
  showPasswordDialog.value = true
}

const resetPasswordForm = () => {
  if (passwordFormRef.value) {
    passwordFormRef.value.resetFields()
  }
  passwordForm.username = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('Passwords do not match'))
  } else {
    callback()
  }
}

const passwordRules = {
  newPassword: [
    { required: true, message: 'Please enter new password', trigger: 'blur' },
    { min: 6, message: 'Password must be at least 6 characters', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: 'Please confirm password', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handlePasswordSubmit = async () => {
  if (!passwordFormRef.value) return
  
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      changingPassword.value = true
      try {
        // Find user by username
        const user = tableData.value.find(u => u.username === passwordForm.username)
        if (!user) {
          ElMessage.error('User not found')
          return
        }
        
        // Get full user data
        const userData = await userApi.getUserById(user.id)
        
        // Since backend updateUser sets password to null, we need to use changePassword API
        // But changePassword requires old password (username, password, newPassword)
        // For admin password reset, we need to prompt for old password or use a workaround
        
        // Show a dialog to get old password for changePassword API
        ElMessageBox.prompt('Please enter the current password for this user:', 'Password Reset', {
          confirmButtonText: 'Reset Password',
          cancelButtonText: 'Cancel',
          inputType: 'password',
          inputPlaceholder: 'Enter current password',
          inputValidator: (value) => {
            if (!value) {
              return 'Please enter current password'
            }
            return true
          }
        }).then(async ({ value: oldPassword }) => {
          try {
            // Use changePassword API with old password
            await userApi.changePassword({
              username: passwordForm.username,
              password: oldPassword,
              newPassword: passwordForm.newPassword
            })
            ElMessage.success('Password updated successfully')
            showPasswordDialog.value = false
            resetPasswordForm()
          } catch (error) {
            ElMessage.error(error.message || 'Failed to update password. Please check if the old password is correct.')
          } finally {
            changingPassword.value = false
          }
        }).catch(() => {
          changingPassword.value = false
        })
      } catch (error) {
        ElMessage.error(error.message || 'Failed to update password')
        changingPassword.value = false
      }
    }
  })
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        if (editingId.value) {
          const updateData = { ...form }
          delete updateData.password
          await userApi.updateUser({ ...updateData, id: editingId.value })
          ElMessage.success('Updated successfully')
        } else {
          await userApi.addUser(form)
          ElMessage.success('Added successfully')
        }
        showDialog.value = false
        loadData()
      } catch (error) {
        ElMessage.error(error.message || 'Operation failed')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 批量操作
const handleSelectionChange = (selection) => {
  selectedUsers.value = selection
}

const handleBatchEnable = async () => {
  if (selectedUsers.value.length === 0) {
    ElMessage.warning('Please select users')
    return
  }
  
  ElMessageBox.confirm(`Enable ${selectedUsers.value.length} users?`, 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      const promises = selectedUsers.value.map(user => 
        userApi.updateUser({ ...user, status: 1 })
      )
      await Promise.all(promises)
      ElMessage.success('Enabled successfully')
      selectedUsers.value = []
      loadData()
    } catch (error) {
      ElMessage.error('Failed to enable users')
    }
  })
}

const handleBatchDisable = async () => {
  if (selectedUsers.value.length === 0) {
    ElMessage.warning('Please select users')
    return
  }
  
  ElMessageBox.confirm(`Disable ${selectedUsers.value.length} users?`, 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      const promises = selectedUsers.value.map(user => 
        userApi.updateUser({ ...user, status: 0 })
      )
      await Promise.all(promises)
      ElMessage.success('Disabled successfully')
      selectedUsers.value = []
      loadData()
    } catch (error) {
      ElMessage.error('Failed to disable users')
    }
  })
}

const handleBatchDelete = async () => {
  if (selectedUsers.value.length === 0) {
    ElMessage.warning('Please select users')
    return
  }
  
  ElMessageBox.confirm(`Delete ${selectedUsers.value.length} users?`, 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      const promises = selectedUsers.value.map(user => 
        userApi.deleteUser(user.id)
      )
      await Promise.all(promises)
      ElMessage.success('Deleted successfully')
      selectedUsers.value = []
      loadData()
    } catch (error) {
      ElMessage.error('Failed to delete users')
    }
  })
}

// 查看用户详情
const handleViewDetails = async (row) => {
  currentViewingUserId.value = row.id
  showDetailsDialog.value = true
  detailsTab.value = 'basic'
  
  try {
    const data = await userApi.getUserById(row.id)
    userDetails.value = data
    
    // 加载身体信息
    bodyInfoLoading.value = true
    try {
      const bodyData = await bodyApi.getBodyById(row.id)
      bodyInfo.value = bodyData
    } catch (error) {
      console.error('Failed to load body info:', error)
      bodyInfo.value = null
    } finally {
      bodyInfoLoading.value = false
    }
    
    // 加载健康记录
    healthRecordsLoading.value = true
    try {
      const recordsData = await bodyApi.getBodyNotes(row.id)
      healthRecords.value = recordsData || []
    } catch (error) {
      console.error('Failed to load health records:', error)
      healthRecords.value = []
    } finally {
      healthRecordsLoading.value = false
    }
  } catch (error) {
    ElMessage.error('Failed to load user details')
  }
}

const closeDetailsDialog = () => {
  userDetails.value = {}
  bodyInfo.value = null
  healthRecords.value = []
  currentViewingUserId.value = null
}

// 查看健康数据
const handleViewHealth = async (row) => {
  currentViewingUserId.value = row.id
  showHealthDialog.value = true
  healthTab.value = 'body'
  
  // 加载身体信息
  healthBodyInfoLoading.value = true
  try {
    const bodyData = await bodyApi.getBodyById(row.id)
    healthBodyInfo.value = bodyData
  } catch (error) {
    console.error('Failed to load body info:', error)
    healthBodyInfo.value = null
  } finally {
    healthBodyInfoLoading.value = false
  }
  
  // 加载健康记录
  healthRecordsLoading.value = true
  try {
    const recordsData = await bodyApi.getBodyNotes(row.id)
    healthRecords.value = recordsData || []
  } catch (error) {
    console.error('Failed to load health records:', error)
    healthRecords.value = []
  } finally {
    healthRecordsLoading.value = false
  }
}

const closeHealthDialog = () => {
  healthBodyInfo.value = null
  healthRecords.value = []
  currentViewingUserId.value = null
}

// 编辑健康记录
const handleEditHealthRecord = (row) => {
  healthRecordForm.value = { ...row }
  showEditHealthRecordDialog.value = true
}

const handleSaveHealthRecord = async () => {
  if (!healthRecordForm.value) return
  
  savingHealthRecord.value = true
  try {
    await bodyApi.updateUserBody(healthRecordForm.value)
    ElMessage.success('Updated successfully')
    showEditHealthRecordDialog.value = false
    
    // 刷新健康记录
    if (currentViewingUserId.value) {
      const recordsData = await bodyApi.getBodyNotes(currentViewingUserId.value)
      healthRecords.value = recordsData || []
    }
  } catch (error) {
    ElMessage.error('Failed to update health record')
  } finally {
    savingHealthRecord.value = false
  }
}

const handleDeleteHealthRecord = async (row) => {
  ElMessageBox.confirm('Are you sure to delete this health record?', 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      await bodyApi.deleteUserBody(row.notes_id)
      ElMessage.success('Deleted successfully')
      
      // 刷新健康记录
      if (currentViewingUserId.value) {
        const recordsData = await bodyApi.getBodyNotes(currentViewingUserId.value)
        healthRecords.value = recordsData || []
      }
    } catch (error) {
      ElMessage.error('Failed to delete health record')
    }
  })
}

const closeEditHealthRecordDialog = () => {
  healthRecordForm.value = null
}

// 导出功能
const handleExport = () => {
  const csvContent = convertToCSV(filteredTableData.value)
  downloadCSV(csvContent, `users_${new Date().toISOString().split('T')[0]}.csv`)
}

const convertToCSV = (data) => {
  const headers = ['ID', 'Username', 'Email', 'Phone', 'Status']
  const rows = data.map(user => [
    user.id,
    user.username,
    user.email || '',
    user.phone || '',
    user.status === 1 ? 'Enabled' : 'Disabled'
  ])
  return [headers, ...rows].map(row => row.map(cell => `"${cell}"`).join(',')).join('\n')
}

const downloadCSV = (content, filename) => {
  const blob = new Blob(['\ufeff' + content], { type: 'text/csv;charset=utf-8;' })
  const url = window.URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = filename
  a.click()
  window.URL.revokeObjectURL(url)
  ElMessage.success('Export successful')
}

const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  try {
    const date = new Date(dateString)
    return date.toLocaleString('en-US', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch (error) {
    return dateString
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.admin-dashboard {
  padding: 0;
}

.stat-card {
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.stat-card-primary {
  border-left: 4px solid #409eff;
}

.stat-card-success {
  border-left: 4px solid #67c23a;
}

.stat-card-warning {
  border-left: 4px solid #e6a23c;
}

.stat-card-info {
  border-left: 4px solid #909399;
}

.stat-content {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.stat-icon {
  margin-right: 20px;
  color: #409eff;
}

.stat-card-success .stat-icon {
  color: #67c23a;
}

.stat-card-warning .stat-icon {
  color: #e6a23c;
}

.stat-card-info .stat-icon {
  color: #909399;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #303133;
  line-height: 1;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-size: 18px;
  flex-wrap: wrap;
  gap: 10px;
}

.header-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;
}

.batch-actions {
  margin-bottom: 15px;
  padding: 12px 15px;
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-radius: 6px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-left: 4px solid #409eff;
}

.batch-info {
  font-weight: 600;
  color: #606266;
  margin-right: 10px;
}

:deep(.el-card__header) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-bottom: 2px solid #e4e7ed;
  padding: 20px 25px;
}

:deep(.el-card__body) {
  padding: 25px;
}

:deep(.el-input__wrapper) {
  border-radius: 6px;
}

:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
  font-size: 14px;
}

:deep(.el-table th) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  font-weight: 600;
  color: #606266;
  padding: 12px 0;
}

:deep(.el-table td) {
  padding: 12px 0;
}

:deep(.el-table tr:hover) {
  background-color: #f5f7fa;
}

:deep(.el-table .el-table__cell) {
  padding: 12px 10px;
}

:deep(.el-button--small) {
  border-radius: 4px;
  font-weight: 500;
  padding: 7px 15px;
}

:deep(.el-tag) {
  border-radius: 4px;
  font-weight: 500;
  padding: 0 12px;
  height: 26px;
  line-height: 26px;
}

:deep(.el-avatar) {
  border-radius: 50%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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

:deep(.el-form-item__label) {
  font-weight: 600;
  color: #606266;
}

:deep(.el-descriptions) {
  margin-top: 20px;
}

:deep(.el-descriptions__label) {
  font-weight: 600;
}

.operation-buttons {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  flex-wrap: nowrap;
  white-space: nowrap;
}

.operation-buttons .el-button {
  margin: 0;
  flex-shrink: 0;
  padding: 5px 10px;
}

.operation-buttons .el-button.is-text {
  padding: 5px 8px;
}
</style>
