package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.circle.ClassCircleBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.ClassCircle;
import com.zhenshu.parent.app.mapper.ClassCircleMapper;
import com.zhenshu.parent.app.service.ClassCircleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc 班级圈
 */
@Service
public class ClassCircleServiceImpl extends ServiceImpl<ClassCircleMapper, ClassCircle> implements ClassCircleService {
    /**
     * 获取班级圈列表
     *
     * @param user 登录用户信息
     * @return 结果
     */
    @Override
    public List<ClassCircleBO> listQuery(LoginUser user) {
        return baseMapper.listQuery(user.getKgId());
    }
}
