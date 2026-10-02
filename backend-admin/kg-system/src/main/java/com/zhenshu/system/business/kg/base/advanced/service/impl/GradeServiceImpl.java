package com.zhenshu.system.business.kg.base.advanced.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.GradeBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Grade;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeEditVO;
import com.zhenshu.system.business.kg.base.advanced.mapper.GradeMapper;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.advanced.service.IGradeService;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc serviceImpl
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {
    @Resource
    private IClassroomService classroomService;

    /**
     * 列表查询
     *
     * @return 结果
     */
    @Override
    public List<GradeBO> getList() {
        List<Grade> list = this.list(
                new QueryWrapper<Grade>().lambda()
                        .eq(Grade::getKgId, SecurityUtils.getUserKgId())
                        .orderByDesc(Grade::getCreateTime)
        );
        return list.stream().map(item -> {
            GradeBO bo = new GradeBO();
            BeanUtils.copyBeanProp(bo, item);
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    public void updateById(GradeEditVO editVO) {
        // 1.校验年级
        this.getAndVerifyGrade(editVO.getId());
        // 2.修改
        Grade data = new Grade();
        data.initUpdateProp();
        data.setId(editVO.getId());
        BeanUtils.copyBeanProp(data, editVO);
        super.updateById(data);
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    public void insert(GradeAddVO addVO) {
        Grade data = new Grade();
        BeanUtils.copyBeanProp(data, addVO);
        data.initCreateProp();
        SysUser login = SecurityUtils.getUser();
        data.setKgId(login.getKgId());
        data.setBlocId(login.getBlocId());
        this.save(data);
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(GradeDeleteVO deleteVO) {
        // 1.校验年级
        this.getAndVerifyGrade(deleteVO.getId());
        // 2.获取年级下的班级
        int count = classroomService.getCountByGradeId(deleteVO.getId());
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.GRADE_EXIST_CLASS);
        }
        // 3.逻辑删除
        super.update(
                new UpdateWrapper<Grade>().lambda()
                        .eq(Grade::getId, deleteVO.getId())
                        .set(Grade::getUpdateTime, LocalDateTime.now())
                        .set(Grade::getUpdateBy, SecurityUtils.getUserId())
                        .set(Grade::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 获取并校验年级
     *
     * @param gradeId 年级Id
     * @return 班级
     */
    @Override
    public Grade getAndVerifyGrade(Long gradeId) {
        // 1.查询年级信息
        Grade grade = this.getById(gradeId);
        if (grade == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 2.判断登录用户是否能操作这个年级
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(grade.getKgId(), login.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return grade;
    }

}
