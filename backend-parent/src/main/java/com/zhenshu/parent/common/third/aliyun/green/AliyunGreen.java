package com.zhenshu.parent.common.third.aliyun.green;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/4/25 13:45
 * @desc
 */
@Slf4j
@Component
public class AliyunGreen {
    @Resource
    private AliyunGreenConfig greenConfig;

    /**
     * 获取IAcsClient
     *
     * @return 结果
     */
    private IAcsClient getClient() {
        IClientProfile profile = DefaultProfile
                .getProfile(greenConfig.getRegionId(), greenConfig.getAccessKeyId(), greenConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint(greenConfig.getRegionId(), "Green", "green.cn-shanghai.aliyuncs.com");
        return new DefaultAcsClient(profile);
    }

    /**
     * 获取公共参数
     *
     * @return 结果
     */
    private String getClientInfo(Long userId, String nickName) {
        Map<String, String> clientInfo = new HashMap<>(3);
        clientInfo.put("userType", "others");
        clientInfo.put("userId", userId.toString());
        clientInfo.put("userNick", nickName);
        return GsonUtil.GsonString(clientInfo);
    }

    /**
     * 图片同步检测
     *
     * @param images 图片结婚
     * @param userId 用户id
     * @param nickName 用户昵称
     * @return 结果
     */
    public AliyunGreenSuggestion imageScan(Long userId, String nickName, List<String> images) {
        IAcsClient client = this.getClient();

        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定API返回格式。
        imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
        // 指定请求方法。
        imageSyncScanRequest.setMethod(MethodType.POST);
        imageSyncScanRequest.setEncoding("utf-8");
        // 支持HTTP和HTTPS。
        imageSyncScanRequest.setProtocol(ProtocolType.HTTP);

        // 设置公共请求参数
        imageSyncScanRequest.setClientInfo(this.getClientInfo(userId, nickName));

        JSONObject httpBody = new JSONObject();
        /*
         * 设置要检测的风险场景。计费依据此处传递的场景计算。
         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算。
         * 例如，检测2张图片，场景传递porn和terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算。
         * porn：图片智能鉴黄
         * terrorism：图片暴恐涉政
         * ad：图文违规
         */
        httpBody.put("scenes", Arrays.asList("porn", "terrorism", "ad"));

        /*
         * 设置待检测图片。一张图片对应一个task。
         * 多张图片同时检测时，处理的时间由最后一个处理完的图片决定。
         * 通常情况下批量检测的平均响应时间比单张检测的要长。一次批量提交的图片数越多，响应时间被拉长的概率越高。
         * 这里以单张图片检测作为示例, 如果是批量图片检测，请自行构建多个task。
         */
        List<JSONObject> tasks = new ArrayList<>(images.size());
        for (String img : images) {
            JSONObject task = new JSONObject();
            task.put("dataId", UUID.randomUUID().toString());
            // 设置图片链接。
            task.put("url", img);
            task.put("time", new Date());
            tasks.add(task);
        }
        httpBody.put("tasks", tasks);

        imageSyncScanRequest.setHttpContent(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()),
                "UTF-8", FormatType.JSON);

        /*
         * 请设置超时时间。服务端全链路处理超时时间为10秒，请做相应设置。
         * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个ReadTimeout异常。
         */
        imageSyncScanRequest.setConnectTimeout(3000);
        imageSyncScanRequest.setReadTimeout(6000);
        HttpResponse httpResponse = null;
        long start = System.currentTimeMillis();
        try {
            httpResponse = client.doAction(imageSyncScanRequest);
            JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
            log.info("接口: {}, 图片检测, 耗时: {}ms, clientInfo: {}, 请求参数: {}, 返回数据: {}", "/green/image/scan", System.currentTimeMillis() - start, imageSyncScanRequest.getClientInfo(), JSON.toJSONString(httpBody, true), JSON.toJSONString(scrResponse, true));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("接口: {}, 图片检测, 调用失败, 耗时: {}ms, clientInfo: {}, 请求参数: {}, 异常描述: {}", "/green/image/scan", System.currentTimeMillis() - start, imageSyncScanRequest.getClientInfo(), JSON.toJSONString(httpBody, true), e.getMessage());
            throw new ServiceException(ErrorEnums.IMAGE_CHECK_ERROR);
        }

        // 服务端接收到请求，完成处理后返回的结果。
        if (httpResponse.isSuccess()) {
            // 格式化返回参数
            JSONObject scrResponse = JSON.parseObject(org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
            // 获取公共返回参数中的错误码
            int requestCode = scrResponse.getIntValue("code");
            // 每一张图片的检测结果。
            JSONArray taskResults = scrResponse.getJSONArray("data");
            if (200 == requestCode) {
                return this.taskResultsHandel(taskResults);
            } else {
                log.error("接口: {}, 图片检测, 返回非200, 描述: {}", "/green/image/scan", scrResponse.getString("msg"));
                throw new ServiceException(ErrorEnums.IMAGE_CHECK_ERROR);
            }
        } else {
            log.error("接口: {}, 图片检测, 调用失败", "/green/image/scan");
            // 接口处理失败
            throw new ServiceException(ErrorEnums.IMAGE_CHECK_ERROR);
        }
    }

    /**
     * 文本同步检测
     *
     * @param texts 文本集合
     * @return 结果
     */
    public AliyunGreenSuggestion textScan(Long userId, String nickName, List<String> texts) {
        // 过滤空字符串
        String text = texts.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining("&"));

        IAcsClient client = this.getClient();
        TextScanRequest textScanRequest = new TextScanRequest();
        // 指定API返回格式。
        textScanRequest.setAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        // 指定请求方法。
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId(greenConfig.getRegionId());

        // 设置公共请求参数
        textScanRequest.setClientInfo(this.getClientInfo(userId, nickName));

        List<Map<String, Object>> tasks = new ArrayList<>();
        Map<String, Object> task = new LinkedHashMap<>();
        task.put("dataId", UUID.randomUUID().toString());
        // 待检测的文本，长度不超过10000个字符。
        task.put("content", text);
        tasks.add(task);
        JSONObject data = new JSONObject();

        // 检测场景。文本垃圾检测请传递antispam。
        data.put("scenes", Collections.singletonList("antispam"));
        data.put("tasks", tasks);
        textScanRequest.setHttpContent(data.toJSONString().getBytes(StandardCharsets.UTF_8), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间。
        textScanRequest.setConnectTimeout(3000);
        textScanRequest.setReadTimeout(6000);
        long start = System.currentTimeMillis();
        HttpResponse httpResponse;
        try {
            httpResponse = client.doAction(textScanRequest);
            JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
            log.info("接口: {}, 文本检测, 耗时: {}ms, clientInfo: {}, 请求参数: {}, 返回数据: {}", "/green/text/scan", System.currentTimeMillis() - start, textScanRequest.getClientInfo(), JSON.toJSONString(data, true), JSON.toJSONString(scrResponse, true));
        } catch (Exception e) {
            log.error("接口: {}, 文本检测, 调用失败, 耗时: {}ms, clientInfo: {}, 请求参数: {}, 异常描述: {}", "/green/text/scan", System.currentTimeMillis() - start, textScanRequest.getClientInfo(), JSON.toJSONString(data, true), e.getMessage());
            // 接口调用失败
            throw new ServiceException(ErrorEnums.TEXT_CHECK_ERROR);
        }
        if (httpResponse.isSuccess()) {
            JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
            if (200 == scrResponse.getInteger("code")) {
                JSONArray taskResults = scrResponse.getJSONArray("data");
                return this.taskResultsHandel(taskResults);
            } else {
                log.error("接口: {}, 图片检测, 返回非200, 描述: {}", "/green/text/scan", scrResponse.getString("msg"));
                throw new ServiceException(ErrorEnums.TEXT_CHECK_ERROR);
            }
        } else {
            log.error("接口: {}, 图片检测, 调用失败", "/green/text/scan");
            // 接口处理失败
            throw new ServiceException(ErrorEnums.TEXT_CHECK_ERROR);
        }
    }

    /**
     * Task处理结果处理方法
     *
     * @param taskResults task的处理结果
     * @return 结果
     */
    private AliyunGreenSuggestion taskResultsHandel(JSONArray taskResults) {
        // 检测结果, 对应AliyunGreenSuggestion枚举类中的code
        int code = 0;
        for (Object taskResult : taskResults) {
            // 单个处理结果。
            int taskCode = ((JSONObject) taskResult).getIntValue("code");
            // 对应检测场景的处理结果。如果是多个场景，则会有每个场景的结果。
            JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
            if (200 == taskCode) {
                for (Object sceneResult : sceneResults) {
                    // 获取检测场景
                    String scene = ((JSONObject) sceneResult).getString("scene");
                    /*
                     * suggestion：结果
                     * pass：结果正常，无需进行其余操作。
                     * review：结果不确定，需要进行人工审核。
                     * block：结果违规，建议直接删除或者限制公开。
                     */
                    String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                    AliyunGreenSuggestion greenSuggestion = AliyunGreenSuggestion.valueOf(suggestion.toUpperCase());
                    if (greenSuggestion == AliyunGreenSuggestion.BLOCK) {
                        // 结果违规的处理, 直接返回违规
                        return greenSuggestion;
                    }
                    if (code < greenSuggestion.getCode()) {
                        code = greenSuggestion.getCode();
                    }
                }
            } else {
                // 单个Task处理失败, 算违规
                throw new ServiceException(ErrorEnums.IMAGE_NOT_GREEN);
            }
        }
        return AliyunGreenSuggestion.values()[code];
    }
}
