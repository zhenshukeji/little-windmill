<template>
  <div class="app-container">
    <template v-if="!addShow && !literShow">
      <!-- 顶部按钮组合 -->
      <div class="button_group">
        <el-button type="primary" @click="goAdd">新增班级</el-button>
        <el-button type="primary" @click="goLiter">一键升班</el-button>
        <el-button type="primary" @click="handleEdit">编辑年级</el-button>
      </div>

      <div style="margin-bottom: 15px;width: 100%;height: 10px; background-color: #f0f0f0; border-bottom: 1px solid #aaa9a9;border-top: 1px solid #aaa9a9;"/>
      <!-- 下面table -->
      <div class="grade_table">
        <el-table :loading="loading" :data="gradeList" border stripe>
          <el-table-column
            align="center"
            label="班级名称"
            prop="className"
          ></el-table-column>
          <el-table-column
            align="center"
            label="班级类别"
            prop="gradeName"
          ></el-table-column>
          <el-table-column align="center" label="主任老师" prop="teacherName">
            <template slot-scope="{ row }">
              <div v-if="row.teacherName">
                {{ row.teacherName
                }}<span class="teacherSpan" @click="relieve(row, 0)">(解绑)</span>
              </div>
              <div v-else class="teacherDiv" @click="bindingClick(row, 0)">
                绑定
              </div>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="副班老师"
            prop="subTeacherName">
            <template slot-scope="{ row }">
              <div v-if="row.subTeacherName">
                {{ row.subTeacherName
                }}<span class="teacherSpan" @click="relieve(row, 1)">(解绑)</span>
              </div>
              <div v-else class="teacherDiv" @click="bindingClick(row, 1)">
                绑定
              </div>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="{ row }">
              <el-button type="text" @click="deleteClick(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <!-- 绑定弹窗 -->
      <el-dialog
        title="检索"
        :visible.sync="bindingVisible"
        width="800px"
        :close-on-click-modal="false"
      >
        <el-divider></el-divider>
        <el-form
          :model="bindingParams"
          ref="bindingForm"
          :inline="true"
          label-width="100px"
        >
          <el-form-item label="教师姓名:" prop="name">
            <el-input
              v-model="bindingParams.name"
              clearable
              size="small"
              placeholder="教师姓名"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="handleCheckBinding"
              >查询</el-button
            >
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table
          :loading="bindingLoading"
          :data="bindingList"
          border
          stripe
          highlight-current-row
          @current-change="bindingChange"
        >
          <el-table-column
            label="序号"
            align="center"
            type="index"
          ></el-table-column>
          <el-table-column
            label="教师姓名"
            prop="name"
            align="center"
          ></el-table-column>
          <el-table-column
            label="手机号"
            prop="phone"
            align="center"
          ></el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination
          v-show="bindingTotas > 0"
          :total="bindingTotas"
          :page.sync="bindingParams.pageNum"
          :limit.sync="bindingParams.pageSize"
          @pagination="getListBinding"
        />
        <div style="margin-top: 30px">
          <el-divider></el-divider>
        </div>
        <div style="text-align: right">
          <el-button type="primary" @click="submitBinding">确定</el-button>
          <el-button @click="bindingCancel">取消</el-button>
        </div>
      </el-dialog>

      <!-- 编辑年级弹窗 -->
      <el-dialog
        title="年级类别"
        :visible.sync="editVisible"
        width="700px"
        :close-on-click-modal="false"
      >
        <el-divider></el-divider>
        <el-table :data="editList" :loading="editLoading" border stripe>
          <el-table-column
            label="序号"
            align="center"
            prop="id"
          ></el-table-column>
          <el-table-column
            label="年级"
            align="center"
            prop="gradeName"
          ></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="{ row }">
              <div
                style="
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              >
                <span
                  style="color: blue; cursor: pointer"
                  @click="editChange(row)"
                  >修改</span
                >
                <span style="margin: 0 20px">|</span>
                <span
                  style="color: red; cursor: pointer"
                  @click="editDelete(row.id)"
                  >删除</span
                >
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- 新增按钮 -->
        <div style="margin: 20px 0">
          <el-button type="primary" @click="editAdd">新增</el-button>
        </div>
        <el-divider></el-divider>
        <!-- 返回按钮 -->
        <div style="text-align: right; margin-right: 60px">
          <el-button type="primary" @click="editVisible = false"
            >返回</el-button
          >
        </div>
      </el-dialog>
      <!-- 新增and修改 按钮触发的弹窗 -->
      <el-dialog
        :title="editTitle"
        :visible.sync="editSonVisible"
        width="700px"
        :close-on-click-modal="false"
      >
        <el-divider></el-divider>
        <el-form
          :model="editSonParams"
          ref="editSonParams"
          :rules="editSonRules"
          label-width="100px"
        >
          <!-- <el-form-item label="序号: " prop="id">
            <el-input
              v-model="editSonParams.id"
              style="width: 480px"
              placeholder="请输入序号"
              :disabled="editTitle === '修改年级类别'"
            />
          </el-form-item> -->
          <el-form-item label="年级: " prop="gradeName">
            <el-input
              v-model="editSonParams.gradeName"
              style="width: 480px"
              placeholder="请输入年级"
            />
          </el-form-item>
        </el-form>

        <div>
          <el-divider></el-divider>
          <div style="text-align: right; margin-right: 80px">
            <el-button type="primary" @click="submitEdit">确定</el-button>
            <el-button type="primary" @click="editCancel">返回</el-button>
          </div>
        </div>
      </el-dialog>
    </template>

    <Add v-if="addShow" @addCancel="addShow = false" @addClass="addClass"></Add>
    <Litre
      v-if="literShow"
      @litreCancel="literShow = false"
      @litreClass="litreClass"></Litre>
  </div>
