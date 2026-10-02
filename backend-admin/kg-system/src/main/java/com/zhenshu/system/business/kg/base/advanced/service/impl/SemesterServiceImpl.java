package com.zhenshu.system.business.kg.base.advanced.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SemesterBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Semester;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterEditVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterQueryVO;
import com.zhenshu.system.business.kg.base.advanced.mapper.SemesterMapper;
import com.zhenshu.system.business.kg.base.advanced.service.ISemesterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-03-01
 * @desc serviceImpl
 */
@Service
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements ISemesterService {

    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<SemesterBO> listPage(SemesterQueryVO queryVO) {
        IPage<Semester> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        page = this.page(page,
                new QueryWrapper<Semester>().lambda()
                        .eq(Semester::getKgId, queryVO.getKgId())
                        .orderByDesc(Semester::getCreateTime)
        );
        return page.convert(item -> {
            SemesterBO bo = new SemesterBO();
            BeanUtils.copyBeanProp(bo, item);
            return bo;
        });
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    public void updateById(SemesterEditVO editVO) {
        // 1.获取并校验id对应的学期
        Semester data = this.getAndVerifySemester(editVO.getId());
        // 2.校验学期开始时间和结束时间是否有交集
        int count = super.count(
                new QueryWrapper<Semester>().lambda()
                        .ge(Semester::getEndDate, editVO.getBeginDate())
                        .le(Semester::getBeginDate, editVO.getEndDate())
                        .ne(Semester::getId, editVO.getId())
        );
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.DATE_HAS_INTERSECTION);
        }
        // 3.修改学期
        Semester update = new Semester();
        update.initUpdateProp();
        BeanUtils.copyBeanProp(update, editVO);
        super.update(update,
                new UpdateWrapper<Semester>().lambda()
                        .eq(Semester::getId, editVO.getId())
        );
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    public void insert(SemesterAddVO addVO) {
        // 1.校验学期开始时间和结束时间是否有交集
        int count = super.count(
                new QueryWrapper<Semester>().lambda()
                        .ge(Semester::getEndDate, addVO.getBeginDate())
                        .le(Semester::getBeginDate, addVO.getEndDate())
        );
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.DATE_HAS_INTERSECTION);
        }
        // 2.保存学期
        SysUser user = SecurityUtils.getUser();
        Semester data = new Semester();
        BeanUtils.copyBeanProp(data, addVO);
        data.initCreateProp();
        data.setKgId(user.getKgId());
        data.setBlocId(user.getBlocId());
        this.save(data);
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(SemesterDeleteVO deleteVO) {
        // 1.获取并校验id对应的学期
        Semester semester = this.getAndVerifySemester(deleteVO.getId());
        // 2.逻辑删除学期（社区版无学期课程，跳过原课程绑定校验）
        Semester update = new Semester();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Semester>().lambda()
                        .eq(Semester::getId, deleteVO.getId())
                        .set(Semester::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id 学期id
     * @return 结果
     */
    @Override
    public Semester getAndVerifySemester(Long id) {
        // 1.查询学期
        Semester semester = super.getById(id);
        // 2.校验学期是否存在
        if (semester == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 3.校验当前登录用户是否能操作id对应的数据
        SysUser user = SecurityUtils.getUser();
        if (!Objects.equals(semester.getKgId(), user.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return semester;
    }

    /**
     * 获取园区当前学期：先按今天落在区间内的学期取，取不到再回退最近一个。
     */
    @Override
    public Semester getCurrentSemester(Long kgId) {
        Date today = new Date();
        Semester current = super.getOne(
                new QueryWrapper<Semester>().lambda()
                        .eq(Semester::getKgId, kgId)
                        .le(Semester::getBeginDate, today)
                        .ge(Semester::getEndDate, today)
                        .orderByDesc(Semester::getBeginDate)
                        .last("limit 1")
        );
        if (current != null) {
            return current;
        }
        return super.getOne(
                new QueryWrapper<Semester>().lambda()
                        .eq(Semester::getKgId, kgId)
                        .orderByDesc(Semester::getBeginDate)
                        .last("limit 1")
        );
    }
}
