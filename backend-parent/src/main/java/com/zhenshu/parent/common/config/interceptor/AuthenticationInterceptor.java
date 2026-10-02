package com.zhenshu.parent.common.config.interceptor;

import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuxi
 * @version 1.0
 * @date 2020/12/20 19:53
 * @desc
 **/
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("Authorization");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        // 执行认证
        if (token == null) {
            throw new ServiceException(ErrorEnums.UNAUTHORIZED);
        }
        // 验证 token
        if (!JwtUtils.verify(token)) {
            throw new ServiceException(ErrorEnums.UNAUTHORIZED);
        }
        return true;
    }
}
