<template>
  <div class="info-upload">
    <h1 class="title">Body Information Upload</h1>
    <el-form
      :model="form"
      :rules="rules"
      ref="form"
      label-width="100px"
      class="form"
    >
      <el-row>
        <el-col :xs="24" :sm="12">
          <el-form-item label="Name" prop="name">
            <el-input
              v-model="form.name"
              :placeholder="'Please enter your name'"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Age" prop="age">
            <el-input-number v-model="form.age" :min="0"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :xs="24" :sm="12">
          <el-form-item label="Gender" prop="gender">
            <el-radio-group v-model="form.gender">
              <el-radio label="男">Male</el-radio>
              <el-radio label="女">Female</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Height (m)" prop="height">
            <el-input-number v-model="form.height" :min="0" :step="0.1"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :xs="24" :sm="12">
          <el-form-item label="Weight (kg)" prop="weight">
            <el-input-number v-model="form.weight" :min="0"></el-input-number>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Blood Glucose" prop="bloodSugar">
            <el-input-number
              v-model="form.bloodSugar"
              :min="0"
              :step="0.1"
            ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :xs="24" :sm="12">
          <el-form-item label="Blood Pressure" prop="bloodPressure">
            <el-input-number
              v-model="form.bloodPressure"
              :min="0"
              :step="0.1"
            ></el-input-number>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Blood Lipid (Cholesterol)" prop="bloodLipid">
            <el-input-number
              v-model="form.bloodLipid"
              :min="0"
              :step="0.1"
            ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :xs="24" :sm="12">
          <el-form-item label="Heart Rate (BPM)" prop="heartRate">
            <el-input-number
              v-model="form.heartRate"
              :min="0"
            ></el-input-number>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Vision (d)" prop="vision">
            <el-input-number v-model="form.vision" :min="0" :step="0.1"></el-input-number>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Sleep Duration (h)" prop="sleepDuration">
            <el-input-number
              v-model="form.sleepDuration"
              :min="0"
              :step="0.1"
            ></el-input-number>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Sleep Quality" prop="sleepQuality">
            <el-radio-group v-model="form.sleepQuality">
              <el-radio :label="'好'">Good</el-radio>
              <el-radio :label="'一般'">Average</el-radio>
              <el-radio :label="'差'">Poor</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Smoking" prop="smoking">
            <el-switch v-model="form.smoking"></el-switch>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Alcohol Consumption" prop="drinking">
            <el-switch v-model="form.drinking"></el-switch>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Exercise" prop="exercise">
            <el-switch v-model="form.exercise"></el-switch>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Preferred Food Types" prop="foodTypes">
            <el-select v-model="form.foodTypes" placeholder="Please select">
              <el-option label="Vegetables" value="蔬菜"></el-option>
              <el-option label="Fruits" value="水果"></el-option>
              <el-option label="Meat" value="肉类"></el-option>
              <el-option label="Fish" value="鱼类"></el-option>
              <el-option label="Legumes" value="豆类"></el-option>
              <el-option label="Grains" value="谷物"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12">
          <el-form-item label="Water Intake (ml)" prop="waterConsumption">
            <el-input-number
              v-model="form.waterConsumption"
              :min="0"
            ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button type="primary" @click="submitForm()">Upload</el-button>
        <el-button @click="resetForm('form')">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
  <script>
import FunctionApi from "@/api/Function_Menu";
import AvatarUpload from "@/components/HeadImage/AvatarUpload";
import userApi from "@/api/userManage";

export default {
  name: "InfoUpload",
  components: {
    AvatarUpload,
  },
  data() {
    return {
      form: {
        id: null,
        name: "",
        age: null,
        gender: "",
        height: "",
        weight: "",
        bloodSugar: "",
        bloodPressure: "",
        bloodLipid: "",
        heartRate: null,
        vision: null,
        sleepDuration: null,
        sleepQuality: "",
        smoking: null,
        drinking: null,
        exercise: null,
        foodTypes: "",
        waterConsumption: null,
      },

      rules: {
        name: [
          { required: true, message: "Please enter your name", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "Length must be between 2 and 10 characters",
            trigger: "blur",
          },
        ],
        age: [
          { required: true, message: "Please enter age", trigger: "blur" },
          { type: "number", message: "Age must be a number" },
        ],
        gender: [{ required: true, message: "Please select gender", trigger: "change" }],
        height: [
          { required: true, message: "Please enter height", trigger: "blur" },
          { type: "number", message: "Height must be a number" },
        ],
        weight: [
          { required: true, message: "Please enter weight", trigger: "blur" },
          { type: "number", message: "Weight must be a number" },
        ],
        bloodSugar: [
          { required: true, message: "Please enter blood glucose", trigger: "blur" },
          { type: "number", message: "Blood glucose must be a number" },
        ],
        bloodPressure: [
          { required: true, message: "Please enter blood pressure", trigger: "blur" },
          { type: "number", message: "Blood pressure must be a number" },
        ],
        bloodLipid: [
          { required: true, message: "Please enter blood lipid", trigger: "blur" },
          { type: "number", message: "Blood lipid must be a number" },
        ],

        heartRate: [
          { required: true, message: "Please enter heart rate", trigger: "blur" },
          { type: "number", message: "Heart rate must be a number" },
        ],
        vision: [
          { required: true, message: "Please enter vision", trigger: "blur" },
          { type: "number", message: "Vision must be a number" },
        ],
        sleepQuality: [
          {
            required: true,
            message: "Please select sleep quality",
            trigger: "change",
          },
        ],
        smoking: [
          { required: true, message: "Please indicate smoking", trigger: "change" },
        ],
        drinking: [
          { required: true, message: "Please indicate alcohol consumption", trigger: "change" },
        ],
        exercise: [
          { required: true, message: "Please indicate exercise", trigger: "change" },
        ],
        foodTypes: [
          { required: true, message: "Please select your primary food types", trigger: "blur" },
        ],
        waterConsumption: [
          { required: true, message: "Please enter water intake", trigger: "blur" },
          { type: "number", message: "Water intake must be a number" },
        ],
      },
    };
  },

  methods: {
  submitForm() {
  // 如果表单数据中没有 id 属性，则将组件的 id 属性赋值给表单数据的 id 属性
  if (!this.form.id) {
    this.form.id = this.id;
  }

  this.$refs.form.validate((valid) => {
    if (valid) {
      FunctionApi.BodyInformation(this.form)
        .then((response) => {
          this.$message({
            type: "success",
            message: response.message,
          });
        })
        .catch((error) => {
        });
        
      FunctionApi.BodyInformationNotes(this.form)
        .then((response) => {

        })
        .catch((error) => {
        });
    } else {
      return false;
    }
  });
},


  // 重置表单数据的方法
  resetForm(formName) {
    this.$refs[formName].resetFields();
    console.log(this.id);
    this.$message({
      message: "Form has been reset",
      type: "info",
    });
  },


  async getUserId() {
    await userApi.getUserId().then((response) => {
      // 如果请求成功并且返回的数据包含 id 属性，则将其转换为整数并赋值给组件的 id 属性
      if (response && response.data) {
        this.id = parseInt(response.data.id);
      }
    });
  },
},

  created() {
    this.getUserId();
  },
};
</script>
  
<style scoped>
.info-upload {
  max-width: 800px;
  margin: 0 auto;
  padding: 50px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);
}

.title {
  font-size: 36px;
  text-align: center;
  color: #555;
}

.form {
  max-width: 600px;
  margin: 0 auto;
}

</style>

  </file>