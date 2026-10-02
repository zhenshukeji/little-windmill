package com.zhenshu.parent.app.controller.circle;

import com.zhenshu.parent.app.domain.bo.circle.ClassCircleBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.service.ClassCircleService;
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
import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc 班级圈相关接口
 */
@Slf4j
@RestController
@RequestMapping("/wx/circle")
@Api(tags = "班级圈相关接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassCircleController {

    @Resource
    private ClassCircleService classCircleService;

    @GetMapping
    @ApiOperation(value = "获取班级圈列表")
    public Result<List<ClassCircleBO>> list(@ApiIgnore @LoginInfo LoginUser user){
        List<ClassCircleBO> list = classCircleService.listQuery(user);
        return new Result<List<ClassCircleBO>>().success(list);
    }
}
