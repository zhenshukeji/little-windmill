package com.zhenshu.system.business.kg.base.advanced.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.GradeBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Grade;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeEditVO;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc service
 */
public interface IGradeService extends IService<Grade> {
    /**
     * 列表查询
     *
     * @return 结果
     */
    List<GradeBO> getList();

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(GradeEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(GradeAddVO addVO);

    /**
     * 根据id删除
     *
     * @param deleteVO 删除入参
     */
    void deleteById(GradeDeleteVO deleteVO);

    /**
     * 获取并校验年级
     *
     * @param gradeId 年级Id
     * @return 年级
     */
    Grade getAndVerifyGrade(Long gradeId);


}
