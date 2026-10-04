<template>
  <div class="app-container">
    <div class="title">数据概览</div>
    <div class="head">
      <div class="tag">
        <div class="tag_title">今日收入/支出</div>
        <div class="tag_txt">
          <span>收入：</span>
          <span class="span-style">{{ todayIncome.expend }}</span>
          <span>元</span>
        </div>
        <div class="people">
          <div class="tag_txt">支出：<span class="span-style">{{ todayIncome.income }}</span>元</div>
        </div>
      </div>
      <div class="tag">
        <div class="tag_title">今日已缴费</div>
        <div class="tag_txt">人数：<span class="span-style">{{ todayExpend.count }}</span>人</div>
        <div class="tag_txt">金额：<span class="span-style">{{ todayExpend.amount }}</span>元</div>
      </div>
      <div class="tag">
        <div class="tag_title">今日待缴费</div>
        <div class="tag_txt">人数：<span class="span-style">{{ unpaid.count }}</span>人</div>
        <div class="tag_txt">金额：<span class="span-style">{{ unpaid.amount }}</span>元</div>
      </div>
      <div class="tag">
        <div class="tag_title">学生出勤</div>
        <div class="tag_txt">人数：<span class="span-style">{{ studentChecking.count }}</span>人</div>
        <div class="tag_txt">出勤率：<span class="span-style">{{ studentChecking.rate }}</span>%</div>
      </div>
    </div>

    <!-- 今日待办 -->
    <div class="container">
      <div class="title">今日待办</div>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="list">
            <div class="list_left">
              <div class="list_img">
                <svg-icon icon-class="medicine" />
              </div>
              <div class="list_num">
                {{ heyMedicine.pendingCount }}条喂药审批
              </div>
            </div>
            <el-divider direction="vertical"></el-divider>
            <div class="list_right">
              <!-- 喂药审批 -->
              <div v-for="(item, index) in heyMedicine.pendingList" :key="index" class="list_item">
                <div class="list_item_time">
                  {{ item.applyTime }}
                </div>
                <div class="list_item_content">
                  {{ item.className }}-{{ item.applyName }}申请喂药
                </div>
                <div class="list_item_txt" @click="medicineLook(item)">
                  查看详情>
                </div>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="list">
            <div class="list_left">
              <div class="list_img">
                <svg-icon icon-class="medicine" />
              </div>
              <div class="list_num">
                {{ resumeClasses.pendingCount }}条复课审批
              </div>
            </div>
            <el-divider direction="vertical"></el-divider>
            <div class="list_right">
              <!-- 复课审批 -->
              <div v-for="(item, index) in resumeClasses.pendingList" :key="index" class="list_item">
                <div class="list_item_time">
                  {{ item.applyTime }}
                </div>
                <div class="list_item_content">
                  {{ item.className }}-{{ item.applyName }}申请复课
                </div>
                <div class="list_item_txt" @click="resumeLook(item)">
                  查看详情>
                </div>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="list">
            <div class="list_left">
              <div class="list_img">
                <svg-icon icon-class="medicine" />
              </div>
              <div class="list_num">
                {{ studentVacate.pendingCount }}条请假审批
              </div>
            </div>
            <el-divider direction="vertical"></el-divider>
            <div class="list_right">
              <!-- 请假审批 -->
              <div v-for="(item, index) in studentVacate.pendingList" :key="index" class="list_item">
                <div class="list_item_time">
                  {{ item.applyTime }}
                </div>
                <div class="list_item_content">
                  {{ item.className }}-{{ item.applyName }}请假审批
                </div>
                <div class="list_item_txt" @click="vacateLook(item)">
                  查看详情>
                </div>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="list">
            <div class="list_left">
              <div class="list_img">
                <svg-icon icon-class="medicine" />
              </div>
              <div class="list_num">
                {{ payment.pendingCount }}条缴费审批
              </div>
            </div>
            <el-divider direction="vertical"></el-divider>
            <div class="list_right">
              <!-- 缴费审批 -->
              <div v-for="(item, index) in payment.pendingList" :key="index" class="list_item">
                <div class="list_item_time">
                  {{ item.applyTime }}
                </div>
                <div class="list_item_content">
                  {{ item.className }}-{{ item.applyName }}缴费审批
                </div>
                <div class="list_item_txt" @click="paymentLook(item)">
                  查看详情>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 底部表格 -->
    <div class="chart">
      <div class="title">考勤情况</div>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="chart_item">
            <el-row>
              <el-col :span="12">
                <div>今日各班级考勤情况</div>
                <p class="chart_item_txt">出勤人数:<span class="blue">{{ this.classCheckingCount.attendanceCount }}</span>人
                </p>
                <p class="chart_item_txt">缺勤人数:<span class="blue">{{ this.classCheckingCount.absenceCount }}</span>人</p>
              </el-col>
              <el-col :span="12">
                <el-select v-model="classId" placeholder="请选择" @change="selectClass">
                  <el-option v-for="item in classrooms" :key="item.id" :label="item.className" :value="item.id">
                  </el-option>
                </el-select>
                <div ref="myChartArea" class="barChart"></div>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="chart_item">
            <span>全校出勤人数分析</span>
            <div ref="barChart" class="barChart"></div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  dataSummary,
  getClassroom,
  getClassChecking
} from '@/api/kg/work/backlog/home'
import echarts from 'echarts'
export default {
  name: 'Index',
  data() {
    return {
      // 版本号
      version: '3.8.1',
      // 数据信息
      dataInfo: {},
      lineChart: '', // 折线图
      barChart: '', // 柱状图
      myChartArea: '', // 饼状图
      timer: '', // 防抖间隔时间
      myObserver: '', // 存储实例化的web api的监听窗口大小变化的返回值
      date: [], //日期
      expend: [], // 支出
      income: [], // 收入
      kgExpend: [],
      kgIncome: [],
      kgName: [],
      todayExpend: {}, // 今日支出
      todayIncome: {}, // 今日收入/支出
      weekDate: {}, //本周收入/支出
      monthDate: {}, //本月收入/支出
      identity: null,
      unpaid: {}, // 待缴费
      studentChecking: [], // 学生出勤
      heyMedicine: {}, // 喂药审批
      resumeClasses: {}, //复课审批
      studentVacate: {}, // 请假审批
      payment: {}, // 缴费审批
      checkingPeople: {
        count: [],
        data: []
      },
      classrooms: [],
      classList: [
        { value: 8, name: '缺勤率' },
        { value: 2, name: '出勤率' }
      ],
      classId: null,
      classCheckingCount: {}
    }
  },
  mounted() {
    this.getClassrooms()
    this.initChart()
  },
  methods: {
    // 获取喂药待审批
    medicineLook() {
      this.$router.push('/daliyWork/health/heyMedicineApprove');
    },

    // 获取复课待审批
    resumeLook() {
      this.$router.push('/daliyWork/health/resumeClassesApprove');
    },

    // 获取请假待审批
    vacateLook() {
      this.$router.push('/daliyWork/backlog/staffVacate');
    },

    // 获取缴费待审批
    paymentLook() {
      this.$router.push('/daliyWork/backlog/payment');
    },

    // 获取班级信息
    getClassrooms() {
      getClassroom().then((res) => {
        if (res.code === 200) {
          if (res.data.length > 0) {
            this.classrooms = res.data
            this.classId = this.classrooms[0].id
            this.getClassroomChecking()
          }
        }
      })
    },
    // 获取班级出勤情况
    getClassroomChecking() {
      getClassChecking(this.classId).then((res) => {
        if (res.code === 200) {
          this.classCheckingCount = res.data
          this.classList = []
          this.classList.push({
            value: this.classCheckingCount.absenceRate,
            name: '缺勤率'
          })
          this.classList.push({
            value: this.classCheckingCount.attendanceRate,
            name: '出勤率'
          })
        }
      })
    },
    // 班级筛选
    async selectClass() {
      if (this.myChartArea) {
        //如果已经实例化过，注销掉
        this.myChartArea.dispose()
      }
      this.myChartArea = echarts.init(this.$refs.myChartArea)
      this.getClassroomChecking()
      this.setPieOptions()
    },
    async getInfo() {
      const res = await dataSummary()
      // await this.selectClass();
      if (res.code === 200) {
        this.todayExpend = res.data.todayIncome
        this.todayIncome = res.data.toDayAmount
        this.unpaid = res.data.todayPending
        this.studentChecking = res.data.attendance
        this.heyMedicine = res.data.heyMedicine
        // 只显示前面三条
        this.heyMedicine.pendingList = this.heyMedicine?.pendingList.slice(0, 3)

        this.resumeClasses = res.data.resumeClasses
        // 只显示前面三条
        this.resumeClasses.pendingList = this.resumeClasses?.pendingList.slice(
          0,
          3
        )

        this.studentVacate = res.data.studentVacate
        // 只显示前面三条
        this.studentVacate.pendingList = this.studentVacate?.pendingList.slice(
          0,
          3
        )

        this.payment = res.data.payment
        this.payment.pendingList = this.payment?.pendingList.slice(0, 3)

        res.data.kgAttendance.forEach((item) => {
          this.checkingPeople.data.push(item.date)
          this.checkingPeople.count.push(item.count)
        })
      }
    },
    // 初始化echarts
    async initChart() {
      if (this.barChart || this.myChartArea) {
        //如果已经实例化过，注销掉
        this.barChart.dispose()
        this.myChartArea.dispose()
      }
      this.barChart = echarts.init(this.$refs.barChart)
      this.myChartArea = echarts.init(this.$refs.myChartArea)
      await this.getInfo()
      this.setBarOptions()
      this.setPieOptions()
    },
    // 柱状图配置项
    setBarOptions() {
      this.barChart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['出勤人数']
        },
        color: ['#247a68'],
        grid: {
          left: '3%',
          right: '3%',
          bottom: '12%',
          containLabel: true
        },
        toolbox: {
          feature: {}
        },
        xAxis: {
          type: 'category',
          data: this.checkingPeople.data,
          axisLabel: {
            //interval设置成 0 强制显示所有标签
            interval: 0,
            rotate: 30 //可以通过旋转解决标签显示不下的问题
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: false //隐藏y轴
          },
          axisTick: {
            show: false //刻度线
          }
        },
        series: [
          {
            type: 'bar',
            name: '出勤人数',
            barGap: 0.1,
            data: this.checkingPeople.count
          }
        ]
      })
    },
    // 饼状图配置项
    setPieOptions() {
      this.myChartArea.setOption({
        legend: {
          top: '5%',
          left: 'center'
        },
        color: ['#247a68', '#d9a967'],

        series: [
          {
            type: 'pie',
            radius: '50%',
            data: this.classList,
            label: {
              //删除指示线
              show: false
            }
          }
        ]
      })
    }
  }
}
</script>