</template>

<script>
import {
  listClassroom,
  listRelieve,
  delClassroom,
  listTeacher,
  bindClassroom,
  listGrade,
  addGrade,
  updateGrade,
  deleteGrade,
} from "@/api/advanced/gradeClass";
import Add from "./add";
import Litre from "./litre";
export default {
  components: {
    Add,
    Litre,
  },
  data() {
    return {
      loading: true,
      bindingLoading: true,
      editLoading: true,
      bindingVisible: false,
      editVisible: false,
      editSonVisible: false,
      editTitle: "",
      gradeList: [],
      bindingList: [],
      editList: [],
      total: 0,
      bindingTotas: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 5,
      },
      bindingParams: {
        pageNum: 1,
        pageSize: 5,
        name: null,
      },
      editSonParams: {},
      editSonRules: {
        gradeName: [
          {
            required: true,
            message: "年级名称不能为空",
            trigger: "blur",
          },
        ],
      },
      // 存储点击绑定时的班级以及是班主任还是副班  0班主任 1副班
      temporaryData: {
        classId: null,
        type: null,
        teacherId: null,
      },
      addShow: false,
      literShow: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listClassroom(this.queryParams).then((res) => {
        this.gradeList = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      });
    },
    // 解绑
    relieve(row, type) {
      this.$confirm("将解绑教师,是否确定?", "解绑教师", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(function () {
          return listRelieve({
            classId: row.id,
            type: type,
          });
        })
        .then(() => {
          this.$message({
            type: "success",
            message: "解绑成功!",
          });
          this.getList();
        })
        .catch(() => {});
    },
    // 删除
    deleteClick(row) {
      this.$confirm("将删除班级,是否确定?")
        .then(function () {
          return delClassroom({
            id: row.id,
          });
        })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.getList();
        })
        .catch(() => {});
    },
    // 绑定
    bindingClick(row, type) {
      this.bindingVisible = true;
      this.temporaryData.type = type;
      this.temporaryData.classId = row.id;

      this.bindingParams.pageNum = 1;
      this.getListBinding();
    },
    handleCheckBinding() {
      this.bindingParams.pageNum = 1;
      this.getListBinding();
    },
    getListBinding() {
      this.bindingLoading = true;
      listTeacher(this.bindingParams).then((res) => {
        this.bindingList = res.data.records;
        this.bindingTotas = res.data.total;
        this.bindingLoading = false;
        console.log(res);
      });
    },
    // 提交选中的老师绑定
    submitBinding() {
      if (!this.temporaryData.teacherId) {
        this.$message({
          type: "warning",
          message: "请选择一个老师进行绑定!",
        });
        return false;
      }
      bindClassroom(this.temporaryData).then((res) => {
        if (res.code === 200) {
          console.log(res);
          this.$message({
            type: "success",
            message: "成功绑定老师!",
          });
          // 关闭绑定弹窗则将老师id置空
          this.temporaryData.teacherId = null;
          this.bindingVisible = false;
          this.getList();
        }
      });
    },
    // 关闭老师绑定弹窗
    bindingCancel() {
      // 关闭绑定弹窗则将老师id置空
      this.temporaryData.teacherId = null;
      this.bindingVisible = false;
    },
    // 单表选中老师
    bindingChange(val) {
      if (val) {
        this.temporaryData.teacherId = val.id;
      }
    },
    // 新增
    goAdd() {
      this.addShow = true;
    },
    addClass() {
      this.addShow = false;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 升班
    goLiter() {
      this.literShow = true;
    },
    litreClass() {
      this.literShow = false;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 编辑班级
    handleEdit() {
      this.editVisible = true;
      this.getEditList();
    },
    // 获取年级表
    getEditList() {
      this.editLoading = true;
      listGrade().then((res) => {
        if (res.code === 200) {
          console.log(res);
          this.editList = res.data;
          this.editLoading = false;
        }
      });
    },
    // 表单重置
    resetEdit() {
      this.editSonParams = {
        gradeName: null,
      };
      this.resetForm("editSonParams");
    },
    editAdd() {
      this.editTitle = "新增年级类别";
      this.editSonVisible = true;
    },
    editChange(row) {
      this.editTitle = "修改年级类别";
      this.editSonParams = { ...row };
      this.editSonVisible = true;
    },
    editDelete(id) {
      this.$confirm("将删除年级,是否确定?")
        .then(function () {
          return deleteGrade({
            id: id,
          });
        })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除年级成功!",
          });
          this.editSonVisible = false;
          this.getEditList();
          this.resetEdit();
        })
        .catch(() => {});
    },
    submitEdit() {
      this.$refs["editSonParams"].validate((valid) => {
        if (valid) {
          if (this.editTitle === "新增年级类别") {
            addGrade(this.editSonParams).then((res) => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: "新增年级成功!",
                });
                this.editSonVisible = false;
                this.getEditList();
                this.resetEdit();
              }
            });
          } else {
            updateGrade(this.editSonParams).then((res) => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: "修改年级成功!",
                });
                this.editSonVisible = false;
                this.getEditList();
                this.resetEdit();
              }
            });
          }
        }
      });
    },
    editCancel() {
      this.resetEdit();
      this.editSonVisible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.button_group {
  text-align: right;
  padding-bottom: 10px;
  //border-bottom: 10px solid #f0f0f0;
}
.grade_table {
  padding: 20px 0 0;
  .teacherSpan {
    margin-left: 5px;
    color: #1890FF;
    cursor: pointer;
  }
  .teacherDiv {
    color: #1890FF;
    cursor: pointer;
  }
}
</style>
