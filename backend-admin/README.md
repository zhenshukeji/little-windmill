# 幼儿园管理系统

### 一、文档地址：

1、产品原型地址：https://modao.cc/app/tg048pH8r4wphbh2VcvKCv

2、测试接口文档地址：https://${API_BASE_URL:http://localhost:8184}/api/dev/Kindergarten/doc.html

### 二、代码规范

1、当前代码层级介绍，需要按照规范放置代码，模块的划分以原型的菜单为准
```
com.zhenshu     
├── api            // 所有的接口定义都在这里，最后一级模块不标注
│       └── bloc          // 集团端
│       └── kg            // 园区端
│               └── base          // 校区功能
│               └── finance       // 财务管理
│               └── health        // 健康管理
│               └── work          // 日常工作
│       └── platform      // 平台端
│       └── ruoyi         // ruoyi框架原生接口
├── common         // 公共的类
│       └── annotation    // 自定义注解
│       └── config        // 全局配置
│       └── constant      // 通用常量
│       └── core          // 核心控制
│       └── enums         // 通用枚举
│       └── exception     // 通用异常
│       └── json          // JSON数据处理
│       └── utils         // 通用类处理
│       └── xss           // XSS过滤处理
├── generator     // 代码生成器
├── quartz        // 定时任务，暂无用处
├── framework     // 全局配置
│       └── aspectj       // 注解实现
│       └── config        // 系统配置
│       └── datasource    // 数据权限
│       └── interceptor   // 拦截器
│       └── manager       // 异步处理
│       └── shiro         // 权限控制
│       └── web           // 前端控制
├── system        // service、mapper、domain
│       └── business          // 业务端
│               └── bloc          // 集团端
│               └── kg            // 园区端
│                       └── base          // 校区功能
│                       └── finance       // 财务管理
│                       └── health        // 健康管理
│                       └── work          // 日常工作
│               └── platform      // 平台端
│               └── ruoyi         // ruoyi框架原生接口
│       └── remote            // 远程调用，如果一个模块需要调用另外一个模块的Service，需要在这里写远程Service，方便后面转成微服务系统
│               └── bloc          // 集团端
│               └── kg            // 园区端
│                       └── base          // 校区功能
│                       └── finance       // 财务管理
│                       └── health        // 健康管理
│                       └── work          // 日常工作
│               └── platform      // 平台端
│               └── ruoyi         // ruoyi框架原生接口

```

2、需要写多表关联的sql时，哪个是主表，就放在哪个表对应的Service里
```$xslt
// service
public interface IPaymentDealService{
  List<HeadTeacherDetailBO> headTeacher(HeadTeacherReportVO reportVO);
}

// service impl
public class PaymentDealServiceImpl  implements IPaymentDealService {
 @Override
    public List<HeadTeacherDetailBO> headTeacher(HeadTeacherReportVO reportVO) {
        return this.baseMapper.headTeacher(reportVO);
    }
}

// mapper
public interface PaymentDealMapper {
    List<HeadTeacherDetailBO> headTeacher(@Param("reportVO") HeadTeacherReportVO reportVO);
}

// mapper.xml
<mapper namespace="com.zhenshu.system.business.kg.finance.payment.mapper.PaymentDealMapper">
        <select id="headTeacher" resultType="com.zhenshu.system.business.kg.finance.report.domain.bo.HeadTeacherDetailBO">
        SELECT
            ks.id `stuId`,
            ks.`name` `stuName`
        FROM
            // 这个是主表，所以放在 IPaymentDealService 下
            kg_payment_deal kpd
            JOIN kg_student ks ON kpd.student_id = ks.id
    </select>
</mapper>
```

3、接口地址根据模块路径来定，比如校区-校区功能-高级管理-考勤时间配置的接口地址是 
/kg/base/advanced/attendance
```$xslt
package com.zhenshu.api.kg.base.advanced;
 
@RequestMapping("/kg/base/advanced/attendance")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:attendance:all')")
public class AttendanceTimeController {
```

4、当一个模块接口过多的时候，可以拆成两个Controller,但配置同一个权限标识。比如缴费管理里 缴费项目和项目详情分成了两个Controller。
```$xslt
@RequestMapping("/kg/finance/payment/deal")
@PreAuthorize("@ss.hasPermi('kg:finance:payment:payment:all')")
public class PaymentDealController {

@RequestMapping("/kg/finance/payment/payment")
@PreAuthorize("@ss.hasPermi('kg:finance:payment:payment:all')")
public class PaymentProjectController {
```

