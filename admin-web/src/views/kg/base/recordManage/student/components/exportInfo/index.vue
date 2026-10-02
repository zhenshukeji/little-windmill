<template>
  <div class="popup">
    <el-dialog title="导出学生信息" :visible.sync="show" width="500px" :before-close="close">
      <el-divider />
      <div class="content">
        <div class="desc">导出目标列:</div>
        <div class="check_box">
          <el-checkbox v-model="checked" @change="allChoose">全选</el-checkbox>
          <el-checkbox v-model="form.name">姓名</el-checkbox>
          <el-checkbox v-model="form.gender">性别</el-checkbox>
          <el-checkbox v-model="form.age">年龄</el-checkbox>
          <el-checkbox v-model="form.nation">民族</el-checkbox>
          <el-checkbox v-model="form.bloodType">血型</el-checkbox>
          <el-checkbox v-model="form.healthStatus">健康状况</el-checkbox>
          <el-checkbox v-model="form.cardType">证件类型</el-checkbox>
          <el-checkbox v-model="form.cardNumber">证件号码</el-checkbox>
          <el-checkbox v-model="form.nationality">国籍</el-checkbox>
          <el-checkbox v-model="form.className">班级</el-checkbox>
          <el-checkbox v-model="form.teacher">教师</el-checkbox>
          <el-checkbox v-model="form.studyingWay">就读方式</el-checkbox>
          <el-checkbox v-model="form.enrollDate">入园日期</el-checkbox>
          <el-checkbox v-model="form.birthdate">孩子出生日期</el-checkbox>
          <el-checkbox v-model="form.placeOfBirth">出生所在地</el-checkbox>
          <el-checkbox v-model="form.nativePlace">籍贯</el-checkbox>
          <el-checkbox v-model="form.accountQuality">户口性质</el-checkbox>
          <el-checkbox v-model="form.accountType">户口类型</el-checkbox>
          <el-checkbox v-model="form.accountAddress">户口所在地</el-checkbox>
          <el-checkbox v-model="form.address">现在住址</el-checkbox>
          <el-checkbox v-model="form.guardianNameOne">监护人1</el-checkbox>
          <el-checkbox v-model="form.guardianRelationOne">监护人1关系</el-checkbox>
          <el-checkbox v-model="form.guardianPhoneOne">监护人1联系电话</el-checkbox>
          <el-checkbox v-model="form.guardianJobOne">监护人1职业</el-checkbox>
          <el-checkbox v-model="form.guardianCardTypeOne">监护人1证件类型</el-checkbox>
          <el-checkbox v-model="form.guardianCardNumberOne">监护人1证件号码</el-checkbox>
          <el-checkbox v-model="form.guardianNameTwo">监护人2</el-checkbox>
          <el-checkbox v-model="form.guardianRelationTwo">监护人2关系</el-checkbox>
          <el-checkbox v-model="form.guardianPhoneTwo">监护人2联系电话</el-checkbox>
          <el-checkbox v-model="form.guardianJobTwo">监护人2职业</el-checkbox>
          <el-checkbox v-model="form.guardianCardTypeTwo">监护人2证件类型</el-checkbox>
          <el-checkbox v-model="form.guardianCardNumberTwo">监护人2证件号码</el-checkbox>
          <el-checkbox v-model="form.isWeak">是否高危体弱</el-checkbox>
          <el-checkbox v-model="form.isOnlyChild">是否独生子女</el-checkbox>
          <el-checkbox v-model="form.isLeft">是否留守儿童</el-checkbox>
          <el-checkbox v-model="form.isOrphan">是否孤儿</el-checkbox>
          <el-checkbox v-model="form.isDisability">是否残疾幼儿</el-checkbox>
          <el-checkbox v-model="form.isWorkers">是否进城务工子女</el-checkbox>
          <el-checkbox v-model="form.specialCase">特殊情况</el-checkbox>
        </div>
      </div>
      <div class="dialog-footer">
        <el-divider />
        <el-button @click="close">返 回</el-button>
        <el-button type="primary" style="marginLeft:40px" @click="submitForm">确 定</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.form = {
          accountAddress: false,
          accountQuality: false,
          accountType: false,
          address: false,
          age: false,
          birthdate: false,
          bloodType: false,
          cardNumber: false,
          cardType: false,
          className: false,
          enrollDate: false,
          gender: false,
          guardianCardNumberOne: false,
          guardianCardNumberTwo: false,
          guardianCardTypeOne: false,
          guardianCardTypeTwo: false,
          guardianJobOne: false,
          guardianJobTwo: false,
          guardianNameOne: false,
          guardianNameTwo: false,
          guardianPhoneOne: false,
          guardianPhoneTwo: false,
          guardianRelationOne: false,
          guardianRelationTwo: false,
          healthStatus: false,
          isDisability: false,
          isLeft: false,
          isOnlyChild: false,
          isOrphan: false,
          isWeak: false,
          isWorkers: false,
          name: false,
          nation: false,
          nationality: false,
          nativePlace: false,
          placeOfBirth: false,
          specialCase: false,
          studyingWay: false,
          teacher: false
        }
        this.checked = false
      }
    }
  },
  data() {
    return {
      checked: false,
      form: {}
    }
  },
  methods: {
    // 全选
    allChoose(val) {
      for (let key in this.form) {
        this.form[key] = val
      }
    },
    // 关闭弹窗
    close() {
      this.$emit('close')
    },
    // 确定
    submitForm() {
      // 至少需要选择一个
      let form = Object.values(this.form)
      let flag = form.some((item) => {
        return item
      })
      if (!flag) {
        this.msgError('执行需要选择一项导出内容')
        return
      }
      this.$emit('handleExport', this.form)
    }
  }
}
</script>

<style lang='scss' scoped>
.popup {
  .content {
    display: flex;
  }
  .desc {
    font-weight: bold;
    margin-right: 20px;
  }
  .check_box {
    width: 300px;
  }
  ::v-deep .el-checkbox__label {
    width: 100px;
  }
}
</style>
