<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="23">
          <!-- Search bar and add button with username & phone filters -->
          <el-input
            v-model="searchModel.username"
            placeholder="Username"
            clearable
          ></el-input>
          <el-input
            v-model="searchModel.phone"
            placeholder="Phone Number"
            clearable
          ></el-input>
          <el-button
            @click="getUserList"
            type="primary"
            round
            icon="el-icon-search"
            >Search</el-button
          >
        </el-col>
        <el-col :span="1">
          <el-button
            @click="openEditUi(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- Results table -->
    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column
          type="index"
          label="No."
          width="180"
        ></el-table-column>
        <el-table-column prop="id" label="User ID" width="180"></el-table-column>
        <el-table-column
          prop="username"
          label="Username"
          width="180"
        ></el-table-column>
        <el-table-column
          prop="phone"
          label="Phone"
          width="180"
        ></el-table-column>
        <el-table-column prop="email" label="Email"></el-table-column>
        <el-table-column label="Actions" width="180">
          <!-- Delete and edit buttons -->
          <template slot-scope="scope">
            <el-button
              @click="openEditUi(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteUser(scope.row)"
              type="danger"
              icon="el-icon-delete"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- Pagination -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- User edit dialog -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="userForm" ref="userFormRef" :rules="rules">
        <el-form-item
          label="Username"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          v-if="userForm.id == null || userForm.id == undefined"
          label="Password"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input
            type="password"
            v-model="userForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="Phone" :label-width="formLabelWidth">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="Email"
          prop="email"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="User Role" :label-width="formLabelWidth">
          <el-checkbox-group v-model="userForm.roleIdList" :max="1">
            <el-checkbox
              v-for="role in roleList"
              :label="role.roleId"
              :key="role.roleId"
              >{{ role.roleDesc }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveUser">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import userApi from "@/api/userManage";
import roleApi from "@/api/roleManage";
export default {
  data() {
    // Custom form validation rules
    var checkEmail = (rule, value, callback) => {
      var reg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
      if (!reg.test(value)) {
        return callback(new Error("Invalid email format"));
      }
    };

    return {
      // Label column width
      formLabelWidth: "135px",
      // Dialog hidden by default
      dialogFormVisible: false,
      userForm: {
        roleIdList: [],
      },
      title: "",
      total: 0,
      roleList: [],
      searchModel: {
        pageNo: 1,
        // Default page size
        pageSize: 10,
      },
      userList: [],
      // Form validation rules

      rules: {
        username: [
          { required: true, message: "Please enter a username", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "Length must be between 2 and 20 characters",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "Please enter a password", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "Length must be between 6 and 20 characters",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "Please enter an email", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    getAllRoleList() {
      roleApi.getAllRoleList().then((response) => {
        this.roleList = response.data;
      });
    },

    saveUser() {
      let isOk = true;
      // Trigger form validation
      this.$refs.userFormRef.validate((valid) => {
        // This only runs if validation fails
        isOk = valid;
      });
      if (isOk) {
        // Submit validated form to backend
        userApi.saveUser(this.userForm).then((response) => {
          this.$message({
            message: response.message,
            type: "success",
          });
          // Close dialog
          this.dialogFormVisible = false;
          // Refresh table data
          this.getUserList();
        });
      } else {
        console.log("error submit!!");
        return false;
      }
    },


    clearForm() {
      this.userForm = {
        roleIdList: [],
      };
      // Clear validation messages
      this.$refs.userFormRef.clearValidate();
    },
    handleSizeChange(pageSize) {
      // Update data
      this.searchModel.pageSize = pageSize;
      this.getUserList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getUserList();
    },

    getUserList() {
      userApi.getUserList(this.searchModel).then((response) => {
        this.userList = response.data.rows;
        this.total = response.data.total;
      });
    },
    openEditUi(id) {
      if (id == null) {
        this.title = "Add User";
      } else {
        this.title = "Edit User";
        // Fetch user data by ID
        userApi.getUserById(id).then((response) => {
          this.userForm = response.data;
        });
      }
      this.dialogFormVisible = true;
    },

    deleteUser(user) {
      this.$confirm(`Delete account ${user.username}?`, "Confirm", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "warning",
      })
        .then(() => {
          userApi.deleteUserById(user.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getUserList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Deletion cancelled",
          });
        });
    },
  },


  created() {
    // Fetch list on load
    this.getUserList();
    this.getAllRoleList();
  },
};
</script>
<style>
#search .el-input {
  width: 200px;
  margin-right: 20px;
}
.el-dialog .el-input {
  width: 43%;
}


/* Stylish card */
.el-card {
  width: 80%;
  margin: 20px auto;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}

/* Stylish table */
.el-table {
  width: 100%;
  border-collapse: collapse;
}

/* Stylish table header */
.el-table-column {
  background-color: lightblue;
  color: white;
  padding: 10px;
  border: 1px solid white;
  text-align: center;
}

/* Stylish table data */
.el-table-column[type="index"],
.el-table-column[prop="id"],
.el-table-column[prop="username"],
.el-table-column[prop="phone"],
.el-table-column[prop="email"] {
  background-color: white;
  color: black;
  padding: 10px;
  border: 1px solid lightblue;
  text-align: center;
}

/* Stylish table hover effect */
.el-table-column[type="index"]:hover,
.el-table-column[prop="id"]:hover,
.el-table-column[prop="username"]:hover,
.el-table-column[prop="phone"]:hover,
.el-table-column[prop="email"]:hover {
  background-color: lightpink;
  color: white;
}

/* Stylish button hover effect */
.el-button:hover {
  transform: scale(1.2);
}

/* Pagination CSS */
.el-pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px;
}

/* Pagination total label */
.el-pagination__total {
  color: #606266;
  margin-right: 20px;
}

/* Pagination page size */
.el-pagination__sizes {
  display: flex;
  align-items: center;
  margin-right: 20px;
}

/* Pagination page size selector */
.el-pagination__sizes .el-select {
  width: 100px;
}

/* Pagination previous button */
.el-pagination__prev {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Pagination previous icon */
.el-pagination__prev .el-icon {
  font-size: 20px;
  color: #409eff;
}

/* Pagination pager */
.el-pagination__pager {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Pagination page button */
.el-pagination__pager button {
  width: 30px;
  height: 30px;
  border-radius: 4px;
  border: none;
  background-color: white;
  color: #606266;
  margin: 2px;
  transition: all 0.3s ease-in-out;
}

/* Pagination page hover */
.el-pagination__pager button:hover {
  background-color: #409eff;
  color: white;
}

/* Pagination active button */
.el-pagination__pager button.is-active {
  background-color: #409eff;
  color: white;
}

/* Pagination next button */
.el-pagination__next {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Pagination next icon */
.el-pagination__next .el-icon {
  font-size: 20px;
  color: #409eff;
}

/* Pagination jump input */
.el-pagination__jump {
  display: flex;
  align-items: center;
}

/* Pagination jump label */
.el-pagination__jump label {
  color: #606266;
}

/* Pagination jump field */
.el-pagination__jump input {
  width: 50px;
  height: 30px;
  border-radius: 4px;
  border: none;
}
</style>