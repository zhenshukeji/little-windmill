<template>
  <el-form ref="basicInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="表名称" prop="tableName">
          <el-input placeholder="请输入仓库名称" v-model="info.tableName" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="表描述" prop="tableComment">
          <el-input placeholder="请输入" v-model="info.tableComment" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="实体类名称" prop="className">
          <el-input placeholder="请输入" v-model="info.className" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="作者" prop="functionAuthor">
          <el-input placeholder="请输入" v-model="info.functionAuthor" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否可搜索" prop="hasList">
          <el-radio v-model="info.hasList" :label="true">是</el-radio>
          <el-radio v-model="info.hasList" :label="false">否</el-radio>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否可新增" prop="hasInsert">
          <el-radio v-model="info.hasInsert" :label="true">是</el-radio>
          <el-radio v-model="info.hasInsert" :label="false">否</el-radio>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否可导出" prop="hasExport">
          <el-radio v-model="info.hasExport" :label="true">是</el-radio>
          <el-radio v-model="info.hasExport" :label="false">否</el-radio>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否可更新" prop="hasUpdate">
          <el-radio v-model="info.hasUpdate" :label="true">是</el-radio>
          <el-radio v-model="info.hasUpdate" :label="false">否</el-radio>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否有详情" prop="hasDetails">
          <el-radio v-model="info.hasDetails" :label="true">是</el-radio>
          <el-radio v-model="info.hasDetails" :label="false">否</el-radio>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="是否可删除" prop="hasDelete">
          <el-radio v-model="info.hasDelete" :label="true">是</el-radio>
          <el-radio v-model="info.hasDelete" :label="false">否</el-radio>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        tableName: [
          { required: true, message: "请输入表名称", trigger: "blur" }
        ],
        tableComment: [
          { required: true, message: "请输入表描述", trigger: "blur" }
        ],
        className: [
          { required: true, message: "请输入实体类名称", trigger: "blur" }
        ],
        functionAuthor: [
          { required: true, message: "请输入作者", trigger: "blur" }
        ]
      }
    };
  }
};
</script>
