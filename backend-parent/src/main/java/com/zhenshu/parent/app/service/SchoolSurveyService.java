package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.bo.survey.SchoolSurveyBO;
import com.zhenshu.parent.app.domain.po.SchoolSurvey;

/**
 * <p>
 * 学校概况
 * </p>
 *
 * @author zch
 * @since 2022-06-24
 */
public interface SchoolSurveyService extends IService<SchoolSurvey> {

    /**
     * 通过学校概况
     *
     * @param kgId 校区id
     * @return 结果
     */
    SchoolSurveyBO getByKgId(Long kgId);

}
