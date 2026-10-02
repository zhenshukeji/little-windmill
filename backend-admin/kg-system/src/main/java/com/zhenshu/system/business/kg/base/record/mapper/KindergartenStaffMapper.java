package com.zhenshu.system.business.kg.base.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.TeacherBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.TeacherQueryVO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenTeacherBO;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenStaffExportVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenStaffQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-15
 * @desc 学校员工 Mapper接口
 */
public interface KindergartenStaffMapper extends BaseMapper<KindergartenStaff> {
    /**
     * 列表查询校区员工
     *
     * @param page    分页对象
     * @param queryVO 查询对象
     * @return 结果
     */
    List<KindergartenStaffBO> detailsListPage(@Param("page") IPage<KindergartenStaffBO> page, @Param("queryVO") KindergartenStaffQueryVO queryVO);

    /**
     * 根据Id查询校区员工的详情
     *
     * @param staffId 校区员工Id
     * @return 结果
     */
    KindergartenStaffDetailsBO getDetailsById(Long staffId);

    /**
     * 列表查询可以进入校区的集团员工
     *
     * @param page    分页对象
     * @param queryVO 查询对象
     * @return 结果
     */
    List<KindergartenStaffBO> detailsBlocStaffListPage(@Param("page") IPage<KindergartenStaffBO> page, @Param("queryVO") KindergartenStaffQueryVO queryVO);

    /**
     * 查询导出数据
     *
     * @param exportVO 入参
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectExportList(@Param("exportVO") KindergartenStaffExportVO exportVO);

    /**
     * 查询可绑定班级的老师
     *
     * @param page    分页对象
     * @param queryVO 入参
     * @return 结果
     */
    IPage<TeacherBO> selectedTeacherListPage(@Param("page") IPage<TeacherBO> page, @Param("queryVO") TeacherQueryVO queryVO);

    /**
     * 通过校区id查询角色为教师的员工id
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<KindergartenTeacherBO> getTeacherIds(@Param("kgId") Long kgId);
}
