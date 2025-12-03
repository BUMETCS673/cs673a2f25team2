<!--
  Code Composition Ratio:
  - AI Generated: 25%
  - Manual Development: 75%
  
  This file contains complex WebSocket communication logic, real-time message streaming,
  and state management for the AI chat interface. The core business logic, message handling,
  and WebSocket lifecycle management were primarily manually developed.
-->
<template>
  <div class="ai-chat-container">
    <div class="session-panel">
    <div class="session-header">
      <div class="session-title">
        <el-icon><ChatDotRound /></el-icon>
        <span>Chat History</span>
      </div>
      <div class="session-actions">
        <el-button type="primary" size="small" @click="createNewSession">
          New Chat
        </el-button>
        <el-button type="primary" size="small" @click="clearAllSessions">
          clean
        </el-button>
      </div>
    </div>
      <el-scrollbar class="session-list">
        <div
            v-for="session in sessions"
            :key="session.id"
            :class="['session-item', { active: session.id === activeSessionId }]"
            @click="switchSession(session.id)"
        >
          <div class="session-item-header">
            <span class="session-name">{{ session.title }}</span>
            <el-tag size="small" type="info">{{ session.messages.length }} msgs</el-tag>
          </div>
          <div class="session-time">{{ formatSessionTime(session.createdAt) }}</div>
        </div>
      </el-scrollbar>
    </div>

    <el-card class="chat-card">
      <template #header>
        <div class="chat-header">
          <el-icon><ChatDotRound /></el-icon>
          <span>{{ currentSessionTitle }}</span>
          <el-tag :type="hasAccess ? 'success' : 'danger'" size="small" class="access-tag">
            {{ accessBadgeText }}
          </el-tag>
          <el-button v-if="!hasAccess" size="small" type="primary" @click="openPlanDialog">
            Purchase access
          </el-button>
        </div>
      </template>

      <div class="chat-content">
        <!-- Message list -->
        <div class="message-list" ref="messageListRef">
          <div
              v-for="(message, index) in messages"
              :key="`${activeSessionId}-${index}`"
              :class="['message-item', message.type]"
          >
            <div class="message-avatar">
              <el-icon v-if="message.type === 'user'">
                <User />
              </el-icon>
              <el-icon v-else>
                <Cpu />
              </el-icon>
            </div>
            <div class="message-content">
              <div class="message-text">{{ message.text }}</div>
              <div class="message-time">{{ message.time }}</div>
            </div>
          </div>

          <!-- Loading indicator -->
          <div v-if="loading" class="message-item ai loading">
            <div class="message-avatar">
              <el-icon><Cpu /></el-icon>
            </div>
            <div class="message-content">
              <div class="typing-indicator">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
        </div>

        <!-- Input area -->
        <div class="input-area">
          <el-button
              type="primary"
              @click="sendHealthAdviceRequest"
              :disabled="loading || !isConnected || !hasAccess"
              class="health-advice-btn"
          >
            Get Health Advice
          </el-button>
          <el-input
              v-model="inputMessage"
              placeholder="Enter your health question..."
              @keyup.enter="sendMessage"
              :disabled="loading || !isConnected || !hasAccess"
              class="message-input"
          />
          <div class="input-actions">
            <input
                ref="fileInputRef"
                type="file"
                accept=".txt,image/*"
                class="file-input"
                @change="handleFileChange"
            >
            <el-button
                :icon="Plus"
                circle
                @click="triggerFileSelect"
                :disabled="loading || !isConnected || !hasAccess"
            />
            <el-button
                type="primary"
                :icon="Promotion"
                circle
                @click="sendMessage"
                :disabled="!inputMessage.trim() || loading || !isConnected || !hasAccess"
            />
          </div>
        </div>

        <!-- Connection status -->
        <div class="connection-status">
          <el-tag v-if="isConnected" type="success" size="small">
            <el-icon><CircleCheck /></el-icon>
            Connected
          </el-tag>
          <el-tag v-else type="danger" size="small">
            <el-icon><CircleClose /></el-icon>
            Disconnected
          </el-tag>
        </div>
      </div>
    </el-card>

    <el-dialog
        v-model="paywallVisible"
        width="480px"
        align-center
        class="paywall-dialog"
    >
      <template #header>
        <div class="paywall-header">
          <el-icon class="paywall-icon"><Lock /></el-icon>
          <div class="paywall-title">AI Chatbox is a premium feature</div>
        </div>
      </template>
      <div class="paywall-content">
        <p class="paywall-text">
          You need an active subscription to chat with the AI assistant.
        </p>
        <p class="paywall-subtitle">
          Choose a plan to unlock access and start chatting with our AI Health Assistant.
        </p>
      </div>
      <template #footer>
        <div class="paywall-footer">
          <el-button type="primary" size="large" @click="openPlanDialog">
            <el-icon style="margin-right: 6px;"><ShoppingCart /></el-icon>
            Go to purchase
          </el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog
        v-model="planDialogVisible"
        width="640px"
        align-center
        class="plan-dialog"
    >
      <template #header>
        <div class="plan-header">
          <el-icon class="plan-header-icon"><Trophy /></el-icon>
          <div class="plan-header-title">Choose your AI Chatbox plan</div>
          <div class="plan-header-subtitle">Select the best plan for your needs</div>
        </div>
      </template>
      <div class="plan-grid">
        <div v-for="plan in plans" :key="plan.key" class="plan-card">
          <div class="plan-card-header">
            <div class="plan-name">{{ plan.title }}</div>
            <div class="plan-price">
              <span class="price-symbol">$</span>
              <span class="price-amount">{{ plan.price }}</span>
            </div>
          </div>
          <div class="plan-duration">{{ plan.duration }}</div>
          <el-button type="primary" class="plan-button" @click="goToPurchase(plan.key)">
            <el-icon style="margin-right: 6px;"><Select /></el-icon>
            Purchase
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, onUnmounted, nextTick, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  ChatDotRound,
  User,
  Cpu,
  Promotion,
  CircleCheck,
  CircleClose,
  Plus,
  Lock,
  ShoppingCart,
  Trophy,
  Select
} from '@element-plus/icons-vue'
import wsConfig from '../config/websocket'
import userApi from '../api/user'

