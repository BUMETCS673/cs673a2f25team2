<template>
  <div class="container">
    <div class="box first-box">
      <div class="title">Body Information</div>
      <div class="content" style="margin-left: 15%">
        <div class="info">
          <div class="label">Weight:</div>
          <div class="value">
            {{ bodyInfo.weight }}<span class="unit">kg</span>
          </div>
        </div>
        <div class="info">
          <div class="label">Height:</div>
          <div class="value">
            {{ bodyInfo.height }}<span class="unit">m</span>
          </div>
        </div>
        <div class="info">
          <div class="label">BMI:</div>
          <div class="value">{{ this.bmiM }}</div>
        </div>
        <div class="info">
          <div class="label">Blood Sugar:</div>
          <div class="value">
            {{ bodyInfo.bloodSugar }} <span class="unit">mmol/L</span>
          </div>
        </div>
        <div class="info">
          <div class="label">Blood Pressure:</div>
          <div class="value">
            {{ bodyInfo.bloodPressure }}<span class="unit">mmHg</span>
          </div>
        </div>
        <div class="info">
          <div class="label">Cholesterol:</div>
          <div class="value">
            {{ bodyInfo.bloodLipid }} <span class="unit">mmol/l</span>
          </div>
        </div>
        <div class="info">
          <div class="label">Heart Rate:</div>
          <div class="value">
            {{ bodyInfo.heartRate }} <span class="unit">bpm</span>
          </div>
        </div>
        <div class="info">
          <div class="label">Vision:</div>
          <div class="value">
            {{ bodyInfo.vision }} <span class="unit">diopter</span>
          </div>
        </div>
      </div>
    </div>

    <div class="box">
      <div class="title">Disease Analysis</div>
      <div id="diseaseChart" class="content">
        <div class="info">
          <div class="label_vision">Possible Diseases:</div>
          <div class="value">{{ this.risk }}<span class="unit"></span></div>
        </div>
        <div class="info">
          <div class="label_vision" style="color: red">Note:</div>
          <div class="value">
            {{
              "The above risk assessment is based only on your uploaded health data and should not be considered a medical diagnosis. Regardless of risk level, please maintain regular exercise and seek medical attention if you feel unwell."
            }}<span class="unit"></span>
          </div>
        </div>
      </div>
    </div>

    <div class="box">
      <div class="title">Basic Energy Consumption</div>
      <el-progress
        type="circle"
        :percentage="Number(this.Standard_hight)"
        style="margin-left: 100px; margin-top: 30px"
        :show-text="true"
      ></el-progress>
      <el-progress
        type="circle"
        :percentage="Number(this.BMR)"
        color="red"
        style="margin-left: 100px; margin-top: 30px"
        :show-text="true"
      ></el-progress>
      <div style="padding-left: 80px; margin-top: 20px">
        Percentage of Body Age Achieved
      </div>
      <div style="padding-left: 340px; margin-top: -20px">Basic Metabolic Rate</div>
    </div>

    <div class="box">
      <div class="title">Obesity Analysis</div>
      <div class="content">
        <div style="display: flex; justify-content: right">
          <el-progress
            :percentage="percentage"
            :show-text="false"
            style="width: 70%; margin-top: 20px"
          ></el-progress>
        </div>
        <div class="info" style="margin-top: 30px">
          <div class="label_vision">Based on Calculation:</div>
          <div class="value">{{ determineHealthRisk }}</div>
        </div>
        <div class="info">
          <div class="label_vision">Potential Risks of Obesity:</div>
          <div class="value">{{ Disease_risk }}</div>
        </div>
      </div>
    </div>

    <div class="box">
      <div class="title">Lifestyle Analysis</div>
      <div class="content">
        <div class="info">
          <div class="label_vision">Your Habits:</div>
        </div>
        <div class="value" style="margin-left: 20px">{{ this.habits }}</div>
        <div class="info" style="padding-top: 25px">
          <div class="label_vision">Recommendation:</div>
          <div class="value">
            Read about exercise knowledge to better understand correct techniques and methods. By learning the science behind exercise, you can design effective plans, reduce risks, and avoid injury or illness.
          </div>
        </div>
      </div>
    </div>

    <div class="box">
      <div class="title">Vision Analysis</div>
      <div class="content">
        <div class="info">
          <div class="label_vision">Your Vision:</div>
          <div class="value">
            {{ bodyInfo.vision }}<span class="unit"></span>
          </div>
        </div>
        <div class="info">
          <div class="label_vision">Myopia Level:</div>
          <div class="value">{{ visionType }}<span class="unit"></span></div>
        </div>
        <div class="info">
          <div class="label_vision">Recommendation:</div>
          <div class="value">
            {{ visionSuggestion }}<span class="unit"></span>
          </div>
        </div>
      </div>
    </div>

    <div class="box">
      <div class="title">Body Type Evaluation</div>
      <div class="content">
        <div class="info">
          <div class="label_vision">Your Body Type:</div>
          <div class="value">{{ bodyType }}<span class="unit"></span></div>
        </div>
        <div class="info">
          <div class="label_vision">Recommendation:</div>
          <div class="value">{{ bodyTypeSuggestion }}</div>
        </div>
      </div>
    </div>

    <div class="boxScore">
      <div class="titleScore">Health Score</div>
      <div>{{ this.score }}</div>
    </div>

    <div class="box"></div>
  </div>
