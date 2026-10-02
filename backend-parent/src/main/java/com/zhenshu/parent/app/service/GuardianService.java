package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.po.Guardian;

import java.util.List;

/**
 * <p>
 * 监护人表  服务类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
public interface GuardianService extends IService<Guardian> {

    /**
     * 根据手机号查询所有的学生信息
     *
     * @param phone 手机号
     * @return 监护人列表
     */
    List<Guardian> getByPhone(String phone);

    /**
     * 获取指定学生的监护人信息
     *
     * @param studentId 学生id
     * @return 结果
     */
    List<Guardian> getByStudentId(Long studentId);

    /**
     * 获取监护人信息
     *
     * @param phone     手机号
     * @param studentId 学生id
     * @return 结果
     */
    Long getByCondition(String phone, Long studentId);
}
