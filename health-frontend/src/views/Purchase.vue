<template>
  <div class="purchase-page">
    <el-page-header content="AI Chatbox Subscription" @back="goBack" />

    <div class="purchase-grid">
      <el-card class="summary-card" shadow="hover">
        <div class="summary-title">Order Summary</div>
        <div class="plan-row">
          <div>
            <div class="plan-name">{{ currentPlan.title }}</div>
            <div class="plan-duration">{{ currentPlan.duration }}</div>
          </div>
          <div class="plan-price">${{ currentPlan.price }}</div>
        </div>
        <div class="plan-switcher">
          <div class="plan-hint">Choose a different plan</div>
          <div class="plan-options">
            <el-radio-group v-model="selectedPlan" size="large">
              <el-radio-button
                  v-for="plan in plans"
                  :key="plan.key"
                  :label="plan.key"
              >
                {{ plan.title }}
              </el-radio-button>
            </el-radio-group>
          </div>
        </div>
        <div class="total-row">
          <span>Total</span>
          <span class="total-amount">${{ currentPlan.price }}</span>
        </div>
      </el-card>

      <el-card class="payment-card" shadow="hover">
        <div class="summary-title">Payment method</div>
        <el-radio-group v-model="paymentMethod" size="large" class="payment-options">
          <el-radio-button label="apple_pay">Apple Pay</el-radio-button>
          <el-radio-button label="paypal">PayPal</el-radio-button>
          <el-radio-button label="card">Credit / Debit Card</el-radio-button>
        </el-radio-group>

        <div v-if="paymentMethod === 'card'" class="card-form">
          <el-input v-model="cardForm.name" placeholder="Name on card" />
          <el-input v-model="cardForm.number" placeholder="Card number" maxlength="19" />
          <div class="card-row">
            <el-input v-model="cardForm.expiry" placeholder="MM/YY" maxlength="5" />
            <el-input v-model="cardForm.cvv" placeholder="CVV" maxlength="4" />
          </div>
        </div>

        <div class="payment-actions">
          <el-button @click="goBack">Cancel</el-button>
          <el-button type="primary" :loading="loading" @click="handlePay">
            Complete Payment
          </el-button>
        </div>

        <div class="secure-note">
          <el-icon><Lock /></el-icon>
          <span>Your payment is encrypted and secure.</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Lock } from '@element-plus/icons-vue'
import userApi from '../api/user'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const selectedPlan = ref(route.query.plan || 'single')
const paymentMethod = ref('apple_pay')
const cardForm = reactive({
  name: '',
  number: '',
  expiry: '',
  cvv: ''
})

const plans = [
  { key: 'single', title: 'One-time access', duration: 'Single chat session', price: 1.99 },
  { key: 'weekly', title: 'Weekly access', duration: '7 days unlimited chats', price: 5.99 },
  { key: 'monthly', title: 'Monthly access', duration: '30 days unlimited chats', price: 9.99 }
]

const currentPlan = computed(() => plans.find((plan) => plan.key === selectedPlan.value) || plans[0])

watch(
    () => route.query.plan,
    (plan) => {
      if (plan) {
        selectedPlan.value = plan
      }
    }
)

const goBack = () => {
  router.back()
}

const validateCardForm = () => {
  if (paymentMethod.value !== 'card') return true
  if (!cardForm.name.trim() || !cardForm.number.trim() || !cardForm.expiry.trim() || !cardForm.cvv.trim()) {
    ElMessage.error('Please fill in cardholder name, number, expiry, and CVV')
    return false
  }
  return true
}

const handlePay = async () => {
  if (!validateCardForm()) return

  loading.value = true
  try {
    const token = localStorage.getItem('token')
    if (!token) {
      ElMessage.error('Please login before purchasing')
      router.push('/login')
      return
    }

    await userApi.completePurchase({
      planType: selectedPlan.value,
      paymentMethod: paymentMethod.value
    })

    const userInfo = await userApi.getUserInfo(token)
    const stored = localStorage.getItem('userInfo')
    const base = stored ? JSON.parse(stored) : {}
    localStorage.setItem('userInfo', JSON.stringify({ ...base, ...userInfo }))

    ElMessage.success('Payment successful! AI Chatbox access has been activated.')
    router.push('/ai-chat')
  } catch (error) {
    ElMessage.error(error.message || 'Payment failed, please try again')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.purchase-page {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.purchase-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 20px;
}

.summary-card, .payment-card {
  border-radius: 12px;
}

.summary-title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 12px;
}

.plan-row, .total-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #ebeef5;
}

.plan-name {
  font-weight: 600;
}

.plan-duration {
  color: #909399;
  font-size: 13px;
  margin-top: 4px;
}

.plan-price, .total-amount {
  font-weight: 700;
  font-size: 18px;
  color: #409EFF;
}

.plan-switcher {
  margin: 16px 0;
}

.plan-hint {
  color: #909399;
  font-size: 13px;
  margin-bottom: 8px;
}

.plan-options {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.total-row {
  border-bottom: none;
  font-size: 15px;
}

.payment-options {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.card-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 16px;
}

.card-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(140px, 1fr));
  gap: 12px;
}

.payment-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 10px;
}

.secure-note {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 16px;
  color: #67c23a;
}
</style>
