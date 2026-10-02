<template>
  <div class="tab_page">
    <div class="tab_wrap">
      <div class="tab_item tab_head">
        {{ title }}
      </div>
      <div v-for="(item, index) in tabData" :key="index" :class="{ 'active': chooseIndex === index }"
        @click="handleChoose(item, index)" class="tab_item">
        <slot :item="item"></slot>
      </div>
      <div v-if="tabData.length === 0" class="tab_item">
        暂无数据
      </div>
    </div>
    <!-- 是否需要翻页 -->
    <template v-if="total">
      <div v-if="currentPage > 0" class="page">
        <el-button :disabled="currentPage === 1" plain @click="changePage(-1)">上一页</el-button>
        <div class="current_page">{{ currentPage }}</div>
        <el-button :disabled="!showNext" plain @click="changePage(1)">下一页</el-button>
      </div>
    </template>
    <div v-else class="total">
      共{{ tabData.length }}条
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    title: {
      type: String,
      default: ''
    },
    tabData: {
      type: Array,
      default: () => []
    },
    limit: {
      type: Number,
      default: 10
    },
    total: {
      type: Number,
      default: 0
    }
  },
  computed: {
    showNext() {
      return this.currentPage < this.total / this.limit
    }
  },
  data() {
    return {
      loading: true,
      chooseIndex: 0,
      currentPage: 1
    }
  },
  mounted() { },
  methods: {
    handleChoose(item, index) {
      this.chooseIndex = index
      this.$emit('handleChoose', item)
    },
    // 翻页
    changePage(val) {
      this.currentPage += val
      this.$emit('changePage', val)
    }
  }
}
</script>

<style lang='scss'>
.tab_page {
  min-width: 200px;
  margin-right: 10px;
}

.tab_wrap {
  max-height: 600px;
  overflow-y: auto;
  overflow-x: hidden;
  // border: 1px solid #e6e6e6;

  .tab_head {
    font-size: 18px !important;
  }

  .tab_item {
    height: 48px;
    line-height: 48px;
    font-size: 14px;
    cursor: pointer;
    text-align: center;
    background: #ffffff;
    // border-bottom: 1px solid #fafafa;
  }

  .tab_item:nth-child(odd) {
    background: #F7F6F6;
  }

  .tab_item:last-child {
    border: none;
  }

  .active {
    color: #fafafa;
    background: #FEB72B !important;
  }
}

.page {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #f2f2f2;

  .current_page {
    margin: 0 30px;
  }

  .page_button {
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    border: 1px solid #f2f2f2;
    border-radius: 4px;
    cursor: pointer;
  }
}

.total {
  margin-top: 10px;
  font-size: 14px;
  color: #959595;
}

// 左边tab组件样式
// .tab {
//   border: 0;
// }

// .tab .tab_item:nth-child(odd) {
//   background: #fff;
// }

// .tab .tab_item {
//   border: 0;
// }

// .tab .active {
//   color: #fafafa;
//   background: #FEB72B !important;
// }
</style>
