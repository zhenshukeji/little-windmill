package com.zhenshu.system.business.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.common.enums.system.OperatorLogAssociationType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.system.business.ruoyi.domain.SysOperLog;
import com.zhenshu.system.business.ruoyi.domain.bo.LogBO;
import com.zhenshu.system.business.ruoyi.domain.bo.LogOperateBO;
import com.zhenshu.system.business.ruoyi.mapper.SysOperLogMapper;
import com.zhenshu.system.business.ruoyi.service.ISysOperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {
    @Resource
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }

    /**
     * 分页查询日志
     *
     * @param pageNum   页数
     * @param pageSize  条数
     * @param startTime 开始时间
     * @param endTime   结果时间
     * @param module    模块
     * @param menu      菜单
     * @return 结果
     */
    @Override
    public IPage<LogBO> listPage(Integer pageNum, Integer pageSize, LocalDateTime startTime, LocalDateTime endTime, String module, String menu) {
        IPage<LogBO> page = new Page<>(pageNum, pageSize);
        SysUser login = SecurityUtils.getUser();
        OperatorLogAssociationType type = OperatorLogAssociationType.convert(login.getIdentity());
        Long associationId = (long) Constants.ZERO;
        if (type == OperatorLogAssociationType.KG) {
            associationId = login.getKgId();
        } else if (type == OperatorLogAssociationType.BLOC) {
            associationId = login.getBlocId();
        }
        return baseMapper.listPage(page, startTime, endTime, module, menu, type, associationId);
    }
}