// WebSocket connection
let ws = null
const isConnected = ref(false)
const loading = ref(false)
const sessions = ref([])
const activeSessionId = ref('')
const messages = ref([])
const inputMessage = ref('')
const messageListRef = ref(null)
const fileInputRef = ref(null)

// Current user info
const userInfo = ref(null)
const paywallVisible = ref(false)
const planDialogVisible = ref(false)
const router = useRouter()

const plans = [
  { key: 'single', title: 'One-time access', price: 1.99, duration: 'Single chat session' },
  { key: 'weekly', title: 'Weekly access', price: 5.99, duration: '7 days unlimited chats' },
  { key: 'monthly', title: 'Monthly access', price: 9.99, duration: '30 days unlimited chats' }
]

const hasAccess = computed(() => {
  if (!userInfo.value) return false
  const status = (userInfo.value.paymentStatus || '').toUpperCase()
  const expiry = userInfo.value.accessExpiry ? new Date(userInfo.value.accessExpiry) : null
  return status === 'ACTIVE' && expiry && expiry.getTime() > Date.now()
})

const accessBadgeText = computed(() => {
  if (hasAccess.value && userInfo.value?.accessExpiry) {
    const expiry = new Date(userInfo.value.accessExpiry)
    return `Active until ${expiry.toLocaleString()}`
  }
  return 'No subscription'
})

// Initialize WebSocket connection
const initWebSocket = () => {
  try {
    // Get WebSocket URL from config
    const wsUrl = wsConfig.wsUrl
    ws = new WebSocket(wsUrl)

    ws.onopen = () => {
      isConnected.value = true
      console.log('WebSocket connected successfully')
      addSystemMessage('Connected to AI Assistant')
    }

    ws.onmessage = (event) => {
      handleMessage(event.data)
    }

    ws.onerror = (error) => {
      console.error('WebSocket error:', error)
      ElMessage.error('WebSocket connection error')
      isConnected.value = false
    }

    ws.onclose = () => {
      isConnected.value = false
      console.log('WebSocket connection closed')
      addSystemMessage('Disconnected')
    }
  } catch (error) {
    console.error('Failed to initialize WebSocket:', error)
    ElMessage.error('Unable to connect to server')
  }
}

// Handle received messages
let currentAiMessage = ''
const handleMessage = (data) => {
  if (data.startsWith('data:')) {
    const content = data.substring(5).replace(/[\r\n]+$/g, '')
    const normalized = content.trim()

    if (normalized === '[DONE]') {
      // Message reception complete
      if (currentAiMessage) {
        addMessage('ai', currentAiMessage)
        currentAiMessage = ''
      }
      loading.value = false
    } else {
      // Accumulate message content
      currentAiMessage += content
    }
  }
}

