<template>
  <div class="home">
    <template v-if="identity == 'BLOC_ADMIN' || identity == 'BLOC_PEOPLE'">
      <div class="title">数据概览</div>
      <div class="app-container">
        <div class="head">
          <div class="tag">
            <div class="tag_title">今日收入</div>
            <div class="num_box">
              <div class="txt"><span>￥</span>{{ todayIncome.amount }}</div>
              <div class="icon">
                <img src="@/assets/images/tag_icon.png" alt="" />
              </div>
            </div>
            <div class="people">
              <div class="icon">
                <img src="@/assets/images/people_icon.png" alt="" />
              </div>
              <div class="people_txt">人数：{{ todayIncome.count }}人</div>
            </div>
            <div class="underline"></div>
            <div class="price_limit">
              <div class="rise_icon">
                <img src="@/assets/images/rise_icon.png" alt="" />
              </div>
            </div>
          </div>
          <div class="tag">
            <div class="tag_title">今日支出</div>
            <div class="num_box">
              <div class="txt"><span>￥</span>{{ todayExpend.amount }}</div>
              <div class="icon">
                <img src="@/assets/images/tag_icon.png" alt="" />
              </div>
            </div>
            <div class="people">
              <div class="icon">
                <img src="@/assets/images/people_icon.png" alt="" />
              </div>
              <div class="people_txt">人数：{{ todayExpend.count }}人</div>
            </div>
            <div class="underline"></div>
            <div class="price_limit">
              <div class="decline_icon">
                <img src="@/assets/images/decline_icon.png" alt="" />
              </div>
            </div>
          </div>
          <div class="tag">
            <div class="tag_title">
              {{ weekShow ? "本周收入" : "本周支出" }}
            </div>
            <div class="num_box">
              <div class="txt">
                <span>￥</span
                >{{ weekShow ? weekDate.income : weekDate.expend }}
              </div>
              <div class="icon">
                <img src="@/assets/images/tag_icon.png" alt="" />
              </div>
            </div>
            <div class="price_limit">
              <div class="rise_icon">
                <img src="@/assets/images/rise_icon.png" alt="" />
              </div>
            </div>
            <div class="underline"></div>
            <div class="btn_group">
              <div class="btn_box">
                <div
                  class="income_icon"
                  :class="{ week_active: weekShow }"
                  @click="weekClick(0)"
                >
                  收入
                </div>
                <div
                  class="expend_icon"
                  :class="{ week_active: !weekShow }"
                  @click="weekClick(1)"
                >
                  支出
                </div>
              </div>
            </div>
          </div>
          <div class="tag">
            <div class="tag_title">
              {{ monthShow ? "本月收入" : "本月支出" }}
            </div>
            <div class="num_box">
              <div class="txt">
                <span>￥</span
                >{{ monthShow ? monthDate.income : monthDate.expend }}
              </div>
              <div class="icon">
                <img src="@/assets/images/tag_icon.png" alt="" />
              </div>
            </div>
            <div class="price_limit">
              <div class="decline_icon">
                <img src="@/assets/images/decline_icon.png" alt="" />
              </div>
            </div>
            <div class="underline"></div>
            <div class="btn_group">
              <div class="btn_box">
                <div
                  class="income_icon"
                  :class="{ month_active: monthShow }"
                  @click="monthClick(0)"
                >
                  收入
                </div>
                <div
                  class="expend_icon"
                  :class="{ month_active: !monthShow }"
                  @click="monthClick(1)"
                >
                  支出
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="container">
          <div class="title">本月数据</div>
          <div ref="lineChart" class="lineChart"></div>
          <div ref="barChart" class="barChart"></div>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { getInfo } from "@/api/home";
