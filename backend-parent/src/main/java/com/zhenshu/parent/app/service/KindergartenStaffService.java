package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.po.KindergartenStaff;

/**
 * <p>
 * 校区员工表  服务类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
public interface KindergartenStaffService extends IService<KindergartenStaff> {
    /**
     * 获取园区管理员id
     *
     * @param kgId 园区id
     * @return 结果
     */
    Long getByKgId(Long kgId);

    }
