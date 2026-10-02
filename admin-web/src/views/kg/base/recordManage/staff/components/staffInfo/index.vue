<template>
  <!-- 员工信息 -->
  <div class="add_modify">
    <el-form
      ref="form"
      class="form_class"
      :model="form"
      label-width="100px"
      label-position="left"
    >
      <div class="basic">基本信息</div>
      <div class="line"></div>

      <el-form-item label="姓名：" prop="name">
        <span>{{ form.name }}</span>
      </el-form-item>
      <el-form-item label="岗位：" prop="postIds">
        <!-- <div v-for="item in form.postIds" :key="item">{{ getJobName(item) }}</div> -->
        {{ postComputed(form.postIds, posts) }}
      </el-form-item>
      <el-form-item label="手机号码：" prop="phone">
        <span>{{ form.phone }}</span>
      </el-form-item>
      <el-form-item label="编号：" prop="staffNumber">
        <span>{{ form.staffNumber }}</span>
      </el-form-item>
      <el-form-item label="身份证：" prop="identityNumber">
        <span>{{ form.identityNumber }}</span>
      </el-form-item>
      <el-form-item label="入职日期：" prop="hiredate">
        <span>{{ form.hiredate }}</span>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <span>{{ form.sex === 0 ? "男" : "女" }}</span>
      </el-form-item>
      <el-form-item label="民族：" prop="nation">
        <span>{{ form.nation }}</span>
      </el-form-item>
      <el-form-item label="婚姻状态：" prop="marriage">
        <span>{{ form.marriage }}</span>
      </el-form-item>
      <el-form-item label="居住地址：" prop="address" style="width: 100%">
        <span>{{ form.address }}</span>
      </el-form-item>
      <el-form-item
        label="户口所在地："
        prop="accountAddress"
        style="width: 100%"
      >
        <span>{{ form.accountAddress }}</span>
      </el-form-item>

      <div class="head_class" v-if="form.avatar">
        <img :src="form.avatar" class="avatar" />
      </div>

      <div class="basic">教育信息</div>
      <div class="line"></div>

      <el-form-item label="学历：" prop="education">
        <span>{{ form.education }}</span>
      </el-form-item>
      <el-form-item label="毕业院校：" prop="school">
        <span>{{ form.school }}</span>
      </el-form-item>
      <el-form-item label="专业：" prop="major">
        <span>{{ form.major }}</span>
      </el-form-item>
      <div class="basic">其他信息</div>
      <div class="line"></div>
      <el-form-item label="说明：" prop="staffExplain" style="width: 100%">
        <span>{{ form.staffExplain }}</span>
      </el-form-item>
      <el-form-item label="注意" style="width: 100%">
        <div style="color: red">新增的员工初始密码为：abcd1234</div>
      </el-form-item>

      <div class="footer">
        <el-button @click="back">返回</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { listStaff, getStaff, getStation } from "@/api/kg/base/record/staff";
export default {
  data() {
    return {
      form: {},
      // 展示员工信息
      showInfo: false,
      // 全部岗位
      posts: [],
    };
  },

  props: ["id", "type"],

  components: {},

  computed: {
    postComputed() {
      return function (val, vals) {
        if(!val){
          return ''
        }
        let str = "";
        for (let i = 0; i < vals.length; i++) {
          for (let j = 0; j < val.length; j++) {
            if (vals[i].roleId === val[j]) {
              str = str + vals[i].roleName + ",";
            }
          }
        }
        str = str.replace(/[,]$/, "");
        return str;
      };
    },
  },

  mounted() {
    this.getStaffInfo();
    this.fetchStation()
  },

  methods: {
    // 获取员工信息
    getStaffInfo() {
      getStaff(this.id, {
        type: this.type,
      }).then((response) => {
        this.form = response.data;
      });
    },
    // 获取岗位
    fetchStation() {
      getStation().then((res) => {
        this.posts = res.data;
      });
    },
    // 根据岗位id在岗位集合中查找对应岗位名
    getJobName(id) {
      let name;
      this.posts.forEach((item) => {
        if (item.roleId === id) {
          name = item.roleName;
        }
      });
      return name;
    },
    // 返回
    back() {
      this.$emit("closeInfo");
    },
  },
};
</script>
<style lang="scss" scoped>
.add_modify {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding: 20px;
  .form_class {
    width: 920px;
    display: flex;
    flex-wrap: wrap;
    position: relative;
    .basic {
      font-size: 22px;
      font-weight: bold;
      color: #777777;
    }
    .line {
      width: 100%;
      margin: 5px 0 10px;
      border-bottom: 2px solid #ececec;
    }
    ::v-deep .el-form-item {
      width: 280px;
      margin-right: 20px;
    }
    .head_class {
      position: absolute;
      left: 920px;
      top: 50px;
      width: 160px;
      height: 160px;
      border: 1px solid #e1e1e1;
      border-radius: 10px;
      .avatar_uploader {
        width: 150px;
        height: 150px;
        display: flex;
        margin: 5px 0 5px 5px;
        border: 1px dashed #cdcdcd;
        border-radius: 20px;
        ::v-deep .el-upload {
          width: 100%;
          height: 100%;
          display: flex;
          align-items: center;
          // border: 1px solid #e4e4e4;
          justify-content: center;
        }
      }
      img {
        width: 100%;
        height: 100%;
      }
      .avatar_desc {
        margin-top: 10px;
        color: #777794;
      }
    }
    .footer {
      margin: 0 auto;
    }
  }
}
</style>
