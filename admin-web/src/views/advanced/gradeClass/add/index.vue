<template>
  <div class="add_page">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :rules="rules"
      label-width="100px"
      label-position="left">
      <el-form-item label="班级名称: " prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入班级名称"></el-input>
      </el-form-item>
      <el-form-item label="年级: " prop="gradeId">
        <el-select
          v-model="queryParams.gradeId"
          clearable
          placeholder="请选择年级">
          <el-option
            v-for="item in gradeList"
            :key="item.id"
            :label="item.gradeName"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitAdd">保存</el-button>
        <el-button @click="addCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { listGrade, addClassroom } from "@/api/advanced/gradeClass";
export default {
  data() {
    return {
      queryParams: {
        className: null,
        gradeId: null,
      },
      rules: {
        className: [
          {
            required: true,
            message: "请输入班级名称",
            trigger: "blur",
          },
        ],
        gradeId: [
          {
            required: true,
            message: "请选择年级",
            trigger: "change",
          },
        ],
      },
      gradeList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      listGrade().then((res) => {
        if (res.code === 200) {
          this.gradeList = res.data;
          console.log(res);
        }
      });
    },
    addCancel() {
      this.$emit("addCancel");
    },
    submitAdd() {
      this.$refs["queryForm"].validate((valid) => {
        if (valid) {
          addClassroom(this.queryParams).then((res) => {
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: "添加班级成功!",
              });
              this.$emit('addClass')
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.add_page {
  margin: auto;
  width: 800px;
  padding-top: 30px;
}
</style>
