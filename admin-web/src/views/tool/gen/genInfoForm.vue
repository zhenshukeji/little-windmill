<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item prop="tplCategory">
          <span slot="label">生成模板</span>
          <el-select v-model="info.tplCategory" @change="tplSelectChange">
            <el-option label="单表（增删改查）" value="crud" />
            <el-option label="树表（增删改查）" value="tree" />
            <el-option label="主子表（增删改查）" value="sub" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="packageName">
          <span slot="label">
            生成包路径
            <el-tooltip content="生成在哪个java包下，例如 com.ruoyi.system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.packageName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="moduleName">
          <span slot="label">
            生成模块名
            <el-tooltip content="可理解为子系统名，例如 system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.moduleName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="businessName">
          <span slot="label">
            生成业务名
            <el-tooltip content="可理解为功能英文名，例如 user" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.businessName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="functionName">
          <span slot="label">
            生成功能名
            <el-tooltip content="用作类描述，例如 用户" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.functionName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            上级菜单
            <el-tooltip content="分配到指定菜单下，例如 系统管理" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <treeselect
            :append-to-body="true"
            v-model="info.parentMenuId"
            :options="menus"
            :normalizer="normalizer"
            :show-count="true"
            placeholder="请选择系统菜单"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="genType">
          <span slot="label">
            生成代码方式
            <el-tooltip content="默认为zip压缩包下载，也可以自定义生成路径" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-radio v-model="info.genType" label="0">zip压缩包</el-radio>
          <el-radio v-model="info.genType" label="1">自定义路径</el-radio>
        </el-form-item>
      </el-col>

      <el-col :span="24" v-if="info.genType == '1'">
        <el-form-item prop="genPath">
          <span slot="label">
            自定义路径
            <el-tooltip content="填写磁盘绝对路径，若不填写，则生成到当前Web项目下" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.genPath">
            <el-dropdown slot="append">
              <el-button type="primary">
                最近路径快速选择
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="info.genPath = '/'">恢复默认的生成基础路径</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row v-show="info.tplCategory == 'tree'">
      <h4 class="form-header">其他信息</h4>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            树编码字段
            <el-tooltip content="树显示的编码字段名， 如：dept_id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeCode" placeholder="请选择">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            树父编码字段
            <el-tooltip content="树显示的父编码字段名， 如：parent_Id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeParentCode" placeholder="请选择">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            树名称字段
            <el-tooltip content="树节点的显示名称字段名， 如：dept_name" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeName" placeholder="请选择">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <template v-if="info.tplCategory == 'sub'">
      <!-- 第一张表 -->
      <el-row>
        <h4 class="form-header">关联信息</h4>
        <el-col :span="12">
          <el-form-item>
            <span slot="label">
              关联子表的表名
              <el-tooltip content="关联子表的表名， 如：sys_user" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <el-select
              v-model="info.subTableName"
              placeholder="请选择"
              @change="subSelectChange($event, 1)"
            >
              <el-option
                v-for="(table, index) in tables"
                :key="index"
                :label="table.tableName + '：' + table.tableComment"
                :value="table.tableName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <span slot="label">
              子表关联的外键名
              <el-tooltip content="子表关联的外键名， 如：user_id" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <el-select v-model="info.subTableFkName" placeholder="请选择">
              <el-option
                v-for="(column, index) in subColumns"
                :key="index"
                :label="column.columnName + '：' + column.columnComment"
                :value="column.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table ref="tableData" :data="tableData" row-key="columnId" :max-height="tableHeight">
        <el-table-column label="序号" type="index" min-width="5%" class-name="allowDrag" />
        <el-table-column
          label="字段列名"
          prop="columnName"
          min-width="10%"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="字段描述" min-width="10%">
          <template slot-scope="scope">
            <el-input v-model="scope.row.columnComment"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="物理类型"
          prop="columnType"
          min-width="10%"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="Java类型" min-width="11%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.javaType">
              <el-option label="Long" value="Long" />
              <el-option label="String" value="String" />
              <el-option label="Integer" value="Integer" />
              <el-option label="Double" value="Double" />
              <el-option label="BigDecimal" value="BigDecimal" />
              <el-option label="Date" value="Date" />
              <el-option label="Boolean" value="Boolean" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="java属性" min-width="10%">
          <template slot-scope="scope">
            <el-input v-model="scope.row.javaField"></el-input>
          </template>
        </el-table-column>

        <el-table-column label="列表" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isList"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isQuery"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="详情" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isDetails"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询方式" min-width="10%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.queryType">
              <el-option label="=" value="EQ" />
              <el-option label="!=" value="NE" />
              <el-option label=">" value="GT" />
              <el-option label=">=" value="GTE" />
              <el-option label="<" value="LT" />
              <el-option label="<=" value="LTE" />
              <el-option label="LIKE" value="LIKE" />
              <el-option label="BETWEEN" value="BETWEEN" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="显示类型" min-width="12%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.htmlType">
              <el-option label="文本框" value="input" />
              <el-option label="文本域" value="textarea" />
              <el-option label="下拉框" value="select" />
              <el-option label="单选框" value="radio" />
              <el-option label="复选框" value="checkbox" />
              <el-option label="日期控件" value="datetime" />
              <el-option label="图片上传" value="imageUpload" />
              <el-option label="文件上传" value="fileUpload" />
              <el-option label="富文本控件" value="editor" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="字典类型" min-width="12%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择">
              <el-option
                v-for="dict in dictOptions"
                :key="dict.dictType"
                :label="dict.dictName"
                :value="dict.dictType"
              >
                <span style="float: left">{{ dict.dictName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
              </el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
      <!-- 第二张子表 -->
      <el-row style="margin-top:20px">
        <el-col :span="12">
          <el-form-item>
            <span slot="label">
              关联子表的表名
              <el-tooltip content="关联子表的表名， 如：sys_user" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <el-select
              v-model="info.subTableNameTwo"
              placeholder="请选择"
              @change="subSelectChange($event, 2)"
            >
              <el-option
                v-for="(table, index) in tables"
                :key="index"
                :label="table.tableName + '：' + table.tableComment"
                :value="table.tableName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <span slot="label">
              子表关联的外键名
              <el-tooltip content="子表关联的外键名， 如：user_id" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <el-select v-model="info.subTableFkNameTwo" placeholder="请选择">
              <el-option
                v-for="(column, index) in subColumns"
                :key="index"
                :label="column.columnName + '：' + column.columnComment"
                :value="column.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table ref="tableData" :data="tableData2" row-key="columnId" :max-height="tableHeight">
        <el-table-column label="序号" type="index" min-width="5%" class-name="allowDrag" />
        <el-table-column
          label="字段列名"
          prop="columnName"
          min-width="10%"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="字段描述" min-width="10%">
          <template slot-scope="scope">
            <el-input v-model="scope.row.columnComment"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="物理类型"
          prop="columnType"
          min-width="10%"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="Java类型" min-width="11%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.javaType">
              <el-option label="Long" value="Long" />
              <el-option label="String" value="String" />
              <el-option label="Integer" value="Integer" />
              <el-option label="Double" value="Double" />
              <el-option label="BigDecimal" value="BigDecimal" />
              <el-option label="Date" value="Date" />
              <el-option label="Boolean" value="Boolean" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="java属性" min-width="10%">
          <template slot-scope="scope">
            <el-input v-model="scope.row.javaField"></el-input>
          </template>
        </el-table-column>

        <el-table-column label="列表" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isList"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isQuery"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="详情" min-width="5%">
          <template slot-scope="scope">
            <el-checkbox true-label="1" v-model="scope.row.isDetails"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询方式" min-width="10%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.queryType">
              <el-option label="=" value="EQ" />
              <el-option label="!=" value="NE" />
              <el-option label=">" value="GT" />
              <el-option label=">=" value="GTE" />
              <el-option label="<" value="LT" />
              <el-option label="<=" value="LTE" />
              <el-option label="LIKE" value="LIKE" />
              <el-option label="BETWEEN" value="BETWEEN" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="显示类型" min-width="12%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.htmlType">
              <el-option label="文本框" value="input" />
              <el-option label="文本域" value="textarea" />
              <el-option label="下拉框" value="select" />
              <el-option label="单选框" value="radio" />
              <el-option label="复选框" value="checkbox" />
              <el-option label="日期控件" value="datetime" />
              <el-option label="图片上传" value="imageUpload" />
              <el-option label="文件上传" value="fileUpload" />
              <el-option label="富文本控件" value="editor" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="字典类型" min-width="12%">
          <template slot-scope="scope">
            <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择">
              <el-option
                v-for="dict in dictOptions"
                :key="dict.dictType"
                :label="dict.dictName"
                :value="dict.dictType"
              >
                <span style="float: left">{{ dict.dictName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
              </el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </el-form>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getTableInfo } from "@/api/tool/gen";
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";

export default {
  components: { Treeselect },
  props: {
    info: {
      type: Object,
      default: null
    },
    tables: {
      type: Array,
      default: null
    },
    subMap: {
      type: Object,
      default: null
    },
    menus: {
      type: Array,
      default: []
    },
  },
  data() {
    return {
      // 字典信息
      dictOptions: [],
      tableData: [],
      tableData2: [],
      // 表格的高度
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      subColumns: [],
      rules: {
        tplCategory: [
          { required: true, message: "请选择生成模板", trigger: "blur" }
        ],
        packageName: [
          { required: true, message: "请输入生成包路径", trigger: "blur" }
        ],
        moduleName: [
          { required: true, message: "请输入生成模块名", trigger: "blur" }
        ],
        businessName: [
          { required: true, message: "请输入生成业务名", trigger: "blur" }
        ],
        functionName: [
          { required: true, message: "请输入生成功能名", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    /** 查询字典下拉列表 */
    getDictOptionselect().then(response => {
      this.dictOptions = response.data;
    });

  },
  watch: {
    // 'info.subTableName': function (val) {
    //   this.setSubTableColumns(val);
    // },
    'info': function (val) {
      console.log('info变化', val);
      this.tableData = this.tableData2 = []
      if (val.tplCategory === 'sub') {
        this.tableData = this.subMap[val.subTableName]
        this.tableData2 = this.subMap[val.subTableNameTwo]
      }
      this.subColumns = val.columns
    },
  },
  methods: {
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      };
    },
    /** 选择子表名触发 */
    subSelectChange(value, index) {
      console.log(value, index);
      this.info.subTableFkName = '';
      // 根据名字找到表id
      let obj = this.tables.find(item => {
        return item.tableName === value
      })
      // 调用接口
      if (obj) {
        this.getTableInfo(obj.tableId, index)
      }
    },
    // 获取表相关信息
    getTableInfo(id, index) {
      getTableInfo(id).then(res => {
        console.log('res', res);
        if (res.code === 200) {
          if (index === 1) {
            this.tableData = res.data.rows
            // 添加表名字的属性
            this.tableData.forEach(item => {
              item.subTableName = this.info.subTableName
              item.tableId = this.info.tableId
            })
          } else {
            this.tableData2 = res.data.rows
            // 添加表名字的属性
            this.tableData2.forEach(item => {
              item.subTableName = this.info.subTableNameTwo
              item.tableId = this.info.tableId
            })
          }
        }
      })
    },
    /** 选择生成模板触发 */
    tplSelectChange(value) {
      if (value !== 'sub') {
        this.info.subTableName = '';
        this.info.subTableFkName = '';
      }
    },
    /** 设置关联外键 */
    setSubTableColumns(value) {
      for (var item in this.tables) {
        const name = this.tables[item].tableName;
        if (value === name) {
          this.subColumns = this.tables[item].columns;
          break;
        }
      }
    }
  }
};
</script>
