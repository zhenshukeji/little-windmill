package com.zhenshu.parent.common.utils.ip.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.geoip.model.v20200101.DescribeIpv4LocationRequest;
import com.aliyuncs.geoip.model.v20200101.DescribeIpv4LocationResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 阿里云ip定位
 * @date 2021/5/13 0013 20:27
 **/
@Service
public class AliyunIpService {

    @Resource
    private AliyunIpConfig aliyunIpConfig;

    private Log log = LogFactory.getLog(AliyunIpService.class);

    /**
     * ip转定位
     * https://help.aliyun.com/document_detail/170546.html?spm=a2c4g.11186623.2.10.5d157541T4zK3h
     */
    public DescribeIpv4LocationResponse ipToAddress(String ip) {
        DefaultProfile profile = DefaultProfile.getProfile(aliyunIpConfig.getRegionId(), aliyunIpConfig.getAccessKeyId(), aliyunIpConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        DescribeIpv4LocationRequest request = new DescribeIpv4LocationRequest();
        request.setIp(ip);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            log.info(String.format("阿里云 ip 转定位异常 ip是%s， 返回 ErrCode:%s, ErrMsg:%s, RequestId:%s", ip, e.getErrCode(), e.getErrMsg(), e.getRequestId()));
            return null;
        }
    }
}
