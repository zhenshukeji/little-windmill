package com.zhenshu.system.business.kg.base.advanced.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Classroom;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.*;

import java.util.Collection;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc service
 */
public interface IClassroomService extends IService<Classroom> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<ClassroomBO> listPage(ClassroomQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(ClassroomEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(ClassroomAddVO addVO);

    /**
     * 根据id删除
     *
     * @param deleteVO 删除入参
     */
    void deleteById(ClassroomDeleteVO deleteVO);

    /**
     * 根据校区id 查询
     *
     * @param kgId 校区id
     * @return 班级列表
     */
    List<ClassroomBO> getByKgId(Long kgId);

    /**
     * 自增班级学生数量
     *
     * @param classId 班级id
     */
    void incrStudentCount(Long classId);

    /**
     * 自减班级学生数量
     *
     * @param classId 班级id
     */
    void decrStudentCount(Long classId);

    /**
     * 自增班级学生数量
     *
     * @param classId 班级id
     * @param num     增加的学生数量
     */
    void incrStudentCount(Long classId, Integer num);

    /**
     * 查询指定校区下的所有班级
     *
     * @return 结果
     */
    List<ClassroomSimpleBO> getClassroomAllByKgId();

    /**
     * 根据班级名称获取班级
     *
     * @param classNames 班级名称集合
     * @return 结果
     */
    List<Classroom> selectByClassNames(Collection<String> classNames);

    /**
     * 班级绑定老师
     *
     * @param bindVO 入参
     */
    void classBindTeacher(ClassBindTeacherVO bindVO);

    /**
     * 班级解绑老师
     *
     * @param relieveVO 入参
     */
    void classRelieveTeacher(ClassRelieveTeacherVO relieveVO);

    /**
     * 一键升班
     *
     * @param voList 入参
     */
    void promotion(List<ClassPromotionVO> voList);

    /**
     * 查询可进行升班的班级
     *
     * @return 结果
     */
    List<ClassroomSimpleBO> promotionList();

    /**
     * 查询所有班级
     *
     * @return 结果
     */
    List<ClassroomSimpleBO> listAll();

    /**
     * 获取并校验班级
     *
     * @param classId 班级Id
     * @return 班级
     */
    Classroom getAndVerifyClassroom(Long classId);

    /**
     * 校验班级
     *
     * @param classIds 班级Id数组
     * @return 结果
     */
    List<Classroom> getAndVerifyClassroomByIds(List<Long> classIds);

    /**
     * 查询该用户绑定的班级
     *
     * @param userId 用户id
     * @param kgId   校区id
     * @return 结果
     */
    ClassroomPartBO getPartByUser(Long userId, Long kgId);

    

    /**
     * 查询这个校区里的班级
     *
     * @param kgId 校区id
     * @return 班级信息
     */
    List<ClassroomPartBO> getPartByKgId(Long kgId);

    /**
     * 根据id查询班级
     *
     * @param classId 班级id
     * @return 结果
     */
    ClassroomSimpleBO getClassroomSimpleById(Long classId);

    /**
     * 获取员工绑定的班级数量
     *
     * @param staffId 员工id
     * @return 结果
     */
    int getStaffBindClassroomCount(Long staffId);

    /**
     * 获取员工绑定的班级
     *
     * @param staffId 员工id
     * @return 结果
     */
    Classroom getStaffBindClassroom(Long staffId);

    /**
     * 获取指定年级的班级数量
     *
     * @param gradeId 年级id
     * @return 结果
     */
    int getCountByGradeId(Long gradeId);

    /**
     * 根据班级名称获取班级
     *
     * @param classNames 班级名称集合
     * @param kgId       校区id
     * @return 结果
     */
    List<Classroom> getByClassNameAndKgId(Collection<String> classNames, Long kgId);

    /**
     * 根据班级id数组查找年级id数组
     * @param classIdList 班级id数组
     * @return 结果
     */
    List<Long> getGradeListByClassIds(List<Long> classIdList);
}
