package com.zhenshu.system.business.kg.base.record.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.kg.base.record.domain.bo.*;
import com.zhenshu.system.business.kg.base.record.domain.dto.StudentExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.po.Student;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;

import java.util.*;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc service
 */
public interface IStudentService extends IService<Student> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<StudentBO> listPage(StudentQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(StudentEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(StudentAddVO addVO);

    /**
     * 根据id删除
     *
     * @param idVO Id入参
     */
    void deleteById(StudentIdVO idVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    StudentDetailsBO getDetailsById(Long id);

    

    

    /**
     * 学生离校
     *
     * @param idVO Id入参
     */
    void leave(StudentIdVO idVO);

    /**
     * 查询历史学生档案
     *
     * @param queryVO 查询入参
     * @return 结果
     */
    IPage<StudentHistoryBO> listHistoryPage(StudentQueryHistoryVO queryVO);

    /**
     * 学生返校重读
     *
     * @param backToSchoolVO 入参
     */
    void backToSchool(StudentBackToSchoolVO backToSchoolVO);

    /**
     * 获取导出数据
     *
     * @param exportVO 导出入参
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> export(StudentExportVO exportVO);

    /**
     * 批量添加学生
     *
     * @param list 数据
     */
    void addBatch(List<StudentExcelDTO> list);

    /**
     * 获取学生人脸
     *
     * @param id id
     * @return 结果
     */
    StudentFaceBO getStudentFace(Long id);

    /**
     * 删除学生人脸
     *
     * @param id id
     */
    void deleteStudentFace(Long id);

    /**
     * 学生换班
     *
     * @param newClassId 新班级
     * @param oldClassId 旧班级
     */
    void changeClass(Long newClassId, Long oldClassId);

    /**
     * 获取并校验学生
     *
     * @param studentId 学生Id
     * @return 学生
     */
    Student getAndVerifyStudent(Long studentId);

    /**
     * 获取并校验学生
     *
     * @param studentId 学生Id
     * @param kgId 校区id
     * @return 学生
     */
    List<Student> getAndVerifyStudent(Set<String> studentId, Long kgId);

    /**
     * 获取并校验学生是否存在, 登录用户是否能查询或操作学生, 学生是否在校;
     *
     * @param studentId 学生Id
     * @return 学生
     */
    Student getAndVerifyStudentInSchool(Long studentId);

    

    /**
     * 校验学生是否全部存在, 且未离校
     *
     * @param studentIds 学生id
     * @return 结果
     */
    boolean verifyStudentInSchoolByIds(Collection<Long> studentIds);

    /**
     * 班级毕业
     *
     * @param classId 班级id
     */
    void classGraduate(Long classId);

    

    

    

    /**
     * 获取班级所有学生人数 -> 包含删除和离校的
     *
     * @param classId 班级id
     * @param kgId    校区id
     * @return 结果
     */
    Long allStudentCount(Long classId, Long kgId);

    /**
     * 学生id获取学生数量
     *
     * @param studentIds 学生ids
     * @param kgId 校区id
     * @return 结果
     */
    void getVerifyCountByIds(Set<Long> studentIds,Long kgId);

    /**
     * 获取指定班级的在读学生数量
     *
     * @param classId 班级id
     * @return 结果
     */
    int selectLiveStudentCountByClassId(Long classId);

    /**
     * 获取园区所有学生数量
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<Student> studentCountByKgId(Long kgId);

    

    

    /**
     * 获取学生id
     *
     * @param studentNames 学生姓名集合
     * @param kgId         校区id
     * @return 结果
     */
    Map<String, Long> getByStudentName(Set<String> studentNames, Long kgId);

    /**
     * 获取校区所有的学生
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<Student> getLiveStudentAllByKgId(Long kgId);
}
