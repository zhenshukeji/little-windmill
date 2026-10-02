package com.zhenshu.parent.app.controller;

import com.zhenshu.parent.common.config.AppConfig;
import com.zhenshu.parent.common.constant.Result;
import com.zhenshu.parent.common.utils.poi.FileUploadUtils;
import com.zhenshu.parent.common.utils.poi.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuxi
 * @version 1.0
 * @date 2020/11/5 18:50
 * @desc 通用接口
 */
@RestController
@Api(tags = "通用接口", value = "WEB - CommonController", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private AppConfig appConfig;

    /**
     * 本地自托管文件上传（社区版，ce-10）：保留类型校验，落盘到
     * app-config.upload-path（环境变量 UPLOAD_PATH 可覆盖），URL 前缀为
     * app-config.file-url-prefix（FILE_URL_PREFIX，经 /files/** 公开读取）。
     */
    @PostMapping("/cdn/upload")
    @ApiOperation(httpMethod = "POST", value = "上传文件")
    public Result<Map<String, String>> store(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @RequestParam(value = "type", required = false) String type) {
        try {
            if (file == null || file.isEmpty()) {
                return new Result<Map<String, String>>().fail("上传文件为空");
            }
            String extension = FileUploadUtils.getExtension(file);
            List<String> uploadLimit = appConfig.getUploadLimit();
            if (CollectionUtils.isNotEmpty(uploadLimit)) {
                String upperCaseExtension = Strings.toUpperCase(extension);
                if (!uploadLimit.contains(upperCaseExtension)) {
                    return Result.buildFail("不支持的文件类型");
                }
            }
            String relativePath = FileUploadUtils.extractFilename(file);
            java.io.File root = new java.io.File(appConfig.getUploadPath());
            java.io.File dest = new java.io.File(root, relativePath);
            if (!dest.getParentFile().exists() && !dest.getParentFile().mkdirs()) {
                return new Result<Map<String, String>>().fail("上传目录创建失败");
            }
            if (!dest.getCanonicalPath().startsWith(root.getCanonicalPath())) {
                return new Result<Map<String, String>>().fail("非法的文件路径");
            }
            file.transferTo(dest.getCanonicalFile());
            String url = appConfig.getFileUrlPrefix() + "/" + relativePath.replace('\\', '/');
            Map<String, String> ajax = new HashMap<>();
            ajax.put("fileName", relativePath);
            ajax.put("url", url);
            ajax.put("type", type);
            return new Result<Map<String, String>>().success(ajax);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new Result<Map<String, String>>().fail(ex.getMessage());
        }
    }

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     */
    @GetMapping("/common/download")
    @ApiOperation(httpMethod = "GET", value = "下载文件")
    public void fileDownload(String fileName, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(String.format("文件名称(%s)非法，不允许下载。 ", fileName));
            }
            String filePath = AppConfig.getDownloadPath() + fileName;

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
}
