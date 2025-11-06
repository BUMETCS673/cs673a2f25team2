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
    <el-card class="chat-card">
      <template #header>
        <div class="chat-header">
          <el-icon><ChatDotRound /></el-icon>
          <span>AI Health Assistant</span>
        </div>
      </template>

      <div class="chat-content">
        <!-- Message list -->
        <div class="message-list" ref="messageListRef">
          <div
              v-for="(message, index) in messages"
              :key="index"
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
              :disabled="loading || !isConnected"
              class="health-advice-btn"
          >
            Get Health Advice
          </el-button>
          <el-input
              v-model="inputMessage"
              placeholder="Enter your health question..."
              @keyup.enter="sendMessage"
              :disabled="loading || !isConnected"
              class="message-input"
          >
            <template #append>
              <el-button
                  :icon="Promotion"
                  @click="sendMessage"
                  :disabled="!inputMessage.trim() || loading || !isConnected"
              />
            </template>
          </el-input>
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
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import {
  ChatDotRound,
  User,
  Cpu,
  Promotion,
  CircleCheck,
  CircleClose
} from '@element-plus/icons-vue'
import wsConfig from '../config/websocket'

// WebSocket connection
let ws = null
const isConnected = ref(false)
const loading = ref(false)
const messages = ref([])
const inputMessage = ref('')
const messageListRef = ref(null)

// Current user info
const userInfo = ref(null)

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
    const content = data.substring(5).trim()

    if (content === '[DONE]') {
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

// Send message
const sendMessage = () => {
  if (!inputMessage.value.trim() || !isConnected.value || loading.value) {
    return
  }

  const message = inputMessage.value.trim()

  // Add user message to interface
  addMessage('user', message)

  // Send to backend
  const chatRequest = {
    type: 'chat',
    text: message,
    username: userInfo.value?.username || 'guest'
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

  // Add user request to interface
  addMessage('user', 'Get Health Advice')

  // Send special request to backend
  const chatRequest = {
    type: 'health_advice',
    text: 'AI Health Advice',
    username: userInfo.value?.username || 'guest'
  }

  ws.send(JSON.stringify(chatRequest))

  loading.value = true
}

// Add message to list
const addMessage = (type, text) => {
  messages.value.push({
    type,
    text,
    time: getCurrentTime()
  })

  // Scroll to bottom
  nextTick(() => {
    scrollToBottom()
  })
}

// Add system message
const addSystemMessage = (text) => {
  messages.value.push({
    type: 'system',
    text,
    time: getCurrentTime()
  })
}

// Get current time
const getCurrentTime = () => {
  const now = new Date()
  return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`
}

// Scroll to bottom
const scrollToBottom = () => {
  if (messageListRef.value) {
    messageListRef.value.scrollTop = messageListRef.value.scrollHeight
  }
}

// Close WebSocket connection
const closeWebSocket = () => {
  if (ws) {
    ws.close()
    ws = null
  }
}

// Lifecycle hooks
onMounted(() => {
  // Get user info
  const userInfoStr = localStorage.getItem('userInfo')
  if (userInfoStr) {
    userInfo.value = JSON.parse(userInfoStr)
  }

  // Initialize WebSocket
  initWebSocket()

  // Add welcome message
  addMessage('ai', 'Hello! I am your AI Health Assistant. You can ask me health questions, or click the "Get Health Advice" button to receive personalized advice based on your health data.')
})

onUnmounted(() => {
  closeWebSocket()
})
</script>

<style scoped>
.ai-chat-container {
  padding: 20px;
  height: calc(100vh - 120px);
  display: flex;
  flex-direction: column;
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

.connection-status {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 10;
}

/* 打字指示器 */
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

/* 滚动条样式 */
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

