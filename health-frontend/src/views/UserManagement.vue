<!--
  Code Composition Ratio:
  - AI Generated: 55%
  - Manual Development: 45%
  
  User management page with search, filter, and CRUD operations.
  User validation, status management, and search logic were manually developed
  to meet specific admin requirements, with standard CRUD framework patterns.
-->
<template>
  <div class="user-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>User Management</span>
          <div>
            <el-input
              v-model="searchForm.username"
              placeholder="Username"
              style="width: 150px; margin-right: 10px;"
              clearable
            />
            <el-input
              v-model="searchForm.phone"
              placeholder="Phone Number"
              style="width: 150px; margin-right: 10px;"
              clearable
            />
            <el-button type="primary" @click="handleSearch">Search</el-button>
            <el-button @click="resetSearch">Reset</el-button>
            <el-button type="success" @click="showDialog = true">Add User</el-button>
          </div>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="Username" width="150" />
        <el-table-column prop="email" label="Email" width="200" />
        <el-table-column prop="phone" label="Phone Number" width="150" />
        <el-table-column prop="status" label="Status" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? 'Enabled' : 'Disabled' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="Avatar" width="100">
          <template #default="scope">
            <el-avatar :src="scope.row.avatar" v-if="scope.row.avatar" />
            <span v-else>None</span>
          </template>
        </el-table-column>
        <el-table-column label="Operations" width="200" fixed="right">
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
      :title="editingId ? 'Edit User' : 'Add User'"
      width="500px"
      @close="resetForm"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" :disabled="!!editingId" />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="Phone Number" prop="phone">
          <el-input v-model="form.phone" />
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
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

const searchForm = reactive({
  username: '',
  phone: ''
})

const form = reactive({
  username: '',
  email: '',
  phone: '',
  status: 1,
  avatar: ''
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

const rules = {
  username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
  email: [{ validator: validateEmail, trigger: 'blur' }],
  phone: [{ validator: validatePhone, trigger: 'blur' }],
  status: [{ required: true, message: 'Please select status', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const data = await userApi.getUserList({
      pageNo: pageNo.value,
      pageSize: pageSize.value,
      username: searchForm.username || undefined,
      phone: searchForm.phone || undefined
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

const resetSearch = () => {
  searchForm.username = ''
  searchForm.phone = ''
  handleSearch()
}

const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  editingId.value = null
  Object.keys(form).forEach(key => {
    if (typeof form[key] === 'number') {
      form[key] = 1
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

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        if (editingId.value) {
          await userApi.updateUser({ ...form, id: editingId.value })
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

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.user-management {
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
</style>