</template>

<script>
import userApi from "@/api/userManage";
export default {
  data() {
    return {
      bodyInfo: "",
      bmi: null,
      risk: "",
      Standard_hight: null,
      metabolic_rate: null,
      BMR: null,
      habits: "",
      score: 100,
      habits_count: [],
    };
  },

  computed: {
    bmiM() {
      // Get height and weight from bodyInfo and convert to Number
      const weight = Number(this.bodyInfo.weight);
      // Calculate BMI
      const bmiValue = weight / (this.bodyInfo.height * this.bodyInfo.height);
      // Return value with two decimals
      this.bmi = bmiValue.toFixed(2);
      return bmiValue.toFixed(2);
    },

    percentage() {
      return Math.round((this.bmi / 35) * 100);
    },
    visionType() {
      const vision = this.bodyInfo.vision;
      if (vision >= 600) {
        return "High Myopia";
      } else if (vision >= 300 && vision <= 600) {
        return "Moderate Myopia";
      } else if (vision > 0 && vision <= 300) {
        return "Mild Myopia";
      } else if (vision === 0) {
        return "No Myopia";
      }
    },

    visionSuggestion() {
      const visionType = this.visionType;
      if (visionType === "High Myopia") {
        return "Seek medical treatment promptly.";
      } else if (visionType === "Moderate Myopia") {
        return "Protect your eyes and have regular vision checkups.";
      } else if (visionType === "Mild Myopia") {
        return "Do eye exercises and maintain eye hygiene.";
      } else if (visionType === "No Myopia") {
        return "Great! Maintain good habits and protect your eyes.";
      }
    },

    bodyType() {
      if (this.bmiM >= 28) {
        return "Obese";
      } else if (this.bmiM > 24 && this.bmiM <= 28) {
        return "Overweight";
      } else if (this.bmiM >= 0 && this.bmiM <= 24) {
        return "Normal";
      }
    },

    determineHealthRisk() {
      if (this.bmiM >= 28) {
        return "Your weight is too high; please lose weight immediately.";
      } else if (this.bmiM > 24 && this.bmiM <= 28) {
        return "Your weight is above normal; consider reducing it soon.";
      } else if (this.bmiM >= 0 && this.bmiM <= 24) {
        return "Your weight is normal; maintain a healthy lifestyle.";
      }
    },
    Disease_risk() {
      if (this.bmiM >= 28) {
        return "High risk of heart disease, stroke, hypertension, high cholesterol, diabetes, respiratory problems, and arthritis.";
      } else if (this.bmiM > 24 && this.bmiM <= 28) {
        return "Increased risk of hypertension, cholesterol, heart disease, stroke, diabetes, insulin irregularities, and respiratory issues.";
      } else if (this.bmiM >= 0 && this.bmiM <= 24) {
        return "Low risk, but ensure sufficient intake of protein, carbohydrates, and fats.";
      }
    },

    bodyTypeSuggestion() {
      if (this.bodyType === "Obese") {
        return "Control your diet, increase physical activity, and seek professional medical advice.";
      } else if (this.bodyType === "Overweight") {
        return "Maintain healthy eating habits, control intake, and strengthen aerobic exercise.";
      } else if (this.bodyType === "Normal") {
        return "Maintain a balanced lifestyle, exercise regularly, and eat well.";
      }
    },
  },

  methods: {
    async getBodyInfo() {
      try {
        // Use destructuring to get bodyInfo from userApi.getBodyInfo()
        const {
          data: {
            bodyList: [bodyInfo],
          },
        } = await userApi.getBodyInfo();
        this.bodyInfo = bodyInfo;
      } catch (error) {
        console.log("Error fetching body information");
      }
    },

    habits_customs() {
      let habits = [];
      this.habits_count = habits;
      // Dietary preferences
      if (this.bodyInfo.foodTypes === "蔬菜") {
        habits.push("Prefers vegetables");
      }
      if (this.bodyInfo.foodTypes === "水果") {
        habits.push("Prefers fruits");
      }
      if (this.bodyInfo.foodTypes === "肉类") {
        habits.push("Prefers meat");
      }
      if (this.bodyInfo.foodTypes === "鱼类") {
        habits.push("Prefers fish");
      }
      if (this.bodyInfo.foodTypes === "豆类") {
        habits.push("Prefers legumes");
      }
      if (this.bodyInfo.foodTypes === "谷物") {
        habits.push("Prefers grains");
      }

      if (this.bodyInfo.bloodSugar > 7) {
        habits.push("High sugar intake and poor lifestyle habits");
      } else {
        habits.push("Normal blood sugar level");
      }

      if (this.bodyInfo.bloodPressure > 5.2) {
        habits.push("High cholesterol diet");
      } else {
        habits.push("Low cholesterol diet");
      }

      if (this.bodyInfo.heartRate > 100) {
        habits.push("Frequent stress and anxiety");
      } else {
        habits.push("Good mood");
      }

      if (this.bodyInfo.vision > 50) {
        habits.push("Overworked and staying up late");
      } else {
        habits.push("Goes to bed on time");
      }

      if (this.bodyInfo.sleepDuration < 8) {
        habits.push("Insufficient sleep");
      } else {
        habits.push("Sufficient sleep");
      }

      if (this.bodyInfo.sleepQuality === "好") {
        habits.push("Overworked and staying up late");
      } else if (this.bodyInfo.sleepQuality === "一般") {
        habits.push("Needs to improve sleep quality");
      } else if (this.bodyInfo.sleepQuality === "差") {
        habits.push("Should improve sleep quality");
      }
      if (this.bodyInfo.smoking === true) {
        habits.push("Smokes");
      } else {
        habits.push("Does not smoke");
      }

      if (this.bodyInfo.drinking === true) {
        habits.push("Drinks alcohol");
      } else {
        habits.push("Does not drink");
      }
      if (this.bodyInfo.exercise === true) {
        habits.push("Exercises regularly");
      } else {
        habits.push("Lack of exercise");
      }

      if (this.bodyInfo.waterConsumption < 1000) {
        habits.push("Insufficient water intake");
      } else {
        habits.push("Adequate water intake");
      }

      this.habits = habits.join(", ");
    },

    diseaserisk() {
      if (this.bodyInfo.bloodPressure >= 90) {
        this.risk += "Hypertension, ";
      }
      if (this.bodyInfo.bloodLipid > 3) {
        this.risk += "Hyperlipidemia, ";
      }
      if (this.bodyInfo.bloodSugar > 6.1) {
        this.risk += "Diabetes, ";
      }
      if (this.bodyInfo.drinking > 3) {
        this.risk += "Alcoholism, ";
      }
      if (this.bodyInfo.exercise < 3) {
        this.risk += "Lack of Exercise, ";
      }
      if (this.bodyInfo.heartRate > 100) {
        this.risk += "Tachycardia, ";
      }
      if (this.bodyInfo.sleepDuration < 7 || this.bodyInfo.sleepQuality < 3) {
        this.risk += "Sleep deprivation, ";
      }
      if (this.bodyInfo.smoking > 0) {
        this.risk += "Pneumonia, ";
      }
      if (this.bodyInfo.vision <= 300) {
        this.risk += "Myopia, ";
      }
      if (this.bodyInfo.waterConsumption < 1500) {
        this.risk += "Kidney Stones";
      }
    },

    compareAgeAndHeight() {
      const height = this.bodyInfo.height;
      const sex = this.bodyInfo.sex;
      let Standardhight = null;
      if (sex === "男") {
        Standardhight = (height - 80) * 0.7 + 160;
        this.Standard_hight = ((Standardhight / height) * 1).toFixed(2);
      } else {
        Standardhight = (height - 70) * 0.6 + 160;
        this.Standard_hight = ((Standardhight / height) * 1).toFixed(2);
      }
    },

    supersession() {
      const height = this.bodyInfo.height;
      const weight = this.bodyInfo.weight;
      const age = this.bodyInfo.age;
      const BMR1 = (88.36 + 13.4 * weight + 4.8 * height - 5.7 * age).toFixed(
        2
      );
      const reference = 1200; // 假设BMR的参考值为每天所需热量的1200卡路里

      this.BMR = Math.round((BMR1 / reference) * 100); // 计算BMR占参考值的百分比
    },

    scoreCom() {
      if (this.bodyInfo.vision > 30) {
        this.score = this.score - 25;
      }
      if ((this.risk.match(/,/g) || []).length > 6) {
        console.log((this.risk.match(/,/g) || []).length);
        this.score = this.score - 25;
      }

      if (this.bmi > 24) {
        this.score = this.score - 25;
      }
      if (this.Standard_hight < 80) {
        this.score = this.score - 25;
      }
    },
  },

  watch: {
    bodyInfo: {
      deep: true,
      handler() {
        this.diseaserisk();
        this.compareAgeAndHeight();
        this.supersession();
        this.habits_customs();
        this.scoreCom();
      },
    },
  },

  created() {
    this.getBodyInfo();
  },

  mounted() {},
};
</script>

