<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <svg class="logo-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="#f86a33" stroke-width="2" stroke-linejoin="round"/>
          <path d="M2 17L12 22L22 17" stroke="#f86a33" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <path d="M2 12L12 17L22 12" stroke="#f86a33" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <circle cx="12" cy="7" r="1.5" fill="#f86a33"/>
        </svg>
        <span class="app-title">AI Personal Health Management System</span>
      </div>

      <br /><br />

      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
      >
        Sign In
      </el-button>

      <div>
        <el-button
          type="primary"
          style="width: 100%; margin-bottom: 30px; margin-left: 0"
          @click.native.prevent="handleRegister"
        >
          Register
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate';

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter a valid username'));
      } else {
        callback();
      }
    };

    const validatePassword = (rule, value, callback) => {
      if (!value || value.length < 6) {
        callback(new Error('Password must be at least 6 characters'));
      } else {
        callback();
      }
    };

    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    };
  },

  watch: {
    $route: {
      handler(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },

  methods: {
    showPwd() {
      this.passwordType = this.passwordType === 'password' ? '' : 'password';
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },

    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch('user/login', this.loginForm)
            .then(() => {
              localStorage.setItem('username', this.loginForm.username);
              this.$router.push({ path: this.redirect || '/' });
            })
            .catch(() => {})
            .finally(() => {
              this.loading = false;
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleRegister() {
      this.$router.push({ path: '/register' });
    }
  }
};
</script>

<style lang="scss">
$bg: #2d3a4b;
$dark_gray: #3472a6;

.login-container {
  min-height: 100vh;
  width: 100%;
  background-color: $bg;
  display: flex;
  justify-content: center;
  align-items: center;

  background-image: url('~@/assets/healthy-people-carrying-different-icons_53876-66139.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;

  .login-form {
    position: relative;
    width: 400px;
    max-width: 100%;
    opacity: 0.95;
    padding: 80px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
    backdrop-filter: saturate(180%) blur(6px);
  }

  .title-container {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .logo-icon {
    width: 40px;
    height: 40px;
    margin-right: 10px;
  }

  .app-title {
    color: #f86a33;
    font-size: 20px;
    font-weight: 600;
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .el-form-item {
    margin-bottom: 20px;
  }

  .el-input {
    width: 100%;
    border: none;
    border-radius: 5px;
    background-color: #f7f7f7;

    input {
      padding: 15px;
      color: $dark_gray;

      &::placeholder {
        color: #9aa5b1;
      }
    }
  }

  .el-button {
    height: 50px;
    font-size: 16px;
    background-color: $dark_gray;
    border: none;

    &:hover,
    &:focus {
      background-color: darken($dark_gray, 10%);
    }
  }
}
</style>