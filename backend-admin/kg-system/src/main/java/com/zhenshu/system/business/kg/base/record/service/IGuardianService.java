package com.zhenshu.system.business.kg.base.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.record.domain.po.Guardian;

import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-23
 * @desc service
 */
public interface IGuardianService extends IService<Guardian> {
    /**
     * 根据手机号查询监护人信息
     *
     * @param phones 手机号集合
     * @return 结果
     */
    List<Guardian> getByPhones(List<String> phones);

    /**
     * 根据学生Id查询监护人信息
     *
     * @param studentId 学生Id
     * @return 结果
     */
    List<Guardian> getByStudentId(Long studentId);

    /**
     * 根据学生Id删除监护人
     *
     * @param studentId 学生Id
     */
    void deleteByStudentId(Long studentId);

    /**
     * 根据学生id和手机号获取监护人
     *
     * @param studentId 学生id
     * @param phone     手机号
     * @return 结果
     */
    Guardian getByStudentIdAndPhone(Long studentId, String phone);

    /**
     * 获取监护人
     *
     * @param students 学生id集合
     * @param phones 手机号集合
     * @return 结果
     */
    List<Guardian> getByStudentIdsAndPhones(List<Long> students, List<String> phones);
}
