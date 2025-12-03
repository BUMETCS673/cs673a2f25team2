<template>
  <div class="purchase-page">
    <div class="page-header-custom">
      <el-button :icon="ArrowLeft" circle @click="goBack" class="back-button" />
      <div class="header-content">
        <el-icon class="header-icon"><ShoppingCart /></el-icon>
        <div>
          <div class="header-title">AI Chatbox Subscription</div>
          <div class="header-subtitle">Unlock unlimited access to your AI Health Assistant</div>
        </div>
      </div>
    </div>

    <div class="purchase-grid">
      <el-card class="summary-card" shadow="hover">
        <div class="card-header">
          <el-icon class="card-icon"><DocumentChecked /></el-icon>
          <span class="summary-title">Order Summary</span>
        </div>
        
        <div class="plan-display">
          <div class="plan-badge">
            <el-icon><Trophy /></el-icon>
            <span>Selected Plan</span>
          </div>
          <div class="plan-row">
            <div class="plan-info">
              <div class="plan-name">{{ currentPlan.title }}</div>
              <div class="plan-duration">
                <el-icon><Clock /></el-icon>
                {{ currentPlan.duration }}
              </div>
            </div>
            <div class="plan-price-box">
              <span class="currency">$</span>
              <span class="amount">{{ currentPlan.price }}</span>
            </div>
          </div>
        </div>

        <div class="plan-switcher">
          <div class="plan-hint">
            <el-icon><Edit /></el-icon>
            Choose a different plan
          </div>
          <div class="plan-options">
            <div 
              v-for="plan in plans" 
              :key="plan.key"
              :class="['plan-option-card', { active: selectedPlan === plan.key }]"
              @click="selectedPlan = plan.key"
            >
              <div class="option-header">
                <span class="option-title">{{ plan.title }}</span>
                <el-icon v-if="selectedPlan === plan.key" class="check-icon"><CircleCheck /></el-icon>
              </div>
              <div class="option-price">${{ plan.price }}</div>
              <div class="option-duration">{{ plan.duration }}</div>
            </div>
          </div>
        </div>

        <div class="total-section">
          <div class="total-row">
            <span class="total-label">Subtotal</span>
            <span class="total-value">${{ currentPlan.price }}</span>
          </div>
          <div class="total-row main-total">
            <span class="total-label">Total</span>
            <span class="total-amount">${{ currentPlan.price }}</span>
          </div>
        </div>
      </el-card>

      <el-card class="payment-card" shadow="hover">
        <div class="card-header">
          <el-icon class="card-icon"><CreditCard /></el-icon>
          <span class="summary-title">Payment Method</span>
        </div>

        <div class="payment-methods">
          <div 
            v-for="method in paymentMethods" 
            :key="method.value"
            :class="['payment-method-card', { active: paymentMethod === method.value }]"
            @click="paymentMethod = method.value"
          >
            <div class="method-icon">
              <component :is="method.icon" />
            </div>
            <div class="method-name">{{ method.label }}</div>
            <el-icon v-if="paymentMethod === method.value" class="check-icon"><CircleCheck /></el-icon>
          </div>
        </div>

        <transition name="slide-fade">
          <div v-if="paymentMethod === 'card'" class="card-form">
            <div class="form-label">Card Information</div>
            <el-input 
              v-model="cardForm.name" 
              placeholder="Name on card" 
              size="large"
              :prefix-icon="User"
            />
            <el-input 
              v-model="cardForm.number" 
              placeholder="Card number" 
              maxlength="19"
              size="large"
              :prefix-icon="CreditCard"
            />
            <div class="card-row">
              <el-input 
                v-model="cardForm.expiry" 
                placeholder="MM/YY" 
                maxlength="5"
                size="large"
                :prefix-icon="Calendar"
              />
              <el-input 
                v-model="cardForm.cvv" 
                placeholder="CVV" 
                maxlength="4"
                size="large"
                type="password"
              />
            </div>
          </div>
        </transition>

        <div class="payment-actions">
          <el-button size="large" @click="goBack" class="cancel-btn">
            <el-icon><Close /></el-icon>
            Cancel
          </el-button>
          <el-button 
            type="primary" 
            size="large"
            :loading="loading" 
            @click="handlePay"
            class="pay-btn"
          >
            <el-icon v-if="!loading"><Checked /></el-icon>
            Complete Payment
          </el-button>
        </div>

        <div class="secure-note">
          <el-icon class="lock-icon"><Lock /></el-icon>
          <div class="secure-text">
            <div class="secure-title">Secure Payment</div>
            <div class="secure-desc">Your payment is encrypted and secure</div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { 
  Lock, 
  ArrowLeft, 
  ShoppingCart, 
  DocumentChecked,
  Trophy,
  Clock,
  Edit,
  CircleCheck,
  CreditCard,
  User,
  Calendar,
  Close,
  Checked
} from '@element-plus/icons-vue'
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

const paymentMethods = [
  { value: 'apple_pay', label: 'Apple Pay', icon: 'i-ep-apple' },
  { value: 'paypal', label: 'PayPal', icon: 'i-ep-money' },
  { value: 'card', label: 'Credit / Debit Card', icon: CreditCard }
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

const openExternalCheckout = () => {
  if (paymentMethod.value === 'apple_pay') {
    const applePayUrl = `https://pay.apple.com/checkout?amount=${currentPlan.value.price}&currency=USD`
    window.open(applePayUrl, '_blank', 'noopener')
    ElMessage.info('Opening Apple Pay for secure checkout...')
  }

  if (paymentMethod.value === 'paypal') {
    const paypalParams = new URLSearchParams({
      flow: 'checkout',
      amount: currentPlan.value.price,
      currency_code: 'USD',
      intent: 'sale'
    })
    const paypalUrl = `https://www.paypal.com/checkoutnow?${paypalParams.toString()}`
    window.open(paypalUrl, '_blank', 'noopener')
    ElMessage.info('Redirecting to PayPal to complete your payment...')
  }
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

    openExternalCheckout()

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
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  max-width: 1200px;
  margin: 0 auto;
}

/* Custom Page Header */
.page-header-custom {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
  animation: fadeInDown 0.6s ease-out;
}

.back-button {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.back-button:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateX(-4px);
}

.header-content {
  display: flex;
  align-items: center;
  gap: 16px;
  flex: 1;
}

.header-icon {
  font-size: 42px;
  color: white;
}

.header-title {
  font-size: 24px;
  font-weight: 700;
  color: white;
  margin-bottom: 4px;
}

.header-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.9);
}

