<!-- 员工档案 -->
<template>
  <div class="app-container">
    <!--<el-tab-pane label="本园离职员工" name="second">-->
    <!--  <KgFireStaff @openInfo="openInfo" :key="Date.parse(new Date())" />-->
    <!--</el-tab-pane>-->
    <div v-if="!showFireStaff">
      <div style="text-align: right">
        <el-button type="text" @click="showFireStaff = true">离职员工</el-button>
      </div>
      <el-tabs v-show="!showInfo" v-model="activeName" @tab-click="handleTapChange">
        <el-tab-pane label="本园在职员工" name="first">
          <!-- 强制刷新组件 -->
          <KgStaff @openInfo="openInfo" :key="Date.parse(new Date())" />
        </el-tab-pane>
        <!--<el-tab-pane label="本园离职员工" name="second">-->
        <!--  <KgFireStaff @openInfo="openInfo" :key="Date.parse(new Date())" />-->
        <!--</el-tab-pane>-->
        <el-tab-pane label="集团员工" name="third">
          <PostStaff @openInfo="openInfo" :key="Date.parse(new Date())" />
        </el-tab-pane>
      </el-tabs>

      <!-- 员工信息 -->
      <StaffInfo v-if="showInfo" :id="staffId" :type="staffType" @closeInfo="closeInfo" />
    </div>
    <KgFireStaff v-if="showFireStaff" @openInfo="openInfo" :key="Date.parse(new Date())" />
  </div>
</template>

<script>
import KgStaff from "./components/kgStaff/index.vue"
import KgFireStaff from "./components/kgFireStaff/index.vue"
import PostStaff from "./components/postStaff/index.vue"
import StaffInfo from "./components/staffInfo/index.vue"

export default {
  components: { KgStaff,KgFireStaff, PostStaff, StaffInfo },
  name: "Staff",
  data() {
    return {
     showFireStaff: false,
      // 当前tap，first-校区员工
      activeName: "first",
      // 学校员工 表格数据
      staffList: [],
      // 员工id，用于查询员工具体信息
      staffId: '',
      // 员工类型，0-学校，1-学校离职，2-集团
      staffType: '',
      // 展示员工信息
      showInfo: false,
    }
  },
  created() {
  },
  methods: {
    // 打开员工信息
    openInfo(val) {
      console.log(val);
      this.showInfo = true
      this.staffId = val.id
      this.staffType = val.type
    },
    // 关闭员工信息
    closeInfo() {
      this.showInfo = false
    },
    // tab 切换
    handleTapChange(val) {
      this.resetForm("queryForm")
      this.activeName = val.name
      switch (val.name) {
        case "first":
          break
        case "second":
          break
        case "third":
          break
      }
    },
  },
}
</script>

<style lang="scss" scoped>
</style>
