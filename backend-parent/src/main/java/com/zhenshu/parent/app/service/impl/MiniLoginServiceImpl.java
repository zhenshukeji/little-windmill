package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhenshu.parent.app.domain.po.MiniLogin;
import com.zhenshu.parent.app.mapper.MiniLoginMapper;
import com.zhenshu.parent.app.service.MiniLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 幼儿园小程序用户表 服务实现类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Service
public class MiniLoginServiceImpl extends ServiceImpl<MiniLoginMapper, MiniLogin> implements MiniLoginService {

    @Override
    public MiniLogin getByPhone(String phone) {
        QueryWrapper<MiniLogin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MiniLogin::getPhone, phone);
        return this.getOne(wrapper,false);
    }
}
