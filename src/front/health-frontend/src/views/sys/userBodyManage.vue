<!-- Search bar and buttons; inputs for name and ID -->
<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="23">
          <!-- v-model绑定组件实现双向数据绑定，页面上用户输入的值会同步更新到该属性中 -->
          <el-input
            v-model="searchModel.name"
            placeholder="Enter nickname"
            clearable
          ></el-input>
          <el-input
            v-model="searchModel.id"
            placeholder="Enter ID"
            clearable
          ></el-input>
          <el-button
            @click="getBodyList"
            type="primary"
            round
            icon="el-icon-search"
            >Search</el-button
          >
        </el-col>
      </el-row>
    </el-card>

    <!-- Results List -->
    <el-card>
      <el-table :data="bodyList" stripe style="width: 100%">
        <el-table-column type="index" label="No." width="80"></el-table-column>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="Nickname" width="80"></el-table-column>
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
          label="Water Intake"
          width="80"
        ></el-table-column>

        <el-table-column label="Actions" width="180">
          <!-- Edit/Delete buttons -->
          <template slot-scope="scope">
            <el-button
              @click="openEditUi(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteBody(scope.row)"
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

    <!-- User Health Info Dialog -->
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
            placeholder="Select frequently consumed food types"
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
      bodyForm: {}, // initialize as an empty object
      bodyList: [],
      // label width
      formLabelWidth: "135px",
      // dialog hidden by default
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        // default page size
        pageSize: 10,
      },
      // form validation rules
      rules: {
        bodyType: [
          { required: true, message: "Please enter exercise type", trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    updateBody() {
      let isOk = true;
      // trigger form validation
      this.$refs.bodyFormRef.validate((valid) => {
        // only enters when validation fails; use isOk flag and set to false on failure
        isOk = valid;
      });

      if (isOk) {
        // submit to backend
        userApi.updateBody(this.bodyForm).then((response) => {
          // success message
          this.$message({
            message: response.message,
            type: "success",
          });
          // close dialog
          this.dialogFormVisible = false;
          // refresh table data
          this.getBodyList();
        });
      } else {
        console.log("error submit!!");
        return false;
      }
    },

    // clear form data
    clearForm() {
      this.bodyForm = {};
      // clear validation messages
      this.$refs.bodyFormRef.clearValidate();
    },
    handleSizeChange(pageSize) {
      // update data
      this.searchModel.pageSize = pageSize;
      this.getBodyList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getBodyList();
    },

    // fetch body list
    getBodyList() {
      userApi.getBodyList(this.searchModel).then((response) => {
        console.log(response);

        this.bodyList = response.data.rows;
        // assign total from response
        this.total = response.data.total;
        console.log(this.bodyList);
      });
    },

    openEditUi(id) {
      this.title = "Edit Health Information";
      // fetch by id
      userApi.getBodyById(id).then((response) => {
        this.bodyForm = response.data;
      });

      this.dialogFormVisible = true;
    },

    deleteBody(body) {
      this.$confirm(`Delete health record for ${body.name}?`, "Confirm", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "warning",
      })
        .then(() => {
          userApi.deleteBodyById(body.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getBodyList();
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

  // fetch once on load
  created() {
    this.getBodyList();
    console.log(this.bodyList);
  },
};
</script>
</style scoped>
#search .el-input {
  width: 200px;
  margin-right: 20px;
}
.el-dialog .el-input {
  width: 43%;
}

/* Aesthetic CSS styles */
/* body {
    background: linear-gradient(to right, lightblue, lightpink);
    margin: 0;
    padding: 0;
    font-family: Arial, Helvetica, sans-serif;
  } */

/* Aesthetic CSS card */
.el-card {
  width: 80%;
  margin: 20px auto;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}

/* Aesthetic CSS table */
.el-table {
  width: 100%;
  border-collapse: collapse;
}

/* Aesthetic CSS table header */
.el-table-column {
  background-color: lightblue;
  color: white;
  padding: 10px;
  border: 1px solid white;
  text-align: center;
}

/* Aesthetic CSS table body */
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

/* Aesthetic CSS table hover effect */
.el-table-column[type="index"]:hover,
.el-table-column[prop="id"]:hover,
.el-table-column[prop="username"]:hover,
.el-table-column[prop="phone"]:hover,
.el-table-column[prop="email"]:hover {
  background-color: lightpink;
  color: white;
}

/* Aesthetic CSS button hover effect */
.el-button:hover {
  transform: scale(1.2);
}

/* Same CSS as this code */
.el-pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px;
}

/* Same CSS total count */
.el-pagination__total {
  color: #606266;
  margin-right: 20px;
}

/* Same CSS: page size */
.el-pagination__sizes {
  display: flex;
  align-items: center;
  margin-right: 20px;
}

/* Same CSS: page size selector */
.el-pagination__sizes .el-select {
  width: 100px;
}

/* Same CSS: prev button */
.el-pagination__prev {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Same CSS: prev icon */
.el-pagination__prev .el-icon {
  font-size: 20px;
  color: #409eff;
}

/* Same CSS: pager */
.el-pagination__pager {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Same CSS: pager button */
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

/* Same CSS: pager button hover */
.el-pagination__pager button:hover {
  background-color: #409eff;
  color: white;
}

/* Same CSS: active pager button */
.el-pagination__pager button.is-active {
  background-color: #409eff;
  color: white;
}

/* Same CSS: next button */
.el-pagination__next {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

/* Same CSS: next icon */
.el-pagination__next .el-icon {
  font-size: 20px;
  color: #409eff;
}

/* Same CSS: jump input */
.el-pagination__jump {
  display: flex;
  align-items: center;
}

/* Same CSS: jump label */
.el-pagination__jump label {
  color: #606266;
}

/* Same CSS: jump input field */
.el-pagination__jump input {
  width: 50px;
  height: 30px;
  border-radius: 4px;
  border: none;
}
</style>