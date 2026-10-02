package com.zhenshu.parent.common.third.wx.tuzhan;

import com.zhenshu.parent.common.utils.AssertUtils;
import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.http.HttpConnector;
import com.zhenshu.parent.common.third.wx.WxMpProperties;
import com.zhenshu.parent.common.third.wx.WxMpService;
import com.zhenshu.parent.common.third.wx.domain.UserData;
import com.zhenshu.parent.common.third.wx.tuzhan.domain.CodeChangeUser;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/20 15:48
 * @desc
 */
public class TuZhanWxMpService implements WxMpService {

    @Resource
    private HttpConnector httpConnector;
    private WxMpProperties wxMpProperties;

    /**
     *  code 换取用户信息
     */
    public CodeChangeUser.DataBean codeChangeUserInfo(String code) {
        HashMap<String, String> body = new HashMap<>(2);
        body.put("code", code);
        body.put("appid", wxMpProperties.getAppId());
        String expand = wxMpProperties.getExpand();
        expand = expand == null ? "" : expand;
        String infoUrl = String.format(wxMpProperties.getService().getCodeUrl(), expand);
        System.out.println(infoUrl);
        String rsp = httpConnector.doFormPost(infoUrl, httpConnector.fromData(body));
        // 异常情况统统判定有效
        CodeChangeUser result;
        try {
            result = GsonUtil.GsonToBean(rsp, CodeChangeUser.class);
        } catch (Exception e) {
            return null;
        }
        int success = 0;
        if (result.getCode() != success) {
            return null;
        } else {
            return result.getData();
        }
    }

    @Override
    public void setWxMpProperties(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Override
    public UserData getUserInfo(String code) {
        CodeChangeUser.DataBean dataBean = this.codeChangeUserInfo(code);
        AssertUtils.notNull(dataBean, "code换取用户信息失败");
        UserData userData = new UserData();
        userData.setOpenid(dataBean.getOpenid());
        userData.setNickname(dataBean.getNickname());
        userData.setHeadimgurl(dataBean.getHeadimgurl());
        return userData;
    }
}
