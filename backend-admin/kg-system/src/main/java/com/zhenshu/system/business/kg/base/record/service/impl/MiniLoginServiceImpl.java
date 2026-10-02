package com.zhenshu.system.business.kg.base.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginAddVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginDeleteVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginEditVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginQueryVO;
import com.zhenshu.system.business.kg.base.record.mapper.MiniLoginMapper;
import com.zhenshu.system.business.kg.base.record.service.IMiniLoginService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc serviceImpl
 */
@Service
public class MiniLoginServiceImpl extends ServiceImpl<MiniLoginMapper, MiniLogin> implements IMiniLoginService {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<MiniLoginBO> listPage(MiniLoginQueryVO queryVO) {
        IPage<MiniLogin> page = queryVO.page();
        QueryWrapper<MiniLogin> queryWrapper = new QueryWrapper<>();
        MiniLogin data = new MiniLogin();
        BeanUtils.copyBeanProp(data, queryVO);
        queryWrapper.setEntity(data);
        queryWrapper.lambda().orderByDesc(MiniLogin::getCreateTime);
        page = this.page(page, queryWrapper);
        return page.convert(item -> {
            MiniLoginBO bo = new MiniLoginBO();
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
    public void updateById(MiniLoginEditVO editVO) {
        // 1.根据id修改
        MiniLogin update = new MiniLogin();
        update.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyBeanProp(update, editVO);
        super.update(update,
                new UpdateWrapper<MiniLogin>().lambda()
                        .eq(MiniLogin::getId, editVO.getId())
        );
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    public void insert(MiniLoginAddVO addVO) {
        MiniLogin data = new MiniLogin();
        data.setCreateTime(LocalDateTime.now());
        BeanUtils.copyBeanProp(data, addVO);
        this.save(data);
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(MiniLoginDeleteVO deleteVO) {
        // 1.校验id对应的数据
        this.getAndVerifyMiniLogin(deleteVO.getId());
        // 2.逻辑删除
        MiniLogin update = new MiniLogin();
        update.setUpdateTime(LocalDateTime.now());
        super.update(update,
                new UpdateWrapper<MiniLogin>().lambda()
                        .eq(MiniLogin::getId, deleteVO.getId())
                        .set(MiniLogin::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    @Override
    public MiniLoginDetailsBO getDetailsById(Long id) {
        MiniLogin data = this.getById(id);
        if (data == null) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        MiniLoginDetailsBO detailsBO = new MiniLoginDetailsBO();
        BeanUtils.copyBeanProp(detailsBO, data);
        return detailsBO;
    }

    /**
     * 条件查询
     *
     * @param queryVO 导出查询入参
     * @return 结果
     */
    @Override
    public List<MiniLoginBO> queryList(MiniLoginQueryVO queryVO) {
        QueryWrapper<MiniLogin> queryWrapper = new QueryWrapper<>();
        MiniLogin data = new MiniLogin();
        BeanUtils.copyBeanProp(data, queryVO);
        queryWrapper.setEntity(data);
        queryWrapper.lambda().orderByDesc(MiniLogin::getCreateTime);
        List<MiniLogin> list = this.list(queryWrapper);
        return list.stream().map(item -> {
            MiniLoginBO bo = new MiniLoginBO();
            BeanUtils.copyBeanProp(bo, item);
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    @Override
    public MiniLogin getAndVerifyMiniLogin(Long id) {
        // 1.根据id查询
        MiniLogin data = super.getById(id);
        // 2.判断是否有数据
        if (data == null) {
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
        return data;
    }

    /**
     * 根据手机号查找
     * @param phones 手机号
     * @return 用户
     */
    @Override
    public List<MiniLogin> queryByPhoneList(List<String> phones) {
        QueryWrapper<MiniLogin> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(MiniLogin::getPhone, phones);
        return this.list(wrapper);
    }
}
