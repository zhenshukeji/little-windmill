<template>
  <div v-show="show" class="opeStudent">
    <el-button type="primary" @click="cancel" class="back">返回</el-button>
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" label-position="left">
      <!-- 基本信息 -->
      <div class="box">
        <div class="title">
          <div class="name">
            基本信息
          </div>
          <el-divider></el-divider>
        </div>
        <div class="content">
          <el-form-item label="学生姓名:" prop="name">
            <el-input v-model="form.name" placeholder="请输入学生姓名" style="width:400px" />
          </el-form-item>
          <el-form-item label="性别:" prop="gender">
            <el-select v-model="form.gender" placeholder="请选择性别" style="width:400px">
              <el-option label="男" :value="0"></el-option>
              <el-option label="女" :value="1"></el-option>
              <el-option label="未知" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="民族:" prop="nation">
            <el-select v-model="form.nation" clearable placeholder="请选择民族" style="width:400px">
              <el-option v-for="(item, index) in nationData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="血型:" prop="bloodType">
            <el-select v-model="form.bloodType" clearable placeholder="请选择民族" style="width:400px">
              <el-option v-for="(item, index) in bloodData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出生日期:" prop="birthdate">
            <el-date-picker clearable size="small" v-model="form.birthdate" type="date" value-format="yyyy-MM-dd" placeholder="选择出生日期" :picker-options="pickerOptions" style="width:400px">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="健康状况:" prop="healthStatus">
            <el-select v-model="form.healthStatus" clearable placeholder="请选择健康状况" style="width:400px">
              <el-option label="健康" value="健康"></el-option>
              <el-option label="一般" value="一般"></el-option>
              <el-option label="有慢性病" value="有慢性病"></el-option>
              <el-option label="残疾" value="残疾"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证件类型:" prop="cardType">
            <el-select v-model="form.cardType" clearable placeholder="请选择证件类型" style="width:400px">
              <el-option v-for="(item, index) in cardTypeData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证件号码:" prop="cardNumber">
            <el-input v-model="form.cardNumber" placeholder="请输入证件号码" style="width:400px" />
          </el-form-item>
          <el-form-item label="国籍:" prop="nationality">
            <el-input v-model="form.nationality" placeholder="请输入国籍" style="width:400px" />
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

      <!-- 学籍信息 -->
      <div class="box">
        <div class="title">
          <div class="name">
            学籍信息
          </div>
          <el-divider></el-divider>
        </div>
        <div class="content">
          <el-form-item label="班级:" prop="classId">
            <el-select v-model="form.classId" placeholder="请选择班级" style="width:400px" @change="changeClass">
              <el-option v-for="item in info.classList" :key="item.id" :label="item.className" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="教师:">
            <el-input v-model="teacherName" disabled style="width:400px" placeholder="选择班级后自动填入"></el-input>
          </el-form-item>
          <el-form-item label="就读方式:" prop="studyingWay">
            <el-select v-model="form.studyingWay" clearable placeholder="请选择就读方式" style="width:400px">
              <el-option label="走读" value="走读"></el-option>
              <el-option label="住校" value="住校"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入园日期:" prop="enrollDate">
            <el-date-picker size="small" v-model="form.enrollDate" clearable type="date" value-format="yyyy-MM-dd" placeholder="选择入园日期" style="width:400px">
            </el-date-picker>
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

      <!-- 户籍信息 -->
      <div class="box">
        <div class="title">
          <div class="name">
            户籍信息
          </div>
          <el-divider></el-divider>
        </div>
        <div class="content">
          <el-form-item label="出生所在地:" prop="placeOfBirth">
            <el-input v-model="form.placeOfBirth" placeholder="请输入出生所在地" style="width:400px" />
          </el-form-item>
          <el-form-item label="籍贯:" prop="nativePlace">
            <el-input v-model="form.nativePlace" placeholder="请输入籍贯" style="width:400px" />
          </el-form-item>
          <el-form-item label="户口性质:" prop="accountQuality">
            <el-select v-model="form.accountQuality" clearable placeholder="请选择户口性质" style="width:400px">
              <el-option label="农业户口" value="农业户口"></el-option>
              <el-option label="非农业户口" value="非农业户口"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="户口类型:" prop="accountType">
            <el-select v-model="form.accountType" clearable placeholder="请选择户口类型" style="width:400px">
              <el-option label="城市" value="城市"></el-option>
              <el-option label="县城" value="县城"></el-option>
              <el-option label="乡镇" value="乡镇"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="户口所在地:" prop="accountAddress">
            <el-input v-model="form.accountAddress" placeholder="请输入户口所在地" style="width:400px" />
          </el-form-item>
          <el-form-item label="现住址:" prop="address">
            <el-input v-model="form.address" placeholder="请输入现住址" style="width:400px" />
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

      <!-- 家庭信息 -->
      <div class="box">
        <div class="title">
          <div class="name">
            家庭信息
          </div>
          <el-divider></el-divider>
        </div>
        <div v-if="form.guardians && form.guardians.length > 0" class="content">
          <el-form-item label="监护人:" prop="guardians[0].name">
            <el-input v-model="form.guardians[0].name" placeholder="请输入监护人" style="width:400px" />
          </el-form-item>
          <el-form-item label="关系:">
            <el-select v-model="form.guardians[0].relation" placeholder="请选择关系" style="width:400px">
              <el-option v-for="(item, index) in relationData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话:" prop="guardians[0].phone">
            <el-input v-model="form.guardians[0].phone" placeholder="请输入联系电话" maxlength="11" style="width:400px" />
          </el-form-item>
          <el-form-item label="职业:">
            <el-input v-model="form.guardians[0].job" placeholder="请输入职业" style="width:400px" />
          </el-form-item>
          <el-form-item label="证件类型:" prop="cardType">
            <el-select v-model="form.guardians[0].cardType" placeholder="请选择证件类型" clearable style="width:400px">
              <el-option v-for="(item, index) in cardTypeData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证件号码:" prop="guardians[0].cardNumber">
            <el-input v-model="form.guardians[0].cardNumber" placeholder="请输入证件号码" style="width:400px" />
          </el-form-item>

          <!-- 监护人二 -->
          <el-form-item label="监护人:">
            <el-input v-model="form.guardians[1].name" placeholder="请输入监护人" style="width:400px" />
          </el-form-item>
          <el-form-item label="关系:">
            <el-select v-model="form.guardians[1].relation" placeholder="请选择关系" style="width:400px">
              <el-option v-for="(item, index) in relationData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话:" prop="guardians[1].phone">
            <el-input maxlength="11" v-model="form.guardians[1].phone" placeholder="请输入联系电话" style="width:400px" />
          </el-form-item>
          <el-form-item label="职业:">
            <el-input v-model="form.guardians[1].job" placeholder="请输入职业" style="width:400px" />
          </el-form-item>
          <el-form-item label="证件类型:" prop="cardType">
            <el-select v-model="form.guardians[1].cardType" placeholder="请选择证件类型" clearable style="width:400px">
              <el-option v-for="(item, index) in cardTypeData" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="证件号码:" prop="guardians[1].cardNumber">
            <el-input v-model="form.guardians[1].cardNumber" placeholder="请输入证件号码" style="width:400px" />
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

      <!-- 其他信息 -->
      <div class="box">
        <div class="title">
          <div class="name">
            其他信息
          </div>
          <el-divider></el-divider>
        </div>
        <div class="content">
          <el-form-item label="是否高危体弱:" prop="isWeak">
            <el-select v-model="form.isWeak" clearable placeholder="请选择是否高危体弱" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否独生子女:" prop="isOnlyChild">
            <el-select v-model="form.isOnlyChild" clearable placeholder="请选择是否独生子女" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否留守学生:" prop="isLeft">
            <el-select v-model="form.isLeft" clearable placeholder="请选择是否留守学生" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否孤儿:" prop="isOrphan">
            <el-select v-model="form.isOrphan" clearable placeholder="请选择是否孤儿" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否残疾幼儿:" prop="isDisability">
            <el-select v-model="form.isDisability" clearable placeholder="请选择是否残疾幼儿" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否务工子女:" prop="isWorkers">
            <el-select v-model="form.isWorkers" clearable placeholder="请选择是否务工子女" style="width:400px">
              <el-option label="是" :value="true"></el-option>
              <el-option label="否" :value="false"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="特殊情况:" prop="specialCase">
            <el-input v-model="form.specialCase" placeholder="请输入特殊情况" style="width:400px" />
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="submitForm" style="marginLeft:40px">确 定</el-button>
    </div>
  </div>
