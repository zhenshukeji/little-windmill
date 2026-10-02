package com.zhenshu.system.business.bloc.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffBO;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.domain.vo.BlocStaffQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 集团员工 Mapper接口
 */
public interface BlocStaffMapper extends BaseMapper<BlocStaff> {
    /**
     * 根据Id查询集团员工的详情
     *
     * @param staffId 集团员工Id
     * @return 结果
     */
    BlocStaffDetailsBO getDetailsById(Long staffId);

    /**
     * 列表查询集团员工
     *
     * @param page
     * @param blocStaffQueryVO
     */
    List<BlocStaffBO> detailsListPage(@Param("page") IPage<BlocStaffBO> page, @Param("queryVO") BlocStaffQueryVO blocStaffQueryVO);
}
