<template>
  <div class="app-container">
    <template v-if="!showDetail">
      <!-- 搜索-S -->
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-position="left">
        <el-row>
          <el-col :span="23">
            <el-form-item label="学生姓名:" prop="studentName">
              <el-input v-model="queryParams.studentName" clearable placeholder="请输入学生姓名" size="small" style="width:180px">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="medium" @click="handleQuery">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <el-form-item class="form-item">
              <el-button type="primary" size="medium" @click="back">返回</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="listData"
        :border="true"
        stripe
        :header-cell-style="{'font-size': '15px'}"
        :cell-style ="{'font-size': '15px'}">
        <el-table-column label="学生姓名" align="center" prop="studentName" />
        <el-table-column label="班级" align="center" prop="className" />
        <el-table-column label="联系电话" align="center" prop="phone" />
        <el-table-column label="请假类别" align="center">
          <template slot-scope="{row}">
            <span v-if="row.vacateType === 0">病假</span>
            <span v-if="row.vacateType === 1">事假</span>
          </template>
        </el-table-column>
        <el-table-column width = "320" label="请假时间" align="center">
          <template slot-scope="{row}">
            {{row.beginTime}} - {{row.endTime}}
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="{row}">
            <span v-if="row.status === 0">待处理</span>
            <span v-if="row.status === 1">已处理</span>
            <span v-if="row.status === 2">拒绝</span>
            <span v-if="row.status === 3">撤回</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width">
          <template slot-scope="{ row }">
            <el-button size="mini" style="font-size: 15px" type="text" @click="handleRecord(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </template>
    <detailHistory v-if="showDetail" :info="info" @backHistory="backPage"></detailHistory>
  </div>
</template>

<script>
import {historyList} from "@/api/kg/work/backlog/studentVacate.js";
import upload from '@/components/UpLoad';
import PriceInput from '@/components/PriceInput';
import detailHistory from './detail';

export default {
  name: 'Staff',
  components: {
    upload,
    PriceInput,
    detailHistory,
  },
  data() {
    return {
      info: null,
      showDetail: false,
      // 时间
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      attendanceVisible: false,
      measureTimeVisible: false,
      measureInfoVisible: false,
      classList: [],
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 学生表格数据
      listData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentName: null,
      },
      // 修改温度/时间
      measureTimeInfo: {
        temperature: '',
        measureTime: '',
        studentId: '',
        time: ''
      },

      // 修改考勤
      attendanceInfo: {
        measureTime: "",
        status: "",
        studentId: '',
        temperature: ''
      },
      // 体温信息
      measureInfo: {
        classId: "",
        maxDate: "",
        maxTemperature: '',
        minDate: "",
        minTemperature: '',
        status: "",
        studentName: ""
      },
      measureArr: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    // 查询
    handleQuery () {
      this.getList();
    },
    // 详情返回
    backPage (val) {
      this.showDetail = val;
    },
    // 详情
    handleRecord (row) {
      this.info = row;
      this.showDetail = true;
    },
    /** 查询学生列表 */
    getList() {
      this.loading = true
      // 删除空参数
      let query = this.delObjEmpty(this.queryParams)
      historyList(query).then((response) => {
        this.listData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 返回上个页面
    back() {
      this.$emit("abandon",false)
    },
  }
}
</script>

<style lang="scss" scoped>
  .app-container {
    background-color: #fff;
  }
  .el-form--inline .el-form-item .form-item {
    margin-right: 20px;
  }
::v-deep .el-upload {
  display: block;

  .el-upload-dragger {
    margin: 0 auto;
  }
}

::v-deep .el-descriptions-item__label {
  font-weight: bold;
}

.faceImg {
  width: 100px;
  height: 100px;
}

.gray {
  color: #b1b1b1;
}

.name {
  cursor: pointer;
  color: #0099ff;
}

.el-icon-edit {
  margin-left: 10px;
  cursor: pointer;
}

.input {
  margin-bottom: 20px;
  display: flex;
  align-items: center;

  .input_desc {
    width: 100px;
  }
}
</style>
