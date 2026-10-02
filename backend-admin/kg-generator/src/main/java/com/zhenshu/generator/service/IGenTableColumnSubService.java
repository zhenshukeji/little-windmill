package com.zhenshu.generator.service;

import com.zhenshu.generator.domain.GenTableColumnSub;

import java.util.List;

/**
 * 业务字段 服务层
 *
 * @author ruoyi
 */
public interface IGenTableColumnSubService {
    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    public List<GenTableColumnSub> selectGenTableColumnSubListByTableId(Long tableId);

    /**
     * 新增业务字段
     *
     * @param GenTableColumnSub 业务字段信息
     * @return 结果
     */
    public int insertGenTableColumnSub(GenTableColumnSub GenTableColumnSub);

    /**
     * 修改业务字段
     *
     * @param GenTableColumnSub 业务字段信息
     * @return 结果
     */
    public int updateGenTableColumnSub(GenTableColumnSub GenTableColumnSub);

    /**
     * 删除业务字段信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableColumnSubByIds(String ids);
}
