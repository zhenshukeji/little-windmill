package com.zhenshu.api.kg.base.advanced;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.constant.HttpStatus;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.TeacherBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.*;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffSimpleBO;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级接口
 */
@RestController
@RequestMapping("/kg/base/advanced/classroom")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:gradeClass:all')")
@Api(tags = "班级", value = "WEB - ClassroomController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassroomController {
    @Resource
    private IClassroomService classroomService;
    @Resource
    private IKindergartenStaffService kindergartenStaffService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询班级表")
    public Result<IPage<ClassroomBO>> listPage(@Validated({Select.class, Default.class}) ClassroomQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<ClassroomBO> page = classroomService.listPage(queryVO);
        return new Result<IPage<ClassroomBO>>().success(page);
    }

    @Log(title = "删除班级", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除班级表 ")
    public Result<Object> deleteById(@RequestBody @Validated ClassroomDeleteVO deleteVO) {
        classroomService.deleteById(deleteVO);
        return new Result<>().success();
    }

    @Log(title = "添加班级", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加班级")
    public Result<Object> insert(@RequestBody @Validated ClassroomAddVO addVO) {
        classroomService.insert(addVO);
        return new Result<>().success();
    }

    @Log(title = "修改班级", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改班级")
    public Result<Object> updateById(@RequestBody @Validated ClassroomEditVO editVO) {
        classroomService.updateById(editVO);
        return new Result<>().success();
    }

    @PostMapping("/bind")
    @Log(title = "班级绑定老师", businessType = BusinessType.INSERT)
    @ApiOperation(value = "班级绑定老师 ")
    public Result<Object> classBindTeacher(@RequestBody @Validated ClassBindTeacherVO bindVO) {
        classroomService.classBindTeacher(bindVO);
        return new Result<>().success();
    }

    @DeleteMapping("/relieve")
    @Log(title = "班级解绑老师", businessType = BusinessType.DELETE)
    @ApiOperation(value = "班级解绑老师")
    public Result<Object> classRelieveTeacher(@RequestBody @Validated ClassRelieveTeacherVO relieveVO) {
        classroomService.classRelieveTeacher(relieveVO);
        return new Result<>().success();
    }

    @GetMapping("/teacher/list")
    @ApiOperation(value = "列表分页查询班级可绑定的老师")
    public Result<IPage<TeacherBO>> teacherListPage(@Validated({Select.class, Default.class}) TeacherQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<TeacherBO> page = kindergartenStaffService.selectedTeacherListPage(queryVO);
        return new Result<IPage<TeacherBO>>().success(page);
    }

    @PostMapping("/promotion")
    @Log(title = "一键升班", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "一键升班 ")
    public Result<Object> promotion(@RequestBody @Validated List<ClassPromotionVO> voList) {
        // 校验入参是否正确
        for (ClassPromotionVO vo : voList) {
            if (!vo.getIsGraduate() && (vo.getNewClassId() == null || vo.getTeacherId() == null)) {
                throw new ServiceException("必传参数不能为空", HttpStatus.BAD_REQUEST);
            }
        }
        classroomService.promotion(voList);
        return new Result<>().success();
    }

    @GetMapping("/promotion/list")
    @ApiOperation(value = "查询可升班班级")
    public Result<List<ClassroomSimpleBO>> promotionList() {
        return new Result<List<ClassroomSimpleBO>>().success(classroomService.promotionList());
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有班级")
    public Result<List<ClassroomSimpleBO>> listAll() {
        return new Result<List<ClassroomSimpleBO>>().success(classroomService.listAll());
    }

    @GetMapping("/teacher/all")
    @ApiOperation(value = "查询所有老师")
    public Result<List<KindergartenStaffSimpleBO>> teacherListAll() {
        return new Result<List<KindergartenStaffSimpleBO>>().success(kindergartenStaffService.listAll());
    }

}
