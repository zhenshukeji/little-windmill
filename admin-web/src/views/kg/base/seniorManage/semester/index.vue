<template>
  <div class="app-container">
    <template v-if="!showAdd && !showUpdate &&!showConfigure">
      <div style="text-align: right;padding-bottom: 10px">
        <el-button
          type="primary"
          size="mini"
          @click="handleAdd">新增</el-button>
      </div>

       <!-- 表格 -->
      <el-table v-loading="loading"
                :data="semesterList"
                stripe
                :border="true">

        <el-table-column label="名称" align="center" prop="semesterName" />

        <el-table-column label="开始日期" align="center" prop="beginDate"/>

        <el-table-column label="结束日期" align="center" prop="endDate" />

        <el-table-column label="操作" align="center">
          <template slot-scope="{ row }">
            <el-button size="mini" type="text" @click="handleConfigure(row)">配置课程内容</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button size="mini" type="text" @click="handleEdit(row)">修改</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button
              size="mini"
              type="text"
              style="color: red"
              @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </template>
    <add v-if="showAdd" @abandon = "abandon" @saveClick = "saveClick"></add>
    <update v-if="showUpdate" :updateInfo = "updateInfo" @abandon = "abandon" @saveClick = "saveClick"></update>
    <configure v-if="showConfigure" :configureInfo = "configureInfo" @abandon = "abandon"></configure>
  </div>
</template>

<script>
import {
  listSemester,
  delSemester,
} from "@/api/kg/base/advanced/semester.js";
import { getToken } from "@/utils/auth";
import upload from "@/components/UpLoad";
import add from "./components/add";
import update from "./components/update";
import configure from "./components/configure"
export default {
  components: {
    upload,add,update,configure
  },
  data() {
    return {
      configureInfo: null,
      updateInfo: null,
      showConfigure: false,
      showUpdate: false,
      showAdd: false,
      semesterList: [],
      loading: true,
      rules: {
        officeHours: [
          {
            required: true,
            message: "请选择上班时间",
            trigger: "change",
          },
        ],
        closingTime: [
          {
            required: true,
            message: "请选择下班时间",
            trigger: "change",
          },
        ]
      },
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
    };
  },
  filters: {},
  mounted() {
    // 获取班级列表
    // this.getClassIdList();
    this.getList();
  },
  methods: {
    // 新增保存操作
    saveClick () {
      this.showAdd = false;
      this.showUpdate = false;
      this.showConfigure = false;
      this.getList();
    },
    // 返回
    abandon (val) {
      this.showAdd = val;
      this.showUpdate = val;
      this.showConfigure = val;
      this.getList();
    },
    // 获取学期列表
    getList () {
      this.loading = true
      listSemester(this.queryParams).then((res) => {
        if (res.code === 200) {
          this.semesterList = res.data.records;
        }
        this.loading = false;
      })
    },
    // 修改操作
    handleEdit (row) {
      this.updateInfo = row;
      this.showUpdate = true;
    },
    // 配置课程内容
    handleConfigure (row) {
      this.configureInfo = row;
      this.showConfigure  = true;
    },

    /** 新增班次 */
    handleAdd() {
      this.showAdd = true;
    },
    // 删除操作
    handleDelete(row) {
      this.$modal
        .confirm("是否确定删除该学期信息？", "删除学期信息", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
        })
        .then(function () {
          return delSemester({
            id: row.id,
          });
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
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
