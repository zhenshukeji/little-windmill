package com.zhenshu.system.business.kg.base.advanced.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SchoolSurveyDetailsBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.SchoolSurvey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc 园区概况 Mapper接口
 */
public interface SchoolSurveyMapper extends BaseMapper<SchoolSurvey> {

    /**
     * 查询登录用户所在园区的概况
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<SchoolSurveyDetailsBO> getList(@Param("kgId") Long kgId);
}
