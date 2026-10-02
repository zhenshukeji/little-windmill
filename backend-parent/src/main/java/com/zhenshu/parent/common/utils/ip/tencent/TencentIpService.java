package com.zhenshu.parent.common.utils.ip.tencent;

import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.http.HttpConnector;
import com.zhenshu.parent.common.utils.ip.tencent.domain.AdInfoBean;
import com.zhenshu.parent.common.utils.ip.tencent.domain.QueryIpResultBO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc 腾讯云ip
 * @date 2021/8/19 0019 16:25
 **/
@Service
public class TencentIpService {

    @Resource
    private TencentIpConfig tencentIpConfig;
    @Resource
    private HttpConnector httpConnector;

    private Log log = LogFactory.getLog(TencentIpService.class);

    /**
     * ip转定位
     * https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp
     */
    public AdInfoBean ipToAddress(String ip) {
        String url = "https://apis.map.qq.com/ws/location/v1/ip";
        Map<String, String> param = new HashMap<>(2);
        param.put("ip", ip);
        param.put("key", tencentIpConfig.getKey());
        try {
            String rspBody = httpConnector.doGet(url, param);
            QueryIpResultBO resultBO = GsonUtil.GsonToBean(rspBody, QueryIpResultBO.class);
            // 返回查询结果
            if (resultBO.getStatus().equals(Constants.ZERO)) {
                log.debug(String.format("腾讯云 ip 转定位 ， ip 是 %s, 定位是 %s", ip, rspBody));
                return resultBO.getResult().getAd_info();
            } else {
                log.warn(String.format("腾讯云 ip 转定位异常 ip是%s， 返回 : %s", ip, rspBody));
            }
        } catch (Exception e) {
            log.warn("腾讯云 ip 转定位异常 ");
        }
        return null;
    }

}
