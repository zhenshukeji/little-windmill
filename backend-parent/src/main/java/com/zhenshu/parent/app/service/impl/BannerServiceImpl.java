package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.banner.BannerBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.Banner;
import com.zhenshu.parent.app.mapper.BannerMapper;
import com.zhenshu.parent.app.service.BannerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc banner轮播图
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    /**
     * 获取轮播图地址
     *
     * @param user 登录用户信息
     * @return 结果
     */
    @Override
    public BannerBO getBannerUrl(LoginUser user) {
        BannerBO bannerBO = new BannerBO();
        // 获取轮播图
        Banner banner = this.getOne(new QueryWrapper<Banner>().lambda().eq(Banner::getKgId, user.getKgId()));
        //判空
        if (Objects.nonNull(banner) && StringUtils.isNotBlank(banner.getImgUrl())) {
            String imgUrl = banner.getImgUrl();
            String[] split = imgUrl.split(",");
            bannerBO.setImgUrl(Arrays.asList(split));
        }
        return bannerBO;
    }
}
