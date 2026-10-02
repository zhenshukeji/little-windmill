package com.zhenshu.system.business.kg.base.advanced.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SchoolSurveyDetailsBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.SchoolSurvey;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SchoolSurveyEditVO;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc service
 */
public interface ISchoolSurveyService extends IService<SchoolSurvey> {

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(SchoolSurveyEditVO editVO);

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    SchoolSurvey getAndVerifySchoolSurvey(Long id);

    /**
     * 查询登录用户所在园区的概况
     *
     * @return 结果
     */
    List<SchoolSurveyDetailsBO> getList();

    /**
     * 初始化一个园区概况
     *
     * @param kgId 校区id
     * @return 结果
     */
    boolean initSchoolSurvey(Long kgId);
}
