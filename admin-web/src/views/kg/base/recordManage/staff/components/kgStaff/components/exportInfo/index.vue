<template>
  <div class="popup">
    <el-dialog title="导出员工信息" :visible.sync="show" width="500px" :before-close="close">
      <el-divider />
      <div class="content">
        <div class="desc">导出目标列:</div>
        <div class="check_box">
          <div>
            <el-checkbox v-model="checked" @change="allChoose">全选</el-checkbox>
          </div>
          <el-checkbox v-model="form.name">姓名</el-checkbox>
          <el-checkbox v-model="form.phone">手机号码</el-checkbox>
          <el-checkbox v-model="form.post">岗位</el-checkbox>
          <el-checkbox v-model="form.sex">性别</el-checkbox>
          <el-checkbox v-model="form.identityNumber">身份证号码</el-checkbox>
          <el-checkbox v-model="form.staffNumber">员工号码</el-checkbox>
          <el-checkbox v-model="form.hiredate">入职日期</el-checkbox>
          <el-checkbox v-model="form.marriage">婚姻状况</el-checkbox>
          <el-checkbox v-model="form.education">学历</el-checkbox>
          <el-checkbox v-model="form.school">毕业院校</el-checkbox>
          <el-checkbox v-model="form.major">专业</el-checkbox>
          <el-checkbox v-model="form.nation">民族</el-checkbox>
          <el-checkbox v-model="form.accountAddress">户口所在地</el-checkbox>
          <el-checkbox v-model="form.address">现居住地址</el-checkbox>
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
    },
  },
  watch: {
    show(val) {
      if (val) {
        this.form = {
          accountAddress: false,
          address: false,
          education: false,
          hiredate: false,
          identityNumber: false,
          major: false,
          marriage: false,
          name: false,
          nation: false,
          phone: false,
          post: false,
          school: false,
          sex: false,
          staffNumber: false
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

  .dialog-footer {
    margin-top: 20px;
  }
}
</style>
