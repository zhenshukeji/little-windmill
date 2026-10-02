package com.zhenshu.api.kg.base.record;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.CheckTeacher;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.config.AppConfig;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.constant.HttpStatus;
import com.zhenshu.common.core.domain.AjaxResult;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.bean.BeanValidators;
import com.zhenshu.common.utils.poi.ExcelUtil;
import com.zhenshu.common.utils.poi.HuToolExcelUtil;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.record.domain.bo.*;
import com.zhenshu.system.business.kg.base.record.domain.dto.StudentExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.kg.base.record.service.IStudentService;
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
import javax.validation.groups.Default;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/kg/base/record/student")
@Api(tags = "学生", value = "WEB - StudentController", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    @Resource
    private IStudentService studentService;
    @Resource
    private IClassroomService classroomService;
    @Resource
    private AppConfig appConfig;
    @Resource
    protected Validator validator;
    @Resource
    private IKindergartenStaffService kindergartenStaffService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询学生 ")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<IPage<StudentBO>> listPage(@Validated({Select.class, Default.class}) StudentQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<StudentBO> page = studentService.listPage(queryVO);
        return new Result<IPage<StudentBO>>().success(page);
    }

    @GetMapping("/history/list")
    @ApiOperation(value = "分页查询历史学生")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<IPage<StudentHistoryBO>> listHistoryPage(@Validated({Select.class, Default.class}) StudentQueryHistoryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<StudentHistoryBO> page = studentService.listHistoryPage(queryVO);
        return new Result<IPage<StudentHistoryBO>>().success(page);
    }

    @PostMapping("/isTeacher")
    @ApiOperation(value = "返回登录用户是否是教师, 如果是校区管理员, 则一定返回false")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<Boolean> isTeacher() {
        boolean isTeacher = kindergartenStaffService.isTeacher(SecurityUtils.getUser());
        return new Result<Boolean>().success(isTeacher);
    }

    @Log(title = "删除学生", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除学生表 ")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public Result<Object> deleteById(@RequestBody @Validated StudentIdVO idVO) {
        studentService.deleteById(idVO);
        return new Result<>().success();
    }

    @Log(title = "学生离校", businessType = BusinessType.DELETE)
    @DeleteMapping("/leave")
    @ApiOperation(value = "学生离校")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public Result<Object> leave(@RequestBody @Validated StudentIdVO idVO) {
        studentService.leave(idVO);
        return new Result<>().success();
    }

    @Log(title = "修改学生", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改学生")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<Object> updateById(@RequestBody @Validated StudentEditVO editVO) {
        // 校验监护人1是否填写姓名和手机号
        GuardianEditVO guardianEditVO = editVO.getGuardians().get(Constants.ZERO);
        if (StringUtils.isEmpty(guardianEditVO.getName().trim()) || StringUtils.isEmpty(guardianEditVO.getPhone().trim())) {
            throw new ServiceException("监护人1姓名和手机号不能为空", HttpStatus.BAD_REQUEST);
        }
        studentService.updateById(editVO);
        return new Result<>().success();
    }

    @Log(title = "添加学生", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加学生")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public Result<Object> insert(@RequestBody @Validated StudentAddVO addVO) {
        // 校验监护人1是否填写姓名和手机号
        GuardianAddVO guardianAddVO = addVO.getGuardians().get(Constants.ZERO);
        if (StringUtils.isEmpty(guardianAddVO.getName().trim()) || StringUtils.isEmpty(guardianAddVO.getPhone().trim())) {
            throw new ServiceException("监护人1姓名和手机号不能为空", HttpStatus.BAD_REQUEST);
        }
        studentService.insert(addVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询学生表 ")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<StudentDetailsBO> getDetailsById(@PathVariable("id") @ApiParam(required = true, value = "id") Long id) {
        StudentDetailsBO detailsBO = studentService.getDetailsById(id);
        return new Result<StudentDetailsBO>().success(detailsBO);
    }

    @Log(title = "返校重读", businessType = BusinessType.INSERT)
    @PostMapping("/backToSchool")
    @ApiOperation(value = "返校重读")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public Result<Object> backToSchool(@RequestBody @Validated StudentBackToSchoolVO backToSchoolVO) {
        studentService.backToSchool(backToSchoolVO);
        return new Result<>().success();
    }

    @GetMapping("/classroom")
    @ApiOperation(value = "获取可选择的班级")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<List<ClassroomSimpleBO>> getClassroomList() {
        return new Result<List<ClassroomSimpleBO>>().success(classroomService.getClassroomAllByKgId());
    }

    @DeleteMapping("/face/{id}")
    @ApiOperation(value = "删除人脸")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    public Result<Object> deleteStudentFace(@PathVariable("id") @ApiParam(required = true, value = "学生id") Long id) {
        studentService.deleteStudentFace(id);
        return new Result<>().success();
    }

    @Log(title = "导出学生", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出学生")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public AjaxResult export(@RequestBody @Validated StudentExportVO exportVO) {
        exportVO.setKgId(SecurityUtils.getUserKgId());
        List<LinkedHashMap<String, Object>> rows = studentService.export(exportVO);
        return HuToolExcelUtil.exportExcelMap(appConfig.getDownloadPath(), rows);
    }

    @Log(title = "导入学生", businessType = BusinessType.IMPORT)
    @ApiOperation(value = "导入学生")
    @PostMapping("/importData")
    @PreAuthorize("@ss.hasAnyPermi('kg:base:record:student:all')")
    @CheckTeacher(value = false, error = ErrorEnums.TEACHER_CANNOT_INTO)
    public Result<Object> importData(@RequestParam("file") MultipartFile file) {
        // 1.将excel中的数据转化为Java对象
        List<StudentExcelDTO> list;
        try {
            ExcelUtil<StudentExcelDTO> util = new ExcelUtil<>(StudentExcelDTO.class);
            list = util.importExcel(file.getInputStream());
        } catch (Exception e) {
            log.error("导入校区员工失败; 失败原因: ", e);
            throw new ServiceException(ErrorEnums.IMPORT_ERROR);
        }
        // 2.excel数据为空直接返回
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ErrorEnums.IMPORT_NOT_DATA);
        }
        // 3.手动对每个对象进行JSR303校验
        list.forEach(item -> BeanValidators.validateWithException(validator, item));
        // 4.批量添加
        studentService.addBatch(list);
        return new Result<>().success();
    }

}
