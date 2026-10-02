package com.zhenshu.parent.app.controller.my;

import com.zhenshu.parent.app.domain.bo.my.MyBO;
import com.zhenshu.parent.app.domain.bo.my.MyClassroomBO;
import com.zhenshu.parent.app.domain.bo.my.MyInfoBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.vo.my.MyInfoEditVO;
import com.zhenshu.parent.app.facade.my.MyFacade;
import com.zhenshu.parent.common.config.aspect.login.LoginInfo;
import com.zhenshu.parent.common.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 16:52
 * @desc 我的
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/my")
@Api(tags = "我的接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController {
    @Resource
    private MyFacade myFacade;

    @GetMapping
    @ApiOperation(value = "我的")
    public Result<MyBO> my(@ApiIgnore @LoginInfo LoginUser loginUser) {
        return new Result<MyBO>().success(myFacade.my(loginUser));
    }

    @PutMapping("/info")
    @ApiOperation(value = "修改我的资料")
    public Result<Object> myInfo(@ApiIgnore @LoginInfo LoginUser loginUser,
                                 @RequestBody @Validated MyInfoEditVO editVO) {
        myFacade.update(loginUser, editVO);
        return new Result<>().success();
    }

    @GetMapping("/info")
    @ApiOperation(value = "我的资料")
    public Result<MyInfoBO> myInfo(@ApiIgnore @LoginInfo LoginUser loginUser) {
        return new Result<MyInfoBO>().success(myFacade.myInfo(loginUser));
    }

    @GetMapping("/class")
    @ApiOperation(value = "我的班级")
    public Result<MyClassroomBO> myClass(@ApiIgnore @LoginInfo LoginUser loginUser) {
        return new Result<MyClassroomBO>().success(myFacade.myClass(loginUser));
    }
}
