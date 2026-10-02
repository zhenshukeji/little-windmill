<template>
  <div v-show="show">
    <el-form :model="form" :inline="true">
      <el-form-item label="活动名称">
        <el-input v-model="form.name" placeholder = "请输入活动名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="tableData"
      stripe
      style="width: 100%">
      <el-table-column
        prop="date"
        label="活动名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="活动时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="报名情况">
      </el-table-column>
      <el-table-column
        prop="address"
        label="回应时间">
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
  export default {
    name: "",
    props: {
      info: {
        default: () => {},
        type: Object
      },
      show: {
        default: false,
        type: Boolean
      }
    },
    watch: {
      show(val) {
        if (val) {
          this.getList()
        }
      }
    },
    data() {
      return {
        form:{
          name: null,
        },
        tableData:[],
        queryParams: {
          pageNum: 1,
          pageSize: 20,
        },
        total: 0,
      }
    },
    created() {
    },
    methods: {
      // 获取报名情况列表
      getList () {

      }
    }
  }
</script>

<style scoped>

</style>
