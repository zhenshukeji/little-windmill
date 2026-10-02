package com.zhenshu.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.common.enums.system.BusinessStatus;
import com.zhenshu.common.enums.system.HttpMethod;
import com.zhenshu.common.enums.system.OperatorLogAssociationType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.ServletUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.ip.IpUtils;
import com.zhenshu.framework.manager.AsyncManager;
import com.zhenshu.framework.manager.factory.AsyncFactory;
import com.zhenshu.system.business.ruoyi.domain.SysOperLog;
import com.zhenshu.system.cache.MenuCacheManages;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private MenuCacheManages menuCacheManages;

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {

            // 获取当前的用户
            LoginUser loginUser = SecurityUtils.getLoginUser();

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                operLog.setOperName(loginUser.getUsername());
                // 设置关联关系
                OperatorLogAssociationType type = OperatorLogAssociationType.convert(loginUser.getUser().getIdentity());
                operLog.setAssociationType(type);
                if (type == OperatorLogAssociationType.PLATFORM) {
                    operLog.setAssociationId((long) Constants.ZERO);
                } else if (type == OperatorLogAssociationType.BLOC) {
                    operLog.setAssociationId(loginUser.getUser().getBlocId());
                } else if (type == OperatorLogAssociationType.KG) {
                    operLog.setAssociationId(loginUser.getUser().getKgId());
                }
                operLog.setUid(loginUser.getUserId());
            }

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setFunction(log.title());
        // 设置菜单
        SysMenu menu = this.getMenu(joinPoint, operLog);
        operLog.setMenu(menu.getMenuId());
        operLog.setModule(menu.getParentId());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)) {
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    private SysMenu getMenu(JoinPoint joinPoint, SysOperLog operLog) throws NoSuchMethodException {
        // 1.获取目标方法和所在类
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        boolean methodHasPre = method.isAnnotationPresent(PreAuthorize.class);
        // 2.获取@PreAuthorize注解
        PreAuthorize annotation;
        if (methodHasPre) {
            annotation = method.getAnnotation(PreAuthorize.class);
        } else {
            boolean classHasPre = clazz.isAnnotationPresent(PreAuthorize.class);
            if (!classHasPre) {
                throw new ServiceException(ErrorEnums.CLASS_AND_METHOD_NOT_PRE);
            }
            annotation = (PreAuthorize) clazz.getAnnotation(PreAuthorize.class);
        }
        // 3.获取目标方法所需权限, 获取被单引号包裹的字符串 @ss.hasPermi('bloc:staff:all')
        String value = annotation.value();
        value = value.substring(value.indexOf("'") + Constants.ONE, value.lastIndexOf("'"));
        String permission = value.split(",")[Constants.ZERO];
        // 4.获取当前操作的菜单类型
        MenuCategory category = null;
        OperatorLogAssociationType type = operLog.getAssociationType();
        if (type == null) {
            // TODO controller所在包修改记得改这这里
            String packageName = clazz.getPackage().getName();
            packageName = packageName.substring(packageName.indexOf("com.zhenshu.api") + Constants.ONE);
            packageName = packageName.substring(Constants.ZERO, packageName.indexOf("."));
            if ("bloc".equals(packageName)) {
                category = MenuCategory.BLOC;
            } else if ("kg".equals(packageName)) {
                category = MenuCategory.KG;
            } else if ("platform".equals(packageName)) {
                category = MenuCategory.PLATFORM;
            }
        } else {
            if (type == OperatorLogAssociationType.BLOC) {
                category = MenuCategory.BLOC;
            } else if (type == OperatorLogAssociationType.KG) {
                category = MenuCategory.KG;
            } else if (type == OperatorLogAssociationType.PLATFORM) {
                category = MenuCategory.PLATFORM;
            }
        }
        List<SysMenu> menus = menuCacheManages.getMenus(category);
        // 5.找到操作的菜单
        for (SysMenu menu : menus) {
            if(Objects.equals(menu.getPerms(), permission)){
                return menu;
            }
        }
        throw new ServiceException(String.format("方法:%s,未找到菜单, 请检查菜单配置", methodName), 500);
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
