<!-- Search bar and Add button; search inputs for username and phone number -->
<template>
    <div>
  
      <!-- 结果列表 -->
      <el-card>
        <el-table :data="bodyList" stripe style="width: 100%">
          <el-table-column type="index" label="No." width="50"></el-table-column>
          <el-table-column prop="date" label="Uploaded At" width="100" :formatter="formatDate"></el-table-column>
          <el-table-column prop="name" label="Name" width="80"></el-table-column>
          <el-table-column prop="age" label="Age" width="80"></el-table-column>

          <el-table-column
            prop="gender"
            label="Gender"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="height"
            label="Height (cm)"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="weight"
            label="Weight (kg)"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="bloodSugar"
            label="Blood Sugar"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="bloodPressure"
            label="Blood Pressure"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="weight"
            label="Weight (kg)"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="bloodLipid"
            label="Blood Lipids"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="heartRate"
            label="Heart Rate (bpm)"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="vision"
            label="Vision"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="sleepDuration"
            label="Sleep Duration"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="sleepQuality"
            label="Sleep Quality"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="heartRate"
            label="Heart Rate (bpm)"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="heartRate"
            label="Heart Rate (bpm)"
            width="80"
          ></el-table-column>

          <el-table-column prop="smoking" label="Smoking" width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.smoking">Yes</span>
              <span v-else>No</span>
            </template></el-table-column
          >

          <el-table-column prop="drinking" label="Drinking" width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.smoking">Yes</span>
              <span v-else>No</span>
            </template></el-table-column
          >

          <el-table-column prop="exercise" label="Exercise" width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.smoking">Yes</span>
              <span v-else>No</span>
            </template></el-table-column
          >

          <el-table-column
            prop="foodTypes"
            label="Preferred Foods"
            width="80"
          ></el-table-column>

          <el-table-column
            prop="waterConsumption"
            label="Water Intake (ml)"
            width="80"
          ></el-table-column>

          <el-table-column label="Actions" width="180">
            <!-- 删除和修改按钮 -->
            <template slot-scope="scope">
              <el-button
                @click="openEditUi(scope.row.notesid)"
                type="primary"
                icon="el-icon-edit"
                circle
              ></el-button>
              <el-button
                @click="deleteUserBody(scope.row)"
                type="danger"
                icon="el-icon-delete"
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
  
      <!-- 分页功能 -->
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
  
      <!-- 用户编辑信息弹出框 -->
      <el-dialog
        @close="clearForm"
        :title="title"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="bodyForm" ref="bodyFormRef">
          <el-form-item label="Nickname" prop="name" :label-width="formLabelWidth">
            <el-input v-model="bodyForm.name" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="Age" prop="age" :label-width="formLabelWidth">
            <el-input v-model="bodyForm.age" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="Gender" prop="gender" :label-width="formLabelWidth">
            <el-input v-model="bodyForm.gender" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Height (cm)"
            prop="height"
            :label-width="formLabelWidth"
          >
            <el-input v-model="bodyForm.height" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Weight (kg)"
            prop="weight"
            :label-width="formLabelWidth"
          >
            <el-input v-model="bodyForm.weight" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Blood Sugar"
            prop="bloodSugar"
            :label-width="formLabelWidth"
          >
            <el-input v-model="bodyForm.bloodSugar" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Blood Pressure"
            prop="bloodPressure"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="bodyForm.bloodPressure"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Blood Lipids"
            prop="bloodLipid"
            :label-width="formLabelWidth"
          >
            <el-input v-model="bodyForm.bloodLipid" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Heart Rate (bpm)"
            prop="weight"
            :label-width="formLabelWidth"
          >
            <el-input v-model="bodyForm.heartRate" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="Vision" prop="vision" :label-width="formLabelWidth">
            <el-input v-model="bodyForm.vision" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Sleep Duration (h)"
            prop="sleepDuration"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="bodyForm.sleepDuration"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Sleep Quality"
            prop="sleepQuality"
            :label-width="formLabelWidth"
          >
            <el-radio-group v-model="bodyForm.sleepQuality">
              <el-radio :label="1">Good</el-radio>
              <el-radio :label="2">Average</el-radio>
              <el-radio :label="3">Poor</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item
            label="Smoking"
            prop="smoking"
            :label-width="formLabelWidth"
          >
            <el-switch
              v-model="bodyForm.smoking"

            ></el-switch>
          </el-form-item>

          <el-form-item
            label="Drinking"
            prop="drinking"
            :label-width="formLabelWidth"
          >
            <el-switch
              v-model="bodyForm.drinking"

            ></el-switch>
          </el-form-item>

          <el-form-item
            label="Exercise"
            prop="exercise"
            :label-width="formLabelWidth"
          >
            <el-switch v-model="bodyForm.exercise"></el-switch>
          </el-form-item>

          <el-form-item
            label="Preferred Foods"
            prop="foodTypes"
            :label-width="formLabelWidth"
          >
            <el-select
              v-model="bodyForm.foodTypes"
              placeholder="Please select frequently consumed food types"
            >
              <el-option label="Vegetables" value="蔬菜"></el-option>
              <el-option label="Fruits" value="水果"></el-option>
              <el-option label="Meat" value="肉类"></el-option>
              <el-option label="Fish" value="鱼类"></el-option>
              <el-option label="Legumes" value="豆类"></el-option>
              <el-option label="Grains" value="谷物"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            label="Water Intake (ml)"
            prop="waterConsumption"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="bodyForm.waterConsumption"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
  
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="updateBody">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import userApi from "@/api/userManage";
  export default {
    data() {
      return {
        bodyForm: {}, //初始化为一个空对象
        bodyList: [],
        //左边宽度
        formLabelWidth: "135px",
        //设置默认值不可见
        dialogFormVisible: false,
        title: "",
        total: 0,
        searchModel: {
          pageNo: 1,
          // 默认显示数量
          pageSize: 10,
        },
        //表单规则配置
  
        rules: {
          bodyType: [
            { required: true, message: "Please enter the exercise type", trigger: "blur" },
          ],
        },
      };
    },
  
    methods: {
      updateBody() {
        let isOk = true;
        //触发表单的验证
        this.$refs.bodyFormRef.validate((valid) => {
          // 这边只有校验失败的时候才会进来,在外面定义一个 isok,校验失败会将他改成 false
          isOk = valid;
        });
  
        if (isOk) {
          //提交验证给后台
          userApi.updateUserBody(this.bodyForm).then((response) => {
            //成功提示
            this.$message({
              message: response.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新表格数据
            this.getUserBodyList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      },
  
      //清理表单数据
      clearForm() {
        this.bodyForm = {};
        //清除表单校验的提示信息
        this.$refs.bodyFormRef.clearValidate();
      },
      handleSizeChange(pageSize) {
        //数据更新
        this.searchModel.pageSize = pageSize;
        this.getUserBodyList();
      },
      handleCurrentChange(pageNo) {
        this.searchModel.pageNo = pageNo;
        this.getUserBodyList();
      },
  
      //用于查询用户列表
      getUserBodyList() {
        userApi.getUserBodyList(this.searchModel).then((response) => {
          console.log(response);
          this.bodyList = response.data.rows;
          // 将查询结果中的 total 属性赋值给 total
          this.total = response.data.total;
          console.log(this.bodyList);
        });
      },
  
      openEditUi(notesid) {
        console.log(notesid)
        this.title = "Edit Health Information";
        //根据id查询用户数据
        userApi.getUserBodyById(notesid).then((response) => {
          this.bodyForm = response.data;
        });

        this.dialogFormVisible = true;
      },

      deleteUserBody(body) {
        this.$confirm(`Delete health record for ${body.name}?`, "Confirmation", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        })
          .then(() => {
            userApi.deleteUserBodyById(body.notesid).then((response) => {
              this.$message({
                type: "success",
                message: response.message,
              });
              this.getUserBodyList();
              
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "Deletion canceled",
            });
          });
      },

      formatDate(row, column, cellValue, index) {
    // 将时间戳转换成日期字符串
    const date = new Date(cellValue);
    return date.toLocaleDateString();
  }
    },
  
    //加载时就查询一次
    created() {
      this.getUserBodyList();
    },
  };
  </script>
  <style scoped>
  #search .el-input {
    width: 200px;
    margin-right: 20px;
  }
  .el-dialog .el-input {
    width: 43%;
  }
  
  
  /* Styled card */
  .el-card {
    width: 80%;
    margin: 20px auto;
    border-radius: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    overflow: hidden;
  }
  
  /* Styled table */
  .el-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  /* Styled table headers */
  .el-table-column {
    background-color: lightblue;
    color: white;
    padding: 10px;
    border: 1px solid white;
    text-align: center;
  }
  
  /* Styled table cells */
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
  
  /* Styled table hover effect */
  .el-table-column[type="index"]:hover,
  .el-table-column[prop="id"]:hover,
  .el-table-column[prop="username"]:hover,
  .el-table-column[prop="phone"]:hover,
  .el-table-column[prop="email"]:hover {
    background-color: lightpink;
    color: white;
  }
  
  /* Button hover effect */
  .el-button:hover {
    transform: scale(1.2);
  }
  
  /* Pagination styles */
  .el-pagination {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 20px;
  }
  
  /* Pagination total */
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
  
  /* Pagination size selector */
  .el-pagination__sizes .el-select {
    width: 100px;
  }
  
  /* Pagination prev button */
  .el-pagination__prev {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }
  
  /* Pagination prev icon */
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
  
  /* Pagination pager button */
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
  
  /* Pagination pager hover */
  .el-pagination__pager button:hover {
    background-color: #409eff;
    color: white;
  }
  
  /* Pagination active page */
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
  
  /* Pagination jump */
  .el-pagination__jump {
    display: flex;
    align-items: center;
  }
  
  /* Pagination jump label */
  .el-pagination__jump label {
    color: #606266;
  }
  
  /* Pagination jump input */
  .el-pagination__jump input {
    width: 50px;
    height: 30px;
    border-radius: 4px;
    border: none;
  }
  </style>