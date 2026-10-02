package com.zhenshu.parent.common.third.certification;

import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.http.HttpConnector;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jing
 * @version 1.0
 * @desc 实名认证类
 * @date 2022/4/6 0006 14:16
 **/
@Service
public class IdCardService {

    @Resource
    private HttpConnector httpConnector;
    @Resource
    private CertificationConfig certificationConfig;

    /**
     * 实名认证接口
     * 接口文档：https://market.aliyun.com/products/57000002/cmapi022049.html#sku=yuncode1604900004
     *
     * @param realName 真实姓名
     * @param idCard   身份证号
     * @return 返回认证是否通过
     */
    public boolean certification(String realName, String idCard) {
        String url = "https://idcert.market.alicloudapi.com/idcard";
        Map<String, String> param = new HashMap<>(2);
        param.put("idCard", idCard);
        param.put("name", realName);
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Authorization", String.format("APPCODE %s", certificationConfig.getAppCode()));
        String rspBody = httpConnector.doGet(url, headers, param);
        CertificationResponse rsp = GsonUtil.GsonToBean(rspBody, CertificationResponse.class);
        String successCode = "01";
        return Objects.equals(rsp.getStatus(), successCode);
    }

}