<style scoped lang="scss">
// 分割线高度
.el-divider--vertical {
  min-height: 127px;
}

.row-style {
  padding: 10px;

  .col-style {
    padding: 10px;
    border: 1px solid #d0d0d0;
    margin-bottom: 20px;
  }
}

//background-color: #f0f2f5;
.title {
  margin-bottom: 15px;
  padding-left: 8px;
  font-size: 18px;
  font-weight: bold;
  background-color: #fff;
  border-left: 4px solid #247a68;
}

.head {
  margin-bottom: 40px;
  display: flex;
  height: 192px;

  .tag {
    margin-right: 24px;
    flex: 1;
    padding: 20px 24px 0;
    min-width: 278px;
    background-color: #fff;
    border-radius: 5px;
    border: #d0d0d0 solid 1px;

    .tag_title {
      font-size: 20px;
      color: #6c6b6b;
    }

    .num_box {
      display: flex;
      align-items: center;
      justify-content: space-between;

      .txt {
        color: #7f7f7f;
        font-weight: bold;
        font-size: 16px;

        span {
          font-size: 20px;
        }
      }
    }

    .people {
      margin-top: 18px;
      color: #6c6b6b;
      font-size: 14px;
      align-items: center;
    }

    .tag_txt {
      margin-top: 18px;
      color: #6c6b6b;
      font-size: 16px;
      align-items: center;
    }

    .underline {
      width: 100%;
      height: 1px;
      margin-bottom: 14px;
      margin-top: 20px;
      background-color: #e8e8e8;
    }

    .rise_icon {
      width: 137px;
      height: 20px;
    }

    .decline_icon {
      width: 137px;
      height: 20px;
    }

    .btn_group {
      .btn_box {
        display: flex;
        justify-content: center;
        -moz-user-select: none;
        /*⽕狐*/
        -webkit-user-select: none;
        /*webkit浏览器*/
        -ms-user-select: none;
        /*IE10*/
        -khtml-user-select: none;
        /*早期浏览器*/
        user-select: none;

        .income_icon {
          width: 56px;
          height: 22px;
          line-height: 22px;
          margin-right: 20px;
          font-size: 14px;
          text-align: center;
          color: #bbbbbb;
          font-weight: 400;
          cursor: pointer;
        }

        .expend_icon {
          width: 56px;
          height: 22px;
          line-height: 22px;
          text-align: center;
          font-size: 14px;
          font-weight: 400;
          color: #bbbbbb;
          cursor: pointer;
        }

        .week_active {
          color: #fff;
          background-color: #feb72b;
          border-radius: 17px;
        }

        .month_active {
          color: #fff;
          background-color: #feb72b;
          border-radius: 17px;
        }
      }
    }
  }

  .tag:last-child {
    margin-right: 0px;
  }
}

