package com.zhenshu.system.business.kg.base.advanced.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomDetailsBO;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomSimpleBO;
import com.zhenshu.system.business.kg.base.advanced.domain.po.Classroom;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.ClassroomQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jing
 * @version 1.0
 * @date 2022-02-16
 * @desc 班级Mapper接口
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {
    /**
     * 列表查询
     *
     * @param page    分页对象
     * @param queryVO 入参VO
     * @return 结果
     */
    List<ClassroomBO> detailsListPage(@Param("page") IPage<ClassroomBO> page, @Param("queryVO") ClassroomQueryVO queryVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    ClassroomDetailsBO getDetailsById(Long id);

    /**
     * 根据校区id查询班级
     *
     * @param kgId 校区id
     * @return 班级列表
     */
    List<ClassroomBO> getByKgId(Long kgId);

    /**
     * 查询指定校区下的所有班级
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<ClassroomSimpleBO> getClassroomAllByKgId(Long kgId);

    /**
     * 查询可进行升班的班级
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<ClassroomSimpleBO> getPromotionList(Long kgId);

    /**
     * 根据id查询班级
     *
     * @param classId 班级id
     * @return 结果
     */
    ClassroomSimpleBO getClassroomSimpleById(@Param("classId") Long classId);

    }
