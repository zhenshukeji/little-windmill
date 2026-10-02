package com.zhenshu.system.business.kg.base.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.AutoGenerateNoUtil;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Classroom;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.record.domain.bo.*;
import com.zhenshu.system.business.kg.base.record.domain.dto.StudentExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.po.Guardian;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import com.zhenshu.system.business.kg.base.record.domain.po.Student;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;
import com.zhenshu.system.business.kg.base.record.mapper.StudentMapper;
import com.zhenshu.system.business.kg.base.record.service.IGuardianService;
import com.zhenshu.system.business.kg.base.record.service.IMiniLoginService;
import com.zhenshu.system.business.kg.base.record.service.IStudentService;
import com.zhenshu.system.cache.MinLoginCacheManages;
import com.zhenshu.system.cache.dto.MiniLoginUser;
import com.zhenshu.system.cache.dto.MiniStudentBO;
import com.zhenshu.system.remote.kg.base.advanced.RemoteClassroomService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc serviceImpl
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Resource
    private IClassroomService classroomService;
    @Resource
    private IGuardianService guardianService;
    @Resource
    private IKindergartenService kindergartenService;
    @Resource
    private RemoteClassroomService remoteClassroomService;
    @Resource
    private IMiniLoginService miniLoginService;
    @Resource
    private MinLoginCacheManages minLoginCacheManages;

    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<StudentBO> listPage(StudentQueryVO queryVO) {
        // 1.获取员工绑定的班级; 绑定了班级的员工就是教师, 教师只能查询本班的学生, 管理员除外
        Classroom classroom = null;
        if (SecurityUtils.getLoginIdentity() != LoginIdentity.KG_ADMIN) {
            classroom = classroomService.getStaffBindClassroom(SecurityUtils.getUser().getAssociationId());
        }
        // 2.如果有绑定班级, 则设置查询参数的班级id
        if (classroom != null) {
            queryVO.setClassId(classroom.getId());
        }
        // 3.分页查询
        IPage<StudentBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        List<StudentBO> list = baseMapper.detailsListPage(page, queryVO);
        page.setRecords(list);
        for (StudentBO bo : list) {
            bo.setAge(this.getAge(bo.getBirthdate()));
        }
        return page;
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(StudentEditVO editVO) {
        // 1.校验班级
        Classroom newClassRoom = classroomService.getAndVerifyClassroom(editVO.getClassId());
        // 2.查询并校验学生
        Student student = this.getAndVerifyStudent(editVO.getId());
        // 3.查询监护人
        List<Guardian> guardians = guardianService.listByIds(editVO.getGuardians().stream().map(GuardianEditVO::getId).collect(Collectors.toList()));
        // 4.判断登录用户是否能操作监护人; 判断监护人和学生是否是绑定关系
        for (Guardian guardian : guardians) {
            if (!Objects.equals(guardian.getKgId(), SecurityUtils.getUserKgId())) {
                throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
            }
            if (!Objects.equals(guardian.getStudentId(), student.getId())) {
                throw new ServiceException(ErrorEnums.GUARDIAN_STUDENT_UNRELATED);
            }
        }
        // 5.修改学生信息
        Student data = new Student();
        BeanUtils.copyBeanProp(data, editVO);
        data.initUpdateProp();
        super.updateById(data);
        // 6.修改班级绑定学生数量
        if (!Objects.equals(editVO.getClassId(), student.getClassId())) {
            // 学生只是换了一个班级, 还是原来的学校, 不需要修改学校的学生数量
            // 原班级学生数量自减一
            classroomService.decrStudentCount(student.getClassId());
            // 新班级学生数量自增一
            classroomService.incrStudentCount(editVO.getClassId());
        }
        // 7.修改监护人
        List<Guardian> guardianList = editVO.getGuardians().stream().map(item -> {
            Guardian guardian = new Guardian();
            BeanUtils.copyBeanProp(guardian, item);
            guardian.initUpdateProp();
            return guardian;
        }).collect(Collectors.toList());
        guardianService.updateBatchById(guardianList);
        // 8.操作小程序登录用户缓存
        // 监护人修改前的手机号
        Set<String> oldPhoneSet = guardians.stream().map(Guardian::getPhone).collect(Collectors.toSet());
        // 监护人修改后的手机号
        List<String> newPhoneList = editVO.getGuardians().stream().map(GuardianEditVO::getPhone).collect(Collectors.toList());
        LoginUser login = SecurityUtils.getLoginUser();
        data.setId(student.getId());
        data.setName(student.getName());
        data.setKgId(login.getUser().getKgId());
        data.setBlocId(login.getUser().getBlocId());
        this.handlerMinLoginUserCache(oldPhoneSet, newPhoneList, data);
    }

    /**
     * 操作小程序登录用户缓存
     * 旧手机号集合与新手机号集合的
     *  差集对应的登录用户缓存将会删除学生
     *  交集对应的登录用户缓存将会更新学生或增加学生
     *
     * @param oldPhoneSet  旧监护人手机号集合
     * @param newPhoneList 新监护人手机号集合
     * @param student      修改后的学生对象
     */
    private void handlerMinLoginUserCache(Set<String> oldPhoneSet, List<String> newPhoneList, Student student) {
        // 移除修改前和修改后相同的手机号
        oldPhoneSet.removeAll(newPhoneList);
        // 合并到修改后的集合中
        newPhoneList.addAll(oldPhoneSet);
        // 查询手机号关联的小程序用户
        List<MiniLogin> miniLoginList = miniLoginService.queryByPhoneList(newPhoneList);
        if (CollectionUtils.isEmpty(miniLoginList)) {
            return;
        }
        // 查询小程序用户对应的登录缓存
        List<MiniLoginUser> miniLoginUsers = minLoginCacheManages.multiGetLoginUser(miniLoginList.stream().map(MiniLogin::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(miniLoginUsers)) {
            return;
        }
        Map<Long, MiniLoginUser> loginUserMap = miniLoginUsers.stream().collect(Collectors.toMap(
                MiniLoginUser::getUserId,
                item -> item
        ));
        miniLoginList.forEach(minLogin -> {
            MiniLoginUser loginUser = loginUserMap.get(minLogin.getId());
            if (loginUser == null) {
                return;
            }
            List<MiniStudentBO> studentList = Optional.ofNullable(loginUser.getStudentList()).orElse(Collections.emptyList());
            // 判断小程序用户对应的手机号是否在修改前的手机号集合中
            if (!CollectionUtils.isEmpty(oldPhoneSet) && oldPhoneSet.contains(minLogin.getPhone())) {
                // 从学生列表中删除这个学生
                for (int i = 0; i < studentList.size(); i++) {
                    MiniStudentBO studentInfo = studentList.get(i);
                    if (studentInfo != null && Objects.equals(studentInfo.getStudentId(), student.getId())) {
                        studentList.remove(i);
                        break;
                    }
                }
                // 判断是否需要删除选择的学生
                if (Objects.nonNull(loginUser.getSelectStudent()) && Objects.equals(loginUser.getSelectStudent().getStudentId(), student.getId())) {
                    loginUser.setSelectStudent(null);
                    loginUser.setStudentId(null);
                }
            } else {
                boolean hasStudent = false;
                // 更新学生列表里的信息
                for (MiniStudentBO studentInfo : studentList) {
                    this.updateLoginStudentInfo(student, studentInfo);
                    if (studentInfo != null && studentInfo.getStudentId().equals(student.getId())) {
                        hasStudent = true;
                    }
                }
                // 更新当前选择的学生信息
                this.updateLoginStudentInfo(student, loginUser.getSelectStudent());
                // 判断是否需要添加一个新的学生
                if (!hasStudent) {
                    MiniStudentBO studentInfo = new MiniStudentBO();
                    BeanUtils.copyBeanProp(studentInfo, student);
                    studentInfo.setStudentId(student.getId());
                    studentInfo.setStudentName(student.getName());
                    studentList.add(studentInfo);
                }
            }
            // 更新小程序用户登录缓存
            minLoginCacheManages.updateLoginUser(loginUser);
        });
    }

    private void updateLoginStudentInfo(Student student, MiniStudentBO studentInfo) {
        // 更新信息
        if (studentInfo != null && studentInfo.getStudentId().equals(student.getId())) {
            studentInfo.setBirthdate(student.getBirthdate());
            studentInfo.setStudentName(student.getName());
            studentInfo.setClassId(student.getClassId());
        }
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(StudentAddVO addVO) {
        // 1.校验班级
        Classroom classroom = classroomService.getAndVerifyClassroom(addVO.getClassId());
        // 2.保存学生信息
        SysUser login = SecurityUtils.getUser();
        Student data = new Student();
        BeanUtils.copyBeanProp(data, addVO);
        data.initCreateProp();
        data.setKgId(login.getKgId());
        data.setBlocId(login.getBlocId());
        data.setIsLeaveSchool(Constants.FALSE);
        //获取学生在班级编号
        Long classNo = allStudentCount(data.getClassId(), SecurityUtils.getUserKgId()) + 1;
        //获取学生学号
        String studentNo = AutoGenerateNoUtil.generateSn(data.getClassId(), classNo);
        data.setStudentNo(studentNo);
        super.save(data);
        // 3.保存监护人
        List<Guardian> guardianList = addVO.getGuardians().stream().map(item -> {
            Guardian guardian = new Guardian();
            BeanUtils.copyBeanProp(guardian, item);
            guardian.initCreateProp();
            guardian.setBlocId(login.getBlocId());
            guardian.setKgId(login.getKgId());
            guardian.setStudentId(data.getId());
            return guardian;
        }).collect(Collectors.toList());
        // 监护人不足两人, 补一个
        if (guardianList.size() != Constants.TWO) {
            Guardian guardian = new Guardian();
            guardian.initCreateProp();
            guardian.setBlocId(login.getBlocId());
            guardian.setKgId(login.getKgId());
            guardian.setStudentId(data.getId());
            guardianList.add(guardian);
        }
        guardianService.saveBatch(guardianList);
        // 4.校区绑定学生数量自增1
        kindergartenService.incrStudentCount(login.getKgId());
        // 5.班级学生数量自增1
        classroomService.incrStudentCount(addVO.getClassId());
        // 6.修改小程序用户登录缓存
        this.handlerMinLoginUserCache(
                Collections.emptySet(),
                guardianList.stream().map(Guardian::getPhone).collect(Collectors.toList()),
                data
        );
    }

    /**
     * 删除
     *
     * @param idVO Id入参
     */
    @Override
    public void deleteById(StudentIdVO idVO) {
        // 1.查询学生信息
        Student student = this.getAndVerifyStudent(idVO.getId());
        // 2.判断学生是否是离校状态
        if (!student.getIsLeaveSchool()) {
            throw new ServiceException(ErrorEnums.ON_SCHOOL_NOT_DELETE);
        }
        // 3.逻辑删除学生
        Student update = new Student();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Student>().lambda()
                        .eq(Student::getId, idVO.getId())
                        .set(Student::getDelFlag, Constants.TRUE)
        );
        // 4.逻辑删除学生绑定的监护人
        guardianService.deleteByStudentId(idVO.getId());
        // TODO 删除学生可能存在的其他操作
    }

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    @Override
    public StudentDetailsBO getDetailsById(Long id) {
        // 1.根据id查询学生的基本信息
        Student student = this.getAndVerifyStudent(id);
        if (SecurityUtils.getLoginIdentity() != LoginIdentity.KG_ADMIN) {
            Classroom classroom = remoteClassroomService.getStaffBindClassroom(SecurityUtils.getUser().getAssociationId());
            if (classroom != null && !Objects.equals(classroom.getId(), student.getClassId())) {
                throw new ServiceException(ErrorEnums.STUDENT_NOT_EXIST);
            }
        }
        // 2.查询学生对应的监护人
        List<Guardian> guardians = guardianService.getByStudentId(id);
        if (guardians.size() < 2) {
            guardians.add(new Guardian());
        }
        // 3.查询班级和对应的班主任
        ClassroomSimpleBO classroomSimpleBO = classroomService.getClassroomSimpleById(student.getClassId());
        // 4.封装返回值
        StudentDetailsBO detailsBO = new StudentDetailsBO();
        BeanUtils.copyBeanProp(detailsBO, student);
        detailsBO.setAge(this.getAge(student.getBirthdate()));
        detailsBO.setClassName(classroomSimpleBO.getClassName());
        detailsBO.setTeacherName(classroomSimpleBO.getTeacherName());
        detailsBO.setGuardians(guardians.stream().map(item -> {
            GuardianBO bo = new GuardianBO();
            BeanUtils.copyBeanProp(bo, item);
            return bo;
        }).collect(Collectors.toList()));
        return detailsBO;
    }

    

    /**
     * 学生离校
     *
     * @param idVO Id入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void leave(StudentIdVO idVO) {
        // 1.查询学生信息
        Student student = this.getAndVerifyStudent(idVO.getId());
        // 2.判断学生是否已经是离校状态
        if (student.getIsLeaveSchool()) {
            throw new ServiceException(ErrorEnums.STUDENT_YET_LEVEL);
        }
        // 3.将学生修改为离校
        Student update = new Student();
        update.setId(idVO.getId());
        update.initUpdateProp();
        update.setIsLeaveSchool(Constants.TRUE);
        super.updateById(update);
        // 4.校区的学生数量自减1
        kindergartenService.decrStudentCount(student.getKgId());
        // 5.班级的学生数量自减1
        classroomService.decrStudentCount(student.getClassId());
        // 6.操作小程序用户登录缓存
        List<Guardian> guardians = guardianService.getByStudentId(student.getId());
        Set<String> phones = guardians.stream().map(Guardian::getPhone).collect(Collectors.toSet());
        this.handlerMinLoginUserCache(phones, Lists.newArrayList(), student);
    }

    /**
     * 查询历史学生档案
     *
     * @param queryVO 查询入参
     * @return 结果
     */
    @Override
    public IPage<StudentHistoryBO> listHistoryPage(StudentQueryHistoryVO queryVO) {
        IPage<StudentHistoryBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        page = baseMapper.listHistoryPage(page, queryVO);
        page.getRecords().forEach(item -> {
            item.setAge(this.getAge(item.getBirthdate()));
            if (StringUtils.isNotEmpty(item.getGuardianName())) {
                item.setGuardianName(item.getGuardianName().split(",")[Constants.ZERO]);
            }
            if (StringUtils.isNotEmpty(item.getGuardianPhone())) {
                item.setGuardianPhone(item.getGuardianPhone().split(",")[Constants.ZERO]);
            }
        });
        return page;
    }

    /**
     * 学生返校重读
     *
     * @param backToSchoolVO 入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backToSchool(StudentBackToSchoolVO backToSchoolVO) {
        // 1.校验传入的学生id是否合法
        SysUser login = SecurityUtils.getUser();
        int count = super.count(
                new QueryWrapper<Student>().lambda()
                        .eq(Student::getKgId, login.getKgId())
                        .eq(Student::getIsLeaveSchool, Constants.TRUE)
                        .in(Student::getId, backToSchoolVO.getStudentIds())
        );
        if (count != backToSchoolVO.getStudentIds().size()) {
            throw new ServiceException(ErrorEnums.STUDENT_ILLEGALITY);
        }
        // 2.校验班级
        classroomService.getAndVerifyClassroom(backToSchoolVO.getClassId());
        // 3.学生返校
        super.update(
                new UpdateWrapper<Student>().lambda()
                        .in(Student::getId, backToSchoolVO.getStudentIds())
                        .set(Student::getIsLeaveSchool, Constants.FALSE)
                        .set(Student::getClassId, backToSchoolVO.getClassId())
                        .set(Student::getUpdateBy, login.getUserId())
                        .set(Student::getUpdateTime, LocalDateTime.now())
        );
        // 4.班级增加学生数量
        classroomService.incrStudentCount(backToSchoolVO.getClassId(), backToSchoolVO.getStudentIds().size());
        // 5.学校增加学生数量
        kindergartenService.incrStaffCount(login.getKgId(), backToSchoolVO.getStudentIds().size());
        List<Student> students = super.listByIds(backToSchoolVO.getStudentIds());
        for (Student student : students) {
            // 6.操作小程序用户登录缓存
            List<Guardian> guardians = guardianService.getByStudentId(student.getId());
            List<String> phones = guardians.stream().map(Guardian::getPhone).collect(Collectors.toList());
            this.handlerMinLoginUserCache(Collections.emptySet(), phones, student);
        }
    }

    /**
     * 获取导出数据
     *
     * @param exportVO 导出入参
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> export(StudentExportVO exportVO) {
        // 1.查询数据
        List<LinkedHashMap<String, Object>> rows = baseMapper.selectExportList(exportVO);
        if (exportVO.getAge()) {
            // 2.处理学生年龄
            for (LinkedHashMap<String, Object> row : rows) {
                LocalDate date = LocalDate.parse((String) row.get("出生日期"));
                row.put("年龄", this.getAge(date));
                if (!exportVO.getBirthdate()) {
                    row.remove("出生日期");
                }
            }
        }
        return rows;
    }

    /**
     * 批量添加学生
     *
     * @param list 数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<StudentExcelDTO> list) {
        // 1.获取所有的班级名称
        Set<String> classNameSet = list.stream().map(StudentExcelDTO::getClassName).collect(Collectors.toSet());
        // 2.根据班级名称查询当前用户对应校区的班级
        List<Classroom> classrooms = classroomService.selectByClassNames(classNameSet);
        // 3.判断是否查询到了班级
        if (CollectionUtils.isEmpty(classrooms)) {
            throw new ServiceException(ErrorEnums.CLASS_NOT_EXIST);
        }
        // 4.判断excel中填写的所有班级, 是否都能在数据库中查到
        Map<String, Classroom> map = new HashMap<>(classNameSet.size());
        for (Classroom classroom : classrooms) {
            if (!map.containsKey(classroom.getClassName())) {
                map.put(classroom.getClassName(), classroom);
            }
        }
        if (map.size() != classNameSet.size()) {
            throw new ServiceException(ErrorEnums.CLASS_NOT_EXIST);
        }
        // 5.转换对象
        List<StudentAddVO> voList = list.stream().map(item -> {
            StudentAddVO vo = new StudentAddVO();
            BeanUtils.copyBeanProp(vo, item);
            // 1.处理boolean类型的属性
            vo.setIsWeak(this.toBoolean(item.getIsWeak()));
            vo.setIsLeft(this.toBoolean(item.getIsLeft()));
            vo.setIsWorkers(this.toBoolean(item.getIsWorkers()));
            vo.setIsDisability(this.toBoolean(item.getIsDisability()));
            vo.setIsOnlyChild(this.toBoolean(item.getIsOnlyChild()));
            vo.setIsOrphan(this.toBoolean(item.getIsOrphan()));
            vo.setGuardians(new ArrayList<>());
            // 2.处理监护人1
            GuardianAddVO guardianAddVO = new GuardianAddVO();
            vo.getGuardians().add(guardianAddVO);
            guardianAddVO.setName(item.getGuardianNameOne());
            guardianAddVO.setCardNumber(item.getGuardianCardNumberOne());
            guardianAddVO.setCardType(item.getGuardianCardTypeOne());
            guardianAddVO.setPhone(item.getGuardianPhoneOne());
            // 3.处理监护人2
            guardianAddVO = new GuardianAddVO();
            vo.getGuardians().add(guardianAddVO);
            guardianAddVO.setName(item.getGuardianNameTwo());
            guardianAddVO.setCardNumber(item.getGuardianCardNumberTwo());
            guardianAddVO.setCardType(item.getGuardianCardTypeTwo());
            guardianAddVO.setPhone(item.getGuardianPhoneTwo());
            // 4.处理性别
            vo.setGender(UserSex.values()[item.getGender()]);
            // 5.处理班级
            vo.setClassId(map.get(item.getClassName()).getId());
            // 6.Date转LocalDate
            vo.setBirthdate(item.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            vo.setEnrollDate(item.getEnrollDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            return vo;
        }).collect(Collectors.toList());
        // 6.添加到数据库中
        for (StudentAddVO addVO : voList) {
            this.insert(addVO);
        }
    }

    /**
     * 获取学生人脸
     *
     * @param id id
     * @return 结果
     */
    @Override
    public StudentFaceBO getStudentFace(Long id) {
        // 1.获取学生并校验
        Student student = this.getAndVerifyStudent(id);
        // 2.封装返回值
        StudentFaceBO bo = new StudentFaceBO();
        BeanUtils.copyBeanProp(bo, student);
        return bo;
    }

    /**
     * 删除学生人脸
     *
     * @param id id
     */
    @Override
    public void deleteStudentFace(Long id) {
        Student student = this.getAndVerifyStudent(id);
        if (SecurityUtils.getLoginIdentity() != LoginIdentity.KG_ADMIN) {
            Classroom classroom = remoteClassroomService.getStaffBindClassroom(SecurityUtils.getUser().getAssociationId());
            if (classroom != null && !Objects.equals(classroom.getId(), student.getClassId())) {
                throw new ServiceException(ErrorEnums.STUDENT_NOT_EXIST);
            }
        }
        Student update = new Student();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Student>().lambda()
                        .eq(Student::getKgId, SecurityUtils.getUserKgId())
                        .set(Student::getRealImgUrl, Constants.EMPTY_STRING)
        );
    }

    /**
     * 学生换班
     *
     * @param newClassId 新班级
     * @param oldClassId 旧班级
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeClass(Long newClassId, Long oldClassId) {
        // 1.查询旧班级的所有学生
        List<Student> list = super.list(
                new QueryWrapper<Student>().lambda()
                        .eq(Student::getClassId, oldClassId)
                        .eq(Student::getIsLeaveSchool, Constants.FALSE)
        );
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 2.将旧班级的所有学生移到新班级
        Student update = new Student();
        update.initUpdateProp();
        super.update(update,
                new UpdateWrapper<Student>().lambda()
                        .in(Student::getId, list.stream().map(Student::getId).collect(Collectors.toList()))
        );
        // 3.修改旧班级学生数量
        Classroom updateClass = new Classroom();
        updateClass.initUpdateProp();
        updateClass.setId(oldClassId);
        updateClass.setStudentCount(Constants.ZERO);
        classroomService.updateById(updateClass);
        // 4.修改新班级学生数量
        updateClass = new Classroom();
        updateClass.initUpdateProp();
        classroomService.incrStudentCount(newClassId, list.size());
    }

    /**
     * 获取并校验学生
     *
     * @param studentId 学生Id
     * @return 学生
     */
    @Override
    public Student getAndVerifyStudent(Long studentId) {
        // 1.查询学生信息
        Student student = super.getById(studentId);
        if (student == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 2.判断登录用户是否能操作这个学生
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(student.getKgId(), login.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return student;
    }

    /**
     * 校验学生编号集合是否都指定校区下的学生和是否离校
     *
     * @param uNos 登录学生no集合
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<Student> getAndVerifyStudent(Set<String> uNos, Long kgId) {
        return super.list(
                new QueryWrapper<Student>().lambda()
                        .in(Student::getStudentNo, uNos)
                        .eq(Student::getIsLeaveSchool, Constants.FALSE)
                        .eq(Student::getKgId, kgId)
        );
    }

    /**
     * 获取并校验学生是否存在, 登录用户是否能查询或操作学生, 学生是否在校;
     *
     * @param studentId 学生Id
     * @return 学生
     */
    @Override
    public Student getAndVerifyStudentInSchool(Long studentId) {
        // 1.校验学生
        Student student = this.getAndVerifyStudent(studentId);
        if (student.getIsLeaveSchool()) {
            throw new ServiceException(ErrorEnums.STUDENT_YET_LEVEL);
        }
        return student;
    }

    

    @Override
    public boolean verifyStudentInSchoolByIds(Collection<Long> studentIds) {
        int count = super.count(
                new QueryWrapper<Student>().lambda()
                        .in(Student::getId, studentIds)
                        .eq(Student::getKgId, SecurityUtils.getUserKgId())
                        .eq(Student::getIsLeaveSchool, Constants.FALSE)
        );
        return count == studentIds.size();
    }

    /**
     * 班级毕业
     *
     * @param classId 班级id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void classGraduate(Long classId) {
        // 1.设置学生毕业
        Student updateStudent = new Student();
        updateStudent.initUpdateProp();
        updateStudent.setIsLeaveSchool(Constants.TRUE);
        super.update(
                updateStudent,
                new UpdateWrapper<Student>().lambda()
                        .eq(Student::getClassId, classId)
        );
        // 2.修改旧班级学生数量为零
        Classroom updateClass = new Classroom();
        updateClass.setId(classId);
        updateClass.initUpdateProp();
        updateClass.setStudentCount(Constants.ZERO);
        classroomService.updateById(updateClass);
    }

    

    

    /**
     * 获取班级学生人数 -> 包含删除和离校学生
     *
     * @param classId 班级id
     * @param kgId    校区id
     * @return 结果
     */
    @Override
    public Long allStudentCount(Long classId, Long kgId) {
        return baseMapper.getAllStudentCount(classId, kgId);
    }

    /**
     * 获取指定班级的在读学生数量
     *
     * @param classId 班级id
     * @return 结果
     */
    @Override
    public int selectLiveStudentCountByClassId(Long classId) {
        return super.count(
                new QueryWrapper<Student>().lambda()
                        .eq(Student::getClassId, classId)
                        .eq(Student::getIsLeaveSchool, Constants.FALSE)
        );
    }

    /**
     * 获取校区学生数量
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<Student> studentCountByKgId(Long kgId) {
        return this.list(new QueryWrapper<Student>().lambda()
                .eq(Student::getDelFlag, 0)
                .eq(Student::getIsLeaveSchool, 0)
                .eq(Student::getKgId, kgId));
    }

    /**
     * 获取学生id
     *
     * @param studentNames 学生姓名集合
     * @param kgId         校区id
     * @return 结果
     */
    @Override
    public Map<String, Long> getByStudentName(Set<String> studentNames, Long kgId) {
        List<Student> list = super.list(
                new QueryWrapper<Student>().lambda()
                        .in(Student::getName, studentNames)
                        .eq(Student::getKgId, kgId)
                        .select(Student::getId, Student::getName)
        );
        return list.stream().collect(Collectors.toMap(
                Student::getName,
                Student::getId
        ));
    }

    /**
     * 获取校区所有的学生
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<Student> getLiveStudentAllByKgId(Long kgId) {
        return super.list(
                new QueryWrapper<Student>().lambda()
                        .eq(Student::getKgId, kgId)
                        .eq(Student::getIsLeaveSchool, Constants.FALSE)
        );
    }

    /**
     * 学生id是否有效
     *
     * @param studentIds 学生ids
     * @param kgId       校区id
     */
    @Override
    public void getVerifyCountByIds(Set<Long> studentIds, Long kgId) {
        //获取学生数量
        int count = this.count(new QueryWrapper<Student>().lambda()
                .eq(Student::getKgId, kgId)
                .eq(Student::getIsLeaveSchool, 0)
                .eq(Student::getDelFlag, 0).in(Student::getId, studentIds));
        //学生是否存在
        if (count == 0 && !Objects.equals(count, studentIds.size())) {
            throw new ServiceException(ErrorEnums.STUDENT_NOT_EXIST);
        }

    }

    /**
     * 根据出生日期返回年龄; 格式:y岁m月
     *
     * @param birthdate 出生日期
     * @return 年龄
     */
    public String getAge(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        long y = ChronoUnit.YEARS.between(birthdate, now);
        long m = ChronoUnit.MONTHS.between(birthdate, now) % 12;
        return String.format("%d岁%d个月", y, m);
    }

    /**
     * 转boolean类型
     */
    public Boolean toBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return BooleanUtils.toBoolean(value);
    }
}
