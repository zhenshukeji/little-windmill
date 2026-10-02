<template>
  <div class="app-container">
    <div v-show="!opeVisible && !historyVisible && !backVisible && !studentVisible && !attendanceVisible" class="content">
      <!-- 搜索-S -->
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="80px" label-position="left">
        <el-row>
          <el-col :span="18">
            <el-form-item label="信息:" prop="info">
              <el-input v-model="queryParams.info" placeholder="请输入姓名/监护人/手机" clearable size="small" style="width:190px"
                        @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item v-if="!isTeacher" label="班级:" prop="classId">
              <el-select v-model="queryParams.classId" clearable placeholder="请选择班级" style="width:180px">
                <el-option v-for="item in classList" :key="item.id" :label="item.className" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="高危体弱:" prop="isWeak">
              <el-select v-model="queryParams.isWeak" clearable placeholder="请选择是否高危体弱" style="width:180px">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="录入人脸:" prop="hasFace">
              <el-select v-model="queryParams.hasFace" clearable placeholder="请选择是否录入人脸" style="width:180px">
                <el-option label="否" :value="false"></el-option>
                <el-option label="是" :value="true"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="text-align: right">
            <el-form-item>
              <el-button v-if="!isTeacher" type="primary" size="mini" @click="handleAdd">新增
              </el-button>
              <el-dropdown v-if="!isTeacher" trigger="click" @command="handleCommand">
                <el-button type="primary" plain size="mini" style="marginLeft:10px">更多 <i class="el-icon-arrow-down"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="0">模板下载</el-dropdown-item>
                  <el-dropdown-item :command="1">导入</el-dropdown-item>
                  <el-dropdown-item :command="2">导出</el-dropdown-item>
                  <el-dropdown-item :command="3">返校重读</el-dropdown-item>
                  <el-dropdown-item :command="4">历史档案</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>

            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 搜索-E -->
      <!-- 表格 -->
      <el-table v-loading="loading" :data="studentList" :border="true">

        <el-table-column label="班级" align="center" prop="className" />

        <el-table-column label="学生姓名" align="center" prop="name">
          <template slot-scope="{row}">
            <span class="name" @click="handleStudent(row)">{{ row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="性别" align="center" prop="gender">
          <template slot-scope="{row}">
            <span v-if="row.gender === 0">男</span>
            <span v-if="row.gender === 1">女</span>
            <span v-if="row.gender === 2">未知</span>
          </template>
        </el-table-column>

        <el-table-column label="监护人1" align="center" prop="nameArr[0]" />
        <el-table-column label="联系电话" align="center" prop="phoneArr[0]" />
        <el-table-column label="监护人2" align="center" prop="nameArr[1]" />

        <el-table-column label="联系电话" align="center" prop="phoneArr[1]" />

        <el-table-column label="高危体弱" align="center" prop="isWeak">
          <template slot-scope="{row}">
            <span v-if="row.isWeak">是</span>
            <span v-if="row.isWeak === false">否</span>
          </template>
        </el-table-column>
        <el-table-column label="特殊情况" align="center" prop="specialCase" />

        <el-table-column width = "240px" label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <el-button size="mini" type="text" @click="handleAttendance(row)">考勤信息</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button size="mini" type="text" @click="handleUpdate(row)">修改</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button size="mini" type="text" @click="handleFace(row)">
              <span :class="{ 'gray': !row.realImgUrl }">人脸</span>
            </el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button size="mini" type="text" @click="handleLeave(row)" style="color:#FF0101">离校</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>

    <!-- 新增/修改学校 -->
    <opeStudent :show="opeVisible" :info="opeInfo" @close="closePopup" />
    <!-- 导出学生 -->
    <exportInfo :show="exportVisible" @close="exportVisible = false" @handleExport="handleExport" />
    <!-- 历史档案 -->
    <historyRecord :show="historyVisible" @close="historyVisible = false" />
    <!-- 返校重读 -->
    <backSchool :show="backVisible" @close="closePopup" />
    <!-- 学校信息 -->
    <studentInfo :show="studentVisible" :info="studentInfo" @close="studentVisible = false" />
    <!-- 导入 -->
    <el-dialog title="导入" :visible.sync="uploadVisible" width="600px">
      <el-divider />
      <el-upload class="upload-demo" action="" drag :show-file-list="false" :http-request="importFile">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-dialog>
    <!-- 考勤信息 -->
    <attendanceRecord :show="attendanceVisible" :studentInfo="studentInfo" @close="attendanceVisible = false" />

    <!-- 人脸 -->
    <el-dialog title="提示" :visible.sync="faceVisible" width="600px">
      <el-divider></el-divider>
      <el-descriptions :column="1">
        <el-descriptions-item label="照片">
          <img :src="faceInfo.realImgUrl" alt="" class="faceImg">
        </el-descriptions-item>
        <el-descriptions-item label="姓名">{{ faceInfo.name }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ faceInfo.updateTime }}
        </el-descriptions-item>
      </el-descriptions>
      <div class="dialog-footer">
        <el-divider></el-divider>
        <el-button @click="faceVisible = false">关闭</el-button>
        <el-button type="danger" style="marginLeft:40px" @click="delFace">删除人脸</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listStudent,
  classList,
  leaveSturdent,
  exportStudent,
  importStudent,
  delFace,
  checkIsTeacher
} from '@/api/kg/base/student'
import attendanceRecord from './components/attendanceRecord'
import exportExcel from '@/components/ExportExcel'
import opeStudent from './components/opeStudent'
import exportInfo from './components/exportInfo'
import historyRecord from './components/historyRecord'
import backSchool from './components/backSchool'
import studentInfo from './components/studentInfo'

