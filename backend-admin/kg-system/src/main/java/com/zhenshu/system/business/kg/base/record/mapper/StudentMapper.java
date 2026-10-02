package com.zhenshu.system.business.kg.base.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.record.domain.bo.StudentBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.StudentHistoryBO;
import com.zhenshu.system.business.kg.base.record.domain.po.Student;
import com.zhenshu.system.business.kg.base.record.domain.vo.StudentExportVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.StudentQueryHistoryVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.StudentQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 学生Mapper接口
 */
public interface StudentMapper extends BaseMapper<Student> {

    

    /**
     * 列表查询
     *
     * @param page    分页对象
     * @param queryVO 入参VO
     * @return 结果
     */
    List<StudentBO> detailsListPage(@Param("page") IPage<StudentBO> page, @Param("queryVO") StudentQueryVO queryVO);

    /**
     * 列表查询历史学生
     *
     * @param page    分页对象
     * @param queryVO 查询VO
     * @return 结果
     */
    IPage<StudentHistoryBO> listHistoryPage(@Param("page") IPage<StudentHistoryBO> page, @Param("queryVO") StudentQueryHistoryVO queryVO);

    /**
     * 获取导出数据
     *
     * @param exportVO 导出入参
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectExportList(@Param("exportVO") StudentExportVO exportVO);

    

    

    

    /**
     * 获取班级学生人数 -> 包含删除和离校
     *
     * @param classId 班级id
     * @param kgId    校区id
     * @return 结果
     */
    Long getAllStudentCount(@Param("classId") Long classId, @Param("kgId") Long kgId);
}
