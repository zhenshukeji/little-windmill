package com.zhenshu.parent.common.third.wx.zhenshu;

import com.zhenshu.parent.common.utils.AssertUtils;
import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.http.HttpConnector;
import com.zhenshu.parent.common.third.wx.WxMpProperties;
import com.zhenshu.parent.common.third.wx.WxMpService;
import com.zhenshu.parent.common.third.wx.domain.UserData;
import com.zhenshu.parent.common.third.wx.zhenshu.domain.CheckOpenidResult;
import com.zhenshu.parent.common.third.wx.zhenshu.domain.CodeChangeResult;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author jing
 * @version 1.0
 * @desc 微信公众号工具类
 * @date 2021/9/8 0008 19:29
 **/
public class ZhenShuWxMpService implements WxMpService {

    private final String CHECK_OPENID_URL = "http://120.77.218.29:5900/wxservice/subscribe";
    private final String CODE_URL = "http://120.77.218.29:5900/wxservice/code_user";
    private final Integer ERRCODE = 40003;

    @Resource
    private HttpConnector httpConnector;
    private WxMpProperties wxMpProperties;

    /**
     * 判断openid 是否有效
     *
     * @return 是否有效
     */
    public Boolean checkOpenid(String openid) {
        HashMap<String, String> body = new HashMap<>(2);
        body.put("openid", openid);
        body.put("appid", wxMpProperties.getAppId());
        String rsp = httpConnector.doFormPost(CHECK_OPENID_URL, httpConnector.fromData(body));
        // 异常情况统统判定有效
        CheckOpenidResult result;
        try {
            result = GsonUtil.GsonToBean(rsp, CheckOpenidResult.class);
        } catch (Exception e) {
            return true;
        }
        if (result == null) {
            return true;
        }
        return !Objects.equals(result.getData().getErrcode(), ERRCODE);
    }


    /**
     * 判断openid 是否有效
     *
     * @return 是否有效
     */
    public CodeChangeResult.Content codeChangeUserInfo(String code) {
        HashMap<String, String> body = new HashMap<>(2);
        body.put("code", code);
        body.put("appid", wxMpProperties.getAppId());
        String rsp = httpConnector.doFormPost(wxMpProperties.getService().getCodeUrl(), httpConnector.fromData(body));
        // 异常情况统统判定有效
        CodeChangeResult result;
        try {
            result = GsonUtil.GsonToBean(rsp, CodeChangeResult.class);
        } catch (Exception e) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void setWxMpProperties(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Override
    public UserData getUserInfo(String code) {
        CodeChangeResult.Content content = this.codeChangeUserInfo(code);
        AssertUtils.notNull(content, "code换取用户信息失败");
        UserData userData = new UserData();
        userData.setOpenid(content.getOpenid());
        userData.setNickname(content.getNickname());
        userData.setHeadimgurl(content.getHeadimgurl());
        return userData;
    }
}