5、不能直接使用数据库对象作为出参和入参，需要封装，入参为 vo , 出参为 bo。

```$xslt
例如：
数据库对象 PaymentDeal
新增入参 PaymentDealInsertVO
列表出参 PaymentDealBO
```

6、当需要数据来标识状态时，使用枚举来做。 目前代码里已实现数据库、出参入参、接口文档、导出数据枚举自动转换的功能，只需要按照 DealStatus 类的格式来写枚举，并在对应地方使用枚举替代int 类型。

```$java
- 比如原本
@Excel(name = "业务状态", readConverterExp = "0=未缴费,1=已缴费,2=部分退,3=已退费,4=作废")
@ApiModelProperty(value="业务状态 0未缴费 1已缴费 2部分退费 3已退费 4作废")
private Interge dealStatus;
- 修改后：
@Excel(name = "业务状态")
@ApiModelProperty(value="业务状态")
private DealStatus dealStatus;
```
这样不需要多个地方来维护键值对，只需要修改枚举里的就可以。

7、调用其他模块（比如校区-财务管理-财务报表，需要用到校区-财务管理-财务管理）的service，需要写remote 。同一模块下的service不需要写remote。
```$xslt
// 这个是在 system.business 目录下
package com.zhenshu.system.business.kg.finance.report.service.impl;
public class HeadTeacherServiceImpl implements IHeadTeacherService {

    // 不同模块通过remote service 来调用方法
    @Resource
    private RemotePaymentDealService remotePaymentDealService;

    @Override
    public List<PaymentStudentBO> detail(Long stuId) {
        return remotePaymentDealService.stuDetail(stuId);
    }
}

// remote service，这个是在 system.remote 目录下
package com.zhenshu.system.remote.kg.finance.payment;
public class RemotePaymentDealService {

    @Resource
    private IPaymentDealService paymentDealService;

    public List<PaymentStudentBO> stuDetail(Long stuId) {
        // 调用对应service 的方法
        return paymentDealService.stuDetail(stuId);
    }
}

```
为了方便以后拆分成微服务使用。

8、只能调用下一层级或者平级的东西，不允许越级调用，不允许越模块调用。 比如controller 层可以调用service，不能调用mapper。 service 可以调用自己的mapper ， 但不能调用其他service 的mapper。 remote service 里也不允许直接调用 mapper ，只能调用 service。

9、为了保证权限隔离，不同菜单的接口不能复用。比如班主任数据统计和财务汇总统计里都有展示全部缴费项目的功能。这时候需要分成两个接口，但可以调用同一个service。
如果共用接口的话，当用户只有其中一个权限的时候，可能出现无法访问的情况。
```$xslt
// 班主任数据统计
public class HeadTeacherController {
    @Resource
    private RemotePaymentProjectService remotePaymentProjectService;
    
    @GetMapping("/project")
    @ApiOperation(value = "缴费项目列表")
    public Result<List<PaymentProjectPartBO>> paymentProject() {
        List<PaymentProjectPartBO> boList = remotePaymentProjectService.getPartByKgId(SecurityUtils.getUserKgId());
        return new Result<List<PaymentProjectPartBO>>().success(boList);
    }
}
// 财务汇总统计
public class FinanceSummaryController {
    @Resource
    private RemotePaymentProjectService remotePaymentProjectService;
    
    @GetMapping("/project")
    @ApiOperation(value = "缴费项目列表")
    public Result<List<PaymentProjectPartBO>> paymentProject() {
        List<PaymentProjectPartBO> boList = remotePaymentProjectService.getPartByKgId(SecurityUtils.getUserKgId());
        return new Result<List<PaymentProjectPartBO>>().success(boList);
    }
}
```

10、写完sql语句后，需要使用 EXPLAIN 语句判断是否有使用索引。如果走全表扫描，需要建上对应索引。尽量使用联合索引来减少建索引的数量，一个表最好不要超过4个索引。

### 三、分支规范

1、dev -开发版本

2、master - 对外测试版本，客户需要体验测试时，使用此稳定版本

3、prod - 正式版本
