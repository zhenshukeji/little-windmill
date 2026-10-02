<template>
  <div class="calendar">
    <div class="calendar_warp">
      <!-- 顶部操作栏 -->
      <div class="head_ope">
        <i class="el-icon-d-arrow-left" @click="changeYear(-1)"></i>
        <i class="el-icon-arrow-left" @click="changeMonth(-1)"></i>
        <div class="head_content">
          {{ year }}年{{ month + 1 }}月
        </div>
        <i class="el-icon-arrow-right" @click="changeMonth(1)"></i>
        <i class="el-icon-d-arrow-right" @click="changeYear(1)"></i>
      </div>
      <template v-for="(item) in daysArr">
        <div class="calendar_box">
          <!-- 周 -->
          <div class="calendar_container">
            <div v-for="(unit, index) in weekDay" :key="index" class="calendar_item">
              <span class="calendar_item_unit">{{ unit }}</span>
            </div>
          </div>
          <div v-for="(day, k1) in item" :key="k1" class="calendar_container">
            <div v-for="(item2, index2) in day" :key="index2" :class="{
              today: item2.today,
              selected: item2.type === 0 && item2.day === chooseDay,
              gray:item2.type === -1 || item2.type === 1
            }" class="calendar_item" @click="chooseItem(item2)">
              <slot name="item" :row="item2">
                <span class="day">{{ item2.day }}</span>
              </slot>
            </div>
          </div>
        </div>
      </template>

      <!-- 底部操作栏 -->
      <div class="bottom_ope" @click="handleToday()">
        今天
      </div>
    </div>
  </div>

</template>

<script>
export default {
  props: {
    time: {
      type: [String, Date, Number],
      default: () => new Date()
    }
    //
  },
  watch: {
    // 监听传入的时间变化
    time(newValue) {
      if (newValue) {
        this.init()
      }
    }
  },
  data() {
    return {
      // 存放日历数组
      daysArr: [],
      year: '',
      month: '',
      date: '',
      // 选择的天
      chooseDay: '-1',
      weekDay: ['日', '一', '二', '三', '四', '五', '六']
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      // 日历初始化
      let now = new Date(this.time)
      this.year = now.getFullYear()
      this.month = now.getMonth()
      this.chooseDay = now.getDate()
      // 如果存在默认日期
      this.render()
    },
    // 渲染日期
    render() {
      // 清空数组
      this.daysArr = []
      let firstDayOfMonth = new Date(this.year, this.month, 1).getDay() //当月第一天是星期几
      console.log('firstDayOfMonth', firstDayOfMonth)
      let lastDateOfMonth = new Date(this.year, this.month + 1, 0).getDate() //当月最后一天
      let lastDayOfLastMonth = new Date(this.year, this.month, 0).getDate() //上个月的最后一天
      let nextDayOfLastMonth = new Date(this.year, this.month + 1, 1).getDay() //下个月的第一天
      console.log('lastDayOfLastMonth', lastDayOfLastMonth)
      // 显示今天日期
      let date = new Date()
      let nowYear = date.getFullYear()
      let nowMonth = date.getMonth()
      let nowDate = date.getDate()
      let i,
        line = 0,
        temp = []
      // 通过遍历获取本月日历
      for (i = 1; i <= lastDateOfMonth; i++) {
        // 获取每一天是星期几
        let day = new Date(this.year, this.month, i).getDay() //返回星期几（0～6）
        // 第一行
        // 当月第一天是星期天
        if (day == 0) {
          temp[line] = []
        } else if (i == 1) {
          // 渲染第一行数据
          temp[line] = []
          // 渲染上一个月的数据 第一行的数据
          for (let j = firstDayOfMonth; j > 0; j--) {
            temp[line].push(
              Object.assign({ day: lastDayOfLastMonth - j + 1, type: -1 })
            )
          }
        }
        let isToday = false
        // 当天
        if (nowYear === this.year && nowMonth === this.month && i === nowDate) {
          isToday = true
        } else {
          isToday = false
        }
        // 推送数据
        // type -1 上个月 0 这个月 1下个月
        temp[line].push(
          Object.assign({
            day: i,
            today: isToday,
            type: 0
          })
        )

        // 到周六换行
        if (day == 6 && i < lastDateOfMonth) {
          line++
        } else if (i == lastDateOfMonth) {
          // 渲染下一个月的数据
          for (let d = 6; d > day; d--) {
            temp[line].push(Object.assign({ day: 6 - d + 1, type: 1 }))
          }
        }
      }
      this.daysArr.push(temp)
      console.log('daysArr', this.daysArr)
    },
    // 选择了某一天
    chooseItem(item) {
      console.log('item', item)
      this.chooseDay = item.day
      // 上个月
      if (item.type === -1) {
        this.changeMonth(-1)
      } else if (item.type === 1) {
        this.changeMonth(1)
      }
      this.chooseTime(item)
    },
    // 触发父级事件
    chooseTime(item = {}) {
      this.$emit(
        'chooseTime',
        `${this.year}-${
          this.month + 1 < 10 ? '0' + (this.month + 1) : this.month + 1
        }-${this.chooseDay < 10 ? '0' + this.chooseDay : this.chooseDay}`,
        item
      )
    },
    // 切换年份
    changeYear(type) {
      this.year += type
      if (this.year < 0) {
        this.year = 0
      }
      if (this.year > 3000) {
        this.year = 3000
      }
      this.render()
    },
    // 切换月份
    changeMonth(type) {
      this.month += type
      // 边界
      if (this.month === 12) {
        this.month = 0
        this.changeYear(1)
      }
      if (this.month === -1) {
        this.month = 11
        this.changeYear(-1)
      }
      this.render()
    },
    // 今天
    handleToday() {
      let now = new Date()
      this.year = now.getFullYear()
      this.month = now.getMonth()
      this.chooseDay = now.getDate()
      this.render()
      this.chooseTime()
    }
  }
}
</script>

