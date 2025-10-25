# 分支管理说明

## 分支结构

### 主要分支
- **main** - 生产分支，包含稳定的发布版本
- **dev** - 主开发分支，所有功能开发的集成分支
- **test** - 测试分支，用于集成测试和功能验证

### 功能分支
- **feature/xxx** - 功能开发分支，从dev分支创建
- **hotfix/xxx** - 紧急修复分支，从main分支创建

## 分支变更记录

### 2025年1月 - 分支清理和重组

#### 删除的分支
1. **旧Test分支** (已删除)
   - **删除原因**: 该分支创建于9月15日，内容过时且不符合当前项目需求
   - **删除时间**: 2025年1月
   - **替代方案**: 创建新的test分支用于当前项目的测试需求

2. **Dependabot分支** (已删除)
   - 删除的分支:
     - `dependabot/npm_and_yarn/frontend/health-frontend/axios-0.30.2`
     - `dependabot/npm_and_yarn/frontend/path-to-regexp-3.3.0`
     - `dependabot/npm_and_yarn/frontend/serve-static-1.16.0`
     - `dependabot/npm_and_yarn/frontend/vue-3.0.0`
   - **删除原因**: 项目为演示性质，不需要自动安全更新，手动管理依赖更合适

#### 新建的分支
1. **dev分支** (新建)
   - **用途**: 主开发分支，所有功能开发在此集成
   - **创建时间**: 2025年1月
   - **基于**: main分支

2. **test分支** (新建)
   - **用途**: 专门用于功能测试、集成测试和验证
   - **创建时间**: 2025年1月
   - **基于**: dev分支
   - **说明**: 替代旧的Test分支，提供更清晰的测试环境

## 开发工作流

### 功能开发流程
```bash
# 1. 从dev创建功能分支
git checkout dev
git pull origin dev
git checkout -b feature/功能名称

# 2. 开发完成后合并回dev
git checkout dev
git merge feature/功能名称
git push origin dev

# 3. 删除功能分支
git branch -d feature/功能名称
```

### 测试流程
```bash
# 1. 将dev分支的更改合并到test分支
git checkout test
git merge dev
git push origin test

# 2. 在test分支进行测试验证
# 3. 测试通过后，将test分支合并到main
git checkout main
git merge test
git push origin main
```

## 当前活跃分支状态
- ✅ **main** - 稳定版本
- ✅ **dev** - 主开发分支
- ✅ **test** - 测试分支
- 🔄 **zhiyao-feature** - 个人功能分支（待评估是否保留）

## 注意事项
1. 所有新功能开发都应从dev分支创建
2. 直接在main分支上的提交应该避免
3. test分支用于验证功能的完整性和稳定性
4. 定期清理已合并的功能分支