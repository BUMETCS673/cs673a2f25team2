<template>
  <router-view />
</template>

<script setup>
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  width: 100%;
  min-height: 100vh;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* Global scrollbar styles */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
}

/* Global card animation */
.el-card {
  transition: all 0.3s ease;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.el-card:hover {
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
  transform: translateY(-2px);
}

/* Global button animation */
.el-button {
  transition: all 0.3s ease;
  border-radius: 6px;
}

.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* Global input styles */
.el-input__wrapper {
  border-radius: 6px;
  transition: all 0.3s ease;
}

.el-input__wrapper:hover {
  box-shadow: 0 0 0 1px #c0c4cc inset;
}

/* Global table styles */
.el-table {
  border-radius: 8px;
  overflow: hidden;
}

.el-table th {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  font-weight: 600;
}

/* Global pagination styles */
.el-pagination {
  margin-top: 20px;
}
</style>



