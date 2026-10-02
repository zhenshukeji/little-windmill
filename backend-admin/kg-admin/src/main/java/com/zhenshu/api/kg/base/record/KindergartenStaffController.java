package com.zhenshu.api.kg.base.record;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.config.AppConfig;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.AjaxResult;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanValidators;
import com.zhenshu.common.utils.poi.ExcelUtil;
import com.zhenshu.common.utils.poi.HuToolExcelUtil;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.dto.KgStaffExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.ruoyi.domain.bo.RoleBO;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/kg/base/record/staff")
@PreAuthorize("@ss.hasPermi('kg:base:record:staff:all')")
@Api(tags = "学校员工 ", value = "WEB - KindergartenStaffController", produces = MediaType.APPLICATION_JSON_VALUE)
public class KindergartenStaffController {
    @Resource
    private IKindergartenStaffService kindergartenStaffService;
    @Resource
    private RemoteSysPostService remoteSysPostService;
    @Resource
    private AppConfig appConfig;
    @Resource
    protected Validator validator;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询学校员工表 ")
    public Result<IPage<KindergartenStaffBO>> listPage(@Validated({Select.class, Default.class}) KindergartenStaffQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<KindergartenStaffBO> page = kindergartenStaffService.listPage(queryVO);
        return new Result<IPage<KindergartenStaffBO>>().success(page);
    }

    @Log(title = "校区员工离职", businessType = BusinessType.DELETE)
    @DeleteMapping("/quit")
    @ApiOperation(value = "校区员工离职")
    public Result<Object> quit(@RequestBody @Validated KindergartenStaffQuitVO quitVO) {
        kindergartenStaffService.quit(quitVO);
        return new Result<>().success();
    }

    @Log(title = "校区员工入职", businessType = BusinessType.INSERT)
    @PostMapping("/entry")
    @ApiOperation(value = "校区员工入职")
    public Result<Object> entry(@RequestBody @Validated KindergartenStaffIdVO idVO) {
        kindergartenStaffService.entry(idVO);
        return new Result<>().success();
    }

    @Log(title = "删除校区员工", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除校区员工")
    public Result<Object> deleteById(@RequestBody @Validated KindergartenStaffIdVO idVO) {
        kindergartenStaffService.deleteById(idVO);
        return new Result<>().success();
    }

    @Log(title = "修改学校员工", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改学校员工")
    public Result<Object> updateById(@RequestBody @Validated KindergartenStaffEditVO editVO) {
        kindergartenStaffService.updateById(editVO);
        return new Result<>().success();
    }

    @Log(title = "添加学校员工", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加学校员工")
    public Result<Object> insert(@RequestBody @Validated KindergartenStaffAddVO addVO) {
        kindergartenStaffService.insert(addVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询员工")
    public Result<KindergartenStaffDetailsBO> getById(@PathVariable("id") @ApiParam(required = true, value = "id") Long id,
                                                      @RequestParam("type")
                                                      @ApiParam(required = true, value = "0:查询校区员工 1:集团员工")
                                                      @Validated @NotNull @Size(max = 1) Integer type) {
        KindergartenStaffDetailsBO detailsBO;
        if (type == Constants.ZERO) {
            detailsBO = kindergartenStaffService.getDetailsById(id);
        } else {
            detailsBO = kindergartenStaffService.getBlocStaffById(id);
        }
        return new Result<KindergartenStaffDetailsBO>().success(detailsBO);
    }

    @Log(title = "进入校区的集团人员分配岗位", businessType = BusinessType.UPDATE)
    @PostMapping("/post")
    @ApiOperation(value = "进入校区的集团人员分配岗位")
    public Result<Object> post(@RequestBody @Validated BlocStaffPostVO postVO) {
        kindergartenStaffService.post(postVO);
        return new Result<>().success();
    }

    @GetMapping("/post/list")
    @ApiOperation(value = "获取可分配的岗位")
    public Result<List<RoleBO>> getPost() {
        return new Result<List<RoleBO>>().success(remoteSysPostService.getPostList());
    }

    @Log(title = "导出学校员工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出学校员工")
    public AjaxResult export(@RequestBody @Validated KindergartenStaffExportVO exportVO) {
        exportVO.setKgId(SecurityUtils.getUserKgId());
        List<LinkedHashMap<String, Object>> rows = kindergartenStaffService.export(exportVO);
        return HuToolExcelUtil.exportExcelMap(appConfig.getDownloadPath(), rows);
    }

    @Log(title = "导出模板", businessType = BusinessType.EXPORT)
    @GetMapping("/template")
    @ApiOperation(value = "导出模板")
    public AjaxResult template() {
        ExcelUtil<KgStaffExcelDTO> excelUtil = new ExcelUtil<>(KgStaffExcelDTO.class);
        return excelUtil.importTemplateExcel("template", appConfig.getDownloadPath());
    }

    @Log(title = "导入校区员工", businessType = BusinessType.IMPORT)
    @ApiOperation(value = "导入校区员工")
    @PostMapping("/importData")
    public Result<Object> importData(@RequestParam("file") MultipartFile file) {
        // 1.将excel中的数据转化为Java对象
        List<KgStaffExcelDTO> list;
        try {
            ExcelUtil<KgStaffExcelDTO> util = new ExcelUtil<>(KgStaffExcelDTO.class);
            list = util.importExcel(file.getInputStream());
        } catch (Exception e) {
            log.error("导入校区员工失败; 失败原因: {};", e.getMessage());
            e.printStackTrace();
            throw new ServiceException(ErrorEnums.IMPORT_ERROR);
        }
        // 2.excel数据为空直接返回
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ErrorEnums.IMPORT_NOT_DATA);
        }
        // 3.手动对每个对象进行JSR303校验
        list.forEach(item -> BeanValidators.validateWithException(validator, item));
        // 4.批量添加
        kindergartenStaffService.addBatch(list);
        return new Result<>().success();
    }
}
