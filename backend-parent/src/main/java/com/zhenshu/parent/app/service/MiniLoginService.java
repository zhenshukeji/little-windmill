package com.zhenshu.parent.app.service;

import com.zhenshu.parent.app.domain.po.MiniLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 幼儿园小程序用户表 服务类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
public interface MiniLoginService extends IService<MiniLogin> {

    /**
     * 根据手机号查询登录信息
     * @param phone 手机号
     * @return 登录信息
     */
    MiniLogin getByPhone(String phone);
}