const refreshUserProfile = async () => {
  const token = localStorage.getItem('token')
  if (!token) {
    paywallVisible.value = true
    return
  }

  try {
    const freshUserInfo = await userApi.getUserInfo(token)
    const stored = localStorage.getItem('userInfo')
    const merged = { ...(stored ? JSON.parse(stored) : {}), ...freshUserInfo }
    userInfo.value = merged
    localStorage.setItem('userInfo', JSON.stringify(merged))
    if (!hasAccess.value) {
      paywallVisible.value = true
    }
  } catch (error) {
    console.error('Failed to refresh user info', error)
    paywallVisible.value = true
  }
}

const requireAccess = () => {
  if (!hasAccess.value) {
    ElMessage.warning('Please purchase access to use the AI Chatbox')
    paywallVisible.value = true
    return false
  }
  return true
}

const openPlanDialog = () => {
  planDialogVisible.value = true
}

const goToPurchase = (planKey) => {
  planDialogVisible.value = false
  paywallVisible.value = false
  router.push({ path: '/purchase', query: { plan: planKey } })
}

const storageKey = computed(() => {
  const identifier =
    userInfo.value?.id ||
    userInfo.value?.userId ||
    userInfo.value?.username ||
    userInfo.value?.email ||
    'guest'
  return `aiChatSessions_${identifier}`
})

const saveSessions = () => {
  localStorage.setItem(storageKey.value, JSON.stringify(sessions.value))
}

const getCurrentSession = () => sessions.value.find((session) => session.id === activeSessionId.value)

const ensureActiveSession = () => {
  if (!activeSessionId.value || !getCurrentSession()) {
    createNewSession()
  }
}

const createNewSession = () => {
  const newSession = {
    id: `session-${Date.now()}`,
    title: 'New Chat',
    createdAt: new Date().toISOString(),
    messages: []
  }

  sessions.value.unshift(newSession)
  activeSessionId.value = newSession.id
  messages.value = newSession.messages
  saveSessions()

  addWelcomeMessage()
}

const switchSession = (sessionId) => {
  if (sessionId === activeSessionId.value) {
    return
  }

  const session = sessions.value.find((item) => item.id === sessionId)
  if (session) {
    activeSessionId.value = session.id
    messages.value = session.messages
    nextTick(() => scrollToBottom())
  }
}

const loadSessions = () => {
  const storedSessions = localStorage.getItem(storageKey.value)
  if (storedSessions) {
    try {
      const parsed = JSON.parse(storedSessions) || []
      sessions.value = parsed.map((session) => ({
        id: session.id || `session-${Date.now()}`,
        title: session.title || 'New Chat',
        createdAt: session.createdAt || new Date().toISOString(),
        messages: session.messages || []
      }))
    } catch (error) {
      sessions.value = []
      console.error('Failed to parse stored sessions', error)
    }
  }

  if (sessions.value.length > 0) {
    activeSessionId.value = sessions.value[0].id
    messages.value = sessions.value[0].messages || []
  } else {
    createNewSession()
  }
}

const clearAllSessions = () => {
  sessions.value = []
  messages.value = []
  activeSessionId.value = ''
  localStorage.removeItem(storageKey.value)
  createNewSession()
}

const updateSessionTitle = (text) => {
  const session = getCurrentSession()
  if (session && (!session.title || session.title === 'New Chat')) {
    const trimmed = text.trim()
    session.title = trimmed.length > 30 ? `${trimmed.slice(0, 30)}...` : trimmed || 'New Chat'
    saveSessions()
  }
}

// Send message
const sendMessage = () => {
  if (!inputMessage.value.trim() || !isConnected.value || loading.value) {
    return
  }

  if (!requireAccess()) {
    return
  }

  const message = inputMessage.value.trim()

  // Add user message to interface
  addMessage('user', message)
  updateSessionTitle(message)

  // Send to backend
    const chatRequest = {
      type: 'chat',
      text: message,
      username: userInfo.value?.username || 'guest',
      sessionId: activeSessionId.value
    }

  ws.send(JSON.stringify(chatRequest))

  // Clear input and show loading state
  inputMessage.value = ''
  loading.value = true
}

// Send health advice request
const sendHealthAdviceRequest = () => {
  if (!isConnected.value || loading.value) {
    return
  }

  if (!requireAccess()) {
    return
  }

  // Add user request to interface
  addMessage('user', 'Get Health Advice')
  updateSessionTitle('Health Advice')

  // Send special request to backend
    const chatRequest = {
      type: 'health_advice',
      text: 'AI Health Advice',
      username: userInfo.value?.username || 'guest',
      sessionId: activeSessionId.value
    }

  ws.send(JSON.stringify(chatRequest))

  loading.value = true
}

