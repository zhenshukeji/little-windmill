package com.zhenshu.system.business.kg.base.advanced.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.kg.base.advanced.ClassBindTeacherType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Classroom;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.*;
import com.zhenshu.system.business.kg.base.advanced.mapper.ClassroomMapper;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.advanced.service.IGradeService;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.kg.base.record.service.IStudentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc serviceImpl
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements IClassroomService {
    @Resource
    private IGradeService gradeService;
    @Resource
    private IKindergartenStaffService kindergartenStaffService;
    @Resource
    private IStudentService studentService;

    /**
     * 列表查询入参
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<ClassroomBO> listPage(ClassroomQueryVO queryVO) {
        IPage<ClassroomBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        List<ClassroomBO> list = baseMapper.detailsListPage(page, queryVO);
        page.setRecords(list);
        return page;
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    public void updateById(ClassroomEditVO editVO) {
        Long kgId = SecurityUtils.getUserKgId();
        Classroom data = new Classroom();
        BeanUtils.copyBeanProp(data, editVO);
        data.setKgId(null);
        data.setBlocId(null);
        super.update(data,
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getKgId, kgId)
                        .eq(Classroom::getId, editVO.getId())
                        .set(Classroom::getUpdateTime, LocalDateTime.now())
                        .set(Classroom::getUpdateBy, SecurityUtils.getUserId())
        );
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    public void insert(ClassroomAddVO addVO) {
        // 1.根据年级id获取并检验年级
        gradeService.getAndVerifyGrade(addVO.getGradeId());
        // 2.添加班级
        Classroom classroom = new Classroom();
        classroom.initCreateProp();
        BeanUtils.copyBeanProp(classroom, addVO);
        classroom.setStudentCount(Constants.ZERO);
        SysUser login = SecurityUtils.getUser();
        classroom.setKgId(login.getKgId());
        classroom.setBlocId(login.getBlocId());
        super.save(classroom);
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(ClassroomDeleteVO deleteVO) {
        // 1.根据id查询班级
        Classroom classroom = this.getAndVerifyClassroom(deleteVO.getId());
        // 2.判断班级是否还有学生
        if (classroom.getStudentCount() > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.CLASS_HAS_STUDENT);
        }
        // 3.逻辑删除班级
        Classroom update = new Classroom();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getId, deleteVO.getId())
                        .set(Classroom::getDelFlag, Constants.TRUE)
        );
    }

    @Override
    public List<ClassroomBO> getByKgId(Long kgId) {
        return this.baseMapper.getByKgId(kgId);
    }

    /**
     * 自增班级学生数量
     *
     * @param classId 班级id
     */
    @Override
    public void incrStudentCount(Long classId) {
        super.update(
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getId, classId)
                        .setSql(Constants.INCR_CLASS_STUDENT_COUNT)
                        .set(Classroom::getUpdateBy, SecurityUtils.getUserId())
                        .set(Classroom::getUpdateTime, LocalDateTime.now())
        );
    }

    /**
     * 自减班级学生数量
     *
     * @param classId 班级id
     */
    @Override
    public void decrStudentCount(Long classId) {
        super.update(
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getId, classId)
                        .setSql(Constants.DECR_CLASS_STUDENT_COUNT)
                        .set(Classroom::getUpdateBy, SecurityUtils.getUserId())
                        .set(Classroom::getUpdateTime, LocalDateTime.now())
        );
    }

    /**
     * 自增班级学生数量
     *
     * @param classId 班级id
     * @param num     增加的学生数量
     */
    @Override
    public void incrStudentCount(Long classId, Integer num) {
        super.update(
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getId, classId)
                        .setSql(String.format(Constants.INCR_BY_CLASS_STUDENT_COUNT, num))
                        .set(Classroom::getUpdateBy, SecurityUtils.getUserId())
                        .set(Classroom::getUpdateTime, LocalDateTime.now())
        );
    }

    /**
     * 查询指定校区下的所有班级
     *
     * @return 结果
     */
    @Override
    public List<ClassroomSimpleBO> getClassroomAllByKgId() {
        return baseMapper.getClassroomAllByKgId(SecurityUtils.getUserKgId());
    }

    /**
     * 根据班级名称获取班级
     *
     * @param classNames 班级名称集合
     * @return 结果
     */
    @Override
    public List<Classroom> selectByClassNames(Collection<String> classNames) {
        return super.list(
                new QueryWrapper<Classroom>().lambda()
                        .in(Classroom::getClassName, classNames)
                        .eq(Classroom::getKgId, SecurityUtils.getUserKgId())
        );
    }

    /**
     * 班级绑定老师
     *
     * @param bindVO 入参
     */
    @Override
    public void classBindTeacher(ClassBindTeacherVO bindVO) {
        // 1.获取并校验班级
        Classroom classroom = this.getAndVerifyClassroom(bindVO.getClassId());
        // 2.判断班级是否已经绑定了老师
        Long teacherId = null;
        if (bindVO.getType() == ClassBindTeacherType.PRIMARY) {
            teacherId = classroom.getTeacherId();
        } else if (bindVO.getType() == ClassBindTeacherType.SUB) {
            teacherId = classroom.getSubTeacherId();
        }
        if (teacherId != null) {
            throw new ServiceException(ErrorEnums.CLASS_HAS_TEACHER);
        }
        // 3.判断老师是否已经绑定了其他班级
        SysUser login = SecurityUtils.getUser();
        int count = super.count(
                new QueryWrapper<Classroom>().lambda()
                        .eq(Classroom::getKgId, login.getKgId())
                        .and(wrapper ->
                                wrapper.eq(Classroom::getTeacherId, bindVO.getTeacherId())
                                        .or()
                                        .eq(Classroom::getSubTeacherId, bindVO.getTeacherId())
                        )
        );
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.TEACHER_BIND_CLASS);
        }
        // 4.判断老师是否存在; 是否离职
        KindergartenStaff staff = kindergartenStaffService.getAndVerifyKgStaff(bindVO.getTeacherId());
        if (staff.getIsQuit()) {
            throw new ServiceException(ErrorEnums.TEACHER_IS_QUIT);
        }
        // 5.班级绑定老师
        Classroom update = new Classroom();
        update.initUpdateProp();
        if (bindVO.getType() == ClassBindTeacherType.PRIMARY) {
            update.setTeacherId(bindVO.getTeacherId());
        } else if (bindVO.getType() == ClassBindTeacherType.SUB) {
            update.setSubTeacherId(bindVO.getTeacherId());
        }
        super.update(update,
                new UpdateWrapper<Classroom>().lambda()
                        .eq(Classroom::getId, classroom.getId())
        );
    }

    /**
     * 班级解绑老师
     *
     * @param relieveVO 入参
     */
    @Override
    public void classRelieveTeacher(ClassRelieveTeacherVO relieveVO) {
        // 1.获取并校验班级
        Classroom classroom = this.getAndVerifyClassroom(relieveVO.getClassId());
        // 2.判断班级是否已经绑定了老师
        Long teacherId = null;
        if (relieveVO.getType() == ClassBindTeacherType.PRIMARY) {
            teacherId = classroom.getTeacherId();
        } else if (relieveVO.getType() == ClassBindTeacherType.SUB) {
            teacherId = classroom.getSubTeacherId();
        }
        // 没有绑定老师, 结束方法
        if (teacherId == null) {
            return;
        }
        // 3.班级解绑老师
        Classroom update = new Classroom();
        update.initUpdateProp();
        UpdateWrapper<Classroom> updateWrapper = new UpdateWrapper<>();
        if (relieveVO.getType() == ClassBindTeacherType.PRIMARY) {
            updateWrapper.lambda().set(Classroom::getTeacherId, null);
        } else if (relieveVO.getType() == ClassBindTeacherType.SUB) {
            updateWrapper.lambda().set(Classroom::getSubTeacherId, null);
        }
        super.update(update,
                updateWrapper.lambda()
                        .eq(Classroom::getId, classroom.getId())
        );
    }

    /**
     * 一键升班
     *
     * @param voList 入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void promotion(List<ClassPromotionVO> voList) {
        Set<Long> classSet = new HashSet<>(voList.size());
        Set<Long> newClassSet = new HashSet<>(voList.size());
        Set<Long> teacherSet = new HashSet<>(voList.size());
        // 1.获取所有班级id和老师id
        for (ClassPromotionVO vo : voList) {
            classSet.add(vo.getOldClassId());
            if (!vo.getIsGraduate()) {
                // 判断班级是否有重复
                if (newClassSet.contains(vo.getNewClassId())) {
                    throw new ServiceException(ErrorEnums.CLASS_ILLEGALITY);
                }
                // 判断班主任是否有重复
                if (teacherSet.contains(vo.getTeacherId())) {
                    throw new ServiceException(ErrorEnums.TEACHER_REPEAT);
                }
                classSet.add(vo.getNewClassId());
                teacherSet.add(vo.getTeacherId());
                newClassSet.add(vo.getNewClassId());
            }
        }
        SysUser login = SecurityUtils.getUser();
        // 2.校验班级是否合法
        if (!CollectionUtils.isEmpty(classSet)) {
            int classCount = super.count(
                    new QueryWrapper<Classroom>().lambda()
                            .in(Classroom::getId, classSet)
                            .eq(Classroom::getKgId, login.getKgId())
            );
            if (classCount != classSet.size()) {
                throw new ServiceException(ErrorEnums.CLASS_ILLEGALITY);
            }
        }
        // 3.校验老师是否合法
        if (!CollectionUtils.isEmpty(teacherSet)) {
            Boolean result = kindergartenStaffService.verifyKgStaffByIds(teacherSet, login.getKgId());
            if (!result) {
                throw new ServiceException(ErrorEnums.TEACHER_ILLEGALITY);
            }
        }
        // 4.升班操作
        this.doPromotion(voList, teacherSet);
    }

    /**
     * 查询可进行升班的班级
     *
     * @return 结果
     */
    @Override
    public List<ClassroomSimpleBO> promotionList() {
        return baseMapper.getPromotionList(SecurityUtils.getUserKgId());
    }

    /**
     * 查询所有班级
     *
     * @return 结果
     */
    @Override
    public List<ClassroomSimpleBO> listAll() {
        List<Classroom> list = super.list(
                new QueryWrapper<Classroom>().lambda()
                        .eq(Classroom::getKgId, SecurityUtils.getUserKgId())
        );
        return list.stream().map(item -> {
            ClassroomSimpleBO bo = new ClassroomSimpleBO();
            bo.setId(item.getId());
            bo.setClassName(item.getClassName());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 一键升班
     *
     * @param voList     入参
     * @param teacherSet 新班级绑定的班主任id
     */
    public void doPromotion(List<ClassPromotionVO> voList, Set<Long> teacherSet) {
        // 1.将班主任从绑定中清空
        Classroom classroom = new Classroom();
        classroom.initUpdateProp();
        if (!CollectionUtils.isEmpty(teacherSet)) {
            super.update(classroom,
                    new UpdateWrapper<Classroom>().lambda()
                            .in(Classroom::getTeacherId, teacherSet)
                            .set(Classroom::getTeacherId, null)
            );
        }
        for (ClassPromotionVO vo : voList) {
            if (vo.getIsGraduate()) {
                // 2.班级毕业
                studentService.classGraduate(vo.getOldClassId());
            } else {
                // 3.修改学生绑定的班级
                studentService.changeClass(vo.getNewClassId(), vo.getOldClassId());
                // 4.修改班级绑定的班主任
                classroom.setTeacherId(vo.getTeacherId());
                super.update(classroom,
                        new UpdateWrapper<Classroom>().lambda()
                                .eq(Classroom::getId, vo.getNewClassId())
                );
            }
        }
    }

    /**
     * 获取并校验班级
     *
     * @param classId 班级Id
     * @return 班级
     */
    @Override
    public Classroom getAndVerifyClassroom(Long classId) {
        // 1.查询班级信息
        Classroom classroom = super.getById(classId);
        if (classroom == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 2.判断登录用户是否能操作这个班级
        SysUser login = SecurityUtils.getUser();
        LoginIdentity identity = login.getIdentity();
        boolean b = !Objects.equals(identity, LoginIdentity.KG_PEOPLE) && !Objects.equals(identity, LoginIdentity.KG_ADMIN);
        if (b && !Objects.equals(classroom.getKgId(), login.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return classroom;
    }

    /**
     * 校验班级
     *
     * @param classIds 班级Id数组
     * @return 结果
     */
    @Override
    public List<Classroom> getAndVerifyClassroomByIds(List<Long> classIds) {
        // 1、校验数据是否存在
        List<Classroom> list = super.list(new QueryWrapper<Classroom>().lambda().in(Classroom::getId, classIds));
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 2.判断登录用户是否能操作这个班级
        SysUser login = SecurityUtils.getUser();
        LoginIdentity identity = login.getIdentity();
        for (Classroom classroom : list) {
            boolean b = !Objects.equals(identity, LoginIdentity.KG_PEOPLE) && !Objects.equals(identity, LoginIdentity.KG_ADMIN);
            if (b && !Objects.equals(classroom.getKgId(), login.getKgId())) {
                throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
            }
        }
        return list;
    }

    @Override
    public ClassroomPartBO getPartByUser(Long kgStaffId, Long kgId) {
        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(query ->
                        query.eq(Classroom::getSubTeacherId, kgStaffId)
                                .or(queryOr -> queryOr.eq(Classroom::getTeacherId, kgStaffId))
        ).eq(Classroom::getKgId, kgId)
                .select(Classroom::getId, Classroom::getClassName);
        Classroom classroom = getOne(wrapper);
        if (classroom != null) {
            ClassroomPartBO partBO = new ClassroomPartBO();
            partBO.setClassName(classroom.getClassName());
            partBO.setId(classroom.getId());
            return partBO;
        }
        return null;
    }

        /**
     * 获取班级id和name
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<ClassroomPartBO> getPartByKgId(Long kgId) {
        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Classroom::getKgId, kgId);
        wrapper.lambda().select(Classroom::getId, Classroom::getClassName);
        List<Classroom> list = list(wrapper);
        return list.stream().map((classroom) -> {
            ClassroomPartBO partBO = new ClassroomPartBO();
            partBO.setClassName(classroom.getClassName());
            partBO.setId(classroom.getId());
            return partBO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据id查询班级
     *
     * @param classId 班级id
     * @return 结果
     */
    @Override
    public ClassroomSimpleBO getClassroomSimpleById(Long classId) {
        return baseMapper.getClassroomSimpleById(classId);
    }

    /**
     * 获取员工绑定的班级数量
     *
     * @param staffId 员工id
     * @return 结果
     */
    @Override
    public int getStaffBindClassroomCount(Long staffId) {
        return super.count(
                new QueryWrapper<Classroom>().lambda()
                        .eq(Classroom::getTeacherId, staffId)
                        .or(wrapper -> wrapper.eq(Classroom::getSubTeacherId, staffId))
        );
    }

    /**
     * 获取员工绑定的班级
     *
     * @param staffId 员工id
     * @return 结果
     */
    @Override
    public Classroom getStaffBindClassroom(Long staffId) {
        return super.getOne(
                new QueryWrapper<Classroom>().lambda()
                        .eq(Classroom::getTeacherId, staffId)
                        .or(wrapper -> wrapper.eq(Classroom::getSubTeacherId, staffId))
        );
    }

    /**
     * 根据班级名称获取班级
     *
     * @param classNames 班级名称集合
     * @param kgId       校区id
     * @return 结果
     */
    @Override
    public List<Classroom> getByClassNameAndKgId(Collection<String> classNames, Long kgId) {
        return super.list(
                new QueryWrapper<Classroom>().lambda()
                        .in(Classroom::getClassName, classNames)
                        .eq(Classroom::getKgId, kgId)
        );
    }

    /**
     * 校验是否允许不同年级
     *
     * @param classIdList 班级id数组
     * @return 结果
     */
    @Override
    public List<Long> getGradeListByClassIds(List<Long> classIdList) {
        List<Classroom> list = super.list(new QueryWrapper<Classroom>().lambda().in(Classroom::getId, classIdList));
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        return list.stream().map(Classroom::getGradeId).collect(Collectors.toList());
    }

    /**
     * 获取指定年级的班级数量
     *
     * @param gradeId 年级id
     * @return 结果
     */
    @Override
    public int getCountByGradeId(Long gradeId) {
        return super.count(
                new QueryWrapper<Classroom>().lambda()
                        .eq(Classroom::getGradeId, gradeId)
        );
    }

}
