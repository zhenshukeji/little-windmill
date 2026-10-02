<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="教学计划：" prop="range">
            <el-select v-model="queryParams.range" placeholder="请选择教学计划">
              <el-option v-for="item in rangeList" :key="item.value" :label="item.name" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="班级：" prop="classId">
            <el-select v-model="queryParams.classId" clearable placeholder="全部">
              <el-option v-for="item in classIdList" :key="item.value" :label="item.className" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12" style="text-align: right">
          <el-form-item>
            <el-button type="primary" size="mini" @click="handleAdd">上传教学计划</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table v-loading="loading"
              :data="listData"
              :header-cell-style="{'font-size': '15px'}"
              :cell-style ="{'font-size': '15px'}"
              stripe
              class="button-style"
              :border="true">
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="教学计划" align="center" prop="id">
        <template slot-scope="{ row }">
          {{ parseTime(row.beginDate, "{y}-{m}-{d}") }}至{{
            parseTime(row.endDate, "{y}-{m}-{d}")
          }}教学计划
        </template>
      </el-table-column>
      <el-table-column label="上传时间" align="center" prop="createTime" />
      <el-table-column label="上传人" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{ row }">
          <el-button size="mini" type="text" @click="handleCheck(row)">查看计划</el-button>
          <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
          <el-button size="mini" type="text" style="color: red" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="新增教学计划" :visible.sync="planVisible" width="600px" append-to-body class="dialog-style">
      <el-divider></el-divider>

      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="发布时间：" prop="beginDate">
          <span>
            <i class="el-icon-arrow-left arrows" @click="lastPage"></i>
            <span class="noSelect">{{ cells[0] }}至{{cells[6]}}</span>
            <i class="el-icon-arrow-right arrows" @click="nextPage"></i>
          </span>
        </el-form-item>

        <el-form-item label="班级：" prop="classId">
          <el-select v-model="form.classId" clearable placeholder="请选择">
            <el-option v-for="item in classIdList" :key="item.value" :label="item.className" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="计划图片：" prop="planUrl">
          <el-upload class="avatar_uploader" :action="action" :headers="headers" accept="image/*" :on-success="handleAvatarSuccess" :before-upload="handleUploadImgBefore" v-if="!form.planUrl">
            <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="avatar_uploader" v-if="form.planUrl">
            <div class="close" @click="closeClick">X</div>
            <img @click="checkImg" :src="form.planUrl" class="avatar" />
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="text-align: center">
          <el-button type="primary" @click="submitForm">保 存</el-button>
          <el-button @click="cancel">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="查看图片" :visible.sync="dialogVisible" @closed="dialogImg = '' ">
      <img width="100%" :src="dialogImg" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  classroom,
  delProgram,
  addProgram
} from '@/api/kg/work/educational/teachingProgram.js'
import { getToken } from '@/utils/auth'
import upload from '@/components/UpLoad'
export default {
  components: {
    upload
  },
  data() {
    return {
      loading: true,
      listData: [],
      form: {},
      rules: {
        beginDate: [
          {
            required: true,
            message: '请选择时间',
            trigger: 'change'
          }
        ],
        classId: [
          {
            required: true,
            message: '请选择班级',
            trigger: 'change'
          }
        ],
        planUrl: [
          {
            required: true,
            message: '请上传教学计划',
            trigger: 'blur'
          }
        ]
      },
      planVisible: false,
      dialogVisible: false,
      dialogImg: '', // 图片查看
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        classId: null,
        range: null //WEEK_TEACH_PLANS,ALL_TEACH_PLANS
      },
      rangeList: [
        { value: 'WEEK_TEACH_PLANS', name: '本周 教学计划' },
        { value: 'ALL_TEACH_PLANS', name: '全部 教学计划' }
      ],
      classIdList: [],
      times: '',
      cells: [],

      action: process.env.VUE_APP_BASE_API + '/cdn/upload',
      headers: {
        Authorization: 'Bearer ' + getToken()
      }
    }
  },
  filters: {},
  mounted() {
    // 获取班级列表
    this.getClassIdList()
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      list(this.queryParams).then((response) => {
        if (response.code === 200) {
          this.listData = response.data.records
          this.total = response.data.total
          this.loading = false
        }
      })
    },
    getClassIdList() {
      classroom().then((res) => {
        if (res.code === 200) {
          this.classIdList = res.data
        }
      })
    },
    // 表单重置
    reset() {
      this.form = {
        planUrl: null,
        classId: null,
        beginDate: new Date()
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 上传 */
    handleAdd() {
      this.reset()
      this.setDate(new Date())
      this.planVisible = true
    },
    // 取消按钮
    cancel() {
      this.planVisible = false
      this.reset()
    },
    checkImg() {
      this.dialogVisible = true
      this.dialogImg = this.form.planUrl
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.beginDate = this.cells[0]
          addProgram(this.form).then((res) => {
            if (res.code === 200) {
              this.$modal.msgSuccess('新增成功')
              this.planVisible = false
              this.getList()
            }
          })
        }
      })
    },
    handleCheck(row) {
      this.dialogVisible = true
      this.dialogImg = row.planUrl
    },
    handleDelete(row) {
      this.$modal
        .confirm('是否确定删除该教学计划？', '删除计划', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
        .then(function () {
          return delProgram({
            id: row.id
          })
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
          this.$message.error('获取数据失败')
        })
    },

    // 图片上传成功回调
    handleAvatarSuccess(res) {
      if (res.code === 200) {
        this.form.planUrl = res.data.url
        console.log('上传图片成功')
      } else {
        console.log('上传图片失败')
      }
    },
    // 限制上传文件类型
    handleUploadImgBefore(file) {
      const isPic = [
        'image/jpeg',
        'image/png',
        'image/jpg',
        'image/gif'
      ].includes(file.type)
      const isLt10M = file.size / 1024 / 1024 < 2
      if (!isPic) {
        this.$message.error('请上传规定的图片格式(png/jpg/jpeg/gif)')
        return false
      }
      if (!isLt10M) {
        this.$message.error(`上传图片大小不能超过2MB!`)
        return false
      }
      return isPic && isLt10M
    },
    closeClick() {
      this.form.planUrl = null
    },

    lastPage() {
      this.cells = []
      this.setDate(this.addDate(this.times, -7))
    },
    nextPage() {
      this.cells = []
      this.setDate(this.addDate(this.times, 7))
    },
    addDate(date, n) {
      date.setDate(date.getDate() + n)
      return date
    },
    setDate(date) {
      var week = date.getDay() - 1
      date = this.addDate(date, week * -1)
      this.times = new Date(date)

      for (var i = 0; i < 7; i++) {
        this.cells.push(this.formatDate(i == 0 ? date : this.addDate(date, 1)))
      }
    },
    formatDate(date) {
      let year = date.getFullYear() + '-'
      let month = ''
      let day = ''
      if (date.getMonth() + 1 < 10) {
        month = '0' + (date.getMonth() + 1) + '-'
      } else {
        month = date.getMonth() + 1 + '-'
      }
      if (date.getDate() < 10) {
        day = '0' + date.getDate()
      } else {
        day = date.getDate()
      }
      return year + month + day
    }
  }
}
</script>

<style lang="scss" scoped>
.arrows {
  font-weight: bold;
  cursor: pointer;
}
.arrows_next {
  margin-left: 10px;
  margin-right: 0;
}
.avatar_uploader {
  position: relative;
  width: 150px;
  height: 150px;
  display: flex;
  margin: 5px 0 5px 5px;
  border: 1px dashed #cdcdcd;
  .close {
    position: absolute;
    top: -10px;
    right: -10px;
    width: 20px;
    height: 20px;
    background-color: red;
    border-radius: 50%;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    cursor: pointer;
  }
  ::v-deep .el-upload {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    // border: 1px solid #e4e4e4;
    justify-content: center;
  }
}
img {
  width: 100%;
  height: 100%;
}
.noSelect {
  user-select: none;
}
// 表格操作按钮
.button-style .el-button {
  font-size: 15px;
}

// 删除对话框
::v-deep .dialog-style .el-dialog__header {
  border-bottom: 1px solid #ececec;
  .el-dialog__title {
    font-size: 16px;
  }
}

::v-deep .dialog-style .el-dialog__footer {
  border-top: 1px solid #ececec;
  text-align: center;
}
</style>
