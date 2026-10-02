package com.zhenshu.system.business.kg.base.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginBO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc 幼儿园小程序用户Mapper接口
 */
public interface MiniLoginMapper extends BaseMapper<MiniLogin> {
}
