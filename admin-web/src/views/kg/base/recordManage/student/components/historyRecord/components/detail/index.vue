<template>
  <div v-show="show" class="opeStudent">
    <el-form ref="form" :model="form" :inline="true" label-width="120px" label-position="left">
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
            <div class="txt name">
              <span>{{ form.name }}</span>
              <span v-if="form.isWeak" class="weak">(弱)</span>
            </div>
          </el-form-item>
          <el-form-item label="性别:" prop="gender">
            <div v-if="form.gender === 0" class="txt">男</div>
            <div v-if="form.gender === 1" class="txt">女</div>
            <div v-if="form.gender === 2" class="txt">未知</div>
          </el-form-item>
          <el-form-item label="民族:" prop="nation">
            <div class="txt">{{ form.nation }}</div>
          </el-form-item>
          <el-form-item label="血型:" prop="bloodType">
            <div class="txt">{{ form.bloodType }}</div>
          </el-form-item>
          <el-form-item label="出生日期:" prop="birthdate">
            <div class="txt">{{ form.birthdate }}</div>
          </el-form-item>
          <el-form-item label="健康状况:" prop="healthStatus">
            <div class="txt">{{ form.healthStatus }}</div>
          </el-form-item>
          <el-form-item label="证件类型:" prop="cardType">
            <div class="txt">{{ form.cardType }}</div>
          </el-form-item>
          <el-form-item label="证件号码:" prop="cardNumber">
            <div class="txt">{{ form.cardNumber }}</div>
          </el-form-item>
          <el-form-item label="国籍:" prop="nationality">
            <div class="txt">{{ form.nationality }}</div>
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
          <el-form-item label="班级:" prop="className">
            <div class="txt">{{ form.className }}</div>
          </el-form-item>
          <el-form-item label="教师:">
            <div class="txt">{{ form.teacherName }}</div>
          </el-form-item>
          <el-form-item label="就读方式:" prop="studyingWay">
            <div class="txt">{{ form.studyingWay }}</div>
          </el-form-item>
          <el-form-item label="入园日期:" prop="enrollDate">
            <div class="txt">{{ form.enrollDate }}</div>
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
            <div class="txt">{{ form.placeOfBirth }}</div>
          </el-form-item>
          <el-form-item label="籍贯:" prop="nativePlace">
            <div class="txt">{{ form.nativePlace }}</div>
          </el-form-item>
          <el-form-item label="户口性质:" prop="accountQuality">
            <div class="txt">{{ form.accountQuality }}</div>
          </el-form-item>
          <el-form-item label="户口类型:" prop="accountType">
            <div class="txt">{{ form.accountType }}</div>
          </el-form-item>
          <el-form-item label="户口所在地:" prop="accountAddress">
            <div class="txt">{{ form.accountAddress }}</div>
          </el-form-item>
          <el-form-item label="现住址:" prop="address">
            <div class="txt">{{ form.address }}</div>
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
        <div class="content">
          <template v-if="form.guardians && form.guardians[0]">
            <el-form-item label="监护人:" prop="guardians[0].name">
              <div class="txt">{{ form.guardians[0].name || '--' }}</div>
            </el-form-item>
            <el-form-item label="关系:">
              <div class="txt">{{ form.guardians[0].relation || '--' }}</div>
            </el-form-item>
            <el-form-item label="联系电话:" prop="guardians[0].phone">
              <div class="txt">{{ form.guardians[0].phone || '--' }}</div>
            </el-form-item>
            <el-form-item label="职业:">
              <div class="txt">{{ form.guardians[0].job  || '--'}}</div>
            </el-form-item>
            <el-form-item label="证件类型:" prop="cardType">
              <div class="txt">{{ form.guardians[0].cardType || '--' }}</div>
            </el-form-item>
            <el-form-item label="证件号码:" prop="cardNumber">
              <div class="txt">{{ form.guardians[0].cardNumber|| '--'  }}</div>
            </el-form-item>
          </template>

          <!-- 监护人二 -->
          <template v-if="form.guardians && form.guardians[1]">
            <el-form-item label="监护人:" prop="guardians[0].name">
              <div class="txt">{{ form.guardians[1].name || '--' }}</div>
            </el-form-item>
            <el-form-item label="关系:">
              <div class="txt">{{ form.guardians[1].relation || '--' }}</div>
            </el-form-item>
            <el-form-item label="联系电话:" prop="guardians[1].phone">
              <div class="txt">{{ form.guardians[1].phone || '--' }}</div>
            </el-form-item>
            <el-form-item label="职业:">
              <div class="txt">{{ form.guardians[1].job || '--' }}</div>
            </el-form-item>
            <el-form-item label="证件类型:" prop="cardType">
              <div class="txt">{{ form.guardians[1].cardType || '--' }}</div>
            </el-form-item>
            <el-form-item label="证件号码:" prop="cardNumber">
              <div class="txt">{{ form.guardians[1].cardNumber || '--' }}</div>
            </el-form-item>
          </template>

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
            <div v-if="form.isWeak" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>
          <el-form-item label="是否独生子女:" prop="isOnlyChild">
            <div v-if="form.isOnlyChild" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>
          <el-form-item label="是否留守学生:" prop="isLeft">
            <div v-if="form.isLeft" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>
          <el-form-item label="是否孤儿:" prop="isOrphan">
            <div v-if="form.isOrphan" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>
          <el-form-item label="是否残疾幼儿:" prop="isDisability">
            <div v-if="form.isDisability" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>
          <el-form-item label="是否务工子女:" prop="isWorkers">
            <div v-if="form.isWorkers" class="txt">是</div>
            <div v-else class="txt">否</div>
          </el-form-item>

          <el-form-item label="特殊情况:" prop="specialCase">
            <div class="txt">{{ form.specialCase }}</div>
          </el-form-item>
        </div>
        <el-divider></el-divider>
      </div>

    </el-form>
    <!-- 默认显示按钮 -->
    <div v-if="type === 0" slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
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
    },
    type: {
      default: 0,
      type: Number
    }
  },
  watch: {
    show(val) {
      if (val) {
        this.getDetail(this.info.id)
      }
    }
  },
  components: {},
  data() {
    return {
      loading: false,
      total: 0,
      form: {},
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
          // 遍历为空的话就为--
          for (let key in this.form) {
            if (
              this.form[key] === '' ||
              this.form[key] === null ||
              this.form[key] === undefined
            ) {
              this.form[key] = '--'
            }
          }
        }
      })
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
        specialCase: null,
        className: null,
        teacherName: null
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
  margin-bottom: 10px;
}
.txt {
  width: 300px;
}
.name {
  color: #1990ff;
}
.weak {
  margin-left: 4px;
  color: #ff0000;
}
</style>
