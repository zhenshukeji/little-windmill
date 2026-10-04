package com.zhenshu.parent.app.facade;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.RandomUtil;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import com.google.gson.JsonObject;
import com.zhenshu.parent.app.domain.bo.LoginBO;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.dto.WxPhoneInfo;
import com.zhenshu.parent.app.domain.po.Guardian;
import com.zhenshu.parent.app.domain.po.MiniLogin;
import com.zhenshu.parent.app.domain.vo.CaptchaVO;
import com.zhenshu.parent.app.domain.vo.ChangeVO;
import com.zhenshu.parent.app.domain.vo.PhoneLoginVO;
import com.zhenshu.parent.app.domain.vo.WxLoginVO;
import com.zhenshu.parent.app.service.GuardianService;
import com.zhenshu.parent.app.service.MiniLoginService;
import com.zhenshu.parent.app.service.StudentService;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.library.token.TokenService;
import com.zhenshu.parent.common.third.aliyun.sms.CaptchaCodeManager;
import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jing
 * @version 1.0
 * @desc 登录逻辑处理
 * @date 2022/5/7 0007 16:07
 **/
@Service
@Slf4j
public class LoginFacade {

    @Resource
    private WxMaService wxService;
    @Resource
    private CaptchaCodeManager captchaCodeManager;
    @Resource
    private GuardianService guardianService;
    @Resource
    private StudentService studentService;
    @Resource
    private MiniLoginService miniLoginService;
    @Resource
    private TokenService tokenService;

    /**
     * 发送短信验证码
     *
     * @param captchaVO 验证码入参
     */
    public void captcha(CaptchaVO captchaVO) {
        @NotEmpty String mobile = captchaVO.getPhoneNum();
        if (!RegexUtil.isMobileExact(mobile)) {
            throw new ServiceException(ErrorEnums.BAD_PARAM);
        }
        String code = RandomUtil.randomNumbers(6);
        boolean isSend = captchaCodeManager.checkPhone(mobile);
        if (isSend) {
            throw new ServiceException(ErrorEnums.BIZ_ERROR_CODE);
        }
        boolean result = captchaCodeManager.sendCode(mobile, code);
        if (!result) {
            throw new ServiceException(ErrorEnums.INNER_ERROR);
        }
        captchaCodeManager.saveCheckCode(mobile, code);
    }

    /**
     * 根据手机号和验证码登录
     *
     * @param phoneLoginVO 手机号验证码
     * @return 出参
     */
    public LoginBO loginByPhone(PhoneLoginVO phoneLoginVO) {
        if (!captchaCodeManager.checkCode(phoneLoginVO.getPhoneNum(), phoneLoginVO.getVerificationCode())) {
            throw new ServiceException(ErrorEnums.AUTH_CAPTCHA_UN_MATCH);
        }
        captchaCodeManager.delCode(phoneLoginVO.getPhoneNum());
        String openid = this.codeChangeOpenid(phoneLoginVO.getOpenidCode());
        return this.checkAndSaveUser(phoneLoginVO.getPhoneNum(), openid);
    }

    /**
     * 根据微信手机号授权登录
     *
     * @param wxLoginVO 微信登录
     * @return 登录信息
     */
    public LoginBO loginByWx(WxLoginVO wxLoginVO) {
        String phoneNum = this.codeChangePhoneNum(wxLoginVO.getPhoneCode());
        String openid = this.codeChangeOpenid(wxLoginVO.getOpenidCode());
        return this.checkAndSaveUser(phoneNum, openid);
    }

