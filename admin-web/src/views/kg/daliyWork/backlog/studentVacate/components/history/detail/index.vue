<template>
  <div class="app-container">
    <div class="box">
      <div class="title">
        <div class="name">儿童请假申请</div>
        <el-divider></el-divider>
      </div>
      <div v-if="detailData">
        <div class="desc">
          <span>请假类型：<span style="color: #1890ff">{{ detailData.vacateType === 0 ? "病假" : "事假" }}</span></span>
          <span>申请日期：{{ parseTime(detailData.applyTime, "{y}-{m}-{d}") }}</span>
        </div>
        <div class="desc">
          <span>请假时间：{{ detailData.beginTime }} - {{ detailData.endTime }}</span>
        </div>
        <el-divider></el-divider>
        <div class="desc">
          <span class="title-style">儿童信息</span>
        </div>
        <div class="desc">
          <span>姓名：{{ detailData.studentName }}</span>
          <span>班级：{{ detailData.className }}</span>
        </div>
        <div class="desc">
          <span>年龄：{{ detailData.age }}</span>
        </div>
        <el-divider></el-divider>
        <div class="desc">
          <span class="title-style">申请信息</span>
        </div>
        <div class="desc">
          <span>申请人：{{ detailData.applyName }}</span>
          <span>申请人电话：{{ detailData.applyPhone }}</span>
        </div>
        <div class="desc">
          <span>请假缘由：{{ detailData.vacateReason }}</span>
        </div>
        <el-divider></el-divider>
        <div class="desc">
          <span>处理结果：
            <span v-if="detailData.status === 0" style="color: red">待处理</span>
            <span v-if="detailData.status === 1" style="color: #1890ff">已同意</span>
            <span v-if="detailData.status === 2" style="color: red">已拒绝</span>
            <span v-if="detailData.status === 3" style="color: red">已撤回</span>
          </span>
        </div>
        <div class="desc">
          <span>审批人：{{ detailData.approveName }}</span>
          <span>审批时间：{{ detailData.approveTime }}</span>
        </div>
        <div class="desc">
          <span>审批意见：{{ detailData.approveOpinion }}</span>
        </div>
        <div style="text-align: center; margin-top: 20px">
          <el-button type="primary" @click="abandon">返回</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  vacateDetail,
} from "@/api/kg/work/backlog/studentVacate.js";
export default {
  props: {
    info: {
      default: () => {},
      type: Object
    }
  },
  components: {},
  data() {
    return {
      loading: true,
      id: null,
      detailData: {},
      // VisibleShow: false,
      form: {},
    };
  },
  mounted() {
    if (this.info.id) {
      this.id = this.info.id;
      this.getDetails();
    }
  },
  methods: {
    // 返回按钮
    abandon() {
      this.$emit("backHistory", false);
    },
    // 学生请假申请详情
    getDetails() {
      this.loading = true;
      vacateDetail(this.id).then((res) => {
        if (res.code === 200) {
          this.loading = false;
          this.detailData = res.data;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.box {
  .title {
    .name {
      margin-bottom: 10px;
      //color: #aaa9a9;
      font-size: 18px;
      padding-left: 8px;
      border-left: 4px solid #0088ff;
    }
  }
  .desc {
    padding: 0 30px;
    display: flex;
    margin-bottom: 30px;
    span {
      flex: 1;
    }
    .flex2 {
      flex: 2;
    }
  }
}
.tab_box {
  padding: 0 20px;
}
// 小模块标题
.title-style {
  color: #666666;
  //padding-top: 97px;
  padding-bottom: 11px;
}
</style>
