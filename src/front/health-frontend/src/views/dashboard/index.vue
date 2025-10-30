<template>
  <el-row class="row-bg" :gutter="20">
    <div>
      <el-col :span="6">
        <div class="grid-content bg-purple-dark">
          <div class="col1">
            <div class="col6">Height</div>
            <div class="col6zhi">
              {{ this.bodyInfo.height }}<span class="unit">m</span>
            </div>
          </div>
          <div>
            <i class="el-icon-user-solid" style="font-size: 80px"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-dark">
          <div class="col1">
            <div class="col6">Weight</div>
            <div class="col6zhi">
              {{ this.bodyInfo.weight }}<span class="unit">kg</span>
            </div>
          </div>
          <div>
            <i class="el-icon-odometer" style="font-size: 80px"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-dark">
          <div class="col1">
            <div class="col6">BMI</div>
            <div class="col6zhi">{{ this.bmi }}<span class="unit"></span></div>
          </div>
          <div>
            <i class="el-icon-date" style="font-size: 80px"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-dark">
          <div class="col1">
            <div class="col6">Age</div>
            <div class="col6zhi" style="text-align: center; font-size: 30px">
              {{ this.bodyInfo.age }}<span class="unit"></span>
            </div>
          </div>
          <div>
            <i class="el-icon-s-data" style="font-size: 80px"></i>
          </div>
        </div>
      </el-col>
    </div>

    <div class="div2">
      <div class="box1 box">
        <div class="box1 box" ref="myChart"></div>
      </div>

      <div class="box2 box">
        <div id="chart-container" style="width: 100%; height: 100%"></div>
      </div>

      <div class="box3 box">
        <div id="chart-containerLine" style="width: 100%; height: 100%"></div>
      </div>
    </div>
  </el-row>
</template>



<script>
import * as echarts from "echarts";

