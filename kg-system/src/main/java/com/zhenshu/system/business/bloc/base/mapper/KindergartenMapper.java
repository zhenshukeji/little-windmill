package com.zhenshu.system.business.bloc.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenBO;
import com.zhenshu.system.business.bloc.base.domain.bo.NameInfoBO;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenQueryVO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc 园区Mapper接口
 */
public interface KindergartenMapper extends BaseMapper<Kindergarten> {
    /**
     * 列表查询
     *
     * @param page    分页对象
     * @param queryVO 查询入参
     * @return 结果
     */
    IPage<KindergartenBO> listPage(@Param("page") IPage<KindergartenBO> page, @Param("queryVO") KindergartenQueryVO queryVO);

    /**
     * 查出名称
     *
     * @param kgId 校区id
     * @return 名称
     */
    NameInfoBO getKgNameAndBlocName(Long kgId);

    /**
     * 通过园区id查询uid
     *
     * @param kgId 园区id
     * @return 结果
     */
    PostRoleNameBO getUid(Long kgId);

    /**
     * 通过园区id查询uid
     *
     * @param kgIds 园区id
     * @return 结果
     */
    List<Long> getIds(@Param("kgIds") List<Long> kgIds);

}