<style lang='scss' scoped>
.calendar {
  max-width: 300px;
  width: 100%;
  margin: 0 auto;

  .calendar_warp {
    padding: 10px 0 10px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);

    .head_ope {
      display: flex;
      justify-content: space-around;
      align-items: center;
      padding-bottom: 10px;
      box-sizing: border-box;
      border-bottom: 1px solid #eaeaea;

      .head_content {
        color: #101010;
        font-weight: bold;
      }

      i {
        cursor: pointer;
      }
    }

    .calendar_box {
      flex-shrink: 0;

      .calendar_container {
        z-index: 1;
        position: relative;
        display: flex;
        height: 30px;

        .calendar_item {
          color: #232e3a;
          flex: 1;
          text-align: center;
          font-size: 14px;
          line-height: 30px;
          cursor: pointer;

          .calendar_item_unit {
            color: #101010;
          }
        }
        .gray {
          color: #b8bcc5;
        }

        .selected {
          background-color: #1b91ff !important;
          border-radius: 4px;
          color: #ffffff;
        }

        .today {
          background-color: #e7f7ff;
          border-radius: 4px;
        }
      }

      .calendar_bg {
        position: absolute;
        top: 0;
        left: 0;
        width: 390px;
        height: 360px;

        img {
          width: 390px;
          height: 360px;
        }
      }
    }

    .calendar_month {
      margin-left: 10px;
      width: 280px;
      color: #9b0e16;
      text-align: center;

      .month {
        position: relative;
        top: -20px;
        font-size: 220px;
        font-family: 'siyuan';
      }

      .month_desc {
        position: relative;
        top: -40px;
        font-family: 'siyuanR';
        font-size: 40px;
      }
    }

    .bottom_ope {
      padding-top: 10px;
      border-top: 1px solid #eaeaea;
      text-align: center;
      color: #1b91ff;
      font-size: 14px;
      cursor: pointer;
    }
  }
}
</style>
