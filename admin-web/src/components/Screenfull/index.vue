<template>
  <div>
    <span style="font-size: 15px;padding-right: 10px">所在学校：{{ user.blocName }}-{{ user.kgName }}</span>
    <svg-icon :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" @click="click" />
  </div>
</template>

<script>
import screenfull from 'screenfull'
import { mapState } from 'vuex'



export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false,
      // kindergarten: '金色摇篮',
      // kindergartenAddress: '深圳校区',
    }
  },
  computed: {
    ...mapState({
      user: state => state.user.user,
    }),
  },
  mounted() {
    this.init()
  },
  beforeDestroy() {
    this.destroy()
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message({ message: '你的浏览器不支持全屏', type: 'warning' })
        return false
      }
      screenfull.toggle()
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.isEnabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.isEnabled) {
        screenfull.off('change', this.change)
      }
    }
  }
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;
  ;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
