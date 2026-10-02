<template>
  <div class="litre_page">
    <el-table :loading="loading" :data="litreList" border stripe>
      <el-table-column
        label="现班级"
        prop="className"
        align="center"
      ></el-table-column>
      <el-table-column
        label="主任老师"
        prop="teacherName"
        align="center"
      ></el-table-column>
      <el-table-column label="新班级" prop="newClassId" align="center">
        <template slot-scope="{ row }">
          <el-select
            v-model="row.newClassId"
            clearable
            placeholder="请选择班级"
            v-if="!row.isGraduate"
          >
            <el-option
              v-for="item in classroomList"
              :key="item.id"
              :label="item.className"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="是否毕业" prop="isGraduate" align="center">
        <template slot-scope="{ row }">
          <el-switch v-model="row.isGraduate"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="新主任老师" prop="teacherId" align="center">
        <template slot-scope="{ row }">
          <el-select
            v-model="row.teacherId"
            clearable
            placeholder="请选择老师"
            v-if="!row.isGraduate"
          >
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>
    <div class="text">
      备注:升班操作比较重要,请谨慎择作,一键升班之后,相应的学生将会自动变换到相应班级,如果班不够,请先添加班再进行操作
    </div>
    <div class="btn">
      <span>
        <el-button type="primary" @click="submitLitreCancel">提交</el-button>
      </span>
      <span>
        <el-button @click="litreCancel">放弃</el-button>
      </span>
    </div>
  </div>
</template>

<script>
import {
  listPromotion,
  listClassroomAll,
  listTeacherAll,
  litrePromotion,
} from "@/api/advanced/gradeClass";
export default {
  data() {
    return {
      loading: true,
      litreList: [],
      teacherList: [],
      classroomList: [],
      queryParams: [], // 提交接口的数据
    };
  },
  created() {
    this.getList();
    this.listTeacherAll();
    this.listClassroomAll();
  },
  methods: {
    getList() {
      this.loading = true;
      listPromotion().then((res) => {
        if (res.code === 200) {
          this.litreList = res.data;
          this.litreList.forEach((item) => {
            this.$set(item, "newClassId", null);
            this.$set(item, "isGraduate", false);
            this.$set(item, "teacherId", null);
          });
          this.loading = false;
        }
      });
    },
    // 老师
    listTeacherAll() {
      listTeacherAll().then((res) => {
        if (res.code === 200) {
          this.teacherList = res.data;
        }
      });
    },
    // 班级
    listClassroomAll() {
      listClassroomAll().then((res) => {
        if (res.code === 200) {
          this.classroomList = res.data;
        }
      });
    },

    litreCancel() {
      this.$emit("litreCancel");
    },
    submitLitreCancel() {
      let flag = false; // 为true就是有未填项 则不给予调接口
      this.queryParams = [];
      for (let i = 0; i < this.litreList.length; i++) {
        // 毕业
        if (this.litreList[i].isGraduate) {
          this.litreList[i].newClassId = null;
          this.litreList[i].teacherId = null;
        } else {
          // 非毕业  则新班级和新主任老师必填
          if (!this.litreList[i].newClassId || !this.litreList[i].teacherId) {
            this.$message({
              type: "warning",
              message: "请查看是否有新班级或者新主任老师未选择!",
            });
            flag = true;
            break;
          }
        }
        this.queryParams.push({
          isGraduate: this.litreList[i].isGraduate,
          newClassId: this.litreList[i].newClassId,
          oldClassId: this.litreList[i].id,
          teacherId: this.litreList[i].teacherId,
        });
      }
      if (!flag) {
        litrePromotion(this.queryParams).then((res) => {
          console.log(res);
          this.$message({
            type: "success",
            message: "升班成功!",
          });
          this.$emit("litreClass");
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.litre_page {
  margin: 0 auto;
  padding: 10px 50px;
  .text {
    padding-top: 20px;
    text-align: center;
    color: red;
  }
  .btn {
    padding-top: 30px;
    text-align: center;
    span:last-child {
      margin-left: 80px;
    }
  }
}
</style>
