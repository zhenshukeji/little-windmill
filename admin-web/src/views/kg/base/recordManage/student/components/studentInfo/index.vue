<template>
  <div v-show="show" class="app-container">
    <!-- tab切换 -->
    <div class="tab_list">
      <div class="tab_item" :class="{ active: activeIndex === 0 }" @click="changeTab(0)">基本信息</div>
      <div class="tab_item" :class="{ active: activeIndex === 2 }" @click="changeTab(2)">报名情况</div>
    </div>

    <!-- 基本信息 -->
    <detail :show="activeIndex === 0" :info="info" :type="1" />

    <!-- 报名情况 -->
    <applySituation :show="activeIndex === 2" :info="info" />

    <div class="back">
      <el-button type="primary" @click="close">返 回</el-button>
    </div>
  </div>
</template>

<script>
import detail from '../historyRecord/components/detail';
import applySituation from './components/applySituation';

export default {
  props: {
    show: {
      default: false,
      type: Boolean
    },
    info: {
      default: () => {},
      type: Object
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.activeIndex = 0
        console.log('info', this.info)
      }
    }
  },
  components: {
    detail,
    applySituation
  },
  data() {
    return {
      detailVisible: false,
      detailInfo: {},
      activeIndex: -1
    }
  },
  mounted() {},
  methods: {
    changeTab(index) {
      this.activeIndex = index
    },
    // 返回
    close() {
      this.activeIndex = -1
      this.$emit('close')
    }
  }
}
</script>

<style lang='scss' scoped>
.back {
  margin-top: 20px;
  text-align: center;
}

.tab_list .tab_item {
  border-top: 0;
  border-bottom: 2px solid #8c8c8c;
  margin-bottom: 20px;
}

.tab_list .active {
  border-top: 0;
  border-bottom: 2px solid #247a68;
  color: #247a68 !important;
  background-color: #e6f7ff;
}
</style>
