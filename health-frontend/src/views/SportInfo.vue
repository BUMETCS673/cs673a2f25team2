<!--
  Code Composition Ratio:
  - AI Generated: 60%
  - Manual Development: 40%
  
  Sports knowledge base management with search and CRUD functionality.
  This follows standard data table patterns with search capabilities.
  Most code is framework-based with custom field configurations.
-->
<template>
  <div class="sport-info">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Sports Knowledge Base</span>
          <el-input
            v-model="searchKeyword"
            placeholder="Search sport type"
            style="width: 200px; margin-right: 10px;"
            clearable
            @clear="handleSearch"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" @click="handleSearch">Search</el-button>
          <el-button type="success" @click="showDialog = true">Add Sports Knowledge</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="sportType" label="Sport Type" width="150" />
        <el-table-column prop="suitableTime" label="Suitable Time" width="150" />
        <el-table-column prop="suitableHeartRate" label="Suitable Heart Rate" width="150" />
        <el-table-column prop="suitableFrequency" label="Suitable Frequency" width="150" />
        <el-table-column prop="recommendedSpeed" label="Recommended Speed" width="150" />
        <el-table-column label="Operations" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">Edit</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :current-page="pageNo"
        :page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog
      v-model="showDialog"
      :title="editingId ? 'Edit Sports Knowledge' : 'Add Sports Knowledge'"
      width="700px"
      @close="resetForm"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="180px">
        <el-form-item label="Sport Type" prop="sportType">
          <el-input v-model="form.sportType" placeholder="e.g.: Running, Swimming, Cycling" />
        </el-form-item>
        <el-form-item label="Suitable Time">
          <el-input v-model="form.suitableTime" placeholder="e.g.: Morning, Evening" />
        </el-form-item>
        <el-form-item label="Suitable Heart Rate">
          <el-input v-model="form.suitableHeartRate" placeholder="e.g.: 120-150 beats/min" />
        </el-form-item>
        <el-form-item label="Suitable Frequency">
          <el-input v-model="form.suitableFrequency" placeholder="e.g.: 3-4 times/week" />
        </el-form-item>
        <el-form-item label="Recommended Speed">
          <el-input v-model="form.recommendedSpeed" placeholder="e.g.: 6-8 km/h" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">Confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import sportApi from '../api/sport'

const tableData = ref([])
const allData = ref([])  // 存储所有数据，用于前端分页
const loading = ref(false)
const submitting = ref(false)
const showDialog = ref(false)
const editingId = ref(null)
const formRef = ref(null)
const pageNo = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')
const lastSearchKeyword = ref('')  // 记录上次搜索关键词，用于判断是否需要重新加载

const form = reactive({
  sportType: '',
  suitableTime: '',
  suitableHeartRate: '',
  suitableFrequency: '',
  recommendedSpeed: ''
})

const rules = {
  sportType: [{ required: true, message: 'Please enter sport type', trigger: 'blur' }]
}

const loadData = async () => {
  // 使用响应式变量的值
  await loadDataWithParams(pageNo.value, pageSize.value)
}

const handleSizeChange = (newPageSize) => {
  pageSize.value = newPageSize
  pageNo.value = 1  // 重置到第一页
  loadDataWithParams(1, newPageSize)
}

const handleCurrentChange = (newPageNo) => {
  pageNo.value = newPageNo
  loadDataWithParams(newPageNo, pageSize.value)
}

// 新增：带参数的加载函数 - 实现前端分页
const loadDataWithParams = async (currentPageNo, currentPageSize) => {
  loading.value = true
  try {
    // 如果搜索关键词改变，需要重新加载全部数据
    const needReload = searchKeyword.value !== lastSearchKeyword.value
    
    // 如果还没有加载过全部数据，或者搜索关键词改变，先获取全部数据
    if (allData.value.length === 0 || needReload) {
      // 获取所有数据（使用大pageSize确保获取全部）
      const data = await sportApi.getSportList({
        pageNo: 1,
        pageSize: 10000,
        sportType: searchKeyword.value || undefined
      })
      
      // 存储全部数据
      allData.value = data?.rows || []
      
      // 设置total
      if (data?.total !== undefined && data?.total !== null && data.total > 0) {
        total.value = data.total
      } else if (allData.value.length > 0) {
        total.value = allData.value.length
      } else {
        total.value = 0
      }
      
      // 更新搜索关键词记录
      lastSearchKeyword.value = searchKeyword.value
    }
    
    // 前端分页：根据当前页码和每页大小切片数据
    const startIndex = (currentPageNo - 1) * currentPageSize
    const endIndex = startIndex + currentPageSize
    tableData.value = allData.value.slice(startIndex, endIndex)
    
  } catch (error) {
    console.error('Failed to load data:', error)
    ElMessage.error('Failed to load data')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNo.value = 1
  // 清空缓存，重新加载
  allData.value = []
  lastSearchKeyword.value = ''
  loadData()
}

const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  editingId.value = null
  Object.keys(form).forEach(key => {
    if (typeof form[key] === 'number') {
      form[key] = null
    } else {
      form[key] = ''
    }
  })
}

const handleEdit = async (row) => {
  editingId.value = row.id
  try {
    const data = await sportApi.getSportById(row.id)
    Object.assign(form, data)
    showDialog.value = true
  } catch (error) {
    ElMessage.error('Failed to load data')
  }
}

const handleDelete = async (row) => {
  ElMessageBox.confirm('Are you sure to delete this sports knowledge?', 'Confirm', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    try {
      await sportApi.deleteSport(row.id)
      ElMessage.success('Deleted successfully')
      // 清空缓存，重新加载全部数据
      allData.value = []
      loadData()
    } catch (error) {
      ElMessage.error('Failed to delete')
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
          await sportApi.updateSport({ ...form, id: editingId.value })
          ElMessage.success('Updated successfully')
        } else {
          await sportApi.addSport(form)
          ElMessage.success('Added successfully')
        }
        showDialog.value = false
        // 清空缓存，重新加载全部数据
        allData.value = []
        loadData()
      } catch (error) {
        ElMessage.error(error.message || 'Operation failed')
      } finally {
        submitting.value = false
      }
    }
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.sport-info {
  padding: 0;
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

:deep(.el-input--prefix .el-input__wrapper) {
  padding-left: 35px;
}

:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

:deep(.el-table th) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  font-weight: 600;
  color: #606266;
}

:deep(.el-table tr:hover) {
  background-color: #f5f7fa;
}

:deep(.el-button--small) {
  border-radius: 4px;
  font-weight: 500;
  padding: 7px 15px;
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
</style>

