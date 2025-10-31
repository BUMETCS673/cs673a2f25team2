<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { createHealthRecord, type HealthData, me } from '../api/client'

const form = ref<HealthData>({
  userId: 0,
  dataType: 'weight',
  value: 70.0,
  unit: 'kg',
  notes: '备注',
})
const success = ref<string | null>(null)
const error = ref<string | null>(null)

onMounted(async () => {
  const u = await me()
  if (u?.id) {
    form.value.userId = Number(u.id)
  }
})

async function onSubmit() {
  success.value = null
  error.value = null
  try {
    const created = await createHealthRecord(form.value)
    success.value = `创建成功，记录ID: ${created.id}`
  } catch (e: any) {
    error.value = e?.message || '创建失败'
  }
}
</script>

<template>
  <section>
    <h2>新增健康记录</h2>
    <form @submit.prevent="onSubmit" class="form">
      <label>用户ID <input type="number" v-model.number="form.userId" disabled /></label>
      <label>类型 <input v-model="form.dataType" /></label>
      <label>数值 <input type="number" step="0.1" v-model.number="form.value" /></label>
      <label>单位 <input v-model="form.unit" /></label>
      <label>备注 <input v-model="form.notes" /></label>
      <button type="submit">提交</button>
    </form>
    <p v-if="success" style="color:green">{{ success }}</p>
    <p v-if="error" style="color:red">{{ error }}</p>
  </section>
</template>

<style scoped>
.form { display: grid; gap: .5rem; max-width: 400px; }
label { display: grid; gap: .25rem; }
</style>
