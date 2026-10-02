package com.zhenshu.parent.app.controller.program;

import com.zhenshu.parent.app.domain.bo.program.TeachingProgramBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramDetailBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.service.TeachingProgramService;
import com.zhenshu.parent.common.config.aspect.login.LoginInfo;
import com.zhenshu.parent.common.config.aspect.login.LoginStudentId;
import com.zhenshu.parent.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @desc 教学计划
 * @date 2022-06-21
 **/
@Slf4j
@RestController
@RequestMapping("/wx/program")
@Validated
@Api(tags = "教学计划接口 ", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeachingProgramController {

    @Resource
    private TeachingProgramService teachingProgramService;

    @GetMapping("list")
    @ApiOperation(value = "教学计划列表")
    public Result<List<TeachingProgramBO>> list(@ApiIgnore @LoginStudentId Long studentId) {
        List<TeachingProgramBO> list = teachingProgramService.getList(studentId);
        return new Result<List<TeachingProgramBO>>().success(list);
    }

    @GetMapping("/listDetail/{id}")
    @ApiOperation(value = "教学计划详情")
    public Result<TeachingProgramDetailBO> loginByPhone(@ApiIgnore @LoginInfo LoginUser login, @PathVariable(value = "id") @ApiParam(value = "教学计划id") Long id) {
        TeachingProgramDetailBO listDetail = teachingProgramService.getListDetail(login,id);
        return new Result<TeachingProgramDetailBO>().success(listDetail);
    }
}
