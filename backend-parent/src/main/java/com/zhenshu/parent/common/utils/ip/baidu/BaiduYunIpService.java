package com.zhenshu.parent.common.utils.ip.baidu;

import com.zhenshu.parent.common.utils.GsonUtil;
import com.zhenshu.parent.common.utils.http.HttpConnector;
import com.zhenshu.parent.common.utils.ip.baidu.domain.BaiduYunIpResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jing
 * @version 1.0
 * @desc 百度云ip定位
 * @date 2021/9/1 0001 11:48
 **/
@Service
public class BaiduYunIpService {

    @Resource
    private BaiduConfig baiduConfig;
    @Resource
    private HttpConnector httpConnector;

    private Log log = LogFactory.getLog(BaiduYunIpService.class);

    private final Integer SUCCESS = 200;

    /**
     * ip转定位
     * https://apis.baidu.com/store/detail/a1c2659c-3596-4a40-811a-b0196e347543
     */
    public BaiduYunIpResult.DataBean ipToAddress(String ip) {
        String url = "https://hcapi01.api.bdymkt.com/ip";
        Map<String, String> param = new HashMap<>(2);
        param.put("ip", ip);
        HashMap<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("X-Bce-Signature", String.format("AppCode/%s", baiduConfig.getAppCode()));
        try {
            String rspBody = httpConnector.doGet(url, headers, param);
            BaiduYunIpResult resultBO = GsonUtil.GsonToBean(rspBody, BaiduYunIpResult.class);
            // 返回查询结果
            if (resultBO.getCode().equals(SUCCESS)) {
                log.info(String.format("百度云ip转定位， ip 是 %s, 定位是 %s", ip, rspBody));
                return resultBO.getData();
            } else {
                log.warn(String.format("百度云ip转定位， ip是%s， 返回 : %s", ip, rspBody));
            }
        } catch (Exception e) {
            log.warn(String.format("百度云 ip 转定位异常 %s", e.getMessage()));
        }
        return null;
    }

}
