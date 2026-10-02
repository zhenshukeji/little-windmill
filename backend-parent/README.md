#幼儿园家长端小程序文档介绍
作者: Jing     时间 : 2022-05-05
## 简介
为了项目快速启动而搭建，里面包括了常用框架的集成，开箱即用。
应用包括Java + Mysql 8 + Redis + Sentry。 主要框架包括 SpringBoot + Mybatis + Mybatis plus + Lombok + Jwt + Druid + Sentry + Knife4j 。  

集成的功能包括：
- 项目启动的基本配置、跨域配置、数据源配置
- 异步任务工厂
- 阿里云cdn、阿里云短信、阿里云图文审核、阿里云身份二要素验证
- 分布式锁、日志拦截器
- mybatis plus 自动生成代码器，逻辑删除，乐观锁
- swagger 接口文档
- excel 模板导出功能
- sentry 异常日志收集
- redis 工具类使用
- 微信公众平台授权
- 平安金管家相关功能
- 发送邮件功能
- 枚举自动映射数据库对象、文档对象、出参入参对象
- druid 数据库监控

## 功能简介

### 一、项目启动的基本配置、跨域配置、数据源配置
基本配置和数据源 详见 application.yml 和 application-dev.yml 文件里的配置 。   其中 application.yml 里的是项目的基本配置，不随着环境更换而变更。 application-dev.yml 里是根据环境不同需要变更的配置。

跨域配置在 com.zhenshu.parent.common.config.CorsConfig 里。

### 二、异步任务工厂
异步工厂相关代码在 com.zhenshu.parent.common.library.async 下。
使用方式是在 AsyncFactory 里新增需要异步执行的方法，然后使用AsyncManager的execute 方法调用。

示例如下：

1、AsyncFactory里新增recordOperLog 方法
```java
/**
 * 异步工厂（产生任务用）
 */
@Slf4j
public class AsyncFactory {

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperLog(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // ... 
                // 可以使用SpringUtils.getBean()来操作
                // 比如SpringUtils.getBean(IOperLogService.class).save(operLog);
            }
        };
    }
}
```
2、在业务代码里引入异步任务
```java
public class LogAspect{
    public void handleLog(){
        SysOperLog operLog = new SysOperLog();
        operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
        // 异步保存数据库
        AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
    }
}
```

注意：异步任务因为是保存在线程池里，重启应用有丢失异步任务的风险。所以只能把可靠性不需要很高的任务丢异步。比如发通知、记录日志

### 三、阿里云cdn、阿里云短信、阿里云图文审核、阿里云身份二要素验证

- 阿里云cdn: 已在CommonController 里接入，可参考
- 阿里云短信：已封装 com.zhenshu.parent.common.library.aliyun.sms.CaptchaCodeManager 类，
```java
@Service
public class LoginFacade {

    @Resource
    private CaptchaCodeManager captchaCodeManager;
    
    /**
     * 账号登录
     *
     * @param mobile           手机号
     * @param verificationCode 短信验证码
     */
    public Result<Object> login(String mobile, String verificationCode) {
        if (!captchaCodeManager.checkCode(mobile, verificationCode)) {
            throw new ServiceException("验证码错误", 400);
        }
        // ...
        return new Result<>().success();
    }

     /**
     * 请求注册验证码
     */
    @PostMapping("/regCaptcha")
    public Result<Object> registerCaptcha(String mobile) {
        if (!RegexUtil.isMobileExact(mobile)) {
            throw new ServiceException(ErrorEnums.BAD_PARAM);
        }
        String code = RandomUtil.randomNumbers(6);
        boolean isSend = captchaCodeManager.checkPhone(mobile);
        if (isSend) {
            throw new ServiceException(ErrorEnums.BIZ_ERROR_CODE);
        }
        boolean result = captchaCodeManager.sendCode(mobile);
        if(!result){
            throw new ServiceException(ErrorEnums.INNER_ERROR);
        }
        captchaCodeManager.saveCheckCode(mobile, code);
        return new Result<>().success();
    }
}
```

