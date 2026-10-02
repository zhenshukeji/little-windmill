<template>
  <div>
    <el-form ref="form" :model="editForm" :rules="rules" label-width="120px" label-position="left">
      <el-form-item label="封面" prop="imgUrl">
        <div class="head_class">
          <Upload :file="editForm.imgUrl" @upload="handleAvatarSuccess"></Upload>
          <div class="avatar_desc">提示:建议封面尺寸350:100</div>
        </div>
      </el-form-item>
      <el-form-item label="内容" prop="surveyContent">
        <editor v-model="editForm.surveyContent" :min-height="192"/>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="onSave">保存</el-button>
        <el-button @click="clickAbandon">放弃</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { updateSurvey } from "@/api/kg/base/advanced/survey";
  import Upload from "@/components/UpLoad";
  export default {
    components:{
      Upload,
    },
    name: "",
    props: {
      updateInfo: {
        default: () => {},
        type: Object,
      }
    },
    data() {
      return {
        editForm: {
          imgUrl: null,
          surveyContent: null,
          id: null,
        },
        rules: {
          imgUrl: [
            { required: true, message: "封面不能为空", trigger: "blur" },
          ]
        },
      }
    },
    created() {
      if (this.updateInfo) {
        this.editForm.id = this.updateInfo.id;
        this.editForm.surveyContent = this.updateInfo.surveyContent;
        this.editForm.imgUrl = this.updateInfo.imgUrl;
      }
    },
    methods: {
      // 图片上传成功回调
      handleAvatarSuccess(data) {
        this.editForm.imgUrl = data.url;
      },
      // 放弃操作
      clickAbandon () {
        this.$emit("abandon", false)
      },
      // 判断富文本编辑器输入是否为空或回车
      getText(str) {
        return str
          .replace(/<[^<p>]+>/g, '')  // 将所有<p>标签 replace ''
          .replace(/<[</p>$]+>/g, '')  // 将所有</p>标签 replace ''
          .replace(/&nbsp;/gi, '')  // 将所有 空格 replace ''
          .replace(/<[^<br/>]+>/g, '') // 将所有 换行符 replace ''
      },
      isNull(str) {
        if (str === '') return true
        var regu = '^[ ]+$'
        var re = new RegExp(regu)
        return re.test(str)
      },
      // 保存
      onSave () {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            if (this.isNull(this.getText(this.editForm.surveyContent))){
              this.$message.warning("内容不能为空");
              return;
            }
            updateSurvey(this.editForm).then((res) => {
              if(res.code === 200) {
                this.$emit("saveClick")
              }
            })
          }
        });
      },
    }
  }
</script>

<style scoped>

</style>
