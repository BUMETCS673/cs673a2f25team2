<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="23">
          <!-- v-model绑定组件实现双向数据绑定，页面上用户输入的值会同步更新到该属性中 -->
          <el-input
            v-model="searchModel.sportType"
            placeholder="Sport Type"
            clearable
          ></el-input>
          <el-button
            @click="getSportList"
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

    <!-- 结果列表 -->
    <el-card>
      <el-table :data="sportList" stripe style="width: 100%">
        <el-table-column
          type="index"
          label="No."
          width="180"
        ></el-table-column>
        <el-table-column prop="id" label="ID" width="150"></el-table-column>
        <el-table-column
          prop="sportType"
          label="Sport Type"
          width="150"
        ></el-table-column>
        <el-table-column
          prop="suitableFrequency"
          label="Frequency"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="suitableTime"
          label="Duration"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="recommendedSpeed"
          label="Speed"
          width="150"
        ></el-table-column>

        <el-table-column
          prop="suitableHeartRate"
          label="Heart Rate"
          width="150"
        ></el-table-column>

        <el-table-column label="Actions" width="180">
          <!-- 删除和修改按钮 -->
          <template slot-scope="scope">
            <el-button
              @click="openEditUi(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              @click="deleteSport(scope.row)"
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
      <el-form :model="sportForm" ref="sportFormRef" >
        <el-form-item label="Sport Type" prop="sportType" :label-width="formLabelWidth">
          <el-input v-model="sportForm.sportType" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
           label="Frequency" prop="suitableFrequency" :label-width="formLabelWidth">
          <el-input v-model="sportForm.suitableFrequency" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="Duration" prop="suitableTime" :label-width="formLabelWidth">
          <el-input v-model="sportForm.suitableTime" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="Speed" prop="emrecommendedSpeedail" :label-width="formLabelWidth">
          <el-input v-model="sportForm.recommendedSpeed" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="Heart Rate" prop="suitableHeartRate" :label-width="formLabelWidth">
          <el-input v-model="sportForm.suitableHeartRate" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveSport">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import sportApi from "@/api/Function_Menu";
export default {
  data() {
  
    return {
      sportForm: {}, // Initialize as an empty object
      sportList:[],
      // Label column width
      formLabelWidth: "135px",
      // Dialog hidden by default
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        // Default page size
        pageSize: 10,
      },
      // Form validation rules

      rules: {
        sportType: [
          { required: true, message: "Please enter a sport type", trigger: "blur" }
        ]
      },
    };
  },

  methods: {
    saveSport() {
      let isOk = true;
      // Trigger form validation
      this.$refs.sportFormRef.validate((valid) => {
        // Validation only fails inside callback; define isOk outside to track result
        isOk = valid;
      });

      if (isOk) {
        // Submit form to backend
        console.log(this.sportForm)
        sportApi.saveSport(this.sportForm).then((response) => {
          // Success message
          this.$message({
            message: response.message,
            type: "success",
          });
          // Close dialog
          this.dialogFormVisible = false;
          // Refresh table
          this.getSportList();
        });
      } else {
        console.log("error submit!!");
        return false;
      }
    },

    // Clear form data
    clearForm() {
      this.sportForm = {
      };
      // Clear validation messages
      this.$refs.sportFormRef.clearValidate();
    },
    handleSizeChange(pageSize) {
      // Update data
      this.searchModel.pageSize = pageSize;
      this.getSportList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getSportList();
    },




    getSportList() {
      sportApi.getSportList(this.searchModel).then((response) => {
        console.log(response)
        // Assign response rows to sportList
        this.sportList = response.data.rows;
        // Assign response total to total
        this.total = response.data.total;
        console.log(this.sportList)
      });
    },



    openEditUi(id) {
      console.log(id)
      if (id == null) {
        this.title = "Add Exercise Info";
      } else {
        this.title = "Edit Exercise Info";
        // Fetch exercise info by ID
        sportApi.getSportById(id).then((response) => {
          this.sportForm = response.data;
          console.log(this.sportForm)
        });
      }
      this.dialogFormVisible = true;
    },


    deleteSport(sport) {
      this.$confirm(`Delete exercise info ${sport.sportType}?`, "Confirm", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "warning",
      })
        .then(() => {
          sportApi.deleteSportById(sport.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getSportList();
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

  
  // Fetch list on component load
  created() {
    this.getSportList();
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

/* Styled CSS */
/* body {
    background: linear-gradient(to right, lightblue, lightpink);
    margin: 0;
    padding: 0;
    font-family: Arial, Helvetica, sans-serif;
  } */

/* Styled CSS card */
.el-card {
  width: 80%;
  margin: 20px auto;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}

/* Styled CSS table */
.el-table {
  width: 100%;
  border-collapse: collapse;
}

/* Styled CSS table header */
.el-table-column {
  background-color: lightblue;
  color: white;
  padding: 10px;
  border: 1px solid white;
  text-align: center;
}

/* Styled CSS table cells */
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

/* Styled CSS hover effect */
.el-table-column[type="index"]:hover,
.el-table-column[prop="id"]:hover,
.el-table-column[prop="username"]:hover,
.el-table-column[prop="phone"]:hover,
.el-table-column[prop="email"]:hover {
  background-color: lightpink;
  color: white;
}

/* Styled CSS button hover */
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

/* Pagination hover button */
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