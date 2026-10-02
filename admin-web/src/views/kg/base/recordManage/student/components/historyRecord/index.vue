<template>
  <div v-show="show" class="app-container">
    <div v-show="!detailVisible" class="content">
      <!-- 搜索 -->
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-position="left">
        <el-row>
          <el-col :span="22">
            <el-form-item label="信息:" prop="info">
              <el-input v-model="queryParams.info" placeholder="请输入姓名/监护人手机" clearable size="small" @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="text-align: right">
            <el-form-item>
              <el-button type="primary" size="mini" @click="cancel">返回</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <!-- 表格 -->
      <el-table v-loading="loading"
                :data="peopleList"
                stripe
                border>
        <el-table-column label="毕业年级" align="center" prop="className" />
        <el-table-column label="学生姓名" align="center" prop="name" />
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
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column label="特殊情况" align="center" prop="specialCase" />
        <el-table-column label="操作" align="center">
          <template slot-scope="{row}">
            <el-button type="text" @click="handleDetail(row)">详情</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button style="color: red" type="text" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
    </div>
    <!-- 详情 -->
    <detail :show="detailVisible" :info="detailInfo" @close="detailVisible=false" />
  </div>
</template>

<script>
import { listHistory, delStudent } from '@/api/kg/base/student'

import detail from '../studentInfo'
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.resetQuery()
      }
    }
  },
  components: {
    detail
  },
  data() {
    return {
      total: 0,
      detailVisible: false,
      detailInfo: {},
      loading: false,
      peopleList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        info: '',
        type: 1
      }
    }
  },
  methods: {
    getList() {
      this.loading = false
      let query = this.delObjEmpty(this.queryParams)
      listHistory(query).then((res) => {
        if (res.code == 200) {
          this.peopleList = res.data.records
          this.peopleList.forEach((item) => {
            if (item.guardianPhone) {
              item.phoneArr = item.guardianPhone.split(',')
            }
            if (item.guardianName) {
              item.nameArr = item.guardianName.split(',')
            }
          })
          this.total = res.data.total
          this.loading = false
        }
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 返回上一层
    cancel() {
      this.$emit('close')
    },
    // 详情
    handleDetail(row) {
      this.detailVisible = true
      this.detailInfo = row
    },
    // 删除
    handleDelete(row) {
      this.$confirm('将删除该学生, 是否确定?', '删除', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delStudent({
            id: row.id
          }).then((res) => {
            if (res.code === 200) {
              this.msgSuccess('删除成功')
              this.getList()
            }
          })
        })
        .catch(() => {
          this.$message.error('获取数据失败')
        })
    }
  }
}
</script>

<style lang='scss'>
</style>
