<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px" label-position="left">
      <el-form-item label="查询信息：" prop="param">
        <el-input v-model="queryParams.param" placeholder="请输入员工姓名或手机" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="peopleList" :border="true">
      <el-table-column label="用户名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="岗位名称" align="center" prop="posts" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleReset(scope.row)">重置密码</el-button>
          <el-button size="mini" type="text" @click="handleAuthor(scope.row)">校区权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :visible.sync="authorVisible" title="校区权限" width="1000px">
      <el-divider />
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="员工姓名：" prop="blocStaffName">
          {{ form.blocStaffName }}
        </el-form-item>
        <el-form-item label="校区权限：">
          <el-table :data="form.kgList" :border="true">
            <el-table-column prop="isChecked" align="center" width="60">
              <template slot-scope="{row}">
                <el-checkbox v-model="row.isChecked" @change="changeChecked(row)"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="校区名称" align="center" prop="name" />
            <el-table-column label="校区地址" align="center" prop="address" />
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="authorVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listPeople,
  resetPassword,
  getAuthor,
  updateAuthor
} from '@/api/base/login'

export default {
  name: 'login',
  components: {},
  data() {
    return {
      authorVisible: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      selectList: [],
      peopleList: [],
      // 表单参数
      form: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询配置列表 */
    getList() {
      this.loading = true
      let query = { ...this.queryParams }
      for (let key in query) {
        if (
          query[key] === null ||
          query[key] === undefined ||
          query[key] === ''
        ) {
          delete query[key]
        }
      }
      listPeople(query)
        .then((response) => {
          this.peopleList = response.data.records
          this.total = response.data.total
        })
        .finally(() => {
          this.loading = false
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
    /** 重置密码操作 */
    handleReset(row) {
      this.$confirm('将重置密码，是否确定', '重置密码', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          resetPassword({
            userId: row.userId
          }).then((res) => {
            if (res.code === 200) {
              this.$alert(`重置成功！新密码：${res.data}`, '重置密码', {
                confirmButtonText: '确定'
              })
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 查询权限
    handleAuthor(row) {
      getAuthor(row.userId).then((res) => {
        if (res.code === 200) {
          this.form = res.data
          this.form.userId = row.userId
          this.authorVisible = true
          this.selectList = this.form.kgList.filter((item) => {
            return item.isChecked
          })
        }
      })
    },
    // 选中的校区
    changeChecked(row) {
      console.log('row', row)
      // 没有选中
      if (row.isChecked) {
        this.selectList.push(row)
      } else {
        var flagIndex = this.selectList.findIndex((item) => {
          return row.kgId === item.kgId
        })
        if (flagIndex != -1) {
          this.selectList.splice(flagIndex, 1)
        }
      }
      console.log('selectList', this.selectList)
    },

    // 提交
    submitForm() {
      if (this.selectList.length > 0) {
        let kgIds = this.selectList.map((item) => item.kgId)
        updateAuthor({
          kgIds,
          userId: this.form.userId
        }).then((res) => {
          if (res.code === 200) {
            this.$modal.msgSuccess('修改成功')
            this.authorVisible = false
          }
        })
      } else {
        this.$modal.msgError('至少需要选择一个校区')
      }
    }
  }
}
</script>