### 四、分布式锁、日志拦截器
- 分布式锁的代码在 com.zhenshu.parent.common.library.aspect.lock.LockAspect 里。  
应用场景：避免用户同时操作多个步骤、避免用户同时进行一个步骤、防重点操作  
使用方式：@LockFunction 注解，示例
```java
public class UserController {
    @Resource
    private UserFacade userFacade;

    @LockFunction(methodName = "certification", keyName = "openid")
    public Result<Object> certification(@RequestBody @Validated CertificationVO certificationVO) {
        // ...
        return new Result<>().success();
    }
}

```
同时引入了redisson ，也可以自己写分布式锁来控制逻辑，如下：
```java
@Service
public class OrderCacheManages extends CachesManages {

    public RLock tryLockCancelOrder(long orderId) {
        String cacheKey = cacheKeyManager.getLockCancelOrderKey(orderId);
        RLock lock = redissonClient.getLock(cacheKey);
        if (!lock.tryLock()) {
            return null;
        } else {
            return lock;
        }
    }
}

@Service
@Slf4j
public class CancelOrderJob {
    @Resource
    private OrderCacheManages orderCacheManages;

    private void doCancelOrder(Long orderId) {
        RLock rLock = null;
        try {
            rLock = this.orderCacheManages.tryLockCancelOrder(orderId);
            if (rLock != null) {
                //...
            }
        } catch (Exception e) {
            //...
        } finally {
            if (rLock != null) {
                this.orderCacheManages.unLockCancelOrder(rLock);
            }
        }
    }
}

```

- 日志拦截器，会打印接口的名称和消耗时间，代码在com.zhenshu.app.common.library.aspect.log.LogAspect
使用方式@LogFunction 注解，示例：

```java
public class UserController {
    @Resource
    private UserFacade userFacade;

    @LogFunction
    public Result<Object> certification(@RequestBody @Validated CertificationVO certificationVO) {
        userFacade.certification(certificationVO);
        return new Result<>().success();
    }
}
```

### 五、mybatis plus 自动生成代码器，逻辑删除，乐观锁
- 自动生成代码在 com.zhenshu.parent.common.library.mybatis.MybatisPlusGenerator 里   
修改对应的配置后直接运行即可
- 逻辑删除的配置已在application.yml 里配置好，只需要在对应的字段名上放在@TableLogic 注解即可。当调用mybatis plus 里的类似 list、getOne方法时，会自动过滤掉已被删除的行。
```java
@TableName("mall_follow")
public class Follow implements Serializable {
    
    @TableLogic
    private Boolean delFlag;
}
```
-  乐观锁的配置已在 com.zhenshu.parent.common.library.mybatis.MybatisPlusConfiguration 里引入

使用方式是乐观锁的字段上面追加 @Version 注解，然后在使用 updateById 时判断返回值
```java
public class Goods implements Serializable {
    /**
     * 乐观锁
     */
    @Version
    private Integer version;

}
```
新增的时候，需要设置version 的初始值为 1,或者数据库设置默认值为1。
```java
public class GoodService {
    
    public void add(Good good){
        good.setVersion(1);
        this.save(good);
    }
}
```
修改时使用乐观锁来避免并发导致的同一行数据被两个事务修改的问题。  
使用方式一，先获取到这行数据（主要是获取当前的version), 然后全量更新：
```java
public class GoodService {
    
    public void update(Long goodId){
        Good good = this.getById(goodId);
        // ...
        boolean updateResult = this.updateById(good);
        if(!updateResult){
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
    }
}
```
使用方式二，如果不想全量更新，只想更新其中几个字段,那么就单独新建一个对象 updateGood，只设置 version 字段和需要修改的值：
```java
public class GoodService {
    
    public void update(Long goodId){
        Good good = this.getById(goodId);

        Good updateGood = new Good();
        updateGood.setVersion(good.getVersion);
        // ...
        boolean updateResult = this.updateById(updateGood);
        if(!updateResult){
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
    }
}
```