</template>

<script>
import { validTel, validCard } from '@/utils/validate.js'
import { addStudent, getStudent, updateStudent } from '@/api/kg/base/student'
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
        // 新增
        if (this.info.isAdd) {
          this.reset()
        } else {
          this.getDetail(this.info.id)
        }
      }
    }
  },
  components: {},
  data() {
    // 校验
    var checkCard = (rule, value, callback) => {
      if (validCard(value)) {
        if (
          (rule.field === 'cardNumber' &&
            this.form.cardType === '居民身份证') ||
          (rule.field === 'guardians[0].cardNumber' &&
            this.form.guardians[0].cardType === '居民身份证') ||
          (rule.field === 'guardians[1].cardNumber' &&
            this.form.guardians[1].cardType === '居民身份证')
        ) {
          callback(new Error('请输入有效的身份证'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    // 校验手机号码
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        if (rule.required) {
          callback(new Error('请输入手机号码'))
        } else {
          callback()
        }
      } else if (validTel(value)) {
        callback(new Error('请输入有效的手机号码'))
      } else {
        callback()
      }
    }
    return {
      // 出生日期限制
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        }
      },
      loading: false,
      total: 0,
      form: {},
      rules: {
        name: [
          { required: true, message: '学生姓名不能为空', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '性别不能为空', trigger: 'change' }
        ],
        birthdate: [
          { required: true, message: '出生日期不能为空', trigger: 'change' }
        ],
        classId: [
          { required: true, message: '班级不能为空', trigger: 'change' }
        ],
        'guardians[0].name': {
          required: true,
          message: '监护人姓名不能为空',
          trigger: 'blur'
        },
        'guardians[0].phone': {
          required: true,
          validator: checkPhone,
          trigger: 'blur'
        },
        'guardians[1].phone': {
          validator: checkPhone,
          trigger: 'blur'
        },
        cardNumber: {
          validator: checkCard,
          trigger: 'blur'
        },
        'guardians[0].cardNumber': {
          validator: checkCard,
          trigger: 'blur'
        },
        'guardians[1].cardNumber': {
          validator: checkCard,
          trigger: 'blur'
        }
      },
      // 民族
      nationData: [
        '汉族',
        '壮族',
        '回族',
        '满族',
        '维吾尔族',
        '苗族',
        '彝族',
        '土家族',
        '藏族',
        '蒙古族',
        '侗族',
        '布依族',
        '瑶族',
        '白族',
        '朝鲜族',
        '哈尼族',
        '黎族',
        '哈萨克族',
        '傣族',
        '畲族',
        '傈僳族',
        '东乡族',
        '仡佬族',
        '拉祜族',
        '佤族',
        '水族',
        '纳西族',
        '羌族',
        '土族',
        '仫佬族',
        '锡伯族',
        '柯尔克孜族',
        '景颇族',
        '达斡尔族',
        '撒拉族',
        '布朗族',
        '毛南族',
        '塔吉克族',
        '普米族',
        '阿昌族',
        '怒族',
        '鄂温克族',
        '京族',
        '基诺族',
        '德昂族',
        '保安族',
        '俄罗斯族',
        '裕固族',
        '乌孜别克族',
        '门巴族',
        '鄂伦春族',
        '独龙族',
        '赫哲族',
        '高山族',
        '珞巴族',
        '塔塔尔族',
        '其他'
      ],
      // 血型
      bloodData: [
        'A型',
        'B型',
        'AB型',
        'O型',
        '未知血型',
        'RH阳性血型',
        'RH阴性血型',
        'HLA血型',
        '未定血型',
        '其他血型'
      ],
      // 证件类型
      cardTypeData: [
        '居民身份证',
        '香港特区护照/身份证明',
        '澳门特区护照/身份证明',
        '台湾居民往来大陆通行证',
        '境外永久居住证',
        '护照',
        '其他'
      ],
      // 关系
      relationData: ['父亲', '母亲', '祖父', '祖母', '外祖母', '外祖父'],
      // 教师名字
      teacherName: ''
    }
  },
  mounted() {},
  methods: {
    // 获取详情
    getDetail(id) {
      getStudent(id).then((res) => {
        if (res.code === 200) {
          this.form = res.data
          this.changeClass(this.form.classId)
        }
      })
    },
    // 选择班级
    changeClass(id) {
      let obj = this.info.classList.find((item) => {
        return item.id === id
      })
      if (obj) {
        this.teacherName = obj.teacherName
      }
    },
    // 表单重置
    reset() {
      this.form = {
        name: null,
        gender: null,
        nation: null,
        bloodType: null,
        birthdate: null,
        healthStatus: null,
        cardType: null,
        cardNumber: null,
        nationality: null,
        classId: null,
        studyingWay: null,
        enrollDate: null,
        placeOfBirth: null,
        nativePlace: null,
        accountQuality: null,
        accountType: null,
        accountAddress: null,
        address: null,
        guardians: [{}, {}],
        isWeak: null,
        isOnlyChild: null,
        isLeft: null,
        isOrphan: null,
        isDisability: null,
        isWorkers: null,
        specialCase: null
      }
      this.resetForm('form')
    },
    // 返回
    cancel() {
      this.$emit('close')
    },
    // 提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // 监护人信息
          if (this.form.id) {
            updateStudent(this.form).then((res) => {
              if (res.code === 200) {
                this.msgSuccess('修改成功')
                this.cancel()
              }
            })
          } else {
            addStudent(this.form).then((res) => {
              if (res.code === 200) {
                this.msgSuccess('新增成功')
                this.cancel()
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.opeStudent {
  .box {
    .title {
      .name {
        margin-bottom: 10px;
        color: #aaa9a9;
        font-size: 18px;
        padding-left: 8px;
        border-left: 4px solid #0088ff;
      }
    }
  }
}

::v-deep .el-form-item {
  margin-right: 60px;
}

.back {
  margin-bottom: 10px;
}
</style>
