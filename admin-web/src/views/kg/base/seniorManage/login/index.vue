<template>
  <div>
    <template v-if="!showAdd && !showDetail && !showHistory">
      <div class="app-container">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="100px">
          <el-form-item label="查询信息：" prop="param">
            <el-input
              v-model="queryParams.param"
              clearable
              placeholder="输入员工姓名或手机号"
              size="small"
              style="width: 180px"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery">查询</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading"
                  :data="listData"
                  stripe
                  :border="true">
          <el-table-column label="用户id" align="center" prop="userId" />
          <el-table-column label="用户名" align="center" prop="name">
          </el-table-column>
          <el-table-column label="岗位名称" align="center" prop="posts" />
          <el-table-column label="状态" align="center">
            <template slot-scope="{row}">
              <span style="color: #1890ff" v-if="row.status === '0'">正常</span>
              <span v-if="row.status === '1'">停用</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="{ row }">
              <el-button
                size="mini"
                type="text"
                @click="handleReset(row)">重置密码</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"/>

      </div>
    </template>
  </div>

</template>

<script>
import {
  listLogin,
  reset,
} from "@/api/kg/base/advanced/login.js";
import { getToken } from "@/utils/auth";
export default {
  components: {
  },
  data() {
    return {
      downId: {
        id: null,
      },
      showHistory: false,
      info: null,
      showDetail: false,
      showAdd: false,
      loading: false,
      listData: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        param: null,
      },
      action: process.env.VUE_APP_BASE_API + "/cdn/upload",
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  filters: {},
  mounted() {
    this.getList();
  },
  methods: {
    // 用户登录记录
    getList () {
      this.loading = true;
      listLogin(this.queryParams).then((res) => {
        if (res.code ===200) {
          this.listData = res.data.records;
        }
        this.loading = false;
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置密码
    handleReset(row) {
      console.info(row.userId)
      this.$modal
        .confirm("是否确定重置密码？", "重置密码", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
        })
        .then(function () {
          return reset({
            userId: row.userId,
          });
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("重置成功");
        })
        .catch(() => {
          this.$message.error('获取数据失败')
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.arrows {
  font-weight: bold;
  margin-right: 10px;
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
</style>
