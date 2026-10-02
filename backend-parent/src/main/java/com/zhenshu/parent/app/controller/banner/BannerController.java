package com.zhenshu.parent.app.controller.banner;

import com.zhenshu.parent.app.domain.bo.banner.BannerBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.service.BannerService;
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
 * @date 2022-06-29
 * @desc 首页上方轮播图
 */
@Slf4j
@RestController
@RequestMapping("/wx/banner")
@Api(tags = "首页上方轮播图接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping
    @ApiOperation(value = "获取banner轮播图地址")
    public Result<BannerBO> bannerUrl(@ApiIgnore @LoginInfo LoginUser user) {
        BannerBO bannerUrl = bannerService.getBannerUrl(user);
        return new Result<BannerBO>().success(bannerUrl);
    }
}
