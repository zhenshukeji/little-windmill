package com.zhenshu.parent.app.controller.survey;

import com.zhenshu.parent.app.domain.bo.survey.SchoolSurveyBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.service.SchoolSurveyService;
import com.zhenshu.parent.common.config.aspect.login.LoginInfo;
import com.zhenshu.parent.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-24
 * @desc 学校概况接口
 */

@Slf4j
@RestController
@RequestMapping("/wx/survey")
@Api(tags = "学校概况相关接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class SchoolSurveyController {

    @Resource
    private SchoolSurveyService schoolSurveyService;

    @GetMapping
    @ApiOperation(value = "获取学校概况")
     public Result<SchoolSurveyBO> getOne(@ApiIgnore @LoginInfo LoginUser loginUser){
        SchoolSurveyBO one = schoolSurveyService.getByKgId(loginUser.getSelectStudent().getKgId());
        return new Result<SchoolSurveyBO>().success(one);
     }
}
