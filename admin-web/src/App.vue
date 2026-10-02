<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
}
</script>

<style>
.el-divider--horizontal {
  margin-top: 0px;
}
.el-dialog__body{
  padding: 10px 20px 20px;
}

.tab_list {
  display: flex;
}
.tab_list .tab_item {
  padding: 0 10px;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  border-top: 2px solid #8c8c8c;
  margin-right: 10px;
  cursor: pointer;
}
.tab_list .active {
  border-top: 2px solid #4f93fe;
  color: #4f93fe !important;
}
.dialog-footer{
  text-align: center;
}
.el-dialog__header{
  padding-bottom: 0;
}
</style>
