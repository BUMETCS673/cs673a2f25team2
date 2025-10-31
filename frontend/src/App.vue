<script setup lang="ts">
// Minimal starter UI for Personal Health Management web app
</script>

<template>
  <main class="container">
    <h1>Personal Health Management</h1>
    <nav class="nav">
      <a href="/" class="brand">PHM</a>
      <router-link to="/records">记录列表</router-link>
      <router-link to="/records/new">新增记录</router-link>
      <span class="spacer"></span>
      <router-link to="/login">登录</router-link>
      <router-link to="/register">注册</router-link>
    </nav>
    <router-view />
  </main>
  
</template>

<style scoped>
  .container {
    padding: 2rem;
    font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
    line-height: 1.6;
  }
  .nav {
    display: flex;
    gap: 1rem;
    align-items: center;
    margin-bottom: 1rem;
  }
  .brand { font-weight: 600; }
  .spacer { flex: 1; }
</style>
