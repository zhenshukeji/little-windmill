package com.zhenshu.generator.controller;

import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.core.controller.BaseController;
import com.zhenshu.common.core.domain.AjaxResult;
import com.zhenshu.common.core.page.TableDataInfo;
import com.zhenshu.common.core.text.Convert;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.generator.domain.GenTable;
import com.zhenshu.generator.domain.GenTableColumn;
import com.zhenshu.generator.domain.GenTableColumnSub;
import com.zhenshu.generator.domain.vo.GenTableVO;
import com.zhenshu.generator.service.IGenTableColumnService;
import com.zhenshu.generator.service.IGenTableColumnSubService;
import com.zhenshu.generator.service.IGenTableService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {
    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @Resource
    private IGenTableColumnSubService genTableColumnSubService;

    /**
     * 查询代码生成列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询代码生成列表")
    public TableDataInfo genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 修改代码生成业务
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:query')")
    @GetMapping(value = "/{talbleId}")
    @ApiOperation(value = "修改代码生成业务")
    public AjaxResult getInfo(@PathVariable Long talbleId) {
        GenTable table = genTableService.selectGenTableById(talbleId);
        // 获取当前查询表的子表列信息
        List<GenTableColumnSub> genTableColumnSubs = genTableColumnSubService.selectGenTableColumnSubListByTableId(talbleId);
        HashMap<String, List<GenTableColumnSub>> subMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(genTableColumnSubs)) {
            // 进行分类
            for (GenTableColumnSub genTableColumnSub : genTableColumnSubs) {
                if (subMap.containsKey(genTableColumnSub.getSubTableName())) {
                    subMap.get(genTableColumnSub.getSubTableName()).add(genTableColumnSub);
                } else {
                    List<GenTableColumnSub> list = new ArrayList<>();
                    list.add(genTableColumnSub);
                    subMap.put(genTableColumnSub.getSubTableName(), list);
                }
            }
        }
        List<List<GenTableColumnSub>> subList = new ArrayList<>();
        if(subMap.size() > 0){
            subMap.forEach((key, value) -> subList.add(value));
        }
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(talbleId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table); // 当前查询的表
        map.put("subMap", subMap); // 当前表的子表, key是表名, value是子表的所有列
        map.put("subList", subList); // 当前表所有子表的列
        map.put("rows", list); // 当前表的列
        map.put("tables", tables); // 所有表
        return AjaxResult.success(map);
    }

    /**
     * 获取表的所有列信息
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:query')")
    @GetMapping(value = "/table/{tableId}")
    @ApiOperation(value = "获取表的所有列信息")
    public AjaxResult getSubColumn(@PathVariable("tableId") Long tableId) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        list.forEach(item -> {
            item.setIsDetails(null);
            item.setIsList(null);
            item.setIsQuery(null);
        });
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", list);
        return AjaxResult.success(map);
    }

    /**
     * 查询数据库列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping("/db/list")
    @ApiOperation(value = "查询数据库列表")
    public TableDataInfo dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 查询数据表字段列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping(value = "/column/{talbleId}")
    @ApiOperation(value = "查询数据表字段列表")
    public TableDataInfo columnList(Long tableId) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:import')")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    @ApiOperation(value = "导入表结构（保存）")
    public AjaxResult importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return AjaxResult.success();
    }

    /**
     * 修改保存代码生成业务
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改保存代码生成业务")
    public AjaxResult editSave(@Validated @RequestBody GenTableVO genTableVO) {
        genTableService.validateEdit(genTableVO);
        genTableService.updateGenTable(genTableVO);
        return AjaxResult.success();
    }

    /**
     * 删除代码生成
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:remove')")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tableIds}")
    @ApiOperation(value = "删除代码生成")
    public AjaxResult remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return AjaxResult.success();
    }

    /**
     * 预览代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:preview')")
    @GetMapping("/preview/{tableId}")
    @ApiOperation(value = "预览代码")
    public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return AjaxResult.success(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    @ApiOperation(value = "生成代码（下载方式）")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    @ApiOperation(value = "生成代码（自定义路径）")
    public AjaxResult genCode(@PathVariable("tableName") String tableName) {
        genTableService.generatorCode(tableName);
        return AjaxResult.success();
    }

    /**
     * 同步数据库
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableName}")
    @ApiOperation(value = "同步数据库")
    public AjaxResult synchDb(@PathVariable("tableName") String tableName) {
        genTableService.synchDb(tableName);
        return AjaxResult.success();
    }

    /**
     * 批量生成代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ApiOperation(value = "批量生成代码")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 上传模板
     */
    @PostMapping("/upload/{type}")
    public AjaxResult uploadVM(MultipartFile file, @ApiParam("上传模板类型 js:js文件 vue:vue文件") @PathVariable("type") String type) throws Exception {
        try {
            // 上传文件路径
            String path = System.getProperty("user.dir") + "/kg-generator/target/classes/vm";
            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(path, file);
            String fileName = file.getOriginalFilename();
            if ("js".equalsIgnoreCase(type)) {
                fileName = "js/api.js.vm";
            } else if ("vue".equalsIgnoreCase(type)) {
                fileName = "vue/index.vue.vm";
            }
            File mk = new File(path);
            if(!mk.exists()){
                mk.mkdirs();
            }
            file.transferTo(new File(path + "/" + fileName));
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