### 六、swagger 接口文档
swagger 的配置在application.yml 、 application-dev.yml 和 com.zhenshu.parent.common.library.knife4j 目录下，目前已经配置了两个目录下的自动扫描。只需要在入参、出参、controller 层进行接入，就会自动转化成接口文档。   
1、controller 层 类上面加 @Api 注解，接口上面增加 @ApiOperation 注解
```java
@Api(tags = "商品相关接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class GoodsController {

    @PostMapping("/details")
    @ApiOperation(value = "商品详情页面")
    public Result<Object> details() {
        // ...
        return new Result<Object>().success();
    }
}
```

2、入参和出参对象上加 @ApiModel 注解，字段上面加@ApiModelProperty("商品id") 注解。   
入参：
```java
@ApiModel
public class GoodsDetailsVO {

    @ApiModelProperty(required = true, name = "商品id")
    private Long goodsId;
}
```
出参：
```java
@ApiModel
public class GoodsDetailsBO {

    @ApiModelProperty("商品名称")
    private String name;
}
```

### 七、excel 模板导出功能
目前已经有excel导出工具类，需要在导出对应的类里引入@Excel 注解，并且使用 ExcelUtil 工具类辅助。   
导出的对象的配置示例：
```java
@Data
public class BkWithdrawApplyBO {

    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
```
导出方式一，当该应用只有一台服务器时，导出的接口返回下载文件名，借助已在CommonController里的下载接口下载对应文件:
```java
@Slf4j
public class BkWithdrawApplyController {
    
    @GetMapping("/export")
    public Object export(HttpServletResponse response, HttpServletRequest request) {
        List<ActivityInfoListBO> boList = new ArrayList<>();
        ExcelUtil<ActivityInfoListBO> excelUtil = new ExcelUtil<>(ActivityInfoListBO.class);
        return excelUtil.exportExcel(boList, "订单详情", "提现信息");
    }
}
```

导出方式二，适用于多台应用时，为了避免生成文件和下载不是同一台服务器导致下载失败，所以直接返回文件流：
```java
@Slf4j
public class BkWithdrawApplyController {
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, HttpServletRequest request) {
        List<ActivityInfoListBO> boList = new ArrayList<>();
        try {
            ExcelUtil<ActivityInfoListBO> excelUtil = new ExcelUtil<>(ActivityInfoListBO.class);
            AjaxResult result = excelUtil.exportExcel(boList, "订单详情", "提现信息");
            String fileName = result.get(AjaxResult.MSG_TAG).toString();
            String filePath = AppConfig.getDownloadPath() + fileName;
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            FileUtils.deleteFile(filePath);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
```
### 八、sentry 异常日志收集
sentry 是一个日志整合应用，可以收集应用的报错信息并整理成可视化界面。目前代码里已接入sentry，并且在 GlobalExceptionHandler 全局异常处理器中将异常发送到sentry。   
除了自动发送异常以外，还可以手动发送异常或者信息到sentry中查看。
```java
@Service
public class BkWithdrawApplyServiceImpl{
    
    @Resource
    private SentryUtils sentryUtils;
    
    private void withdraw() {
        //...
        sentryUtils.sendMessage("...");
        sentryUtils.sendException(new Exception());
    }
}
```
### 九、redis 工具类使用
代码里已经整合了 redis ，可以直接使用RedisUtils 来做缓存。   
示例：
- 一、作为缓存    
CacheKey 里统一管理缓存key
```java
public class CacheKey {

    @Value("${spring.redis.cache_name}")
    public String cacheName;
    
    public String getPlatformListKey() {
        return String.format("%s:platform", cacheName);
    }

    public String getPlatformByIdKey(Long id) {
        return String.format("%s:platform-id:%d", cacheName, id);
    }
}
```
可以直接继承CachesManages类，下面是列表的缓存和单行数据的缓存示例:
```java
@Component
public class PlatformCachesManages extends CachesManages {
    @Resource
    private PlatformService platformService;
    
    // 列表缓存
    public List<PlatformBO> getPlatformList() {
        // 1.查询缓存
        String cacheKey = cacheKeyManager.getPlatformListKey();
        String data = cache.get(cacheKey);
        if (StringUtils.isEmpty(data)) {
            // 2.数据库查询数据
            List<PlatformBO> list = platformService.getPlatformList();
            // 3.保存到缓存
            cache.set(cacheKey, JacksonUtil.toJson(list), super.getDayTime());
            return list;
        }
        return GsonUtil.GsonToList(data, PlatformBO.class);
    }

    public void deletePlatformList() {
        String cacheKey = cacheKeyManager.getPlatformListKey();
        cache.delete(cacheKey);
    }
    
    // 单行数据缓存
    public Platform getPlatformById(Long id) {
        // 1.查询缓存
        String cacheKey = cacheKeyManager.getPlatformByIdKey(id);
        String data = cache.get(cacheKey);
        if (StringUtils.isEmpty(data)) {
            // 2.数据库查询数据
            Platform platform = platformService.getById(id);
            // 3.保存到缓存
            cache.set(cacheKey, GsonUtil.GsonString(platform), super.getDayTime());
            return platform;
        }
        return GsonUtil.GsonToBean(data, Platform.class);
    }

    public void deletePlatformById(Long id) {
        String cacheKey = cacheKeyManager.getPlatformByIdKey(id);
        cache.delete(cacheKey);
    }
}
```

