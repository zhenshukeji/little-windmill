package com.zhenshu.framework.aspectj;

import com.zhenshu.common.annotation.CheckTeacher;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/8 14:53
 * @desc @CheckTeacher注解切面
 */
@Aspect
@Component
public class CheckTeacherAspect {
    @Resource
    private IKindergartenStaffService kindergartenStaffService;

    @Before(value = "@annotation(checkTeacher)")
    public void doBefore(JoinPoint joinPoint, CheckTeacher checkTeacher) {
        SysUser login = SecurityUtils.getUser();
        if (login.getIdentity() == LoginIdentity.KG_ADMIN) {
            return;
        }
        boolean isTeacher = kindergartenStaffService.isTeacher(login);
        if (isTeacher != checkTeacher.value()) {
            throw new ServiceException(checkTeacher.error());
        }
    }
}
