package com.zhenshu.parent.common.config.aspect.login;

import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.library.token.TokenService;
import com.zhenshu.parent.common.utils.bean.SpringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author jing
 * @version 1.0
 * @desc 注入登录学生id
 * @date 2022/5/24 0024 17:22
 **/
public class LoginStudentIdHandlerResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Long.class) && parameter.hasParameterAnnotation(LoginStudentId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        TokenService tokenService = SpringUtils.getBean(TokenService.class);
        LoginUser loginData = tokenService.getLoginData(request);
        if(loginData == null){
            throw new ServiceException(ErrorEnums.UNAUTHORIZED);
        }
        return loginData.getStudentId();
    }
}
