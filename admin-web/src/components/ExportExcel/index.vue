<template>
  <el-button :loading="downloadLoading" @click="exportExcel">
    导出
  </el-button>
</template>

<script>
export default {
  props: {
    tHeader: {
      type: Array,
      default: () => []
    },
    merges: {
      type: Array,
      default: () => []
    },
    data: {
      type: Array,
      default: () => []
    },
    name: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      downloadLoading: false
    }
  },
  methods: {
    // 测试
    exportExcel() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then((excel) => {
        const tHeader = this.tHeader
        const data = this.formatJson(tHeader, this.data)
        const merges = this.merges
        excel.export_json_to_excel({
          header: tHeader, //表头 必填
          data, //具体数据 必填
          merges: merges,
          filename: this.name
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          if (j === 'timestamp') {
            return this.parseTime(v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>

