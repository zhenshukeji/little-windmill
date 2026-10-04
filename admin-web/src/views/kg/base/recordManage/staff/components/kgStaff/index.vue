<template>
  <div>
    <div class="table" v-show="!showEdit">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="员工姓名:" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入员工姓名" clearable size="small"
                        @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="手机号码:" prop="phone">
              <el-input v-model="queryParams.phone" placeholder="请输入手机号码" clearable size="small"
                        @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="text-align: right">
            <el-form-item>
              <el-button type="text" size="mini" @click="handleConfigure">年级班级配置</el-button>
              <el-button type="primary" size="mini" @click="handleAdd">新增</el-button>
              <el-dropdown trigger="click" @command="handleCommand">
                <el-button type="primary" plain size="mini" style="margin-left: 10px">更多 <i class="el-icon-arrow-down"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="0">模板下载</el-dropdown-item>
                  <el-dropdown-item :command="1">导入</el-dropdown-item>
                  <el-dropdown-item :command="2">导出</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <!-- 表格 -->
      <el-table v-loading="loading" :data="staffList" :border="true">
        <el-table-column label="员工编号" align="center" prop="staffNumber" />

        <el-table-column label="员工姓名" align="center" prop="name">
          <template slot-scope="{ row }">
            <span style="color: #247a68" @click="checkInfo(row)">{{
                row.name
            }}</span>
          </template>
        </el-table-column>

        <el-table-column label="岗位" align="center" prop="postNames" />

        <el-table-column label="性别" align="center" prop="sex">
          <template slot-scope="{ row }">{{
              row.sex === 0 ? "男" : "女"
          }}</template>
        </el-table-column>

        <el-table-column label="手机号码" align="center" prop="phone" />

        <el-table-column label="入职日期" align="center" prop="hiredate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.hiredate, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>

        <el-table-column label="学历" align="center" prop="education" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)">修改</el-button>
            <span style="border-left: 1px solid #999999; margin: 0 10px"></span>
            <el-button size="mini" style="color: red" type="text" @click="handleFire(scope.row)">离职</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>

    <!-- 修改或新增员工 -->
    <div class="add_modify" v-if="showEdit">
      <el-form ref="form" class="form_class" :model="form" :rules="rules" label-width="100px" label-position="left">
        <div class="basic">基本信息</div>
        <div class="line"></div>

        <el-form-item label="姓名：" prop="name">
          <el-input v-model="form.name" placeholder="请输入员工姓名" style="width: 150px" />
        </el-form-item>
        <el-form-item label="岗位：" prop="postIds">
          <el-select v-model="form.postIds" multiple placeholder="请选择" style="width: 120px">
            <el-option v-for="item in posts" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码：" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码" style="width: 180px" />
        </el-form-item>
        <el-form-item label="编号：" prop="staffNumber">
          <el-input v-model="form.staffNumber" placeholder="请输入员工编号" style="width: 180px" />
        </el-form-item>
        <el-form-item label="身份证：" prop="identityNumber">
          <el-input v-model="form.identityNumber" placeholder="请输入身份证号码" style="width: 180px" />
        </el-form-item>
        <el-form-item label="入职日期：" prop="hiredate">
          <el-date-picker clearable size="small" v-model="form.hiredate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择入职日期" style="width: 150px"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别：" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择" style="width: 100px">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="民族：" prop="nation">
          <el-select v-model="form.nation" placeholder="请选择" style="width: 100px">
            <el-option v-for="(item, index) in nations" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="婚姻状态：" prop="marriage">
          <el-select v-model="form.marriage" placeholder="请选择" style="width: 100px">
            <el-option label="未婚" value="未婚"></el-option>
            <el-option label="已婚" value="已婚"></el-option>
            <el-option label="离婚" value="离婚"></el-option>
            <el-option label="丧偶" value="丧偶"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="居住地址：" prop="address" style="width: 100%">
          <el-input v-model="form.address" placeholder="请输入居住地址" />
        </el-form-item>
        <el-form-item label="户口所在地：" prop="accountAddress" style="width: 100%">
          <el-input v-model="form.accountAddress" placeholder="请输入户口所在地" />
        </el-form-item>

        <div class="head_class">
          <Upload :file="form.avatar" @upload="handleAvatarSuccess"></Upload>
          <div class="avatar_desc">上传员工照片</div>
        </div>

        <div class="basic">教育信息</div>
        <div class="line"></div>

        <el-form-item label="学历：" prop="education">
          <el-select v-model="form.education" placeholder="请选择" style="width: 100px">
            <el-option label="中专" value="中专"></el-option>
            <el-option label="大专" value="大专"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="研究生" value="研究生"></el-option>
            <el-option label="高中" value="高中"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="毕业院校：" prop="school">
          <el-input v-model="form.school" placeholder="请输入毕业院校" />
        </el-form-item>
        <el-form-item label="专业：" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业名称" />
        </el-form-item>
        <div class="basic">其他信息</div>
        <div class="line"></div>
        <el-form-item label="说明：" prop="staffExplain" style="width: 100%">
          <el-input maxlength="40" v-model="form.staffExplain" placeholder="请输入说明（40字内）" />
        </el-form-item>
        <el-form-item label="注意" style="width: 100%">
          <div style="color: red">新增的员工初始密码为：abcd1234</div>
        </el-form-item>

        <div class="footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-form>
    </div>

    <!-- 导入 -->
    <el-dialog title="导入" :visible="uploadVisible" width="600px" @close="uploadVisible = false">
      <el-divider />
      <Upload type="xlsx" @upload="importFile" />
    </el-dialog>
    <!-- 导出员工 -->
    <exportInfo :show="exportVisible" @close="exportVisible = false" @handleExport="handleExport" />
    <!-- 离职弹窗 -->
    <el-dialog title="离职" :visible.sync="showFireDialog" width="900px">
      <el-form :inline="true" ref="fireForm" :model="fireForm" :rules="fireRules" label-width="100px"
        label-position="center">
        <el-form-item label="姓名">
          <el-input v-model="fireForm.name" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="入职时间">
          <el-input v-model="fireForm.hiredate" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="民族">
          <el-input v-model="fireForm.nation" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="fireForm.sex" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="学历">
          <el-input v-model="fireForm.education" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="fireForm.phone" disabled style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item label="离职时间" prop="quitDate">
          <el-date-picker clearable size="small" v-model="fireForm.quitDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择离职日期" style="width: 150px"></el-date-picker>
        </el-form-item>
        <el-form-item label="离职原因" prop="quitReason">
          <el-input type="textarea" v-model="fireForm.quitReason" style="width: 600px"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelFire">取 消</el-button>
        <el-button type="primary" @click="confirmFire">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listStaff,
  getStaff,
  addStaff,
  updateStaff,
  getStation,
  fireStaff,
  importStaff,
  exportStaff
} from "@/api/kg/base/record/staff";
import Upload from "@/components/UpLoad";
import exportInfo from './components/exportInfo'
export default {
  components: {
    Upload,
    exportInfo
  },
  data() {
    const checkPhone = (rule, value, callback) => {
      const reg =
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (!reg.test(value)) {
        callback(new Error("请输入合法的手机号"));
      } else {
        callback();
      }
    };
    return {
      // 56个民族
      nations: [
        "汉族",
        "壮族",
        "满族",
        "回族",
        "苗族",
        "维吾尔族",
        "土家族",
        "彝族",
        "蒙古族",
        "藏族",
        "布依族",
        "侗族",
        "瑶族",
        "朝鲜族",
        "白族",
        "哈尼族",
        "哈萨克族",
        "黎族",
        "傣族",
        "畲族",
        "傈僳族",
        "仡佬族",
        "东乡族",
        "高山族",
        "拉祜族",
        "水族",
        "佤族",
        "纳西族",
        "羌族",
        "土族",
        "仫佬族",
        "锡伯族",
        "柯尔克孜族",
        "达斡尔族",
        "景颇族",
        "毛南族",
        "撒拉族",
        "布朗族",
        "塔吉克族",
        "阿昌族",
        "普米族",
        "鄂温克族",
        "怒族",
        "京族",
        "基诺族",
        "德昂族",
        "保安族",
        "俄罗斯族",
        "裕固族",
        "乌孜别克族",
        "门巴族",
        "鄂伦春族",
        "独龙族",
        "塔塔尔族",
        "赫哲族",
        "珞巴族",
      ],
      // 遮罩层
      loading: true,
      // 学校员工 表格数据
      staffList: [],
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 全部岗位
      posts: [],
      // 导入
      uploadVisible: false,
      // 导出
      exportVisible: false,
      // 是否展示编辑或新增页面
      showEdit: false,
      // 是否显示离职弹窗
      showFireDialog: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        phone: null,
        isQuit: null,
        queryType: 0, //0-在职员工,1-离职员工,2-集团员工
      },
      // 表单参数
      form: {
        postIds: null,
      },
      // 表单校验
      rules: {
        name: [
          {
            required: true,
            message: "请输入员工姓名",
            trigger: "blur",
          },
        ],
        staffNumber: [
          {
            required: true,
            message: "请输入员工编号",
            trigger: "blur",
          },
        ],
        postIds: [
          {
            required: true,
            message: "请选择一个岗位",
            trigger: "change",
          },
        ],
        phone: [
          {
            required: true,
            message: "请输入手机号码",
            trigger: "blur",
          },
          {
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        sex: [
          {
            required: true,
            message: "性别不能为空",
            trigger: "change",
          },
        ],
      },
      // 离职表单
      fireForm: {},
      fireRules: {
        quitDate: [{ required: true, message: "请选择离职日期" }],
        quitReason: [
          { required: true, message: "请输入离职缘由", trigger: "blur" },
        ],
      },
      action: process.env.VUE_APP_BASE_API + "/cdn/upload",
    };
  },

  computed: {},

  created() {
    this.getList();
    this.fetchStation();
  },

  methods: {
    //年级班级配置
    handleConfigure () {
      this.$router.push('/campus/advanced/gradeClass');
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.showEdit = true;
      this.title = "添加学校员工 ";
    },
    // 更多按钮
    handleCommand(val) {
      switch (val) {
        case 0:
          window.location.href =
            '/file/staff_template.xlsx';
          break;
        case 1:
          this.uploadVisible = true;
          break;
        case 2:
          // 导出
          this.exportVisible = true
          break;
      }
    },
    // 导入
    importFile(file) {
      importStaff(file).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("导入成功");
          this.uploadVisible = false;
          this.getList()
        }
      })
    },
    // 导出
    handleExport(data) {
      exportStaff({
        ...data,
        queryName: this.queryParams.name,
        queryPhone: this.queryParams.phone,
      }).then((res) => {
        if (res.code === 200) {
          if (res.code === 200) {
            this.downloadFile({
              fileName: res.msg
            })
            this.exportVisible = false
          }
        }
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        avatar: null,
        blocId: null,
        postIds: [],
        kindergartenId: null,
        uid: null,
        name: null,
        identity: null,
        phone: null,
        staffNumber: null,
        identityNumber: null,
        hiredate: null,
        nation: null,
        marriage: null,
        address: null,
        accountAddress: null,
        education: null,
        school: null,
        major: null,
        staffExplain: null,
        sex: null,
        isQuit: null,
        quitDate: null,
        quitReason: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
      };
      this.resetForm("form");
    },
    // 修改或新增取消按钮
    cancel() {
      this.showEdit = false;
      this.reset();
    },
    // 查看员工信息
    checkInfo(row) {
      this.$emit("openInfo", {
        id: row.id,
        type: 0,
      });
    },
    // 获取岗位
    fetchStation() {
      getStation().then((res) => {
        this.posts = res.data;
      });
    },
    /**
     * 查询学校员工 列表
     * @param 0-在职员工,1-离职员工,2-集团员工
     */
    getList() {
      this.loading = true;
      this.queryParams.queryType = 0;
      listStaff(this.queryParams).then((response) => {
        this.staffList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /**
     * 修改按钮操作
     * @param type 0:查询校区员工(在职或离职) 1:集团员工
     * */
    handleUpdate(row, type) {
      type = type == null ? 0 : type;
      this.reset();
      const id = row.id || this.ids;
      getStaff(id, {
        type: type,
      }).then((response) => {
        this.form = response.data;
        this.form.id = id;
        this.showEdit = true;
        this.title = "修改学校员工 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateStaff(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.showEdit = false;
              this.getList();
            });
          } else {
            addStaff(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.showEdit = false;
              this.getList();
            });
          }
        }
      });
    },
    // 离职
    handleFire(row) {
      getStaff(row.id, {
        type: 0,
      }).then((response) => {
        this.fireForm = response.data;
        this.fireForm.id = row.id;
        this.showFireDialog = true;
      });
    },
    /** 离职确认按钮 */
    confirmFire() {
      this.$refs["fireForm"].validate((valid) => {
        if (valid) {
          fireStaff(this.fireForm).then((response) => {
            this.$modal.msgSuccess("离职成功");
            this.showFireDialog = false;
            this.getList();
          });
        }
      });
    },
    // 离职取消按钮
    cancelFire() {
      this.showFireDialog = false;
      this.fireForm = {
        id: null,
        quitDate: null,
        quitReason: null,
      };
      this.resetForm("fireForm");
    },
    // 图片上传成功回调
    handleAvatarSuccess(data) {
      this.form.avatar = data.url;
    },
    // 限制上传文件类型
    handleUploadImgBefore(file) {
      const isPic = [
        "image/jpeg",
        "image/png",
        "image/jpg",
        "image/gif",
      ].includes(file.type);
      const isLt10M = file.size / 1024 / 1024 < 2;
      if (!isPic) {
        this.$message.error("请上传规定的图片格式(png/jpg/jpeg/gif)");
        return false;
      }
      if (!isLt10M) {
        this.$message.error(`上传图片大小不能超过2MB!`);
        return false;
      }
      return isPic && isLt10M;
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
      border-radius: 10px;

      .avatar_desc {
        margin-top: 10px;
        text-align: center;
        color: #777794;
      }
    }

    .footer {
      margin: 0 auto;
    }
  }
}
</style>
