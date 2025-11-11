# 自动化测试实施指南
## Health Management System

---

## 📋 目录

1. [快速开始](#快速开始)
2. [前端测试](#前端测试)
3. [后端测试](#后端测试)
4. [集成测试](#集成测试)
5. [生成测试报告](#生成测试报告)
6. [常见问题](#常见问题)

---

## 🚀 快速开始

### 已完成的工作

我已经为你创建了以下测试基础设施：

**前端测试**:
- ✅ `tests/unit/auth.spec.js` - 认证工具测试
- ✅ `tests/unit/router.spec.js` - 路由配置测试
- ✅ `vitest.config.js` - Vitest配置文件
- ✅ 更新了 `package.json` 添加测试脚本

**后端测试**:
- ✅ `src/test/java/.../UserServiceTest.java` - 用户服务测试
- ✅ `src/test/java/.../SportInfoServiceTest.java` - 运动知识服务测试

**文档**:
- ✅ `AUTOMATED_TESTING_REPORT.md` - Markdown格式的测试报告
- ✅ `AUTOMATED_TESTING_REPORT_FOR_WORD.txt` - Word格式友好的报告

---

## 🎯 前端测试

### 步骤1: 安装测试依赖

```bash
cd health-frontend
npm install
```

这将安装以下测试相关包：
- vitest
- @vitest/ui
- @vue/test-utils
- jsdom
- @vitest/coverage-v8

### 步骤2: 运行测试

```bash
# 运行所有测试
npm run test

# 运行测试并生成覆盖率报告
npm run test:coverage

# 使用UI界面运行测试
npm run test:ui

# 监视模式（自动重新运行）
npm run test -- --watch
```

### 步骤3: 查看覆盖率报告

运行 `npm run test:coverage` 后：

1. 打开 `coverage/index.html` 在浏览器中
2. 你会看到每个文件的覆盖率详情
3. 红色表示未覆盖的代码行

### 步骤4: 添加更多测试

创建新的测试文件：

```javascript
// tests/unit/Login.spec.js
import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Login from '@/views/Login.vue'

describe('Login Component', () => {
  it('should render login form', () => {
    const wrapper = mount(Login)
    expect(wrapper.find('form').exists()).toBe(true)
  })

  it('should validate empty username', async () => {
    const wrapper = mount(Login)
    const loginButton = wrapper.find('button[type="submit"]')
    await loginButton.trigger('click')
    // 添加断言检查错误信息
  })
})
```

---

## 🔧 后端测试

### 步骤1: 确认Maven配置

查看 `pom.xml`，确保包含以下依赖（通常Spring Boot已包含）：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
```

### 步骤2: 运行测试

```bash
cd health-backend

# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=UserServiceTest

# 运行测试并生成覆盖率报告
mvn test jacoco:report

# 跳过测试构建项目
mvn clean install -DskipTests
```

### 步骤3: 查看覆盖率报告

运行 `mvn test jacoco:report` 后：

1. 打开 `target/site/jacoco/index.html`
2. 查看每个包和类的覆盖率
3. 点击类名查看具体的代码覆盖情况

### 步骤4: 添加更多测试

创建新的测试类：

```java
package com.shanzhu.health.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BodyServiceTest {
    
    @Autowired
    private IBodyService bodyService;
    
    @Test
    void testGetBodyInfo() {
        // 编写测试逻辑
    }
}
```

---

## 🔗 集成测试

### 使用Postman

1. **导出集合**:
   - 在Postman中创建API测试集合
   - File → Export → 选择Collection v2.1
   - 保存到 `postman-collections/` 目录

2. **使用Newman运行**:

```bash
# 安装Newman
npm install -g newman

# 运行集合
newman run postman-collections/User-API-Tests.json

# 使用环境变量
newman run postman-collections/User-API-Tests.json \
  --environment postman-collections/local.environment.json

# 生成HTML报告
newman run postman-collections/User-API-Tests.json \
  --reporters html \
  --reporter-html-export test-report.html
```

3. **在Postman中编写测试**:

```javascript
// Tests标签页
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has token", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.token).to.exist;
});

pm.test("Username is correct", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.username).to.eql("testuser");
});
```

---

## 📊 生成测试报告

### 为STD文档生成报告

#### 前端报告

1. **运行测试并生成覆盖率**:
```bash
cd health-frontend
npm run test:coverage
```

2. **截图内容**:
   - 终端输出（测试通过情况）
   - `coverage/index.html` 页面（覆盖率表格）

3. **复制到Word**:
   - 打开 `AUTOMATED_TESTING_REPORT_FOR_WORD.txt`
   - 复制"Frontend Generated Testing Report"部分
   - 粘贴到Word文档
   - 插入截图

#### 后端报告

1. **运行测试并生成报告**:
```bash
cd health-backend
mvn clean test
mvn jacoco:report
```

2. **截图内容**:
   - Maven测试输出
   - `target/site/jacoco/index.html` 页面

3. **复制到Word**:
   - 复制"Backend Generated Testing Report"部分
   - 粘贴到Word文档
   - 插入截图

---

## ❓ 常见问题

### Q1: 前端测试运行时出现 "Cannot find module" 错误

**解决方案**:
```bash
# 删除node_modules重新安装
rm -rf node_modules package-lock.json
npm install
```

### Q2: 后端测试失败，提示数据库连接错误

**解决方案**:
```java
// 在测试类上添加注解使用H2内存数据库
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceTest {
    // 测试代码
}
```

### Q3: 如何模拟HTTP请求？

**前端**:
```javascript
import { vi } from 'vitest'
import axios from 'axios'

vi.mock('axios')

it('should fetch user data', async () => {
  axios.get.mockResolvedValue({ data: { username: 'test' } })
  // 测试代码
})
```

**后端**:
```java
@Mock
private UserMapper userMapper;

@InjectMocks
private UserServiceImpl userService;

@Test
void testGetUser() {
    when(userMapper.selectById(1)).thenReturn(mockUser);
    // 测试代码
}
```

### Q4: 覆盖率太低怎么办？

**优先测试**:
1. 关键业务逻辑（认证、支付等）
2. 复杂算法和计算
3. 错误处理路径
4. 边界条件

**不用过度测试**:
- 简单的getter/setter
- 第三方库代码
- 配置文件
- 纯展示组件

### Q5: 测试运行很慢

**前端优化**:
```javascript
// vitest.config.js
export default defineConfig({
  test: {
    threads: true,  // 启用多线程
    isolate: false, // 禁用完全隔离
  }
})
```

**后端优化**:
```bash
# 并行运行测试
mvn test -T 4C  # 4个线程
```

---

## 📈 下一步

1. **扩展测试覆盖率**:
   - 为每个View组件添加测试
   - 为所有Service添加单元测试
   - 测试Controller层

2. **添加E2E测试**:
   - 安装Cypress
   - 编写关键用户流程测试
   - 自动化UI测试

3. **设置CI/CD**:
   - 配置GitHub Actions
   - 每次PR自动运行测试
   - 生成测试报告

4. **性能测试**:
   - 使用JMeter测试API性能
   - 监控响应时间
   - 压力测试

---

## 📚 参考资源

- [Vitest官方文档](https://vitest.dev/)
- [Vue Test Utils](https://test-utils.vuejs.org/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)

---

**需要帮助？**

如果遇到问题：
1. 查看错误日志
2. 参考上面的常见问题
3. 查阅官方文档
4. 联系团队成员

祝测试顺利！🎉

