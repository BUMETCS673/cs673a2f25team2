<template>
  <div class="register-form">
    <h1>User Registration</h1>
    <el-form :model="form" ref="form" :rules="rules" label-width="120px" class="form">
      <el-form-item label="Username" prop="username">
        <el-input v-model="form.username" placeholder="Enter username"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="form.password" type="password" placeholder="Enter password"></el-input>
      </el-form-item>
      <el-form-item label="Confirm Password" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" type="password" placeholder="Confirm password"></el-input>
      </el-form-item>
      
      <!-- <el-form-item label="Email" prop="email">
        <el-input v-model="form.email" placeholder="Enter email"></el-input>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" @click="submitForm">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import userApi from "@/api/userManage";

export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: 'Please enter username', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter password', trigger: 'blur' },
          { min: 6, message: 'Password must be at least 6 characters', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: 'Please confirm password', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ],
        // email: [
        //   { required: true, message: 'Please enter email', trigger: 'blur' },
        //   { type: 'email', message: 'Invalid email format', trigger: 'blur' }
        // ],
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // Construct request body
          const requestBody = {
            username: this.form.username,
            password: this.form.password,
            email: this.form.email
          };
          // Submit to backend
          userApi.register(requestBody).then(response=> {
            // Success message
            this.$message({
              message: response.message,
              type: "success"
            });
            this.$router.push('/login');
          });
        } else {
          return false;
        }
      });
    },
    
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.form.password) {
        callback(new Error('Passwords do not match'))
      } else {
        callback()
      }
    }
  }
}
</script>

<style scoped>
.register-form {
  margin: 50px auto;
  max-width: 500px;
  padding: 20px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  font-family: Arial, sans-serif;
}

.register-form h1 {
  margin-top: 0;
  text-align: center;
  font-size: 28px;
  font-weight: 500;
}

.form {
  margin-top: 30px;
}
</style>