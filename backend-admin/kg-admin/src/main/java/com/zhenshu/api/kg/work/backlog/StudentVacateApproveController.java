package com.zhenshu.api.kg.work.backlog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.constant.HttpStatus;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyBO;
import com.zhenshu.system.business.kg.work.backlog.domain.bo.StudentVacateApplyDetailsBO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyEditVO;
import com.zhenshu.system.business.kg.work.backlog.domain.vo.StudentVacateApplyQueryVO;
import com.zhenshu.system.business.kg.work.backlog.service.IStudentVacateApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-07
 * @desc TODO 家长端无法进行申请, 待测试
 */
@RestController
@RequestMapping("/kg/work/backlog/studentVacate")
@PreAuthorize("@ss.hasPermi('kg:work:backlog:studentVacate:all')")
@Api(tags = "学生请假申请 ", value = "WEB - StudentVacateApproveController", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentVacateApproveController {
    @Resource
    private IStudentVacateApplyService studentVacateApplyService;

    @GetMapping("/pending/list")
    @ApiOperation(value = "查询待处理的请假申请")
    public Result<IPage<StudentVacateApplyBO>> listPendingPage(@Validated({Select.class, Default.class}) StudentVacateApplyQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<StudentVacateApplyBO> page = studentVacateApplyService.listPendingPage(queryVO);
        return new Result<IPage<StudentVacateApplyBO>>().success(page);
    }

    @GetMapping("/history/list")
    @ApiOperation(value = "列表查询申请历史")
    public Result<IPage<StudentVacateApplyBO>> listHistoryPage(@Validated({Select.class, Default.class}) StudentVacateApplyQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<StudentVacateApplyBO> page = studentVacateApplyService.listHistoryPage(queryVO);
        return new Result<IPage<StudentVacateApplyBO>>().success(page);
    }

    @Log(title = "处理学生请假申请 ", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "处理学生请假申请")
    public Result<Object> updateById(@RequestBody @Validated StudentVacateApplyEditVO editVO) {
        if (editVO.getResult() && StringUtils.isBlank(editVO.getApproveOpinion())) {
            throw new ServiceException("参数校验错误!", HttpStatus.BAD_REQUEST);
        }
        studentVacateApplyService.updateById(editVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询学生请假申请表")
    public Result<StudentVacateApplyDetailsBO> getDetailsById(@PathVariable("id") @ApiParam(required = true, value = "请假申请id") Long id) {
        StudentVacateApplyDetailsBO detailsBO = studentVacateApplyService.getDetailsById(id);
        return new Result<StudentVacateApplyDetailsBO>().success(detailsBO);
    }

}

