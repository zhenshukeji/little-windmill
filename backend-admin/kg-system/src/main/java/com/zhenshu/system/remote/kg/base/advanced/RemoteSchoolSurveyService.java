package com.zhenshu.system.remote.kg.base.advanced;

import com.zhenshu.system.business.kg.base.advanced.service.ISchoolSurveyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @desc 校区
 * @date 2022/3/1
 */
@Service
public class RemoteSchoolSurveyService {
    @Resource
    private ISchoolSurveyService schoolSurveyService;

    /**
     * 初始化一个园区概况
     *
     * @param kgId 校区id
     * @return 结果
     */
    public boolean initSchoolSurvey(Long kgId){
        return schoolSurveyService.initSchoolSurvey(kgId);
    }
}
