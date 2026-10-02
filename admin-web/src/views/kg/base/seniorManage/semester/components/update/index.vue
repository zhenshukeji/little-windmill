<template>
  <div class="add_page">
    <el-form ref="form" :model="updateForm" label-width="80px">
      <el-form-item label="学期名称" prop="semesterName">
        <el-select v-model="updateForm.semesterName" placeholder="请选择">
          <el-option
            v-for="item in semesterList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="beginDate">
        <el-date-picker
          v-model="updateForm.beginDate"
          type="datetime"
          placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endDate">
        <el-date-picker
          v-model="updateForm.endDate"
          type="datetime"
          placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="updateCancel">放弃</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {updateSemester} from "@/api/kg/base/advanced/semester.js"
  export default {
    name: "",
    props: {
      updateInfo : {
        default: ()=>{},
        type: Object,
      }
    },
    data() {
      return {
        semesterList: [
          {
            value: '2020-2021第一学期',
            label: '2020-2021第二学期'
          },
          {
            value: '2021-2022第一学期',
            label: '2021-2022第二学期'
          },
          {
            value: '2022-2023第一学期',
            label: '2022-2023第二学期'
          }
        ],
        updateForm: {},
      }
    },
    created() {
      if (this.updateInfo) {
        this.updateForm = Object.assign({}, this.updateInfo)
      }
    },
    methods: {
      // 放弃编辑
      updateCancel () {
        this.$emit("abandon", false)
      },
      // 提交
      onSubmit() {
        updateSemester(this.updateForm).then((res) => {
          if (res.code === 200) {
            this.$emit("saveClick");
          }
        })
      }
    }
  }
</script>

<style scoped>
  .add_page {
    margin: auto;
    width: 800px;
    padding-top: 80px;
    padding-left: 180px;
  }
</style>
