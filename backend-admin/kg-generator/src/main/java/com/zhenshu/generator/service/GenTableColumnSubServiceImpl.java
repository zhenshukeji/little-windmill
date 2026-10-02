package com.zhenshu.generator.service;

import com.zhenshu.common.core.text.Convert;
import com.zhenshu.generator.domain.GenTableColumnSub;
import com.zhenshu.generator.mapper.GenTableColumnSubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务字段 服务层实现
 *
 * @author ruoyi
 */
@Service
public class GenTableColumnSubServiceImpl implements IGenTableColumnSubService {
    @Resource
    private GenTableColumnSubMapper genTableColumnSubMapper;

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumnSub> selectGenTableColumnSubListByTableId(Long tableId) {
        return genTableColumnSubMapper.selectGenTableColumnSubListByTableId(tableId);
    }

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int insertGenTableColumnSub(GenTableColumnSub genTableColumn) {
        return genTableColumnSubMapper.insertGenTableColumnSub(genTableColumn);
    }

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int updateGenTableColumnSub(GenTableColumnSub genTableColumn) {
        return genTableColumnSubMapper.updateGenTableColumnSub(genTableColumn);
    }

    /**
     * 删除业务字段对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGenTableColumnSubByIds(String ids) {
        return genTableColumnSubMapper.deleteGenTableColumnSubByIds(Convert.toLongArray(ids));
    }
}
