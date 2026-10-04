<template>
  <div>
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="员工姓名:" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入员工姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码:" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >查询</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="staffList" :border="true">
      <el-table-column label="员工编号" align="center" prop="staffNumber" />

      <el-table-column label="员工姓名" align="center" prop="name">
        <template slot-scope="{ row }">
          <span style="color: #247a68" @click="checkInfo(row, 1)">{{
            row.name
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="岗位" align="center" prop="postNames">
        <template slot-scope="{ row }">
          <span
            @click="distribute(row)"
            v-if="row.postNames"
            style="color: #247a68"
            >{{ row.postNames }}</span
          >
          <span @click="distribute(row)" v-else style="color: #15b72a"
            >分配</span
          >
        </template>
      </el-table-column>

      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="{ row }">{{
          row.sex === 0 ? "男" : "女"
        }}</template>
      </el-table-column>

      <el-table-column label="手机号码" align="center" prop="phone" />

      <el-table-column
        label="入职日期"
        align="center"
        prop="hiredate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hiredate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column label="学历" align="center" prop="education" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 集团员工分配岗位 -->
    <el-dialog
      title="分配岗位"
      :visible.sync="showDistributeDialog"
      width="460px"
    >
      <el-form
        ref="distributeForm"
        :model="distributeForm"
        :rules="rules"
        label-width="100px"
        label-position="right"
        style="margin-top: 30px"
      >
        <el-form-item label="员工姓名:">
          <el-input
            v-model="distributeForm.name"
            disabled
            style="width: 260px"
          ></el-input>
        </el-form-item>
        <el-form-item label="岗位：" prop="postIds">
          <el-select
            v-model="distributeForm.postIds"
            multiple
            placeholder="请选择"
            style="width: 260px"
          >
            <el-option
              v-for="item in posts"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelDistribute">取 消</el-button>
        <el-button type="primary" @click="confirmDistribute">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listStaff,
  getStaff,
  delStaff,
  addStaff,
  updateStaff,
  getStation,
  fireStaff,
  hireStaff,
  distributeJob,
} from "@/api/kg/base/record/staff";
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 全部岗位
      posts: [],
      // 总条数
      total: 0,
      // 学校员工 表格数据
      staffList: [],
      // 展示员工信息
      showInfo: false,
      // 是否显示分配岗位弹窗
      showDistributeDialog: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        phone: null,
        isQuit: null,
        queryType: 0, //0-在职员工,1-离职员工,2-集团员工
      },
      rules: {
        postIds: [
          { required: true, message: "必选分配一个岗位以上", trigger: "blur" },
        ],
      },
      // 集团分配岗位表单
      distributeForm: {
        postIds: [],
      },
    };
  },

  components: {},

  computed: {},

  mounted() {
    this.getList();
    this.fetchStation();
  },

  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 获取岗位
    fetchStation() {
      getStation().then((res) => {
        this.posts = res.data;
      });
    },
    // 入职
    handleHire(row) {
      this.$confirm("是否确认将该员工入职?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(() => {
        hireStaff({
          id: row.id,
        }).then((res) => {
          this.getList(1);
          this.$message({
            type: "success",
            message: "入职成功!",
          });
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除该数据项？")
        .then(function () {
          return delStaff({
            id: ids,
          });
        })
        .then(() => {
          this.getList(1);
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
          this.$message.error('获取数据失败')
        });
    },
    /**
     * 查询学校员工 列表
     * @param 0-在职员工,1-离职员工,2-集团员工
     */
    getList() {
      this.loading = true;
      this.queryParams.queryType = 2;
      listStaff(this.queryParams).then((response) => {
        this.staffList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 给集团员工分配岗位
    distribute(row) {
      console.log(row);
      this.showDistributeDialog = true;
      let postIds = null;
      if (row.postIds) {
        postIds = row.postIds.split(",");
        postIds = postIds.map((item) => {
          return parseInt(item);
        });
      }
      this.distributeForm = {
        id: row.id,
        name: row.name,
        postIds: postIds,
      };
    },
    /** 集团分配岗位确认按钮 */
    confirmDistribute() {
      this.$refs["distributeForm"].validate((valid) => {
        if (valid) {
          distributeJob(this.distributeForm).then((response) => {
            this.$modal.msgSuccess("分配成功");
            this.showDistributeDialog = false;
            this.getList(2);
          });
        }
      });
    },
    // 取消按钮
    cancelDistribute() {
      this.showDistributeDialog = false;
      this.distributeForm = {
        // id: null,
        // quitDate: null,
        // quitReason: null,
      };
      this.resetForm("distributeForm");
    },
    // 查看员工信息
    checkInfo(row) {
      this.$emit("openInfo", {
        id: row.id,
        type: 1,
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