二、使用批处理redis 命令，pipeline   
需要新建一个批处理连接，并且使用该连接来操作redis。
```java
@Service
public class GoodsCacheManages extends CachesManages {

    public void deleteMyGoods(StringRedisConnection connection, Long uid) {
        String cacheKey = cacheKeyManager.getMyGoods(uid);
        connection.del(cacheKey);
    }

    public void deleteGoods(StringRedisConnection connection, Long goodsId) {
        String cacheKey = cacheKeyManager.getGoods(goodsId);
        connection.del(cacheKey);
    }
}
```
如果不需要返回结果
```java
@Service
public class OrderFacade {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public void create() {
        // ...
        redisTemplate.executePipelined((RedisCallback<?>) redisConnection -> {
            StringRedisConnection connection = (StringRedisConnection) redisConnection;
            connection.openPipeline();
            cacheManages.deleteGoods(connection, goods.getId());
            cacheManages.deleteMyGoods(connection, goods.getUid());
            // ...
            return null;
        });
    }
}
```
如果需要拿到批处理命令的返回值，可以从executePipelined的执行返回里拿，里面会有每个有返回值命令的返回值。可以通过下标拿取。
```java
@Service
public class OrderFacade {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public void create() {
            List<Object> objects = redisTemplate.executePipelined((RedisCallback<?>) redisConnection -> {
            StringRedisConnection connection = (StringRedisConnection) redisConnection;
            connection.openPipeline();
            connection.hIncrBy(cacheKey, "sunCount", goddessConfig.getSignInSun());
            connection.hIncrBy(cacheKey, "dayCount", goddessConfig.getSignInSun());
            return null;
        });
        // 拿取返回值
        Long sunCount = (Long) objects.get(0);
        Long dayCount = (Long) objects.get(1);
    }
}
```
### 十、微信公众平台授权
微信授权获取的code换取微信唯一身份标识openid
```java
@Service
public class UserFacade {
    @Resource
    private WxMpService wxMpService;

    public void submit(String code) {
        UserData userData = wxMpService.getUserInfo(code);
        if (userData == null) {
            throw new CustomException(ErrorEnums.OPENID_INVALID);
        }
        // ...
    }
}
```
### 十一、平安金管家相关功能
平安金管家是中国平安的综合性app，我们偶尔接一些金管家的项目，就会涉及到里面一些功能
### 十二、发送邮件功能
### 十三、枚举自动映射数据库对象、文档对象、出参入参对象
