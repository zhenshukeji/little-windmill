<template>
  <div class="content">
    <div class="examination_page" v-if="!showHistory">
      <div class="left">
        <tab class="tab-style" title="儿童列表" :tabData="studentDataList" :total="total" @changePage="changePage"
          @handleChoose="handleChoose">
          <template v-slot="{ item }">
            <el-row>
              <el-col :span="12">{{ item.className }}</el-col>
              <el-col :span="12">{{ item.studentName }}</el-col>
            </el-row>
          </template>
        </tab>

      </div>
      <div class="right">
        <div>
          <div class="box">
            <div class="title">
              <el-row>
                <el-col :span="20">
                  <div class="name">儿童请假申请</div>
                </el-col>
                <el-col :span="4">
                  <el-button style="font-size: 16px;color: #247a68" type="text" @click="showHistory = true">申请历史
                  </el-button>
                </el-col>
              </el-row>
            </div>
            <div class="item">
              <div class="row1 details">
                <div>请假类型：<span style="color: #247a68">{{ detailData.type === 0 ? "病假" : "事假" }}</span></div>
                <div>申请日期：{{ parseTime(detailData.applyTime, "{y}-{m}-{d}") }}</div>
              </div>
              <div class="row1 details">
                <div>请假时间：{{ detailData.beginTime }} - {{ detailData.endTime }}</div>
              </div>
              <el-divider></el-divider>
              <div class="title-style">儿童信息</div>
              <div class="row2 details">
                <div>姓名：{{ detailData.studentName }}</div>
                <div>班级：{{ detailData.className }}</div>
              </div>
              <div class="row3 details">
                <div>年龄：{{ detailData.age }}</div>
              </div>
              <el-divider></el-divider>
              <div class="title-style">申请信息</div>
              <div class="row4 details">
                <div>申请人：{{ detailData.applyName }}</div>
                <div>申请人电话：{{ detailData.applyPhone }}</div>
              </div>
              <div class="row4 details">
                <div>请假缘由：{{ detailData.vacateReason }}</div>
              </div>
              <el-divider></el-divider>
              <div class="row4 details">
                <el-row>
                  <el-col :span="2">
                    <div>审批意见：</div>
                  </el-col>
                  <el-col :span="22">
                    <el-input placeholder="输入审批意见" :autosize="{ minRows: 8, maxRows: 10 }" type="textarea"
                      v-model="detailData.approveOpinion"></el-input>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
          <div v-if="detailData.id" class="btn" style="margin-left: 110px">
            <el-button type="primary" @click="apply(true)">同意</el-button>
            <el-button @click="apply(false)">不同意</el-button>
          </div>
        </div>
      </div>
    </div>
    <history v-if="showHistory" @abandon="abandon"></history>
  </div>
</template>

<script>
import {
  studentList,
  vacateDetail,
  vacateApprove,
  getClass,
  list,
  delExamination,
  details,
  exportExamination
} from '@/api/kg/work/backlog/studentVacate.js'
import tab from '@/components/tab'
import history from './components/history'
export default {
  components: { history, tab },
  data() {
    return {
      dialogVisible: false,
      showHistory: false,
      form: {},
      detailData: {},
      loading: true,
      listData: [],
      total: 0,
      studentDataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentName: null
      },
      classIdList: [],
      changeType: 0, // 0 新增 1修改 2详情
      showNext: false
    }
  },
  mounted() {
    this.typeList()
  },
  methods: {
    // 学生请假列表
    typeList() {
      studentList(this.queryParams).then((res) => {
        if (res.code === 200) {
          this.studentDataList = res.data.records
          this.total = res.data.total
          if (this.studentDataList.length > 0) {
            this.getDetail(this.studentDataList[0])
          }
        }
      })
    },
    // 翻页
    changePage(val) {
      this.queryParams.pageNum += val
      this.typeList()
    },
    // 选中
    handleChoose(item) {
      this.getDetail(item)
    },
    // 申请审批
    apply(val) {
      // 判断是否有数据
      if (!this.detailData.id) {
        return
      }
      // 判断是否有数据
      if (!this.detailData.approveOpinion) {
        this.msgError('请输入审批意见')
        return
      }
      const query = {
        id: this.detailData.id,
        approveOpinion: this.detailData.approveOpinion,
        result: val
      }
      vacateApprove(query).then((res) => {
        if (res.code === 200) {
          this.detailData = {}
          this.typeList()
          this.msgSuccess('审批成功')
          this.detailData.approveOpinion = ''
        }
      })
    },
    // 返回
    abandon(val) {
      this.detailData = {}
      this.showHistory = false
      this.typeList()
    },
    // 学生请假详情
    getDetail(row) {
      vacateDetail(row.id).then((res) => {
        if (res.code === 200) {
          this.detailData = res.data
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  //padding: 20px;
  background-color: #f0f0f0;
  padding: 20px 20px 0;
  min-height: calc(100vh - 84px);
}

.examination_page {
  padding: 20px;
  background-color: #fff;
  display: flex;

  .left {
    min-width: 280px;
    margin-right: 30px;
    border-right: 1px solid #E9E9E9;
  }

  .right {
    padding-right: 20px;
    flex: 1;
  }
}

.box {
  margin-bottom: 10px;

  .title {
    border-bottom: 1px solid #ececec;
    margin-bottom: 30px;

    .name {
      margin-bottom: 10px;
      //color: #aaa9a9;
      font-size: 21px;
      //padding-left: 8px;
      //border-left: 4px solid #0088ff;
    }
  }

  .item {
    // border-top: 1px solid #ececec;
    //border-bottom: 1px solid #ececec;
    //padding: 10px 0;
    margin-bottom: 10px;

    .row1,
    .row2 {
      display: flex;

      ::v-deep {
        .el-form-item {
          flex: 1;
        }
      }
    }

    .row3,
    .row4 {
      width: 100%;

      ::v-deep {
        .el-form-item {
          display: flex;
          width: 100%;

          .el-form-item__content {
            width: 100%;
          }
        }
      }
    }

    .student_box {
      display: flex;

      .studentSelect_active {
        margin-right: 20px;
        user-select: none;
      }

      .studentSelect {
        width: 120px;
        text-align: left;
        color: blue;
        user-select: none;
        cursor: pointer;
      }
    }

    .details {
      display: flex;
      margin-bottom: 20px;
      font-size: 16px;

      div {
        flex: 1;
      }
    }

    .c2 {
      display: flex;

      ::v-deep {
        .el-form-item {
          flex: 1;
        }

        .el-form-item:last-child {
          flex: 2;
        }
      }

      div {
        flex: 1;
      }

      div:last-child {
        flex: 2;
      }
    }
  }
}

.title-style {
  font-size: 16px;
  color: #666666;
  padding-bottom: 11px;
}

// 分页组件样式
::v-deep .page {
  margin-top: 0;
}
</style>
