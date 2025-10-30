<template>
  <div class="register-form">
    <h1>Change Password</h1>
    <el-form
      :model="form"
      ref="form"
      :rules="rules"
      label-width="80px"
      class="form"
    >
      <el-form-item label="Username" prop="username">
        <el-input v-model="form.username" placeholder="Please enter username"></el-input>
      </el-form-item>
      <el-form-item label="Current Password" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="Please enter current password"
        ></el-input>
      </el-form-item>
      <el-form-item label="New Password" prop="newPassword">
        <el-input
          v-model="form.newPassword"
          type="password"
          placeholder="Please enter new password"
        ></el-input>
      </el-form-item>
      <el-form-item label="Confirm Password" prop="confirmNewPassword">
        <el-input
          v-model="form.confirmNewPassword"
          type="password"
          placeholder="Please re-enter new password"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">Submit</el-button>
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
        username: "",
        password: "",
        newPassword: "",
        confirmNewPassword: "",
        id: null,
      },
      rules: {
        username: [
          { required: true, message: "Please enter username", trigger: "blur" },
        ],
        password: [
          { required: true, message: "Please enter current password", trigger: "blur" },
          { min: 6, message: "Password must be at least 6 characters", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "Please enter new password", trigger: "blur" },
          { min: 6, message: "Password must be at least 6 characters", trigger: "blur" },
        ],
        confirmNewPassword: [
          { required: true, message: "Please confirm password", trigger: "blur" },
          { validator: this.validateConfirmNewPassword, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // Construct request body
          const requestBody = {
            id: this.id,
            username: this.form.username,
            password: this.form.password,
            newPassword: this.form.newPassword,
          };
          // Submit verification to backend
          userApi
            .changePassword(requestBody)
            .then((response) => {
              // Success message
              this.$message({
                message: response.message,
                type: "success",
              });
              this.$router.replace('/login');
            })
            
            .catch((error) => {
              // Error message
              this.$message({
                message: error.response.data.message,
                type: "error",
              });
            });
        } else {
          // Form validation failed
          return false;
        }
      });
    },
    validateConfirmNewPassword(rule, value, callback) {
      if (value !== this.form.newPassword) {
        callback(new Error("Passwords do not match"));
      } else {
        callback();
      }
    },

     getUserId() {
      userApi
        .getUserId()
        .then((response) => {
          console.log(response)
          // Successfully retrieved user ID
          this.id = response.data.userId;
          // Other logic
        })
        .catch((error) => {
          // Handle error case
        });
    },


    
  },

  created(){
    this.getUserId();
  }
};
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
  
  