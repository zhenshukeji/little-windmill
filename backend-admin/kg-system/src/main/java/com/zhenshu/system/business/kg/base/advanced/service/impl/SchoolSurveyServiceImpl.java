package com.zhenshu.system.business.kg.base.advanced.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SchoolSurveyDetailsBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.SchoolSurvey;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SchoolSurveyEditVO;
import com.zhenshu.system.business.kg.base.advanced.mapper.SchoolSurveyMapper;
import com.zhenshu.system.business.kg.base.advanced.service.ISchoolSurveyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc serviceImpl
 */
@Service
public class SchoolSurveyServiceImpl extends ServiceImpl<SchoolSurveyMapper, SchoolSurvey> implements ISchoolSurveyService {

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    public void updateById(SchoolSurveyEditVO editVO) {
        // 1.获取并校验id对应的数据
        SchoolSurvey data = this.getAndVerifySchoolSurvey(editVO.getId());
        // 2.图片和内容没有变化不修改
        if (editVO.getImgUrl().equals(data.getImgUrl()) && editVO.getSurveyContent().equals(data.getSurveyContent())) {
            return;
        }
        // 3.根据id修改
        SchoolSurvey update = new SchoolSurvey();
        update.initUpdateProp();
        BeanUtils.copyBeanProp(update, editVO);
        super.update(update,
                new UpdateWrapper<SchoolSurvey>().lambda()
                        .eq(SchoolSurvey::getId, editVO.getId())
        );
    }

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    @Override
    public SchoolSurvey getAndVerifySchoolSurvey(Long id) {
        // 1.根据id查询
        SchoolSurvey data = super.getById(id);
        // 2.判断是否有数据
        if (data == null) {
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
        // 3.判断当前用户是否能查看或操作id对应的数据
        if (!Objects.equals(data.getKgId(), SecurityUtils.getUserKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return data;
    }

    /**
     * 查询登录用户所在园区的概况
     *
     * @return 结果
     */
    @Override
    public List<SchoolSurveyDetailsBO> getList() {
        return baseMapper.getList(SecurityUtils.getUserKgId());
    }


    /**
     * 初始化一个园区概况
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public boolean initSchoolSurvey(Long kgId) {
        SchoolSurvey data = new SchoolSurvey();
        data.initCreateProp();
        SysUser login = SecurityUtils.getUser();
        data.setKgId(kgId);
        data.setBlocId(login.getBlocId());
        return super.save(data);
    }
}
