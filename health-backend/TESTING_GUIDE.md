# 后端测试指南
## Health Management System Backend

---

## 📋 测试文件概览

### Service 层测试（使用 Mockito）
- **UserServiceTest.java** - 用户服务测试（5个测试）
  - 测试 CRUD 操作
  - 使用 @Mock 和 @InjectMocks
  
- **SportInfoServiceTest.java** - 运动知识服务测试（5个测试）
  - 测试运动信息的增删改查
  
- **BodyServiceTest.java** - 身体信息服务测试（7个测试）
  - 测试健康数据管理
  - 包含数据验证测试

### Entity 层测试
- **UserTest.java** - 用户实体测试（7个测试）
  - 测试 getter/setter
  - 验证邮箱和手机号格式
  
- **SportInfoTest.java** - 运动信息实体测试（6个测试）
  - 测试实体属性
  - 验证数据格式

**总计**: 30个测试

---

## 🚀 运行测试

### 基本命令

```bash
cd health-backend

# 1. 运行所有测试
mvn test

# 2. 清理并运行测试
mvn clean test

# 3. 运行特定测试类
mvn test -Dtest=UserServiceTest

# 4. 运行测试并生成覆盖率报告
mvn clean test jacoco:report

# 5. 跳过测试构建项目
mvn clean install -DskipTests
```

### 查看覆盖率报告

运行测试后，打开覆盖率报告：

```bash
# 报告位置
open target/site/jacoco/index.html

# 或者直接在浏览器打开
# file:///Users/alistairwong/cs673a2f25team2/health-backend/target/site/jacoco/index.html
```

---

## 📊 预期测试结果

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.shanzhu.health.service.UserServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Running com.shanzhu.health.service.SportInfoServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Running com.shanzhu.health.service.BodyServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Running com.shanzhu.health.entity.UserTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Running com.shanzhu.health.entity.SportInfoTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 覆盖率预期

| 模块 | 预期覆盖率 |
|------|-----------|
| Entity 层 | 80-90% |
| Service 层 | 60-70% |
| Controller 层 | 0% (未测试) |
| 整体 | 40-50% |

---

## 🔧 配置说明

### 测试数据库

我们使用 **H2 内存数据库**进行测试，这样不需要MySQL运行：

```yaml
# src/test/resources/application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
```

### JaCoCo 插件

在 `pom.xml` 中配置了 JaCoCo：

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```

---

## 🧪 测试类型说明

### 1. 单元测试 (Service层)

使用 **Mockito** 模拟依赖：

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserMapper userMapper;  // 模拟的依赖
    
    @InjectMocks
    private UserServiceImpl userService;  // 被测试的类
    
    @Test
    void testGetUserById() {
        // Arrange - 设置模拟行为
        when(userMapper.selectById(1)).thenReturn(mockUser);
        
        // Act - 执行测试
        User result = userService.getById(1);
        
        // Assert - 验证结果
        assertNotNull(result);
        verify(userMapper, times(1)).selectById(1);
    }
}
```

### 2. 实体测试

测试 POJO 对象的基本功能：

```java
class UserTest {
    @Test
    void testUserCreation() {
        User user = new User();
        user.setUsername("test");
        
        assertEquals("test", user.getUsername());
    }
}
```

---

## ❓ 常见问题

### Q1: 测试失败，提示找不到数据库

**原因**: H2数据库依赖未正确添加

**解决**: 确保 `pom.xml` 中有：
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

### Q2: Mockito 相关错误

**原因**: Spring Boot Test 已包含 Mockito，不需要额外添加

**解决**: 使用 `@ExtendWith(MockitoExtension.class)`

### Q3: 测试运行很慢

**解决**: 
```bash
# 并行运行测试
mvn test -T 4C

# 或禁用日志
# 在 application-test.yml 中设置
logging.level.root: ERROR
```

### Q4: 覆盖率报告没有生成

**解决**:
```bash
# 确保运行了 jacoco:report
mvn clean test jacoco:report

# 检查报告位置
ls -la target/site/jacoco/
```

### Q5: MyBatis-Plus 相关错误

**解决**: 在测试中使用 Mockito 模拟 Mapper，不需要实际数据库连接

---

## 📈 提高覆盖率建议

### 短期（当前）
- ✅ 已完成：Service 层单元测试
- ✅ 已完成：Entity 层测试
- ⏳ 可选：添加更多 Service 测试（MenuService, RoleService等）

### 中期（下周）
- Controller 层集成测试
- 使用 MockMvc 测试 REST API
- WebSocket 测试

### 长期
- E2E 集成测试
- 性能测试
- 安全测试

---

## 📝 截图指南

### 用于STD文档的截图

1. **运行测试**:
```bash
mvn clean test
```
截图：终端输出显示所有测试通过

2. **查看覆盖率**:
```bash
mvn test jacoco:report
open target/site/jacoco/index.html
```
截图：
- 整体覆盖率表格
- 包级别的覆盖率
- 类级别的覆盖率（选几个高覆盖率的）

3. **测试详情**:
在 IDE 中运行测试，截图测试结果面板

---

## 🎯 下一步

1. **运行测试**: `mvn clean test`
2. **生成报告**: `mvn jacoco:report`
3. **查看报告**: 打开 `target/site/jacoco/index.html`
4. **截图保存**: 用于STD文档
5. **创建 Testing Metrics**: 基于实际数据

---

**需要帮助?** 

如果测试失败，查看错误信息并参考常见问题部分。

祝测试顺利！🎉

