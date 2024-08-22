package com.zhenshu.api.ruoyi.common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.zhenshu.common.config.AliyunConfig;
import com.zhenshu.common.config.AppConfig;
import com.zhenshu.common.config.RuoYiConfig;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.file.FileUploadUtils;
import com.zhenshu.common.utils.file.FileUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.framework.config.ServerConfig;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Resource
    private AppConfig appConfig;
    @Resource
    private AliyunConfig aliyunConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
//    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public Result<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "type", required = false) String type)throws Exception {
        try {
            // 上传文件路径
            String filePath = System.getProperty("user.dir") + RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Map<String, String> ajax = new HashMap<>();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            ajax.put("type", type);
            return new Result<Map<String, String>>().success(ajax);
        } catch (Exception e) {
            return new Result<Map<String, String>>().fail(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     */
    @PostMapping("/common/download")
    @ApiOperation(httpMethod = "GET", value = "下载文件")
    public void fileDownload(String fileName, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String filePath = appConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            FileUtils.deleteFile(filePath);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 阿里云OSS对象存储简单上传实现
     */
    @PostMapping("/cdn/upload")
    @ApiOperation(httpMethod = "POST", value = "上传文件")
    public Result<Map<String, String>> store(@RequestParam("file") MultipartFile file, @RequestParam(value = "type", required = false) String type) {
        try {
            String extension = FileUploadUtils.getExtension(file);
            List<String> uploadLimit = appConfig.getUploadLimit();
            if (CollectionUtils.isNotEmpty(uploadLimit)) {
                String upperCaseExtension = extension.toUpperCase();
                if (!uploadLimit.contains(upperCaseExtension)) {
                    return Result.buildFail("不支持的文件类型");
                }
            }
            // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            // 对象键（Key）是对象在存储桶中的唯一标识
            String keyName = aliyunConfig.getProjectName() + "/" + FileUploadUtils.extractFilename(file);
            System.out.println(keyName);
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunConfig.getBucketName(), keyName, file.getInputStream(), objectMetadata);
            getOSSClient().putObject(putObjectRequest);
            // 返回地址
            String url = aliyunConfig.getAddress() + keyName;
            Map<String, String> ajax = new HashMap<>();
            ajax.put("fileName", keyName);
            ajax.put("url", url);
            ajax.put("type", type);
            return new Result<Map<String, String>>().success(ajax);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new Result<Map<String, String>>().fail(ex.getMessage());
        }
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    private OSSClient getOSSClient() {
        return new OSSClient(aliyunConfig.getEndpoint(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
    }
}
