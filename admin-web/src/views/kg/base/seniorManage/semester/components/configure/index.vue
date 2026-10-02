<template>
  <div class="app-container">
    <div style="padding-bottom: 20px">
      <el-button type="primary" size="mini" @click="handleAdd">新增</el-button>
      <el-button size="mini" @click="handleBack">返回</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="semesterList" :border="true">

      <el-table-column label="学期" align="center" prop="semesterName" />

      <el-table-column label="课程名称" align="center" prop="courseName" />

      <el-table-column label="开始日期" align="center" prop="beginTime" />

      <el-table-column label="结束日期" align="center" prop="endTime" />

      <el-table-column label="操作" align="center">
        <template slot-scope="{ row }">
          <el-button size="mini" type="text" @click="handleEdit(row)">修改</el-button>
          <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
          <el-button size="mini" type="text" style="color: red" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <el-dialog title="新增学期课程时间" :visible.sync="dialogAddVisible" width="600px">
      <el-divider></el-divider>
      <el-form ref="addForm" :rules="rules" :model="addForm" label-width="80px">
        <el-form-item label="课程名称">
          <el-input v-model="addForm.courseName" style="width:400px"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-time-picker v-model="addForm.beginTime" format="HH:mm" value-format="HH:mm" placeholder="选择开始时间"  style="width:400px">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-time-picker v-model="addForm.endTime" format="HH:mm" value-format="HH:mm" placeholder="选择结束时间"  style="width:400px">
          </el-time-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑学期课程时间" :visible.sync="dialogEditVisible">
      <el-divider></el-divider>
      <el-form ref="editForm" :rules="rules" :model="editForm" label-width="80px">
        <el-form-item label="课程名称">
          <el-input v-model="editForm.courseName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-time-picker v-model="editForm.beginTime" format="HH:mm" value-format="HH:mm" placeholder="选择开始时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-time-picker v-model="editForm.endTime" format="HH:mm" value-format="HH:mm" placeholder="选择结束时间">
          </el-time-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCourse,
  addCourse,
  updateCourse,
  delCourse,
} from "@/api/kg/base/advanced/semester.js";
import { getToken } from "@/utils/auth";
export default {
  components: {
  },
  props: {
    configureInfo: {
      default: () => { },
      type: Object,
    }
  },
  data() {
    return {
      editForm: {
        courseName: null,
        beginTime: null,
        endTime: null,
        id: null,
      },
      addForm: {
        courseName: null,
        beginTime: null,
        endTime: null,
        semesterId: null,
      },
      dialogEditVisible: false,
      dialogAddVisible: false,
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
        semesterId: null,
      },
      action: process.env.VUE_APP_BASE_API + "/cdn/upload",
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  filters: {},
  mounted() {
    // 获取班级列表
    // this.getClassIdList();
    if (this.configureInfo.id) {
      this.queryParams.semesterId = this.configureInfo.id;
      this.getList();
    }
  },
  methods: {
    // 保存编辑
    saveEdit() {
      this.$refs["editForm"].validate((valid) => {
        if (valid) {
          updateCourse(this.editForm).then((res) => {
            if (res.code === 200) {
              this.dialogEditVisible = false;
              this.getList();
            }
          })
        }
      })
    },
    // 保存新增
    saveAdd() {
      console.info("semester:" + this.queryParams.semesterId)
      this.$refs["addForm"].validate((valid) => {
        if (valid) {
          this.addForm.semesterId = this.queryParams.semesterId;
          addCourse(this.addForm).then((res) => {
            if (res.code === 200) {
              this.dialogAddVisible = false;
              this.getList();
            }
          })
        }
      })
    },
    // 返回操作
    handleBack() {
      this.$emit("abandon", false);
    },
    // 获取学期列表
    getList() {
      this.loading = true
      listCourse(this.queryParams).then((res) => {
        if (res.code === 200) {
          this.semesterList = res.data.records;
        }
        this.loading = false;
      })
    },
    // 修改操作
    handleEdit(row) {
      this.editForm = {};
      this.editForm = Object.assign({}, row);
      this.dialogEditVisible = true;
    },
    // 配置课程内容
    handleConfigure(row) {
    },

    /** 新增课程 */
    handleAdd() {
      this.addForm = {};
      this.dialogAddVisible = true;
    },
    // 删除操作
    handleDelete(row) {
      this.$modal
        .confirm("是否确定删除该学期课程信息？", "删除学期课程信息", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
        })
        .then(function () {
          return delCourse({
            id: row.id,
          });
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
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