import echarts from "echarts";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.1",
      weekShow: true,
      monthShow: true,
      monthDates: [], // 这是本月收入数据
      kgDates: [], // 校区数据
      lineChart: "", // 折线图
      barChart: "", // 柱状图
      timer: "", // 防抖间隔时间
      myObserver: "", // 存储实例化的web api的监听窗口大小变化的返回值
      date: [], //日期
      expend: [], // 支出
      income: [], // 收入
      kgExpend: [],
      kgIncome: [],
      kgName: [],
      todayExpend: {}, // 今日支出
      todayIncome: {}, // 今日收入
      weekDate: {}, //本周收入/支出
      monthDate: {}, //本月收入/支出
      identity: null,
    };
  },
  mounted() {
    this.identity = this.$store.state.user.user.identity;
    this.$nextTick(() => {
      if (this.identity == "BLOC_ADMIN" || this.identity == "BLOC_PEOPLE") {
        this.myObserver = new ResizeObserver((entries) => {
          if (this.timer) {
            clearTimeout(this.timer);
          }
          this.timer = setTimeout(() => {
            this.initChart();
          }, 50);
        });
        this.myObserver.observe(this.$refs.lineChart);
        this.myObserver.observe(this.$refs.barChart);
      }
    });
  },
  methods: {
    async getInfo() {
      const res = await getInfo();
      if (res.code === 200 && res.data && res.data.monthDates) {
        this.monthDates = res.data.monthDates;
        this.kgDates = res.data.kgDates;
        this.todayExpend = res.data.todayExpend;
        this.todayIncome = res.data.todayIncome;
        this.weekDate = res.data.weekDate;
        this.monthDate = res.data.monthDate;
      }

      if (this.monthDates && this.monthDates.length) {
        this.monthDates.forEach((item) => {
          this.date.push(item.date);
          this.expend.push(item.expend);
          this.income.push(item.income);
        });
      }

      this.kgDates.forEach((item) => {
        this.kgName.push(item.kindergartenName);
        this.kgExpend.push(item.expend);
        this.kgIncome.push(item.income);
      });
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    weekClick(index) {
      if (index === 1) {
        // console.log("点击了本周的支出按钮");
        this.weekShow = false;
      } else {
        // console.log("点击了本周的收入按钮");
        this.weekShow = true;
      }
    },
    monthClick(index) {
      if (index === 1) {
        // console.log("点击了本月的支出按钮");
        this.monthShow = false;
      } else {
        // console.log("点击了本月的收入按钮");
        this.monthShow = true;
      }
    },
    // 初始化echarts
    async initChart() {
      if (this.lineChart || this.barChart) {
        //如果已经实例化过，注销掉
        this.lineChart.dispose();
        this.barChart.dispose();
      }
      this.lineChart = echarts.init(this.$refs.lineChart);
      this.barChart = echarts.init(this.$refs.barChart);
      if (this.monthDates.length <= 0 || this.kgDates.length <= 0) {
        await this.getInfo();
      }
      this.setOptions();
      this.setBarOptions();
    },
    // 折线图配置项
    setOptions() {
      this.lineChart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {
          data: ["收入（元）", "支出（元）"],
        },
        color: ["#ff4650", "#2fc25b"],
        grid: {
          left: "3%",
          right: "3%",
          bottom: "12%",
          containLabel: true,
        },
        toolbox: {
          feature: {},
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.date,
        },
        yAxis: {
          axisLine: {
            show: false, //隐藏y轴
          },
          axisTick: {
            show: false, //刻度线
          },
          type: "value",
        },
        series: [
          {
            name: "收入（元）",
            type: "line",
            data: this.income,
          },
          {
            name: "支出（元）",
            type: "line",
            data: this.expend,
          },
        ],
      });
    },
    // 柱状图配置项
    setBarOptions() {
      this.barChart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {
          data: ["收入（元）", "支出（元）"],
        },
        color: ["#ff4650", "#2fc25b"],
        grid: {
          left: "3%",
          right: "3%",
          bottom: "12%",
          containLabel: true,
        },
        toolbox: {
          feature: {},
        },
        xAxis: {
          type: "category",
          data: this.kgName,
        },
        yAxis: {
          type: "value",
          axisLine: {
            show: false, //隐藏y轴
          },
          axisTick: {
            show: false, //刻度线
          },
        },
        series: [
          { type: "bar", name: "收入（元）", barGap: 0.1, data: this.kgExpend },
          { type: "bar", name: "支出（元）", barGap: 0.1, data: this.kgIncome },
        ],
      });
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  background-color: #f0f2f5;
  .title {
    padding: 10px 20px;
    font-size: 18px;
    font-weight: bold;
    background-color: #fff;
  }
  .head {
    height: 192px;
    display: flex;
    margin-bottom: 20px;
    .tag {
      flex: 1;
      padding: 20px 24px 0;
      margin-right: 24px;
      min-width: 278px;
      background-color: #fff;
      .tag_title {
        font-size: 14px;
        font-weight: bold;
        color: #7f7f7f;
      }
      .num_box {
        display: flex;
        align-items: center;
        justify-content: space-between;
        .txt {
          color: #feb72b;
          font-size: 30px;
          span {
            font-size: 20px;
          }
        }
      }
      .people {
        display: flex;
        color: #7f7f7f;
        font-weight: bold;
        align-items: center;
        .people_txt {
          margin-left: 3px;
          font-size: 14px;
        }
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
          -moz-user-select: none; /*⽕狐*/
          -webkit-user-select: none; /*webkit浏览器*/
          -ms-user-select: none; /*IE10*/
          -khtml-user-select: none; /*早期浏览器*/
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
    .lineChart {
      position: relative;
      width: 100%;
      height: 300px;
    }
    .barChart {
      width: 100%;
      height: 400px;
    }
  }
}
</style>