    /**
     * code 换取 openid
     *
     * @param openidCode code
     * @return openid
     */
    private String codeChangeOpenid(@NotEmpty String openidCode) {
        String openid;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(openidCode);
            openid = result.getOpenid();
        } catch (WxErrorException e) {
            // 微信侧明确返回的错误（如 40029 invalid code、45011 限频），记录原始码并透传，避免被笼统吞掉难以定位
            WxError err = e.getError();
            log.error("code 换取 openid 失败, 微信返回 errcode={}, errmsg={}", err.getErrorCode(), err.getErrorMsg());
            throw new ServiceException("微信登录失败(" + err.getErrorCode() + ")，请重试或联系管理员",
                    ErrorEnums.CODE_INVALID.getCode());
        } catch (Exception e) {
            log.error("code 换取 openid 错误", e);
            throw new ServiceException(ErrorEnums.CODE_INVALID);
        }
        return openid;
    }

    /**
     * 校验用户的有效性并保存到数据库里，返回登录信息
     *
     * @param phone  电话号码
     * @param openid openid
     * @return 登录信息
     */
    private LoginBO checkAndSaveUser(String phone, String openid) {
        // 1、去监护人表里查询有没有这个手机号
        List<Guardian> guardianList = guardianService.getByPhone(phone);
        if (guardianList.isEmpty()) {
            throw new ServiceException(ErrorEnums.PHONE_NOT_EXIST);
        }
        // 2、根据监护人查到对应的学生信息
        List<Long> studentIds = guardianList.stream().map(Guardian::getStudentId).collect(Collectors.toList());
        List<StudentBO> studentList = studentService.getByIdsAndNotLeave(studentIds);
        // 3、创建或者获取小程序用户信息
        MiniLogin miniLogin = miniLoginService.getByPhone(phone);
        if (miniLogin == null) {
            miniLogin = new MiniLogin();
            miniLogin.setOpenid(openid);
            miniLogin.setPhone(phone);
            miniLogin.setCreateTime(LocalDateTime.now());
            miniLogin.setUpdateTime(LocalDateTime.now());
            miniLogin.setDelFlag(false);
            miniLoginService.save(miniLogin);
        } else if (!Objects.equals(miniLogin.getOpenid(), openid)) {
            MiniLogin update = new MiniLogin();
            update.setId(miniLogin.getId());
            update.setOpenid(openid);
            update.setUpdateTime(LocalDateTime.now());
            miniLoginService.updateById(update);
        }
        // 4、保存用户信息到redis，并获取token
        LoginUser loginUser = new LoginUser();
        loginUser.setMiniLogin(miniLogin);
        loginUser.setStudentList(studentList);
        String token = tokenService.createToken(loginUser);
        // 5、转换用户信息
        LoginBO loginBO = new LoginBO();
        loginBO.setToken(token);
        loginBO.setStudentList(studentList);
        return loginBO;
    }

    /**
     * 微信code换取手机号码
     *
     * @param phoneCode 手机号code
     * @return 手机号
     */
    private String codeChangePhoneNum(@NotEmpty String phoneCode) {
        String phoneNum;
        try {
            JsonObject object = new JsonObject();
            object.addProperty("code", phoneCode);
            String rspBody = wxService.post("https://api.weixin.qq.com/wxa/business/getuserphonenumber", object);
            WxPhoneInfo phoneInfo = GsonUtil.GsonToBean(rspBody, WxPhoneInfo.class);
            phoneNum = phoneInfo.getPhone_info().getPhoneNumber();
        } catch (Exception e) {
            log.error("code 换取手机号错误", e);
            throw new ServiceException(ErrorEnums.CHANGE_PHONE_ERROR);
        }
        return phoneNum;
    }

    /**
     * 选择或者切换其中一个小朋友登录
     *
     * @param loginUser 登录信息
     * @param changeVO  选择入参
     */
    public void change(LoginUser loginUser, ChangeVO changeVO) {
        List<StudentBO> studentList = loginUser.getStudentList();
        StudentBO studentInfo = studentList.stream()
                .filter(studentBO -> studentBO.getStudentId().equals(changeVO.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new ServiceException(ErrorEnums.NOT_BIND_STUDENT));
        loginUser.setSelectStudent(studentInfo);
        loginUser.setStudentId(studentInfo.getStudentId());
        tokenService.refreshToken(loginUser);
    }
}
