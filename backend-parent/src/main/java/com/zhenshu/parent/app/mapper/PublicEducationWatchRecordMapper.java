package com.zhenshu.parent.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenshu.parent.app.domain.bo.WatchCountBO;
import com.zhenshu.parent.app.domain.po.PublicEducationWatchRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公共教育表 Mapper 接口
 * </p>
 *
 * @author zch
 * @since 2022-06-21
 */
public interface PublicEducationWatchRecordMapper extends BaseMapper<PublicEducationWatchRecord> {

    /**
     * 获取公共教育下载总次数
     *
     * @param ids 公共教育ids
     * @return 结果
     */
    List<WatchCountBO> getTotalCount(@Param("ids") List<Long> ids);
}
