<template>
  <div class="body-notes">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Health Records Management</span>
          <el-button type="primary" @click="openDialog">Add Record</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="notesid" label="Record ID" width="100" />
        <el-table-column prop="date" label="Record Date" width="150">
          <template #default="scope">
            {{ formatDate(scope.row.Date || scope.row.date) }}
          </template>
        </el-table-column>
        <el-table-column prop="weight" label="Weight (kg)" width="100" />
        <el-table-column prop="bloodSugar" label="Blood Sugar (mmol/L)" width="120" />
        <el-table-column prop="bloodPressure" label="Blood Pressure (mmHg)" width="120" />
        <el-table-column prop="heartRate" label="Heart Rate (bpm)" width="100" />
        <el-table-column prop="name" label="Name" width="100" />
        <el-table-column label="Actions" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">Edit</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog
      v-model="showDialog"
      :title="editingId ? 'Edit Record' : 'Add Record'"
      width="600px"
      @close="resetForm"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="Record Date" prop="date">
          <el-date-picker
            v-model="form.date"
            type="date"
            placeholder="Select Date"
            style="width: 100%"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Name">
              <el-input v-model="form.name" :disabled="!editingId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Age">
              <el-input-number v-model="form.age" :min="1" :max="150" :disabled="!editingId" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Gender">
              <el-select v-model="form.gender" :disabled="!editingId" style="width: 100%">
                <el-option label="Male" value="Male" />
                <el-option label="Female" value="Female" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Height (cm)">
              <el-input-number v-model="form.height" :precision="1" :min="0" :disabled="!editingId" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Weight (kg)">
              <el-input-number v-model="form.weight" :precision="1" :min="0" :disabled="!editingId" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Blood Sugar (mmol/L)">
              <el-input-number v-model="form.bloodSugar" :precision="2" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Blood Pressure (mmHg)">
              <el-input v-model="form.bloodPressure" placeholder="e.g.: 120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Heart Rate (bpm)">
              <el-input-number v-model="form.heartRate" :precision="0" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">Confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch, onActivated } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import bodyApi from '../api/body'
import userApi from '../api/user'

const tableData = ref([])
const loading = ref(false)
const submitting = ref(false)
const showDialog = ref(false)
const editingId = ref(null)
const formRef = ref(null)
const pageNo = ref(1)
const pageSize = ref(10)
const total = ref(0)
const bodyInfo = ref(null)  // Add this to store body information

const form = reactive({
  date: '',
  name: '',
  age: null,
  gender: '',
  height: null,
  weight: null,
  bloodSugar: null,
  bloodPressure: '',
  bloodLipid: '',
  heartRate: null,
  vision: null,
  sleepDuration: null,
  sleepQuality: '',
  smoking: false,
  drinking: false,
  exercise: false,
  foodTypes: '',
  waterConsumption: null
})

const rules = {
  date: [{ required: true, message: 'Please select record date', trigger: 'change' }]
}

const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleDateString('en-US')
}

const loadData = async () => {
  loading.value = true
  try {
    // Remove code to get userId, as getUserBodyList handles it internally
    // Get user's body records list
    const data = await bodyApi.getUserBodyList({
      pageNo: pageNo.value,
      pageSize: pageSize.value
    })
    
    tableData.value = data.rows || []
    total.value = data.total || 0
  } catch (error) {
    console.error('Failed to load data:', error)
    ElMessage.error('Failed to load data')
  } finally {
    loading.value = false
  }
}

// Add function to load body information
const loadBodyInfo = async () => {
  try {
    const data = await bodyApi.getBodyInfo()
    if (data && data.bodyList && data.bodyList.length > 0) {
      bodyInfo.value = data.bodyList[0]
      // Sync basic information to form
      if (bodyInfo.value) {
        form.name = bodyInfo.value.name || ''
        form.age = bodyInfo.value.age || null
        form.gender = bodyInfo.value.gender || ''
        form.height = bodyInfo.value.height || null
        form.weight = bodyInfo.value.weight || null
      }
      return bodyInfo.value
    }
  } catch (error) {
    console.error('Failed to load body information:', error)
  }
  return null
}

// Add function to sync basic information for all records
const syncAllRecordsBasicInfo = async () => {
  if (!bodyInfo.value) {
    const info = await loadBodyInfo()
    if (!info) return
  }
  
  if (!bodyInfo.value) return
  
  // 获取所有记录
  try {
    const allData = await bodyApi.getUserBodyList({
      pageNo: 1,
      pageSize: 10000  // Get all records
    })
    
    const records = allData.rows || []
    
    // Batch update basic information for all records
    const userIdData = await userApi.getUserId()
    if (!userIdData || !userIdData.id) return
    
    let updateCount = 0
    for (const record of records) {
      // 检查是否需要更新（比较基本信息）
      const needsUpdate = 
        record.name !== bodyInfo.value.name ||
        record.age !== bodyInfo.value.age ||
        record.gender !== bodyInfo.value.gender ||
        record.height !== bodyInfo.value.height ||
        record.weight !== bodyInfo.value.weight
        
      if (needsUpdate) {
        try {
          const updateData = {
            ...record,
            notesid: record.notesid,
            id: userIdData.id,
            name: bodyInfo.value.name,
            age: bodyInfo.value.age,
            gender: bodyInfo.value.gender,
            height: bodyInfo.value.height,
            weight: bodyInfo.value.weight
          }
          
          // 处理日期字段
          if (updateData.Date) {
            // 保持原有日期
          } else if (updateData.date) {
            updateData.Date = new Date(updateData.date)
          }
          
          await bodyApi.updateUserBody(updateData)
          updateCount++
        } catch (error) {
          console.error(`更新记录 ${record.notesid} 失败:`, error)
        }
      }
    }
    
    if (updateCount > 0) {
      console.log(`已同步 ${updateCount} 条记录的基本信息`)
      // 重新加载当前页数据
      await loadData()
    }
  } catch (error) {
    console.error('同步记录失败:', error)
  }
}

