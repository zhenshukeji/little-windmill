package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.survey.SchoolSurveyBO;
import com.zhenshu.parent.app.domain.po.SchoolSurvey;
import com.zhenshu.parent.app.mapper.SchoolSurveyMapper;
import com.zhenshu.parent.app.service.SchoolSurveyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学校概况表 服务实现类
 * </p>
 *
 * @author zch
 * @since 2022-06-24
 */
@Service
public class SchoolSurveyServiceImpl extends ServiceImpl<SchoolSurveyMapper, SchoolSurvey> implements SchoolSurveyService {

    @Override
    public SchoolSurveyBO getByKgId(Long kgId) {
        SchoolSurvey survey = super.lambdaQuery()
                .eq(SchoolSurvey::getKgId, kgId)
                .one();
        SchoolSurveyBO bo = new SchoolSurveyBO();
        bo.setImgUrl(survey.getImgUrl());
        bo.setSurveyContent(survey.getSurveyContent());
        return bo;
    }
}
