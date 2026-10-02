<template>
  <div v-show="show" class="backSchool">
    <div class="title">
      <div class="name">
        返校重读
      </div>
      <el-divider></el-divider>
    </div>

    <el-form ref="form" :model="form" label-width="120px" label-position="left">
      <!-- 步骤一 -->
      <div v-show="stepIndex === 0" class="step_one">
        <el-form-item label="身份证号码:">
          <el-input v-model="queryParams.identityNumber" clearable placeholder="请输入身份证号码" style="width:400px"></el-input>
          <el-button type="primary" @click="handleQuery" style="marginLeft:20px">查询</el-button>
        </el-form-item>

        <el-form-item label="待返校学生:">
          <!-- 表格 -->
          <el-table v-loading="loading" :data="studentList" border highlight-current-row>
            <el-table-column label="选择" align="center" width="55">
              <template slot-scope="{row}">
                <el-checkbox v-model="row.checked" @change="changeChecked(row)"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="学生姓名" align="center" prop="name" />
            <el-table-column label="年龄" align="center" prop="age" />
            <el-table-column label="性别" align="center" prop="gender">
              <template slot-scope="{row}">
                <span v-if="row.gender === 0">男</span>
                <span v-if="row.gender === 1">女</span>
                <span v-if="row.gender === 2">未知</span>
              </template>
            </el-table-column>
            <el-table-column label="原班级" align="center" prop="className" />
            <el-table-column label="监护人姓名" align="center" prop="nameArr[0]" />
            <el-table-column label="监护人电话" align="center" prop="phoneArr[0]" />
          </el-table>

          <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
        </el-form-item>

      </div>

      <!-- 步骤二 -->
      <div v-show="stepIndex === 1" class="step_two">
        <el-form-item label="待返校学生:">
          <el-table :data="selectStudent" border>
            <el-table-column label="学生姓名" align="center" prop="name" />
            <el-table-column label="年龄" align="center" prop="age" />
            <el-table-column label="性别" align="center" prop="gender">
              <template slot-scope="{row}">
                <span v-if="row.gender === 0">男</span>
                <span v-if="row.gender === 1">女</span>
                <span v-if="row.gender === 2">未知</span>
              </template>
            </el-table-column>
            <el-table-column label="原班级" align="center" prop="className" />
            <el-table-column label="监护人姓名" align="center" prop="nameArr[0]" />
            <el-table-column label="监护人电话" align="center" prop="phoneArr[0]" />
          </el-table>
        </el-form-item>
        <el-form-item label="分配班级:">
          <el-select v-model="classId" clearable placeholder="请选择班级" @change="changeClass">
            <el-option v-for="item in classList" :key="item.id" :label="item.className" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任课老师:">
          {{ classInfo.teacherName }}
        </el-form-item>
        <el-form-item label="班级学生数量:">
          {{ classInfo.studentCount }}
        </el-form-item>
      </div>

    </el-form>

    <div class="dialog-footer">
      <el-divider />
      <el-button v-show="stepIndex != 0" type="primary" @click="prevStep">上一步</el-button>
      <el-button @click="close">放 弃</el-button>
      <el-button v-show="stepIndex === 0" type="primary" @click="nextStep">下一步</el-button>
      <el-button v-show="stepIndex === 1" type="primary" @click="submitForm">提交</el-button>
    </div>
  </div>
</template>

<script>
import {
  listHistory,
  delStudent,
  classList,
  backToSchool
} from '@/api/kg/base/student'
export default {
  props: {
    show: {
      default: false,
      type: Boolean
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.resetQuery()
        this.stepIndex = 0
        this.selectStudent = []
      }
    }
  },
  components: {},
  data() {
    return {
      submitClick: true,
      classId: '',
      classList: [],
      stepIndex: 0,
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        type: 0,
        identityNumber: ''
      },
      total: 0,
      form: {},
      studentList: [],
      selectStudent: [],
      // 班级信息
      classInfo: {}
    }
  },
  methods: {
    getList() {
      this.loading = false
      console.log('queryParams',this.queryParams);
      let query = this.delObjEmpty(this.queryParams)
      listHistory(query).then((res) => {
        if (res.code == 200) {
          this.studentList = res.data.records
          // 已经选中的学生id
          let idArr = this.selectStudent.map((item) => {
            return item.id
          })
          this.studentList.forEach((item) => {
            if (item.guardianPhone) {
              item.phoneArr = item.guardianPhone.split(',')
            }
            if (item.guardianName) {
              item.nameArr = item.guardianName.split(',')
            }
            if (idArr.includes(item.id)) {
              this.$set(item, 'checked', true)
            } else {
              this.$set(item, 'checked', false)
            }
          })
          this.total = res.data.total
          this.loading = false
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
    // 关闭弹窗
    close() {
      this.$emit('close')
    },
    /** 收费方案配置添加按钮操作 */
    changeChecked(row) {
      if (row.checked) {
        this.selectStudent.push(row)
      } else {
        var flagIndex = this.selectStudent.findIndex((item) => {
          return row.id === item.id
        })
        if (flagIndex != -1) {
          this.selectStudent.splice(flagIndex, 1)
        }
      }
      console.log(this.selectStudent)
    },
    // 上一步
    prevStep() {
      if (this.stepIndex > 0) {
        this.stepIndex--
      }
    },
    // 下一步
    nextStep() {
      // 没有选学生
      if (this.selectStudent.length === 0) {
        this.msgError('请选择返校学生')
        return
      }
      this.stepIndex++
      this.classId = ''
      this.classInfo = {}
      // 获取班级列表
      this.getClassList()
    },
    // 获取班级列表
    getClassList() {
      classList().then((res) => {
        if (res.code === 200) {
          this.classList = res.data
        }
      })
    },
    // 改变班级
    changeClass(id) {
      // 通过id匹配到对应班级信息
      let obj = this.classList.find((item) => {
        return item.id === id
      })
      this.classInfo = obj ? obj : {}
    },
    // 提交
    submitForm() {
      // 选择班级
      if (!this.classId) {
        this.msgError('请选择班级')
        return
      }
      if (this.submitClick) {
        this.submitClick = false
        // 选中的学生id
        let idArr = this.selectStudent.map((item) => {
          return item.id
        })
        backToSchool({
          studentIds: idArr,
          classId: this.classId
        })
          .then((res) => {
            if (res.code === 200) {
              this.msgSuccess('操作成功')
              this.close()
            }
          })
          .finally(() => {
            this.submitClick = true
          })
      }
    }
  }
}
</script>

<style lang='scss' scoped>
.backSchool {
  .title {
    .name {
      margin-bottom: 10px;
      color: #aaa9a9;
      font-size: 18px;
      padding-left: 8px;
      border-left: 4px solid #0088ff;
    }
  }
}
</style>
