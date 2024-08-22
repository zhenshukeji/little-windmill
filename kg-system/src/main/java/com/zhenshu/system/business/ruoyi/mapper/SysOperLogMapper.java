package com.zhenshu.system.business.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.common.enums.system.OperatorLogAssociationType;
import com.zhenshu.system.business.ruoyi.domain.SysOperLog;
import com.zhenshu.system.business.ruoyi.domain.bo.LogBO;
import com.zhenshu.system.business.ruoyi.domain.bo.LogOperateBO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志 数据层
 *
 * @author ruoyi
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();

    /**
     * 分页查询日志
     *
     * @param startTime     开始时间
     * @param endTime       结果时间
     * @param module        模块
     * @param menu          菜单
     * @param type          日志
     * @param associationId 关联id
     * @return 结果
     */
    IPage<LogBO> listPage(@Param("page") IPage<LogBO> page,
                          @Param("startTime") LocalDateTime startTime,
                          @Param("endTime") LocalDateTime endTime,
                          @Param("module") String module,
                          @Param("menu") String menu,
                          @Param("type") OperatorLogAssociationType type,
                          @Param("associationId") Long associationId);
}