import userApi from "@/api/userManage";
import FunctionApi from "@/api/Function_Menu";
export default {
  data() {
    return {
      charts: "",
      bodyInfo: "",
      bmi: null,
      score: null,
      BodyNotesInfo: "",

      vision: [],
      waterConsumption: [],
      bloodSugar: [],
      bloodPressure: [],
      date: [],
      heartRate: [],
    };
  },
  methods: {
    async getBodyInfo() {
      try {
        // Use destructuring to extract the first bodyInfo object from data.bodyList returned by userApi.getBodyInfo()
        const {
          data: {
            bodyList: [bodyInfo],
          },
        } = await userApi.getBodyInfo();
        this.bodyInfo = bodyInfo;
      } catch (error) {
        console.log("Failed to fetch body information");
      }
    },


    async getBodyNotes() {
      try {

        const response = await FunctionApi.getBodyNotes(this.bodyInfo.id);

        // Retrieve BodyNotesInfo from the response and assign it to the component property
        this.BodyNotesInfo = response.data;

        // Iterate through BodyNotesInfo and push each field into the corresponding arrays; note is each data object
        this.BodyNotesInfo.forEach((note) => {
          this.vision.push(note.vision);
          this.waterConsumption.push(note.waterConsumption);
          this.bloodSugar.push(note.bloodSugar);
          this.bloodPressure.push(note.bloodPressure);
          this.heartRate.push(note.heartRate);
          const formattedDate = new Date(note.date).toLocaleString("en-US", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
          });
          this.date.push(formattedDate);
        });
      } catch (error) {
        console.log("Failed to fetch body notes");
      }
    },

    bmiM() {
      // Get height and weight from bodyInfo and convert to Number
      const weight = Number(this.bodyInfo.weight);
      // Calculate BMI
      const bmiValue = weight / (this.bodyInfo.height * this.bodyInfo.height);
      // Return result with two decimal places
      this.bmi = bmiValue.toFixed(2);
      return bmiValue.toFixed(2);
    },

    BarChart() {
      const chartDom = document.getElementById("chart-container");
      const myChart = echarts.init(chartDom);

      const option = {
        color: ["#3398DB"],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        title: {
          text: "Vision Trend",
          textStyle: {
            fontWeight: "normal",
            fontSize: 25,
            color: "#666",
          },
          left: "center",
          top: 20,
          padding: [10, 10, 0, 10],
        },
        xAxis: [
          {
            type: "category",
            data: this.date,
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: {
              interval: 1, // Set X-axis label interval
              rotate: 45, // Rotate X-axis labels
              textStyle: {
                fontSize: 12, // X-axis label style
              },
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisLabel: {
              textStyle: {
                fontSize: 12, // Y-axis label style
              },
            },
          },
        ],
        series: [
          {
            name: "Vision",
            type: "bar",
            barWidth: "60%",
            data: this.vision,
            itemStyle: {
              // Shadow blur
              shadowBlur: 5,
              // Shadow offset X
              shadowOffsetX: 2,
              // Shadow offset Y
              shadowOffsetY: 2,
              // Shadow color
              shadowColor: "rgba(0, 0, 0, 0.5)",
              // Bar corner radius
              barBorderRadius: 5,
            },
          },
        ],
      };

      myChart.setOption(option);
    },
    area() {
      const chartDom = document.getElementById("chart-containerLine");
      const myChart = echarts.init(chartDom);

      const option = {
        title: {
          text: "Blood Pressure & Blood Sugar Trends",
          textStyle: {
            fontWeight: "normal",
            fontSize: 25,
            color: "#666",
          },
          left: "center",
          top: 20,
          padding: [10, 10, 0, 10],
        },
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["Blood Pressure", "Blood Sugar"],
        },
        xAxis: {
          type: "category",
          data: this.date,
          axisLabel: {
            interval: 1, // Set X-axis label interval
            textStyle: {
              fontSize: 12, // X-axis label style
            },
          },
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            name: "Blood Pressure",
            data: this.bloodPressure,
            type: "line",
          },
          {
            name: "Blood Sugar",
            data: this.bloodSugar,
            type: "line",
          },
        ],
      };

      myChart.setOption(option);
    },
  },

  watch: {
    bodyInfo: {
      deep: true, //监听对象内部属性的变化
      async handler() {
        this.bmiM(); // 计算BMI值
        await this.getBodyNotes(); // 获取身体数据信息
        this.BarChart();
        this.area();
        const chartDom = this.$refs.myChart;
        const myChart = echarts.init(chartDom);

        const option = {
          title: {
            text: "Heart Rate Trend",
            textStyle: {
              fontWeight: "normal",
              fontSize: 25,
              color: "#666",
            },
            left: "center",
            top: 20,
          },
          xAxis: {
            type: "category",
            data: this.date,
            axisLabel: {
              fontSize: 12,
              interval: 2,
            },
            axisTick: {
              show: false,
            },
          },
          yAxis: {
            type: "value",
            axisLine: {
              show: false,
            },
            splitLine: {
              lineStyle: {
                type: "dashed",
                color: "#ddd",
              },
            },
            axisTick: {
              show: false,
            },
          },
          tooltip: {
            trigger: "axis",
            formatter: function (params) {
              return params[0].name + ": " + params[0].value;
            },
          },
          series: [
            {
              data: this.heartRate,
              type: "line",
              smooth: true,
              lineStyle: {
                width: 3,
                color: "#00bfff",
              },
              symbol: "circle",
              symbolSize: 8,
              itemStyle: {
                color: "#00bfff",
                borderColor: "#fff",
                borderWidth: 2,
              },
              markLine: {
                data: [
                  {
                    type: "average",
                    name: "Average",
                  },
                ],
                label: {
                  position: "insideEndBottom",
                  formatter: "{b}: {c}",
                },
                lineStyle: {
                  type: "dashed",
                  color: "green",
                  width: 2,
                },
                symbol: "none",
              },
              animation: true,
              animationDuration: 3000,
              animationEasing: "cubicInOut",
            },
          ],
        };

        myChart.setOption(option);
      },
    },
  },

  created() {
    this.getBodyInfo();
  },

  async mounted() {},
};
</script>
<style scoped>
.bg-purple-dark {
  background: #ffffff;
  box-shadow: 0px 0px 1px #000000;
}

.unit {
  font-size: 20px;
  font-weight: normal;
  margin-left: 5px;
  color: #555;
}
.grid-content {
  border-radius: 4px;
  min-height: 130px; /* increase the height for better visibility */
  display: flex; /* center content horizontally and vertically */
  justify-content: center;
  align-items: center;
  margin-top: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* Add box shadow on all sides */
}

.row-bg {
  padding: 10px;
  background-color: #f9fafc;
}
.div2 {
  margin-top: 15%;
  height: 500px;
  width: 100%;
  background-color: #555;
  display: flex;
  flex-direction: row;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* Add box shadow on all sides */
}

.box {
  height: 100%;
  width: 100%;
  flex-grow: 1;
  background: #ffffff;
  box-shadow: 0px 0px 1px #000000;
}
.col6 {
  font-size: 26px;
  font-weight: bold;
  color: #313131;
  text-align: right;
  padding-right: 10px;
  margin-top: -30px;
}

.col6zhi {
  font-size: 20px;
  font-weight: bold;
  color: #746c6c;
  padding-top: 30px;
}

.col1 {
  margin-right: -80%;
  padding-top: 20px;
}
</style>
