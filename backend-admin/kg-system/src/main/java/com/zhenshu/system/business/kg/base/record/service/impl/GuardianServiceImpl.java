package com.zhenshu.system.business.kg.base.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.system.business.kg.base.record.domain.po.Guardian;
import com.zhenshu.system.business.kg.base.record.mapper.GuardianMapper;
import com.zhenshu.system.business.kg.base.record.service.IGuardianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-23
 * @desc serviceImpl
 */
@Service
public class GuardianServiceImpl extends ServiceImpl<GuardianMapper, Guardian> implements IGuardianService {
    /**
     * 根据手机号查询监护人信息
     *
     * @param phones 手机号集合
     * @return 结果
     */
    @Override
    public List<Guardian> getByPhones(List<String> phones) {
        return super.list(
                new QueryWrapper<Guardian>().lambda()
                        .in(Guardian::getPhone, phones)
        );
    }

    /**
     * 根据学生Id查询监护人信息
     *
     * @param studentId 学生Id
     * @return 结果
     */
    @Override
    public List<Guardian> getByStudentId(Long studentId) {
        return super.list(
                new QueryWrapper<Guardian>().lambda()
                        .eq(Guardian::getStudentId, studentId)
        );
    }

    /**
     * 根据学生Id删除监护人
     *
     * @param studentId 学生Id
     */
    @Override
    public void deleteByStudentId(Long studentId) {
        // 1.根据学生Id查询监护人信息
        List<Guardian> guardians = this.getByStudentId(studentId);
        // 2.逻辑删除
        Guardian update = new Guardian();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Guardian>().lambda()
                        .in(Guardian::getId, guardians.stream().map(Guardian::getId).collect(Collectors.toList()))
                        .set(Guardian::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 根据学生id和手机号获取监护人
     *
     * @param studentId 学生id
     * @param phone     手机号
     * @return 结果
     */
    @Override
    public Guardian getByStudentIdAndPhone(Long studentId, String phone) {
        return super.getOne(
                new QueryWrapper<Guardian>().lambda()
                        .eq(Guardian::getStudentId, studentId)
                        .eq(Guardian::getPhone, phone)
        );
    }

    /**
     * 获取监护人
     *
     * @param students 学生id集合
     * @param phones 手机号集合
     * @return 结果
     */
    @Override
    public List<Guardian> getByStudentIdsAndPhones(List<Long> students, List<String> phones) {
        return super.list(
                new QueryWrapper<Guardian>().lambda()
                        .in(Guardian::getStudentId, students)
                        .in(Guardian::getPhone, phones)
        );
    }
}
