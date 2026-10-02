package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.banner.BannerBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.Banner;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc banner轮播图
 */
public interface BannerService extends IService<Banner> {
    /**
     * 获取轮播图地址
     *
     * @param user 登录用户信息
     * @return 结果
     */
    BannerBO getBannerUrl(LoginUser user);
}
