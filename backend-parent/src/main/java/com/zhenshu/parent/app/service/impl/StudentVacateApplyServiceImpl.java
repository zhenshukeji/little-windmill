package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyIdBO;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyDetailsBO;
import com.zhenshu.parent.app.domain.po.StudentVacateApply;
import com.zhenshu.parent.app.mapper.StudentVacateApplyMapper;
import com.zhenshu.parent.app.service.StudentVacateApplyService;
import com.zhenshu.parent.common.utils.bean.BeanUtils;
import com.zhenshu.parent.common.utils.page.PageEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:49
 * @desc
 */
@Service
public class StudentVacateApplyServiceImpl extends ServiceImpl<StudentVacateApplyMapper, StudentVacateApply> implements StudentVacateApplyService {

                /**
         * 分页查询
         *
         * @param studentId 学生id
         * @param queryVO   入参
         * @return 结果
         */
        @Override
        public IPage<VacateApplyIdBO> listPage(Long studentId, PageEntity queryVO) {
            return super.page(
                    queryVO.page(),
                    new QueryWrapper<StudentVacateApply>().lambda()
                            .eq(StudentVacateApply::getStudentId, studentId)
                            .orderByDesc(StudentVacateApply::getId)
            ).convert(item -> {
                VacateApplyIdBO bo = new VacateApplyIdBO();
                BeanUtils.copyBeanProp(bo, item);
                return bo;
            });
        }

        /**
         * 获取详情
         *
         * @param vacateApplyId 请假申请id
         * @return 结果
         */
        @Override
        public VacateApplyDetailsBO getDetailsById(Long vacateApplyId) {
            return baseMapper.getDetailsById(vacateApplyId);
        }
    }
