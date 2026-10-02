<template>
  <div>
    <div class="studentSelect" @click="selectClick" v-if="!studentName">
      ＋选择
    </div>
    <div class="studentSelect_active" v-else>
      {{ studentName }}
    </div>

    <!-- 弹窗 -->
    <el-dialog
      title="检索"
      :visible="studentVisible"
      width="1000px"
      :show-close="false"
      append-to-body
    >
      <el-divider></el-divider>
      <el-form
        :model="studentParams"
        ref="studentForm"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="班级:" prop="classId">
          <el-select
            v-model="studentParams.classId"
            clearable
            placeholder="全部"
          >
            <el-option
              v-for="item in classIdList"
              :key="item.value"
              :label="item.className"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学生姓名:" prop="studentName">
          <el-input
            v-model="studentParams.studentName"
            placeholder="请输入学生姓名"
            clearable
            size="small"
            @keyup.enter.native="handleStudent"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleStudent"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        :data="studentNameList"
        :border="true"
        @row-click="rowClick"
        stripe
        highlight-current-row
        :header-cell-style="{ background: '#186ba0', color: '#ffffff' }"
      >
        <el-table-column label="班级" align="center" prop="className" />
        <el-table-column label="学生姓名" align="center" prop="studentName" />
        <el-table-column
          label="监护人姓名"
          align="center"
          prop="guardianName"
        />
        <el-table-column
          label="监护人电话"
          align="center"
          prop="guardianPhone"
        />
      </el-table>

      <pagination
        v-show="studentTotal > 0"
        :total="studentTotal"
        :page.sync="studentParams.pageNum"
        :limit.sync="studentParams.pageSize"
        @pagination="selectClick"
      />
      <div class="studentBtn">
        <div>
          <el-button type="primary" @click="submitStudent">确 定</el-button>
          <el-button @click="cancelStudent">取 消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    studentName: {
      // 接收父组件传递的学生姓名
      type: String,
      default: "",
    },
    classIdList: {
      // 接收父组件传递的班级数组
      type: Array,
      default: [],
    },
    studentVisible: {
      // 这是是否显示 学生弹窗
      type: Boolean,
      default: false,
    },
    studentNameList: {
      // 这是学生列表数据
      type: Array,
      default: [],
    },
    studentTotal: {
      // 学生人数总数
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      studentParams: {
        // 搜索参数
        pageNum: 1,
        pageSize: 10,
        classId: null, // 班级id
        studentName: null, // 学生姓名
      },
      currentStudent: {}, // 保留点击学生某行
    };
  },
  methods: {
    // 监听学生列表点击某行
    rowClick(row) {
      this.currentStudent = row;
    },
    // 新增时 里选择学生
    selectClick() {
      // 列表分页查询学生
      this.$emit("selectClick", this.studentParams);
    },
    /** 学生列表搜素事件 */
    handleStudent() {
      this.studentParams.pageNum = 1;
      this.selectClick();
    },
    // 确定选择当前学生
    submitStudent() {
      this.studentParams = {
        pageNum: 1,
        pageSize: 10,
        classId: null, // 班级id
        studentName: null, // 学生姓名
      };
      this.$emit("submitStudent", this.currentStudent);
    },
    // 取消选择学生列表的弹窗
    cancelStudent() {
      this.studentParams = {
        pageNum: 1,
        pageSize: 10,
        classId: null, // 班级id
        studentName: null, // 学生姓名
      };
      this.$emit("cancelStudent", false);
    },
  },
};
</script>

<style land="scss" scoped>
.studentSelect {
  color: blue;
  -moz-user-select: none; /*火狐*/
  -webkit-user-select: none; /*webkit浏览器*/
  -ms-user-select: none; /*IE10*/
  -khtml-user-select: none; /*早期浏览器*/
  user-select: none;
  cursor: pointer;
}
.studentSelect_active {
  -moz-user-select: none; /*火狐*/
  -webkit-user-select: none; /*webkit浏览器*/
  -ms-user-select: none; /*IE10*/
  -khtml-user-select: none; /*早期浏览器*/
  user-select: none;
}
.studentBtn {
  padding-top: 20px;
  text-align: center;
}
</style>
