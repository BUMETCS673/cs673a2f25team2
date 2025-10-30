<!-- Search box and Add button; includes search input and basic fields -->
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
              @click="getDetailList"
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
        <el-table :data="detailList" stripe style="width: 100%">
          <el-table-column
            type="index"
            label="No."
            width="180"
          ></el-table-column>
          <el-table-column prop="id" label="ID" width="180"></el-table-column>
          <el-table-column
            prop="sportType"
            label="Sport Type"
            width="180"
          ></el-table-column>
          <el-table-column
            prop="disease"
            label="Contraindicated Conditions"
            width="180"
          ></el-table-column>

          <el-table-column
            prop="method"
            label="Exercise Method"
            width="180"
          ></el-table-column>

          <el-table-column
            prop="notes"
            label="Notes"
            width="180"
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
                @click="deleteDetail(scope.row)"
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

      <!-- Detail edit dialog -->
      <el-dialog
        @close="clearForm"
        :title="title"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="detailForm" ref="detailFormRef" >
          <el-form-item label="Sport Type" prop="sportType" :label-width="formLabelWidth">
            <el-input v-model="detailForm.sportType" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
             label="Contraindicated Conditions" prop="disease" :label-width="formLabelWidth">
            <el-input v-model="detailForm.disease" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="Exercise Method" prop="method" :label-width="formLabelWidth">
            <el-input v-model="detailForm.method" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item
            label="Notes" prop="notes" :label-width="formLabelWidth">
            <el-input v-model="detailForm.notes" autocomplete="off"></el-input>
          </el-form-item>


        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveDetail">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </template>

  <script>
  import sportApi from "@/api/Function_Menu";
  export default {
    data() {

      return {
        detailForm: {}, // initialize as empty object
        detailList:[],
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
        // form rules
        rules: {
          sportType: [
            { required: true, message: "Please enter a sport type", trigger: "blur" }
          ]
        },
      };
    },

    methods: {
      saveDetail() {
        let isOk = true;
        //触发表单的验证
        this.$refs.detailFormRef.validate((valid) => {
          // 这边只有校验失败的时候才会进来,在外面定义一个 isok,校验失败会将他改成 false
          isOk = valid;
        });

        if (isOk) {
          //提交验证给后台
          console.log(this.detailtForm)
          sportApi.saveDetail(this.detailForm).then((response) => {
            //成功提示
            this.$message({
              message: response.message,
              type: "success",
            });
            //关闭对话框
            this.dialogFormVisible = false;
            //刷新表格数据
            this.getDetailList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      },

      // clear form data
      clearForm() {
        this.detailForm = {
        };
        // clear form validation
        this.$refs.detailFormRef.clearValidate();
      },
      handleSizeChange(pageSize) {
        // update data
        this.searchModel.pageSize = pageSize;
        this.getDetailList();
      },
      handleCurrentChange(pageNo) {
        this.searchModel.pageNo = pageNo;
        this.getDetailList();
      },



      // for querying detail list
      getDetailList() {
        sportApi.getDetailList(this.searchModel).then((response) => {
          this.detailList = response.data.rows;
          this.total = response.data.total;
        });
      },



      openEditUi(id) {
        console.log(id)
        if (id == null) {
          this.title = "Add Exercise Detail";
        } else {
          this.title = "Edit Exercise Detail";
          // query detail by id
          sportApi.getDetailById(id).then((response) => {
            this.detailForm = response.data;
            console.log(this.detailForm)
          });
        }
        this.dialogFormVisible = true;
      },


      deleteDetail(detail) {
        this.$confirm(`Delete exercise detail "${detail.sportType}"?`, "Confirm", {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        })
          .then(() => {
            sportApi.deleteDetailById(detail.id).then((response) => {
              this.$message({
                type: "success",
                message: response.message,
              });
              this.getDetailList();
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


    //加载时就查询一次
    created() {
      this.getDetailList();
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

  /* Styled CSS table hover */
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

  /* CSS consistent with this code */
  .el-pagination {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 20px;
  }

  /* CSS total label */
  .el-pagination__total {
    color: #606266;
    margin-right: 20px;
  }

  /* CSS page size */
  .el-pagination__sizes {
    display: flex;
    align-items: center;
    margin-right: 20px;
  }

  /* CSS page size selector */
  .el-pagination__sizes .el-select {
    width: 100px;
  }

  /* CSS prev button */
  .el-pagination__prev {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }

  /* CSS prev icon */
  .el-pagination__prev .el-icon {
    font-size: 20px;
    color: #409eff;
  }

  /* CSS pager */
  .el-pagination__pager {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }

  /* CSS pager button */
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

  /* CSS pager button hover */
  .el-pagination__pager button:hover {
    background-color: #409eff;
    color: white;
  }

  /* CSS active page button */
  .el-pagination__pager button.is-active {
    background-color: #409eff;
    color: white;
  }

  /* CSS next button */
  .el-pagination__next {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }

  /* CSS next icon */
  .el-pagination__next .el-icon {
    font-size: 20px;
    color: #409eff;
  }

  /* CSS jump input */
  .el-pagination__jump {
    display: flex;
    align-items: center;
  }

  /* CSS jump input label */
  .el-pagination__jump label {
    color: #606266;
  }

  /* CSS jump input field */
  .el-pagination__jump input {
    width: 50px;
    height: 30px;
    border-radius: 4px;
    border: none;
  }
  </style>
