<template>
  <div class="app-container home">
    <div class="welcome-card">
      <div class="wc-left">
        <div class="wc-hello">{{ greeting }}，{{ nickName }}</div>
        <div class="wc-sub">{{ kgName }} · {{ roleName }} · {{ today }}</div>
      </div>
    </div>

    <el-row :gutter="16" class="workbench-row">
      <el-col :xs="24" :md="16">
        <div class="card">
          <div class="card-title">
            请假待办
            <span class="card-extra" v-if="!vacateError && vacateTotal > 0">共 {{ vacateTotal }} 条</span>
          </div>

          <div v-if="vacateError" class="state-box">
            <div class="state-text">待办加载失败：{{ vacateError }}</div>
            <el-button size="mini" type="primary" plain @click="loadVacate">重试</el-button>
          </div>

          <el-table
            v-else
            :data="vacateList"
            v-loading="vacateLoading"
            empty-text="暂无待办审批"
            class="vacate-table"
          >
            <el-table-column label="学生" prop="studentName" min-width="90" show-overflow-tooltip />
            <el-table-column label="班级" prop="className" min-width="90" show-overflow-tooltip />
            <el-table-column label="类型" min-width="70">
              <template slot-scope="scope">{{ vacateTypeText(scope.row.type) }}</template>
            </el-table-column>
            <el-table-column label="起止时间" min-width="150">
              <template slot-scope="scope">{{ vacateRange(scope.row) }}</template>
            </el-table-column>
            <el-table-column label="操作" width="90" align="center">
              <template slot-scope="scope">
                <el-button type="text" class="brand-link" @click="goApprove(scope.row)">去审批</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>

      <el-col :xs="24" :md="8">
        <div class="card">
          <div class="card-title">基础园务</div>
          <div class="quick-grid">
            <div v-for="q in visibleQuicks" :key="q.path" class="quick-item" @click="$router.push(q.path).catch(() => {})">
              <svg-icon :icon-class="q.icon" class="quick-icon" />
              <div class="quick-name">{{ q.name }}</div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getInfo } from "@/api/login";
import { studentList } from "@/api/kg/work/backlog/studentVacate";

export default {
  name: "Index",
  data() {
    return {
      nickName: "",
      kgName: "",
      roleName: "",
      roles: [],
      vacateList: [],
      vacateTotal: 0,
      vacateLoading: false,
      vacateError: "",
      quicks: [
        { name: "幼儿档案", path: "/base/student", icon: "peoples", roles: ["admin", "kg_admin"] },
        { name: "教职工档案", path: "/base/staff", icon: "user", roles: ["admin", "kg_admin"] },
        { name: "年级班级", path: "/base/gradeClass", icon: "tree", roles: ["admin", "kg_admin"] },
        { name: "学期管理", path: "/base/semester", icon: "date", roles: ["admin", "kg_admin"] },
        { name: "学校概况", path: "/base/survey", icon: "form", roles: ["admin", "kg_admin"] },
        { name: "请假审批", path: "/backlog/studentVacateHandle", icon: "clipboard", roles: [] }
      ]
    };
  },
  computed: {
    visibleQuicks() {
      return this.quicks.filter(q => !q.roles.length || q.roles.some(r => this.roles.indexOf(r) !== -1));
    },
    greeting() {
      const h = new Date().getHours();
      if (h < 6) return "凌晨好";
      if (h < 12) return "早上好";
      if (h < 14) return "中午好";
      if (h < 18) return "下午好";
      return "晚上好";
    },
    today() {
      const week = "日一二三四五六".charAt(new Date().getDay());
      return this.parseTime(new Date(), "{y}年{m}月{d}日") + " 星期" + week;
    }
  },
  created() {
    this.loadIdentity();
    this.loadVacate();
  },
  methods: {
    loadIdentity() {
      getInfo()
        .then(res => {
          this.nickName = (res.user && res.user.nickName) || "";
          this.kgName = res.kgName || "";
          this.roles = res.roles || [];
          this.roleName = this.roleText(this.roles);
        })
        .catch(() => {
          this.kgName = "";
        });
    },
    roleText(roles) {
      if (roles.indexOf("kg_admin") !== -1) return "园所管理员";
      if (roles.indexOf("teacher") !== -1) return "教师";
      if (roles.indexOf("admin") !== -1) return "超级管理员";
      return "";
    },
    loadVacate() {
      this.vacateLoading = true;
      this.vacateError = "";
      studentList({ pageNum: 1, pageSize: 10 })
        .then(res => {
          const data = res.data || {};
          this.vacateList = data.records || [];
          this.vacateTotal = data.total || 0;
        })
        .catch(err => {
          this.vacateError = (err && err.message) || "请稍后重试";
        })
        .finally(() => {
          this.vacateLoading = false;
        });
    },
    vacateTypeText(t) {
      if (t === 0 || t === "0") return "病假";
      if (t === 1 || t === "1") return "事假";
      return "-";
    },
    vacateRange(row) {
      const b = this.emptyToNull(row.beginTime);
      const e = this.emptyToNull(row.endTime);
      if (!b && !e) return "-";
      return (b || "-") + " ~ " + (e || "-");
    },
    emptyToNull(v) {
      if (v === undefined || v === null) return "";
      const s = String(v);
      return s === "None" || s === "null" ? "" : s;
    },
    goApprove(row) {
      this.$router.push("/backlog/studentVacateHandle").catch(() => {});
    }
  }
};
</script>

<style lang="scss" scoped>
.home {
  .welcome-card {
    background: #ffffff;
    border: 1px solid #e3e9e6;
    border-radius: 8px;
    padding: 22px 24px;
    margin-bottom: 16px;
    .wc-hello {
      font-size: 20px;
      font-weight: 600;
      color: #233831;
      letter-spacing: 0.5px;
    }
    .wc-sub {
      margin-top: 8px;
      font-size: 13px;
      color: #60736b;
    }
  }
  .card {
    background: #ffffff;
    border: 1px solid #e3e9e6;
    border-radius: 8px;
    padding: 16px 20px;
    min-height: 320px;
    .card-title {
      font-size: 15px;
      font-weight: 600;
      color: #233831;
      padding-bottom: 12px;
      border-bottom: 1px solid #eef2f0;
      margin-bottom: 12px;
      .card-extra {
        float: right;
        font-size: 12px;
        font-weight: normal;
        color: #60736b;
      }
    }
  }
  .state-box {
    padding: 32px 0;
    text-align: center;
    .state-text {
      color: #b4542f;
      font-size: 13px;
      margin-bottom: 12px;
    }
  }
  .brand-link {
    color: #247a68;
  }
  .quick-grid {
    display: flex;
    flex-wrap: wrap;
    .quick-item {
      width: 33.33%;
      text-align: center;
      padding: 18px 0 14px;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.2s;
      &:hover {
        background: #eaf3ef;
      }
      .quick-icon {
        font-size: 26px;
        color: #247a68;
      }
      .quick-name {
        margin-top: 8px;
        font-size: 13px;
        color: #233831;
      }
    }
  }
}
</style>
