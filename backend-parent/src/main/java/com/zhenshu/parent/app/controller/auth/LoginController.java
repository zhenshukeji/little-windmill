package com.zhenshu.parent.app.controller.auth;

import com.zhenshu.parent.app.domain.bo.LoginBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.vo.CaptchaVO;
import com.zhenshu.parent.app.domain.vo.ChangeVO;
import com.zhenshu.parent.app.domain.vo.PhoneLoginVO;
import com.zhenshu.parent.app.domain.vo.WxLoginVO;
import com.zhenshu.parent.app.facade.LoginFacade;
import com.zhenshu.parent.common.config.aspect.login.LoginInfo;
import com.zhenshu.parent.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 登录
 * @date 2022/5/7 0007 16:04
 **/
@RestController
@RequestMapping("/wx/auth")
@Validated
@Api(tags = "登录相关接口 ", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Resource
    private LoginFacade loginFacade;

    @PostMapping("captcha")
    @ApiOperation(value = "发送验证码")
    public Result<Object> captcha(@RequestBody CaptchaVO captchaVO) {
//        loginFacade.captcha(captchaVO);
        return new Result<>().success();
    }

    @PostMapping("/loginByPhone")
    @ApiOperation(value = "手机号验证码登录")
    public Result<LoginBO> loginByPhone(@RequestBody PhoneLoginVO phoneLoginVO) {
        LoginBO loginBO = loginFacade.loginByPhone(phoneLoginVO);
        return new Result<LoginBO>().success(loginBO);
    }

    @PostMapping("/loginByWx")
    @ApiOperation(value = "获取微信手机号登录")
    public Result<LoginBO> loginByWx(@RequestBody WxLoginVO wxLoginVO) {
        LoginBO loginBO = loginFacade.loginByWx(wxLoginVO);
        return new Result<LoginBO>().success(loginBO);
    }

    @PostMapping("/change")
    @ApiOperation(value = "切换或者选择其中一个学生登录")
    public Result<Object> change(@ApiIgnore @LoginInfo LoginUser loginUser, @RequestBody ChangeVO changeVO) {
        loginFacade.change(loginUser, changeVO);
        return new Result<>().success();
    }

    @GetMapping("/myStudent")
    @ApiOperation(value = "获取关联的学生列表")
    public Result<LoginUser> myStudent(@ApiIgnore @LoginInfo LoginUser loginUser) {
        return new Result<LoginUser>().success(loginUser);
    }

}
