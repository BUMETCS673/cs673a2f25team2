<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { listHealthRecords, listHealthRecordsByUser, type HealthData, me } from '../api/client'

const records = ref<HealthData[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const userIdInput = ref<string>('')
const currentUser = ref<{ id: number; username: string; role: string } | null>(null)

async function loadAll() {
  loading.value = true
  error.value = null
  try {
    records.value = await listHealthRecords()
  } catch (e: any) {
    error.value = e?.message || '加载失败'
  } finally { loading.value = false }
}

async function loadMine() {
  loading.value = true
  error.value = null
  try {
    // 如果尚未获取到当前用户，则尝试拉取
    if (!currentUser.value) {
      currentUser.value = await me()
    }
    if (!currentUser.value) {
      throw new Error('未登录或无法获取当前用户')
    }
    const uid = Number(currentUser.value.id)
    userIdInput.value = String(uid)
    records.value = await listHealthRecordsByUser(uid)
  } catch (e: any) {
    error.value = e?.message || '加载失败'
  } finally { loading.value = false }
}

onMounted(async () => {
  // 优先加载我的记录；若权限不足则显示错误并允许管理员按钮加载全部
  try {
    await loadMine()
  } catch {
    await loadAll()
  }
})
</script>

<template>
  <section>
    <h2>健康记录</h2>
    <div class="actions">
      <button @click="loadAll">加载全部(管理员)</button>
      <span v-if="currentUser">当前用户：{{ currentUser.username }} (ID: {{ currentUser.id }})</span>
      <button @click="loadMine">加载我的记录</button>
      <a href="#" onclick="return false;"><router-link to="/records/new">新增记录</router-link></a>
    </div>
    <p v-if="loading">加载中...</p>
    <p v-if="error" style="color:red">{{ error }}</p>
    <table v-if="records.length" class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>用户ID</th>
          <th>类型</th>
          <th>数值</th>
          <th>单位</th>
          <th>备注</th>
          <th>时间</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="r in records" :key="r.id">
          <td>{{ r.id }}</td>
          <td>{{ r.userId }}</td>
          <td>{{ r.dataType }}</td>
          <td>{{ r.value }}</td>
          <td>{{ r.unit }}</td>
          <td>{{ r.notes }}</td>
          <td>{{ r.timestamp }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else>暂无数据</p>
  </section>
</template>

<style scoped>
.actions { display: flex; gap: .5rem; align-items: center; margin-bottom: .5rem }
.table { border-collapse: collapse; width: 100%; }
.table th, .table td { border: 1px solid #ddd; padding: .5rem; }
</style>
