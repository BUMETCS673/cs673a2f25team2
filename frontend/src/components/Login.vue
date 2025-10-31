<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api/client'

const username = ref('admin')
const password = ref('admin123')
const error = ref<string | null>(null)
const success = ref<string | null>(null)
const router = useRouter()

async function onSubmit() {
  error.value = null
  success.value = null
  try {
    const token = await login(username.value, password.value)
    success.value = '登录成功，令牌已保存'
    router.push('/records')
  } catch (e: any) {
    error.value = e?.message || '登录失败'
  }
}
</script>

<template>
  <section>
    <h2>登录</h2>
    <form @submit.prevent="onSubmit">
      <label>
        用户名
        <input v-model="username" placeholder="用户名" />
      </label>
      <label>
        密码
        <input type="password" v-model="password" placeholder="密码" />
      </label>
      <button type="submit">登录</button>
    </form>
    <p v-if="success" style="color:green">{{ success }}</p>
    <p v-if="error" style="color:red">{{ error }}</p>
  </section>
</template>

<style scoped>
form { display: grid; gap: .5rem; max-width: 320px; }
label { display: grid; gap: .25rem; }
</style>