/* Grid Layout */
.purchase-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

@media (max-width: 968px) {
  .purchase-grid {
    grid-template-columns: 1fr;
  }
}

.summary-card, .payment-card {
  border-radius: 16px;
  padding: 24px;
  transition: all 0.3s ease;
  animation: fadeInUp 0.6s ease-out;
  border: 2px solid transparent;
}

.summary-card:hover, .payment-card:hover {
  border-color: #409EFF;
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(64, 158, 255, 0.15);
}

/* Card Header */
.card-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 2px solid #f0f0f0;
}

.card-icon {
  font-size: 24px;
  color: #409EFF;
}

.summary-title {
  font-weight: 700;
  font-size: 20px;
  color: #303133;
}

/* Plan Display */
.plan-display {
  margin-bottom: 24px;
}

.plan-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 14px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 16px;
}

.plan-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.plan-row:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.plan-info {
  flex: 1;
}

.plan-name {
  font-weight: 700;
  font-size: 18px;
  color: #303133;
  margin-bottom: 8px;
}

.plan-duration {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  font-size: 14px;
}

.plan-price-box {
  display: flex;
  align-items: baseline;
  gap: 4px;
  padding: 12px 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.currency {
  font-size: 20px;
  font-weight: 600;
  color: #409EFF;
}

.amount {
  font-size: 32px;
  font-weight: 800;
  color: #409EFF;
}

/* Plan Switcher */
.plan-switcher {
  margin: 24px 0;
}

.plan-hint {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #606266;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 16px;
}

.plan-options {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 12px;
}

.plan-option-card {
  padding: 16px;
  border: 2px solid #e4e7ed;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: white;
}

.plan-option-card:hover {
  border-color: #409EFF;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.15);
  transform: translateY(-2px);
}

.plan-option-card.active {
  border-color: #409EFF;
  background: linear-gradient(135deg, #e6f0ff 0%, #f0f7ff 100%);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.option-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.option-title {
  font-weight: 600;
  font-size: 15px;
  color: #303133;
}

.check-icon {
  color: #67C23A;
  font-size: 20px;
}

.option-price {
  font-size: 24px;
  font-weight: 700;
  color: #409EFF;
  margin-bottom: 4px;
}

.option-duration {
  font-size: 12px;
  color: #909399;
  line-height: 1.4;
}

/* Total Section */
.total-section {
  margin-top: 24px;
  padding-top: 20px;
  border-top: 2px solid #f0f0f0;
}

.total-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
}

.total-row.main-total {
  padding: 16px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  margin-top: 12px;
}

.total-row.main-total .total-label,
.total-row.main-total .total-amount {
  color: white;
}

.total-label {
  font-size: 15px;
  color: #606266;
  font-weight: 500;
}

.total-value {
  font-size: 16px;
  color: #303133;
  font-weight: 600;
}

.total-amount {
  font-weight: 800;
  font-size: 28px;
  color: #409EFF;
}

/* Payment Methods */
.payment-methods {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 12px;
  margin-bottom: 24px;
}

.payment-method-card {
  position: relative;
  padding: 20px 16px;
  border: 2px solid #e4e7ed;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-align: center;
  background: white;
}

.payment-method-card:hover {
  border-color: #409EFF;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.15);
  transform: translateY(-2px);
}

.payment-method-card.active {
  border-color: #409EFF;
  background: linear-gradient(135deg, #e6f0ff 0%, #f0f7ff 100%);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.method-icon {
  font-size: 32px;
  margin-bottom: 8px;
  color: #409EFF;
}

.payment-method-card.active .method-icon {
  color: #409EFF;
}

.method-name {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.payment-method-card .check-icon {
  position: absolute;
  top: 8px;
  right: 8px;
  color: #67C23A;
  font-size: 20px;
}

/* Card Form */
.card-form {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 12px;
  margin-bottom: 20px;
}

.form-label {
  font-size: 14px;
  font-weight: 600;
  color: #606266;
  margin-bottom: 12px;
}

.card-form .el-input {
  margin-bottom: 12px;
}

.card-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

/* Payment Actions */
.payment-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 20px;
}

.cancel-btn {
  border: 2px solid #e4e7ed;
  font-weight: 600;
  transition: all 0.3s ease;
}

.cancel-btn:hover {
  border-color: #f56c6c;
  color: #f56c6c;
}

.pay-btn {
  font-weight: 700;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
}

.pay-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

/* Secure Note */
.secure-note {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 20px;
  padding: 16px;
  background: linear-gradient(135deg, #e8f5e9 0%, #f1f8e9 100%);
  border-radius: 12px;
  border-left: 4px solid #67c23a;
}

.lock-icon {
  font-size: 28px;
  color: #67c23a;
}

.secure-text {
  flex: 1;
}

.secure-title {
  font-size: 14px;
  font-weight: 700;
  color: #67c23a;
  margin-bottom: 2px;
}

.secure-desc {
  font-size: 12px;
  color: #5a6c57;
}

/* Animations */
@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.2s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style>
