package com.zhenshu.system.business.kg.base.advanced.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Semester;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterQueryVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterEditVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SemesterBO;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc service
 */
public interface ISemesterService extends IService<Semester> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<SemesterBO> listPage(SemesterQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(SemesterEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(SemesterAddVO addVO);

    /**
     * 根据id删除
     *
     * @param deleteVO 删除入参
     */
    void deleteById(SemesterDeleteVO deleteVO);

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id 学期id
     * @return 结果
     */
    Semester getAndVerifySemester(Long id);

    /**
     * 获取园区当前学期（今天落在 [beginDate, endDate] 内的学期）；
     * 若无匹配，回退到最近一个学期；仍无则返回 null。
     *
     * @param kgId 园区id
     * @return 当前学期（可能为 null）
     */
    Semester getCurrentSemester(Long kgId);
}
