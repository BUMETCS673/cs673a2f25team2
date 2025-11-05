<template>
  <div class="body-info">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>Body Information Management</span>
          <el-button type="primary" @click="showDialog = true">Add/Update Body Information</el-button>
        </div>
      </template>
      
      <el-form :model="bodyForm" ref="bodyFormRef" label-width="120px" v-if="bodyInfo">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Name">
              <el-input v-model="bodyInfo.name" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Age">
              <el-input-number v-model="bodyInfo.age" :min="1" :max="150" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Gender">
              <el-input v-model="bodyInfo.gender" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Height (cm)">
              <el-input-number v-model="bodyInfo.height" :precision="1" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Weight (kg)">
              <el-input-number v-model="bodyInfo.weight" :precision="1" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Blood Sugar (mmol/L)">
              <el-input-number v-model="bodyInfo.bloodSugar" :precision="2" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Blood Pressure (mmHg)">
              <el-input v-model="bodyInfo.bloodPressure" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Blood Lipid">
              <el-input v-model="bodyInfo.bloodLipid" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Heart Rate (bpm)">
              <el-input-number v-model="bodyInfo.heartRate" :precision="0" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Vision">
              <el-input-number v-model="bodyInfo.vision" :min="0" :max="10" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Sleep Duration (hours)">
              <el-input-number v-model="bodyInfo.sleepDuration" :precision="1" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Sleep Quality">
              <el-input v-model="bodyInfo.sleepQuality" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Daily Water Intake (L)">
              <el-input-number v-model="bodyInfo.waterConsumption" :precision="1" :min="0" disabled style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="Smoking">
              <el-tag :type="bodyInfo.smoking ? 'danger' : 'success'">
                {{ bodyInfo.smoking ? 'Yes' : 'No' }}
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Drinking">
              <el-tag :type="bodyInfo.drinking ? 'warning' : 'success'">
                {{ bodyInfo.drinking ? 'Yes' : 'No' }}
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Exercise">
              <el-tag :type="bodyInfo.exercise ? 'success' : 'info'">
                {{ bodyInfo.exercise ? 'Yes' : 'No' }}
              </el-tag>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="Dietary Habits">
          <el-input v-model="bodyInfo.foodTypes" type="textarea" :rows="3" disabled />
        </el-form-item>
      </el-form>
      
      <el-empty v-else description="No body information, please add" />
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog
      v-model="showDialog"
      :title="bodyInfo ? 'Update Body Information' : 'Add Body Information'"
      width="800px"
      @close="resetForm"
    >
      <el-form :model="bodyForm" :rules="rules" ref="formRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Name" prop="name">
              <el-input v-model="bodyForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Age" prop="age">
              <el-input-number v-model="bodyForm.age" :min="1" :max="150" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Gender" prop="gender">
              <el-select v-model="bodyForm.gender" style="width: 100%">
                <el-option label="Male" value="Male" />
                <el-option label="Female" value="Female" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Height (cm)" prop="height">
              <el-input-number v-model="bodyForm.height" :precision="1" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Weight (kg)" prop="weight">
              <el-input-number v-model="bodyForm.weight" :precision="1" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Blood Sugar (mmol/L)">
              <el-input-number v-model="bodyForm.bloodSugar" :precision="2" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Blood Pressure (mmHg)">
              <el-input v-model="bodyForm.bloodPressure" placeholder="e.g.: 120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Blood Lipid">
              <el-input v-model="bodyForm.bloodLipid" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Heart Rate (bpm)">
              <el-input-number v-model="bodyForm.heartRate" :precision="0" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Vision">
              <el-input-number v-model="bodyForm.vision" :min="0" :max="10" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Sleep Duration (hours)">
              <el-input-number v-model="bodyForm.sleepDuration" :precision="1" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Sleep Quality">
              <el-select v-model="bodyForm.sleepQuality" style="width: 100%">
                <el-option label="Excellent" value="Excellent" />
                <el-option label="Good" value="Good" />
                <el-option label="Fair" value="Fair" />
                <el-option label="Poor" value="Poor" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Daily Water Intake (L)">
              <el-input-number v-model="bodyForm.waterConsumption" :precision="1" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="Smoking">
              <el-switch v-model="bodyForm.smoking" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Drinking">
              <el-switch v-model="bodyForm.drinking" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Exercise">
              <el-switch v-model="bodyForm.exercise" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="Dietary Habits">
          <el-input v-model="bodyForm.foodTypes" type="textarea" :rows="3" placeholder="Please enter dietary habits" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">Cancel</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="loading">Confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import bodyApi from '../api/body'
