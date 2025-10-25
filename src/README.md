# 项目源代码结构 / Project Source Code Structure

## 📁 目录结构 / Directory Structure

```
/src/
├── back/           # 后端代码 / Backend Code
│   ├── main/       # 主要源代码 / Main Source Code
│   │   ├── java/   # Java源文件 / Java Source Files
│   │   └── resources/ # 资源文件 / Resource Files
│   └── test/       # 测试代码 / Test Code
│       └── java/   # Java测试文件 / Java Test Files
│
└── front/          # 前端代码 / Frontend Code
    ├── health-frontend/  # 健康管理前端子项目 / Health Management Frontend Subproject
    ├── package.json      # 前端依赖配置 / Frontend Dependencies
    ├── src/             # Vue.js源代码 / Vue.js Source Code
    └── ...              # 其他前端配置文件 / Other Frontend Config Files
```

## 🔧 技术栈 / Technology Stack

### 后端 / Backend
- **框架**: Spring Boot
- **数据库**: SQLite
- **构建工具**: Maven
- **ORM**: MyBatis

### 前端 / Frontend
- **框架**: Vue.js
- **UI库**: Element UI
- **构建工具**: Vue CLI
- **包管理**: npm/yarn

## 📝 说明 / Notes

1. **后端代码** 位于 `back/` 目录，遵循标准的Maven项目结构
   **Backend code** is located in `back/` directory, following standard Maven project structure

2. **前端代码** 位于 `front/` 目录，包含Vue.js应用和相关配置
   **Frontend code** is located in `front/` directory, containing Vue.js application and related configurations

3. 此结构便于**前后端分离开发**和**统一项目管理**
   This structure facilitates **frontend-backend separation** and **unified project management**

## 🚀 开发指南 / Development Guide

### 后端开发 / Backend Development
```bash
cd src/back
# 运行Spring Boot应用 / Run Spring Boot application
mvn spring-boot:run
```

### 前端开发 / Frontend Development
```bash
cd src/front
# 安装依赖 / Install dependencies
npm install --legacy-peer-deps
# 启动开发服务器 / Start development server
npm run serve
```