const triggerFileSelect = () => {
  if (!isConnected.value) {
    ElMessage.warning('Please connect before uploading context files')
    return
  }

  if (!requireAccess()) {
    return
  }

  if (fileInputRef.value) {
    fileInputRef.value.value = ''
    fileInputRef.value.click()
  }
}

const handleFileChange = (event) => {
  const file = event.target.files?.[0]
  if (!file) {
    return
  }

  const isImage = file.type.startsWith('image/')
  const isText = file.type === 'text/plain' || file.name.toLowerCase().endsWith('.txt')

  if (!isImage && !isText) {
    ElMessage.error('Please upload a txt document or an image file')
    event.target.value = ''
    return
  }

  const reader = new FileReader()
  reader.onload = () => {
    const content = reader.result
    if (typeof content === 'string') {
      sendContextFile(file, content)
    }
    event.target.value = ''
  }

  if (isImage) {
    reader.readAsDataURL(file)
  } else {
    reader.readAsText(file)
  }
}

const sendContextFile = (file, content) => {
  if (!isConnected.value || loading.value) {
    ElMessage.warning('Please wait for the current response to finish before uploading')
    return
  }

  if (!requireAccess()) {
    return
  }

  const descriptor = file.name || 'Uploaded file'
  addMessage('user', `Uploaded context: ${descriptor}`)
  updateSessionTitle(descriptor)

  const chatRequest = {
    type: 'upload_context',
    text: 'Context upload',
    username: userInfo.value?.username || 'guest',
    sessionId: activeSessionId.value,
    fileName: descriptor,
    fileType: file.type || 'text/plain',
    fileContent: content
  }

  ws.send(JSON.stringify(chatRequest))
  loading.value = true
}

// Add message to list
const addMessage = (type, text) => {
  ensureActiveSession()

  messages.value.push({
    type,
    text,
    time: getCurrentTime()
  })

  saveSessions()

  // Scroll to bottom
  nextTick(() => {
    scrollToBottom()
  })
}

// Add system message
const addSystemMessage = (text) => {
  ensureActiveSession()

  messages.value.push({
    type: 'system',
    text,
    time: getCurrentTime()
  })

  saveSessions()
}

// Add welcome message if session is empty
const addWelcomeMessage = () => {
  const session = getCurrentSession()
  if (session && session.messages.length === 0) {
    addMessage('ai', 'Hello! I am your AI Health Assistant. You can ask me health questions, or click the "Get Health Advice" button to receive personalized advice based on your health data.')
  }
}