.container {
  background-color: #fff;

  .title {
    font-weight: bold;
    color: #3a3a3a;
  }

  .list {
    margin-bottom: 40px;
    display: flex;
    align-items: center;
    padding: 20px 0;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #d0d0d0;

    .list_left {
      width: 200px;
      text-align: center;

      .list_img {
        font-size: 30px;
      }

      .list_num {
        margin-top: 10px;
        font-size: 18px;
        color: #505559;
        white-space: nowrap;
      }
    }

    .list_right {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      width: 100%;
      height: 128px;
      padding: 0 30px;
      box-sizing: border-box;
      font-size: 14px;
      white-space: nowrap;
      overflow: hidden;

      .list_item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 16px;
        color: #101010;

        .list_item_content {
          max-width: 200px;
        }

        .list_item_txt {
          cursor: pointer;
          color: #247a68;
        }
      }
    }
  }

  .lineChart {
    position: relative;
    width: 100%;
    height: 300px;
  }
}

.chart {
  .chart_item {
    border-radius: 5px;
    border: 1px solid #d0d0d0;
    padding: 20px 30px;
    box-sizing: border-box;
    color: #6c6c6c;
    font-size: 20px;

    .chart_item_txt {
      margin-top: 30px;
      font-size: 18px;

      .blue {
        color: #247a68;
        margin: 0 6px;
      }
    }

    .barChart {
      width: 100%;
      height: 400px;
    }
  }
}

.span-style {
  color: #ff6200;
  margin: 0 6px;
}
</style>
