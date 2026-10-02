package com.zhenshu.system.business.kg.work.educational.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.kg.work.educational.TeachingProgramRange;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.work.educational.domain.bo.TeachingProgramBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.TeachingProgram;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramQueryVO;
import com.zhenshu.system.business.kg.work.educational.mapper.TeachingProgramMapper;
import com.zhenshu.system.business.kg.work.educational.service.ITeachingProgramService;
import com.zhenshu.system.remote.kg.base.advanced.RemoteClassroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-09
 * @desc 教学计划 ServiceImpl
 */
@Service
public class TeachingProgramServiceImpl extends ServiceImpl<TeachingProgramMapper, TeachingProgram> implements ITeachingProgramService {

    @Resource
    private RemoteClassroomService classroomService;

    /**
     * 列表查询
     *
     * @param queryVo 列表查询入参
     * @return 教学计划列表
     */
    @Override
    public IPage<TeachingProgramBO> listPage(TeachingProgramQueryVO queryVo) {
        Page<TeachingProgramBO> page = new Page<>(queryVo.getPageNum(), queryVo.getPageSize());
        //判断是否获取本周教学计划
        if (Objects.equals(queryVo.getRange(), TeachingProgramRange.WEEK_TEACH_PLANS)) {
            //本周开始日期
            LocalDateTime monday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(DayOfWeek.MONDAY);
            queryVo.setBeginDate(monday.toLocalDate());
        }
        //获取教学计划列表
        List<TeachingProgramBO> teachingProgramList = baseMapper.listPage(page, queryVo);
        teachingProgramList.forEach(e -> e.setEndDate(LocalDateTime.of(e.getBeginDate().toLocalDate(), LocalTime.MIN)
                .with(DayOfWeek.SUNDAY)));
        page.setRecords(teachingProgramList);
        return page;
    }

    /**
     * 添加
     *
     * @param addVo 添加入参
     */
    @Override
    public void insert(TeachingProgramAddVO addVo) {
        QueryWrapper<TeachingProgram> query = new QueryWrapper<>();
        TeachingProgram teachingProgram = new TeachingProgram();
        BeanUtils.copyBeanProp(teachingProgram, addVo);
        query.eq("kg_id", SecurityUtils.getUserKgId());
        query.eq("class_id", addVo.getClassId());
        query.eq("begin_date", addVo.getBeginDate());
        teachingProgram.initCreateProp();
        int count = this.count(query);
        if (count > 0) {
            throw new ServiceException(ErrorEnums.EXIST_DATA);
        }
        teachingProgram.setKgId(SecurityUtils.getUserKgId());
        teachingProgram.setBlocId(SecurityUtils.getUserBlocId());
        this.save(teachingProgram);
    }

    /**
     * 删除
     *
     * @param deleteVo 删除入参
     */
    @Override
    public void deleteById(TeachingProgramDeleteVO deleteVo) {
        TeachingProgram update = getAndVerifyTeachingProgram(deleteVo.getId());
        update.initUpdateProp();
        super.update(
                update, new UpdateWrapper<TeachingProgram>()
                        .lambda().eq(TeachingProgram::getId, deleteVo.getId())
                        .set(TeachingProgram::getDelFlag, Constants.TRUE));
    }

    /**
     * 获取并校验当前用户是否能查看和操作id对应数据
     *
     * @param id 教学计划id
     * @return 结果
     */
    @Override
    public TeachingProgram getAndVerifyTeachingProgram(Long id) {
        //1,根据id查询
        TeachingProgram byId = super.getById(id);
        if (byId == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        //2,当用户登录到系统之后,确定用户身份,用户信息保存,可以在SecurityUtils的user中获取
        Integer code = SecurityUtils.getLoginIdentity().getCode();
        if (Objects.equals(code, LoginIdentity.BLOC_ADMIN.getCode()) || Objects.equals(code, LoginIdentity.KG_ADMIN.getCode())) {
            return byId;
        }

        //3,判断当前用户是否能查看和操作id对应数据
        if (!Objects.equals(byId.getKgId(), SecurityUtils.getUserKgId()) || !Objects.equals(byId.getCreateBy(), SecurityUtils.getUserId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return byId;
    }

    /**
     * 获取班级信息
     *
     * @return 结果
     */
    @Override
    public List<ClassroomPartBO> getClassroomByKgId() {
        return classroomService.getPartByKgId(SecurityUtils.getUserKgId());
    }
}
