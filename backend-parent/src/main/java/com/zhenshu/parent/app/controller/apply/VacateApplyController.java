package com.zhenshu.parent.app.controller.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyIdBO;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyDetailsBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.vo.apply.VacateApplyAddVO;
import com.zhenshu.parent.app.facade.apply.VacateApplyFacade;
import com.zhenshu.parent.common.config.aspect.login.LoginInfo;
import com.zhenshu.parent.common.config.aspect.login.LoginStudentId;
import com.zhenshu.parent.common.constant.Result;
import com.zhenshu.parent.common.utils.page.PageEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:35
 * @desc 请假申请
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/vacate/apply")
@Api(tags = "请假申请接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class VacateApplyController {
    @Resource
    private VacateApplyFacade vacateApplyFacade;

    @PostMapping("/list")
    @ApiOperation(value = "获取请假申请列表")
    public Result<IPage<VacateApplyIdBO>> listPage(@ApiIgnore @LoginStudentId Long studentId,
                                                   @RequestBody @Validated({IPage.class, Default.class}) PageEntity queryVO) {
        IPage<VacateApplyIdBO> page = vacateApplyFacade.listPage(studentId, queryVO);
        return new Result<IPage<VacateApplyIdBO>>().success(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取请假申请详情")
    public Result<VacateApplyDetailsBO> getDetailsById(@ApiIgnore @LoginInfo LoginUser loginUser, @PathVariable("id") Long id) {
        VacateApplyDetailsBO page = vacateApplyFacade.getDetailsById(loginUser, id);
        return new Result<VacateApplyDetailsBO>().success(page);
    }

    @PostMapping
    @ApiOperation(value = "申请请假")
    public Result<Object> insert(@ApiIgnore @LoginInfo LoginUser loginUser,
                                 @RequestBody @Validated VacateApplyAddVO addVO) {
        vacateApplyFacade.insert(loginUser, addVO);
        return new Result<>().success();
    }
}