<style scoped>
.label_vision {
  font-size: 16px;
  font-weight: bold;
  color: #666;
}

.titleScore {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}
.boxScore {
  width: 270px;
  height: 270px;
  background-color: #ffdffa;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: Arial, sans-serif;
  font-size: 40px;
  font-weight: bold;
  color: #333333;
  margin-left: 130px;
}

.container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(3, 1fr);
  gap: 20px;
  height: 100vh;
  padding: 20px;
  box-sizing: border-box;
}

.box {
  background-color: #fff;
  box-shadow: 0 0 10px rgba(76, 122, 184, 0.3);
  border-radius: 10px;
  float: left;
  position: relative;
}

.first-box {
  grid-row: 1 / 3;
}

.content {
  padding: 20px;
  box-sizing: border-box;
  display: inline-block;
  position: absolute;
  width: 70%;
  height: auto;
}

.content_fp {
  text-align: center;
  padding: 20px;
  box-sizing: border-box;
}
.img {
  position: absolute;
  left: 50%;
  width: 50%;
  height: 50%;
}

.image_info {
  position: absolute;
  width: 100%;
  height: 70%;
  margin-top: 350px;
  margin-left: -120px;
}

.title {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 10px;
  margin-top: 5px;
  text-shadow: 2px 2px 4px rgba(148, 28, 138, 0.5);
  color: #1e90ff;
  text-align: center;
}

.info {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  position: relative;
  margin-left: 20px; /* 调整左侧信息区域的间距 */
}

.info::after {
  content: "";
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: #ddd;
}

.label {
  width: 80px;
  font-size: 25px;
  font-weight: bold;
  color: #555;
}

.value {
  font-size: 14px;
  color: #333;
  display: flex;
  align-items: center;
}

.image {
  width: 100px;
  height: auto;
  float: right; /* 图片向右浮动 */
  margin-left: 20px; /* 给图片和左侧元素之间添加间距 */
}

.weight {
  font-size: 20px;
  font-weight: bold;
  margin-right: 10px;
}

.height {
  font-size: 20px;
  font-weight: bold;
  margin-right: 10px;
}

.bmi {
  font-size: 24px;
  font-weight: bold;
  color: #f00;
}

.unit {
  font-size: 20px;
  font-weight: normal;
  margin-left: 5px;
  color: #555;
}
</style>
