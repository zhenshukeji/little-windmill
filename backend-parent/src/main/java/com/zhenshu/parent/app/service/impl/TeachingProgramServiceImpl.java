package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramDetailBO;
import com.zhenshu.parent.app.domain.bo.program.TeachingProgramPartBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.TeachingProgram;
import com.zhenshu.parent.app.mapper.TeachingProgramMapper;
import com.zhenshu.parent.app.service.TeachingProgramService;
import com.zhenshu.parent.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 教学计划表
 * </p>
 *
 * @author zch
 * @since 2022-06-21
 */
@Service
public class TeachingProgramServiceImpl extends ServiceImpl<TeachingProgramMapper, TeachingProgram> implements TeachingProgramService {

    /**
     * 获取教学计划列表
     *
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public List<TeachingProgramBO> getList(Long studentId) {
        //获取学生班级教学计划
        List<TeachingProgramPartBO> list = baseMapper.getList(studentId);
        if(Objects.isNull(list)){
            return null;
        }
        //遍历封装对象
        List<TeachingProgramBO> teachingProgramList = list.stream().map(e -> {
            TeachingProgramBO teachingProgramBO = new TeachingProgramBO();
            //设置教学计划id
            teachingProgramBO.setId(e.getId());
            //设置创建时间和当前时间间隔
            String timeDivision = DateUtils.getTimeDivision(e.getCreateTime());
            //设置修改时间和当前时间间隔
            teachingProgramBO.setCreateTime(timeDivision);
            LocalDateTime updateTime = e.getUpdateTime();
            if (Objects.nonNull(updateTime)) {
                String timeDay = DateUtils.getTimeDay(updateTime);
                teachingProgramBO.setUpdateTime(timeDay);
            }
            //设置教学计划时间范围
            LocalDate beginDate = e.getBeginDate();
            LocalDate sunday = beginDate.with(DayOfWeek.SUNDAY);
            teachingProgramBO.setDate(beginDate + "至" + sunday + "计划");
            //设置创建者姓名
            teachingProgramBO.setCreateBy(e.getCreateBy());
            //设置图片地址
            teachingProgramBO.setPlanUrl(e.getPlanUrl());
            return teachingProgramBO;
        }).collect(Collectors.toList());
        return teachingProgramList;
    }

    /**
     * 获取教学计划详情
     *
     * @param login 登录用户信息
     * @param id 教学计划详情 id
     * @return 结果
     */
    @Override
    public TeachingProgramDetailBO getListDetail(LoginUser login, Long id) {
        TeachingProgramDetailBO detail = baseMapper.getListDetail(login.getSelectStudent(),id);
            // 设置教学计划时间范围
            LocalDate beginDate = detail.getBeginDate();
            LocalDate sunday = beginDate.with(DayOfWeek.SUNDAY);
            detail.setDate(beginDate + "至" + sunday + "计划");
        return detail;
    }
}
