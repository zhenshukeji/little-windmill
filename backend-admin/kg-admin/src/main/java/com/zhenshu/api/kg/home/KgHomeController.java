package com.zhenshu.api.kg.home;

import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.home.domain.bo.KgHomeBO;
import com.zhenshu.system.business.kg.home.facade.KgHomeFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 17:47
 * @desc 首页
 */
@RestController
@RequestMapping("/kg/home")
@Api(tags = "首页", value = "WEB - KgHomeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class KgHomeController {
    @Resource
    private KgHomeFacade kgHomeFacade;

    @GetMapping
    @ApiOperation(value = "首页数据")
    public Result<KgHomeBO> home() {
        return new Result<KgHomeBO>().success(kgHomeFacade.home());
    }

    @GetMapping("/class/list")
    @ApiOperation(value = "获取班级")
    @PreAuthorize("@ss.hasPermi('kg:home:list')")
    public Result<List<ClassroomPartBO>> classList() {
        return new Result<List<ClassroomPartBO>>().success(kgHomeFacade.classList(SecurityUtils.getUserKgId()));
    }
}
