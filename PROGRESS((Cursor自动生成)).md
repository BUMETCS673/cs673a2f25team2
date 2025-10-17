## 项目进度评估（前后端分离单页应用）

### 概览
- 项目采用 Vue2 + Element-UI 的前端管理模板与后端 Spring Boot + MyBatis + SQLite。
- 后端已完成用户/角色/权限的数据模型、数据库初始化、基础接口与认证服务逻辑（含密码加密与 Google 登录）。
- 前端完成路由与权限守卫、Axios 请求封装与基础页面，但仍沿用模板的 token/接口返回结构，尚未与后端真实接口完全对接。

---

### 后端当前进度
- 配置与存储：
  - SQLite 数据源与 Hikari 连接池已配置，`schema.sql` 启动自动执行。
  - 表结构包含 `users`、`roles`、`permissions`、`user_roles`、`role_permissions`，并初始化默认角色与权限。
- 领域与持久化：
  - 实体：`User`、`Role`、`Permission`。
  - MyBatis XML/Mapper：提供用户/角色/权限的 CRUD、关联查询与分配/移除关系操作。
- 控制器与服务：
  - `AuthController`：注册、登录、Google 登录、修改密码、更新资料。
  - `UserController`：查询用户、按用户查询角色/权限、为用户分配/移除角色、删除用户。
  - `RoleController`：角色列表/详情、角色权限查询、创建/更新/删除角色、为角色分配/移除权限。
  - `AuthService`：密码加密校验、默认角色赋予、Google 登录绑定/创建、新旧密码校验、资料更新。
- 尚未实现/改进点：
  - 认证令牌：未下发/校验 JWT 或 Session，接口直接返回 `User`。
  - 统一响应：缺少 `{ code, data, message }` 的统一返回结构。
  - 跨域/安全：未见 CORS、安全与全局异常处理配置。

---

### 前端当前进度
- 技术栈与模板：Vue2 + Vue-Router + Vuex + Element-UI，采用 admin 模板结构。
- 路由与权限：
  - 常量路由已配置；权限守卫依赖 token 判断，登录后期望通过后端返回的 `menuList` 动态注入路由。
- 请求封装：
  - Axios 拦截器强依赖后端返回 `{ code: 20000, data: ... }` 判定成功状态，并在请求头注入 `X-Token`。
- 用户模块：
  - `api/user.js`、`store/modules/user.js` 仍沿用模板默认接口路径（如 `/user/login`、`/user/info`），与后端真实路径不一致。
- 本地开发：
  - `vue.config.js` 未配置 devServer 代理到后端 `/api`，`VUE_APP_BASE_API` 未显式设置，可能导致跨域或路径错误。
- 仓库内存在两个前端目录（`frontend` 与 `frontend/health-frontend`），建议统一一个为主，避免混淆。

---

### 前后端对接差异
- 路径不一致：
  - 后端：`/api/auth/login`、`/api/auth/register` 等。
  - 前端：模板默认 `/user/login`、`/user/info`、`/user/logout`。
- 响应结构不一致：
  - 后端直接返回 `User` 或错误 `Map`；前端要求 `code = 20000` 且数据置于 `data` 字段。
- 鉴权机制不一致：
  - 前端以 token 为核心（`X-Token`、路由守卫、`getInfo` 拉菜单）。
  - 后端尚未发放/校验 token。
- 网关/代理：
  - 前端未将 `/api` 代理至后端，也未固定 `baseURL` 指向 `/api`。

---

### 未来计划（精简建议，两条路线择一优先打通）
- 方案A（后端适配前端模板，前端改动最小）：
  - 增加 JWT 发放与校验；统一响应为 `{ code:20000, data, message }`；提供 `GET /api/user/info` 返回 `name/avatar/menuList`；支持 `X-Token` 或 `Authorization: Bearer`；开启 CORS/全局异常处理。
- 方案B（前端适配现有后端，后端改动最小）：
  - 放宽 Axios 拦截器对 `code=20000` 的强依赖，改以 HTTP 状态码判断；`baseURL` 指向 `/api` 或配置 devServer 代理；改写 `api/user.js` 指向 `/api/auth/...`；`store` 保存后端返回的用户信息或本地生成临时 token 以兼容守卫；`getInfo` 调整对接实际接口。

---

### 结论
- 后端：功能原型已可跑（用户/角色/权限 + 认证逻辑），待补鉴权令牌与统一响应。
- 前端：框架与守卫就绪，但 API 与返回结构未对齐后端，需按路线 A 或 B 快速统一，先打通登录与用户信息流程。


