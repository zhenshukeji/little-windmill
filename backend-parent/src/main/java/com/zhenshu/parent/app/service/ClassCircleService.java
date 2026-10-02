package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.circle.ClassCircleBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.ClassCircle;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-29
 * @desc 班级圈
 */
public interface ClassCircleService extends IService<ClassCircle> {
    /**
     * 获取班级圈列表
     *
     * @param user 登录用户信息
     * @return 结果
     */
    List<ClassCircleBO> listQuery(LoginUser user);
}