import userApi from '../api/user'

const bodyInfo = ref(null)
const showDialog = ref(false)
const loading = ref(false)
const formRef = ref(null)
const bodyFormRef = ref(null)

const bodyForm = reactive({
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
  name: [{ required: true, message: 'Please enter name', trigger: 'blur' }],
  age: [{ required: true, message: 'Please enter age', trigger: 'blur' }],
  gender: [{ required: true, message: 'Please select gender', trigger: 'change' }],
  height: [{ required: true, message: 'Please enter height', trigger: 'blur' }],
  weight: [{ required: true, message: 'Please enter weight', trigger: 'blur' }]
}

const loadBodyInfo = async () => {
  try {
    // Ensure backend loginUser is initialized
    const token = localStorage.getItem('token')
    if (token) {
      try {
        await userApi.getUserInfo(token)
      } catch (error) {
        console.error('Failed to initialize user info:', error)
      }
    }
    
    const data = await bodyApi.getBodyInfo()
    // Process returned data structure
    if (data && data.bodyList && data.bodyList.length > 0) {
      bodyInfo.value = data.bodyList[0] // Take the first record
    } else {
      bodyInfo.value = null
    }
  } catch (error) {
    // If no data, keep bodyInfo as null
    bodyInfo.value = null
  }
}

const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  if (bodyInfo.value) {
    Object.assign(bodyForm, bodyInfo.value)
  } else {
    Object.keys(bodyForm).forEach(key => {
      if (typeof bodyForm[key] === 'boolean') {
        bodyForm[key] = false
      } else if (typeof bodyForm[key] === 'number') {
        bodyForm[key] = null
      } else {
        bodyForm[key] = ''
      }
    })
  }
}

// Add this helper function before handleSubmit method
const cleanFormData = (data) => {
  const cleaned = { ...data }
  
  // Convert all empty strings to null
  Object.keys(cleaned).forEach(key => {
    if (cleaned[key] === '') {
      cleaned[key] = null
    }
  })
  
  return cleaned
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // Ensure backend loginUser is initialized
        const token = localStorage.getItem('token')
        if (token) {
          await userApi.getUserInfo(token)
        }
        
        // Get userId
        const userIdData = await userApi.getUserId()
        if (!userIdData || !userIdData.id) {
          ElMessage.error('Unable to get user ID, please login again')
          return
        }
        
        // Construct submission data, clean empty strings and invalid values
        const submitData = { ...bodyForm }
        submitData.id = userIdData.id  // Set user ID as Body's id
        
        // Clean data: convert empty strings to null
        // For string fields, empty strings become null
        if (submitData.bloodPressure === '') {
          submitData.bloodPressure = null
        }
        if (submitData.bloodLipid === '') {
          submitData.bloodLipid = null
        }
        if (submitData.sleepQuality === '') {
          submitData.sleepQuality = null
        }
        if (submitData.foodTypes === '') {
          submitData.foodTypes = null
        }
        
        // Ensure numeric fields are numbers or null, not empty strings
        // bloodSugar is already a number type in the form, but ensure safety
        if (submitData.bloodSugar === '' || submitData.bloodSugar === undefined) {
          submitData.bloodSugar = null
        }
        
        await bodyApi.uploadBodyInfo(submitData)
        ElMessage.success('Save successful')
        showDialog.value = false
        
        // Reload data
        await loadBodyInfo()
        
        // Set flag to notify BodyNotes page to sync
        localStorage.setItem('bodyInfoUpdated', 'true')
      } catch (error) {
        console.error('Save failed:', error)
        ElMessage.error(error.message || 'Save failed')
      } finally {
        loading.value = false
      }
    }
  })
}

onMounted(() => {
  loadBodyInfo()
})
</script>

<style scoped>
.body-info {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-size: 18px;
}

:deep(.el-card__header) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-bottom: 2px solid #e4e7ed;
  padding: 20px 25px;
}

:deep(.el-card__body) {
  padding: 30px;
}

:deep(.el-form-item__label) {
  font-weight: 600;
  color: #606266;
}

:deep(.el-input__wrapper) {
  border-radius: 6px;
}

:deep(.el-button) {
  border-radius: 6px;
  font-weight: 500;
}

:deep(.el-tag) {
  border-radius: 4px;
  font-weight: 500;
  padding: 0 12px;
  height: 28px;
  line-height: 28px;
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
</style>


