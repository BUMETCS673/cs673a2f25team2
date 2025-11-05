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
const loading = ref(false)
const submitting = ref(false)
const showDialog = ref(false)
const editingId = ref(null)
const formRef = ref(null)
const pageNo = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')

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
  loading.value = true
  try {
    const data = await sportApi.getSportList({
      pageNo: pageNo.value,
      pageSize: pageSize.value,
      sportType: searchKeyword.value || undefined
    })
    tableData.value = data.rows || []
    total.value = data.total || 0
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

