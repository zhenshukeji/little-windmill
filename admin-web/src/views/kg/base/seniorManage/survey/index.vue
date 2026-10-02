<template>
  <div class="app-container">
    <template v-if="!showUpdate">
      <!-- 表格 -->
      <el-table v-loading="loading" :data="surveyList"  :border="true">

        <el-table-column label="Banner图" align="center" prop="imgUrl">
          <template slot-scope="{row}">
            <img :src="row.imgUrl" alt=""/>
          </template>
        </el-table-column>

        <el-table-column label="修改人" align="center" prop="modifier" />

        <el-table-column label="修改时间" align="center" prop="updateTime" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <update v-if="showUpdate" :update-info="updateInfo" @abandon = "abandon" @saveClick = "saveClick"></update>
  </div>
</template>

<script>
import { listSurvey} from "@/api/kg/base/advanced/survey";
import update from "./components/update";

export default {
  name: "Survey",
  components: {
    update
  },
  data() {
    return {
      updateInfo: null,
      showUpdate: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedSysUser: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 园区概况 表格数据
      surveyList: [],
      // 用户信息表格数据
      sysUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      opeVisible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 编辑
    handleUpdate (row) {
      this.updateInfo = row;
      this.showUpdate = true;
    },
    // 放弃修改
    abandon (val) {
      this.showUpdate = val;
      this.getList();
    },
    // 保存修改
    saveClick () {
      this.showUpdate = false;
      this.getList();
    },
    /** 查询园区概况 列表 */
    getList() {
      this.loading = true;
      listSurvey(this.queryParams).then(response => {
        this.surveyList = response.data;
        this.loading = false;
      });
    },
  }
};
</script>
