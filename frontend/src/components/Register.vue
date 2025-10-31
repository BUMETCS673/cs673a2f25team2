<script setup lang="ts">
import { ref } from 'vue'
import { register } from '../api/client'

const username = ref('user1')
const password = ref('password')
const role = ref('user')
const error = ref<string | null>(null)
const success = ref<string | null>(null)

async function onSubmit() {
  error.value = null
  success.value = null
  try {
    await register(username.value, password.value, role.value)
    success.value = '注册成功，您可以前往登录'
  } catch (e: any) {
    error.value = e?.message || '注册失败'
  }
}
</script>

<template>
  <section>
    <h2>注册</h2>
    <form @submit.prevent="onSubmit">
      <label>
        用户名
        <input v-model="username" placeholder="用户名" />
      </label>
      <label>
        密码
        <input type="password" v-model="password" placeholder="密码" />
      </label>
      <label>
        角色
        <select v-model="role">
          <option value="user">用户</option>
          <option value="admin">管理员</option>
        </select>
      </label>
      <button type="submit">注册</button>
    </form>
    <p v-if="success" style="color:green">{{ success }}</p>
    <p v-if="error" style="color:red">{{ error }}</p>
  </section>
</template>

<style scoped>
form { display: grid; gap: .5rem; max-width: 320px; }
label { display: grid; gap: .25rem; }
</style>

