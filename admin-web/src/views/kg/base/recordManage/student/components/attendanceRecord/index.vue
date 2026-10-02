<template>
  <div v-if="show">
    <el-button type="primary" @click="$emit('close')" style="marginBottom:20px">返回</el-button>
    <el-row :gutter="10">
      <el-col :span="6">
        <Calendar @chooseTime="chooseTime">
          <template v-slot:item="{row}">
            <div :class="checkHoliday(row.day)">
              {{ checkHoliday(row.day) === 'rest' ? '休' :row.day }}
            </div>
          </template>
        </Calendar>
      </el-col>
      <el-col :span="18">
        <el-table v-loading="loading" :data="attendanceData" border stripe>
          <el-table-column label="学生姓名" align="center" prop="studentName" />
          <el-table-column label="体温监测" align="center" prop="temperature" />
          <el-table-column label="检测时间" align="center" prop="time" />
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Calendar from '@/components/Calendar'
import { getCalendarInfo, getAttendanceData } from '@/api/kg/base/student'
export default {
  components: {
    Calendar
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    studentInfo: {
      type: Object,
      default: () => ({})
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.date = this.parseTime(new Date(), '{y}-{m}-{d}')
        this.getCalendarInfo()
        this.getList()
      }
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      attendanceData: [],
      date: '',
      // 节假日
      holidayArr: [],
      // 考勤日
      attendanceArr: []
    }
  },
  mounted() {},
  methods: {
    // 获取日历信息
    getCalendarInfo() {
      getCalendarInfo({
        date: this.date,
        studentId: this.studentInfo.id
      }).then((res) => {
        if (res.code === 200) {
          if (res.data.length > 0) {
            this.holidayArr = res.data
              .filter((item) => item.isRest)
              .map((item) => item.day)

            this.attendanceArr = res.data
              .filter((item) => item.hasChecking)
              .map((item) => item.day)
          }
        }
      })
    },
    // 获取出勤信息
    getList() {
      this.loading = true
      getAttendanceData({
        date: this.date,
        studentId: this.studentInfo.id
      }).then((res) => {
        this.attendanceData = res.data
        this.loading = false
      })
    },
    // 判断是不是节假日
    checkHoliday(day) {
      let flag = ''
      // 判断是否是节假日
      if (this.holidayArr.includes(day)) {
        flag = 'rest'
      }
      // 判断是否有考勤记录
      if (this.attendanceArr.includes(day)) {
        flag = 'hasAttendance'
      }
      return flag
    },
    // 选择时间
    chooseTime(time, item) {
      this.date = time
      this.getList()
      // 判断是否是下个月
      if (item.type !== 0) {
        this.getCalendarInfo()
      }
    }
  }
}
</script>

<style lang='scss' scoped>
.hasAttendance {
  background: #186ba0 !important;
  color: #fff;
}
</style>