export default {
  name: 'Student',
  components: {
    opeStudent,
    exportInfo,
    historyRecord,
    backSchool,
    exportExcel,
    studentInfo,
    attendanceRecord
  },
  data() {
    return {
      // 是否是教师
      isTeacher: false,
      // 考勤信息
      attendanceVisible: false,
      // 学生信息
      studentVisible: false,
      studentInfo: {},
      downloadLoading: false,
      faceInfo: {},
      faceVisible: false,
      // 导入
      uploadVisible: false,
      // 返校重读
      backVisible: false,
      // 历史档案
      historyVisible: false,
      exportVisible: false,
      opeVisible: false,
      opeInfo: {},
      classList: [],
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 学生表格数据
      studentList: [],
      // 是否显示弹出层
      opeVisible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: 0,
        classId: null,
        info: null,
        isWeak: null,
        hasFace: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()

    // 判断登录身份是否是教师
    this.checkIsTeacher()
  },
  methods: {
    /** 查询学生列表 */
    getList() {
      this.loading = true
      // 删除空参数
      let query = this.delObjEmpty(this.queryParams)

      listStudent(query).then((response) => {
        this.studentList = response.data.records
        this.studentList.forEach((item) => {
          if (item.guardianPhone) {
            item.phoneArr = item.guardianPhone.split(',')
          }
          if (item.guardianName) {
            item.nameArr = item.guardianName.split(',')
          }
        })
        this.total = response.data.total
        this.loading = false
      })
    },
    // 判断身份
    checkIsTeacher() {
      checkIsTeacher().then((res) => {
        if (res.code === 200) {
          this.isTeacher = res.data
          if (!this.isTeacher) {
            this.getClassList()
          }
        }
      })
    },
    // 获取班级列表
    getClassList() {
      classList().then((res) => {
        if (res.code === 200) {
          this.classList = res.data
        }
      })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.opeVisible = true
      this.opeInfo = {
        isAdd: true,
        classList: this.classList
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.opeInfo = {
        id: row.id,
        isAdd: false,
        classList: this.classList
      }
      this.opeVisible = true
    },
    //  关闭新增/修改弹窗
    closePopup() {
      this.getClassList()
      this.opeVisible = false
      this.backVisible = false
      this.resetQuery()
    },
    // 离校
    handleLeave(row) {
      this.$confirm('该学生将离校,是否确认?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          leaveSturdent({
            id: row.id
          }).then((res) => {
            if (res.code === 200) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.getList()
              this.getClassList()
            }
          })
        })
        .catch(() => { })
    },
    // 更多按钮
    handleCommand(val) {
      switch (val) {
        case 0:
          window.location.href =
            'https://${CDN_URL:}/kindergarten_admin/file/student_template.xls'
          break
        case 1:
          this.uploadVisible = true
          break
        case 2:
          this.exportVisible = true
          break
        case 3:
          this.backVisible = true
          break
        case 4:
          this.historyVisible = true
          break
      }
    },
    /** 导出按钮操作 */
    handleExport(form) {
      exportStudent(form).then((res) => {
        if (res.code === 200) {
          if (res.code === 200) {
            this.downloadFile({
              fileName: res.msg
            })
            this.exportVisible = false
          }
        }
      })
    },
    // 考勤信息
    handleAttendance(row) {
      this.studentInfo = row
      this.attendanceVisible = true
    },
    // 导入
    importFile(res) {
      console.log('res', res.file)
      var file = new FormData()
      file.append('file', res.file)
      importStudent(file).then((res) => {
        if (res.code == 200) {
          this.msgSuccess('导入成功')
          this.uploadVisible = false
          this.getList()
        }
      })
    },
    // 处理人脸
    handleFace(row) {
      // 如果没有人脸的话
      if (!row.realImgUrl) {
        return
      }
      this.faceInfo = row
      this.faceVisible = true
    },
    // 删除人脸
    delFace() {
      delFace(this.faceInfo.id).then((res) => {
        if (res.code === 200) {
          this.msgSuccess('删除成功')
          this.getList()
          this.faceVisible = false
        }
      })
    },
    // 学生信息
    handleStudent(row) {
      this.studentVisible = true
      this.studentInfo = row
    }
  }
}
</script>

<style lang="scss" scoped>
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
</style>