// Get current time
const getCurrentTime = () => {
  const now = new Date()
  return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`
}

const formatSessionTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  return Number.isNaN(date.getTime())
    ? ''
    : date.toLocaleString([], { hour: '2-digit', minute: '2-digit', month: 'short', day: 'numeric' })
}

// Scroll to bottom
const scrollToBottom = () => {
  if (messageListRef.value) {
    messageListRef.value.scrollTop = messageListRef.value.scrollHeight
  }
}

const currentSessionTitle = computed(() => getCurrentSession()?.title || 'AI Health Assistant')

// Close WebSocket connection
const closeWebSocket = () => {
  if (ws) {
    ws.close()
    ws = null
  }
}

// Lifecycle hooks
onMounted(async () => {
  // Get user info
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }

  await refreshUserProfile()

  loadSessions()

  // Initialize WebSocket
  initWebSocket()
})

onUnmounted(() => {
  closeWebSocket()
})
</script>

<style scoped>
.ai-chat-container {
  padding: 20px;
  height: calc(100vh - 120px);
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 16px;
}

.session-panel {
  background: white;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.session-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-bottom: 1px solid #ebeef5;
}

.session-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
}

.session-actions {
  display: flex;
  gap: 8px;
}

.access-tag {
  margin-left: 10px;
}

.session-list {
  flex: 1;
  padding: 8px 0;
}

.session-item {
  padding: 10px 16px;
  cursor: pointer;
  border-left: 3px solid transparent;
  transition: background-color 0.2s, border-color 0.2s;
}

.session-item:hover {
  background: #f5f7fa;
}

.session-item.active {
  background: #ecf5ff;
  border-color: #409eff;
}

.session-item-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}

.session-name {
  font-weight: 600;
  color: #303133;
}

.session-time {
  font-size: 12px;
  color: #909399;
}

.chat-card {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-card :deep(.el-card__body) {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0;
  overflow: hidden;
}

.chat-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: bold;
}

.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f7fa;
}

.message-item {
  display: flex;
  margin-bottom: 20px;
  animation: fadeIn 0.3s ease-in;
}

.message-item.user {
  flex-direction: row-reverse;
}

.message-item.system {
  justify-content: center;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  flex-shrink: 0;
}

.message-item.user .message-avatar {
  background-color: #409EFF;
  color: white;
  margin-left: 10px;
}

.message-item.ai .message-avatar {
  background-color: #67C23A;
  color: white;
  margin-right: 10px;
}

.message-content {
  max-width: 70%;
  display: flex;
  flex-direction: column;
}

.message-item.user .message-content {
  align-items: flex-end;
}

.message-item.system .message-content {
  align-items: center;
  max-width: 100%;
}

.message-text {
  padding: 12px 16px;
  border-radius: 8px;
  line-height: 1.6;
  word-wrap: break-word;
  white-space: pre-wrap;
}

.message-item.user .message-text {
  background-color: #409EFF;
  color: white;
}

.message-item.ai .message-text {
  background-color: white;
  color: #303133;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.message-item.system .message-text {
  background-color: #e4e7ed;
  color: #909399;
  font-size: 12px;
  padding: 6px 12px;
}

.message-time {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
  padding: 0 5px;
}

.input-area {
  padding: 20px;
  background-color: white;
  border-top: 1px solid #EBEEF5;
  display: flex;
  gap: 10px;
  align-items: center;
}

.health-advice-btn {
  flex-shrink: 0;
}

.message-input {
  flex: 1;
}

.file-input {
  display: none;
}

.input-actions {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: 4px;
}

.connection-status {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 10;
}

/* Paywall dialog styles */
.paywall-dialog :deep(.el-dialog__header) {
  padding: 30px 30px 20px;
  text-align: center;
  border-bottom: 1px solid #f0f0f0;
}

.paywall-dialog :deep(.el-dialog__body) {
  padding: 30px;
}

.paywall-dialog :deep(.el-dialog__footer) {
  padding: 20px 30px 30px;
  text-align: center;
  border-top: 1px solid #f0f0f0;
}

.paywall-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.paywall-icon {
  font-size: 48px;
  color: #409EFF;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.paywall-title {
  font-size: 22px;
  font-weight: 600;
  color: #303133;
  text-align: center;
}

.paywall-content {
  text-align: center;
  padding: 10px 0;
}

.paywall-text {
  color: #606266;
  font-size: 16px;
  line-height: 1.8;
  margin: 0 0 12px 0;
  text-align: center;
}

.paywall-subtitle {
  color: #909399;
  font-size: 14px;
  line-height: 1.6;
  margin: 0;
  text-align: center;
}

.paywall-footer {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.paywall-footer .el-button {
  font-size: 16px;
  padding: 12px 32px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
}

.paywall-footer .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

/* Plan dialog styles */
.plan-dialog :deep(.el-dialog__header) {
  padding: 30px 30px 20px;
  text-align: center;
  border-bottom: 1px solid #f0f0f0;
}

.plan-dialog :deep(.el-dialog__body) {
  padding: 30px;
}

.plan-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.plan-header-icon {
  font-size: 42px;
  color: #F56C6C;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.plan-header-title {
  font-size: 22px;
  font-weight: 600;
  color: #303133;
}

.plan-header-subtitle {
  font-size: 14px;
  color: #909399;
}

.plan-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.plan-card {
  border: 2px solid #ebeef5;
  border-radius: 12px;
  padding: 20px 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  transition: all 0.3s ease;
  background: white;
}

.plan-card:hover {
  border-color: #409EFF;
  box-shadow: 0 4px 16px rgba(64, 158, 255, 0.15);
  transform: translateY(-4px);
}

.plan-card-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.plan-name {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  text-align: center;
}

.plan-price {
  display: flex;
  align-items: baseline;
  gap: 2px;
}

.price-symbol {
  font-size: 18px;
  color: #409EFF;
  font-weight: 600;
}

.price-amount {
  font-size: 32px;
  font-weight: 700;
  color: #409EFF;
  line-height: 1;
}

.plan-duration {
  font-size: 13px;
  color: #606266;
  text-align: center;
  min-height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 8px;
}

.plan-button {
  width: 100%;
  font-weight: 600;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.plan-button:hover {
  transform: scale(1.05);
}

/* Typing indicator */
.typing-indicator {
  display: flex;
  gap: 4px;
  padding: 12px 16px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #67C23A;
  animation: typing 1.4s infinite;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 60%, 100% {
    opacity: 0.3;
    transform: scale(0.8);
  }
  30% {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Scrollbar styles */
.message-list::-webkit-scrollbar {
  width: 6px;
}

.message-list::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.message-list::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 3px;
}

.message-list::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>

