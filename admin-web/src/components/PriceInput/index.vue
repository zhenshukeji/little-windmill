<template>
  <div>
    <el-input :value="value" @blur="handleBlur" @input="handleInput" :maxlength="maxlength" :placeholder="placeholder">
    </el-input>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      default: '',
      type: [Number, String]
    },
    placeholder: {
      default: '',
      type: String
    },
    // 0为两位小数 1为整数
    type: {
      default: 0,
      type: Number
    },
    // 最大值
    max: {
      default: 0,
      type: Number
    },
    // 最小值
    min: {
      default: 0,
      type: Number
    },
    maxlength: {
      default: 16,
      type: Number
    }
  },
  methods: {
    // 限制输入两位小数金额
    handleInput(val) {
      if (this.type === 0) {
        val = val.replace(/[^\d.]/g, '') //清除“数字”和“.”以外的字符
        val = val.replace(/\.{2,}/g, '.') //只保留第一个. 清除多余的
        val = val.replace('.', '$#$').replace(/\./g, '').replace('$#$', '.')
        val = val.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3') //只能输入两个小数
        if (val.indexOf('.') < 0 && val != '') {
          //以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
          val = parseFloat(val)
        }
        let strObj = val.toString()
        if (strObj.indexOf('.') > -1 && strObj === '0.00') {
          val = parseFloat(val).toFixed(1)
        }
      } else {
        val = val.replace(/^(0+)|[^\d]+/g, '')
      }
      // 不能超过最大值
      if (this.max > 0 && val > this.max) {
        val = this.max
      }
      // 不能小于最小值
      if (this.min >= 0 && val < this.min) {
        val = this.min
      }
      this.$emit('input', val)
    },
    // 失去焦点
    handleBlur(event) {
      let value = event.target.value
      if (!value) {
        this.$emit('input', 0)
      }
      // 最后一位是.
      if (value.indexOf('.') === value.length - 1) {
        this.$emit('input', value.replace('.', ''))
      }
    }
  }
}
</script>


