package com.zhenshu.system.business.kg.work.educational.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.work.educational.domain.bo.ClassCircleBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.ClassCircle;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleQueryVO;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc service
 */
public interface IClassCircleService extends IService<ClassCircle> {

    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 分页结果(设置时间)
     */
    IPage<ClassCircleBO> listQuery(ClassCircleQueryVO queryVO);


    /**
     * 发布班级圈
     *
     * @param addVO 班级圈入参
     * @return 结果
     */
    void insertOne(ClassCircleAddVO addVO);

    /**
     * 根据id删除
     *
     * @param deleteVO 删除入参
     */
    void deleteById(ClassCircleDeleteVO deleteVO);


    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     */
    void getAndVerifyClassCircle(Long id);
}
