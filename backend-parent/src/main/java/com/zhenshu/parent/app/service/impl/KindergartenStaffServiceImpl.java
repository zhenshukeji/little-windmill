package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.KindergartenStaff;
import com.zhenshu.parent.app.mapper.KindergartenStaffMapper;
import com.zhenshu.parent.app.service.KindergartenStaffService;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.UserIdentity;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 校区员工表  服务实现类
 * </p>
 *
 * @author zch
 * @since 2022-06-24
 */
@Service
public class KindergartenStaffServiceImpl extends ServiceImpl<KindergartenStaffMapper, KindergartenStaff> implements KindergartenStaffService {

    /**
     * 获取园区管理员id
     * <p>
     * 2026-09-29：园区存在多名在职管理员时（演示库 kg 900002 即有两名），
     * 原 selectOne 抛 TooManyResultsException 使信箱等依赖方直接 500；
     * 改为按 id 升序 LIMIT 1，单管理员时行为与原来完全一致。
     *
     * @param kgId 园区id
     * @return 结果
     */
    @Override
    public Long getByKgId(Long kgId) {
        KindergartenStaff one = this.getOne(new QueryWrapper<KindergartenStaff>().lambda()
                .eq(KindergartenStaff::getKgId, kgId)
                .eq(KindergartenStaff::getIdentity, UserIdentity.ADMIN)
                .eq(KindergartenStaff::getDelFlag, Constants.FALSE)
                .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
                .orderByAsc(KindergartenStaff::getId)
                .last("LIMIT 1"));
        return Objects.nonNull(one) ? one.getId() : null;
    }

    }