const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  editingId.value = null
  
  // 重置时，如果是添加新记录，重新加载身体信息并同步
  if (!editingId.value && bodyInfo.value) {
    form.name = bodyInfo.value.name || ''
    form.age = bodyInfo.value.age || null
    form.gender = bodyInfo.value.gender || ''
    form.height = bodyInfo.value.height || null
    form.weight = bodyInfo.value.weight || null
  }
  
  // 重置其他字段
  form.date = ''
  form.bloodSugar = null
  form.bloodPressure = ''
  form.bloodLipid = ''
  form.heartRate = null
  form.vision = null
  form.sleepDuration = null
  form.sleepQuality = ''
  form.smoking = false
  form.drinking = false
  form.exercise = false
  form.foodTypes = ''
  form.waterConsumption = null
}

// 修改对话框打开时的处理
const openDialog = async () => {
  editingId.value = null  // 确保是添加模式
  // 添加新记录时，加载并同步身体信息
  await loadBodyInfo()
  showDialog.value = true
}

const handleEdit = async (row) => {
  editingId.value = row.notesid
  try {
    // 先加载身体信息
    await loadBodyInfo()
    
    // 然后加载记录数据
    const data = await bodyApi.getUserBodyById(row.notesid)
    
    // 合并数据：记录的其他字段 + 身体信息的基本字段
    Object.assign(form, {
      ...data,
      // 用身体信息覆盖基本信息（姓名、年龄、性别、身高、体重）
      name: bodyInfo.value?.name || data.name || '',
      age: bodyInfo.value?.age ?? data.age ?? null,
      gender: bodyInfo.value?.gender || data.gender || '',
      height: bodyInfo.value?.height ?? data.height ?? null,
      weight: bodyInfo.value?.weight ?? data.weight ?? null
    })
    
    // 处理日期格式
    if (data.Date || data.date) {
      const dateValue = data.Date || data.date
      form.date = new Date(dateValue).toISOString().split('T')[0]
    }
    showDialog.value = true
  } catch (error) {
    console.error('Failed to load data:', error)
    ElMessage.error('Failed to load data')
  }
}

const handleDelete = async (row) => {
  ElMessageBox.confirm('Confirm要Delete这条记录吗？', 'Prompt', {
    confirmButtonText: 'Confirm',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await bodyApi.deleteUserBody(row.notesid)
      ElMessage.success('Delete成功')
      loadData()
    } catch (error) {
      ElMessage.error('Delete失败')
    }
  })
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        // 获取用户ID
        const userIdData = await userApi.getUserId()
        if (!userIdData || !userIdData.id) {
          ElMessage.error('Unable to get user ID, please login again')
          submitting.value = false
          return
        }
        
        if (editingId.value) {
          // Edit模式
          const updateData = { 
            ...form, 
            notesid: editingId.value,
            id: userIdData.id  // 确保包含用户ID
          }
          // 处理日期字段
          if (updateData.date) {
            updateData.Date = new Date(updateData.date)
            delete updateData.date
          }
          await bodyApi.updateUserBody(updateData)
          ElMessage.success('Update successful')
        } else {
          // 添加模式
          const submitData = {
            id: userIdData.id,  // 必须设置用户ID
            name: form.name,
            age: form.age,
            gender: form.gender,
            height: form.height,
            weight: form.weight,
            bloodSugar: form.bloodSugar || null,
            bloodPressure: form.bloodPressure || null,
            bloodLipid: form.bloodLipid || null,
            heartRate: form.heartRate || null,
            vision: form.vision || null,
            sleepDuration: form.sleepDuration || null,
            sleepQuality: form.sleepQuality || null,
            smoking: form.smoking || false,
            drinking: form.drinking || false,
            exercise: form.exercise || false,
            foodTypes: form.foodTypes || null,
            waterConsumption: form.waterConsumption || null
          }
          
          // 处理日期字段 - BodyNotes使用Date（大写）
          if (form.date) {
            submitData.Date = new Date(form.date)
          } else {
            submitData.Date = new Date()  // 如果没有提供日期，使用当前日期
          }
          
          await bodyApi.addBodyNotes(submitData)
          ElMessage.success('Add successful')
        }
        showDialog.value = false
        loadData()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error(error.message || '操作失败')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 监听路由激活，当从BodyInfo页面返回时同步数据
onActivated(() => {
  // 检查是否有身体信息更新的标志
  const bodyInfoUpdated = localStorage.getItem('bodyInfoUpdated')
  if (bodyInfoUpdated === 'true') {
    syncAllRecordsBasicInfo()
    localStorage.removeItem('bodyInfoUpdated')
  }
})

// 页面加载时也检查并同步
onMounted(async () => {
  loadData()
  await loadBodyInfo()
  
  // 检查是否有身体信息更新的标志
  const bodyInfoUpdated = localStorage.getItem('bodyInfoUpdated')
  if (bodyInfoUpdated === 'true') {
    syncAllRecordsBasicInfo()
    localStorage.removeItem('bodyInfoUpdated')
  }
})
</script>

<style scoped>
.body-notes {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

