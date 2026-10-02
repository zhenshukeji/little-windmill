package com.zhenshu.system.business.kg.base.record.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.TeacherBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.TeacherQueryVO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffSimpleBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenTeacherBO;
import com.zhenshu.system.business.kg.base.record.domain.dto.KgStaffExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc service
 */
public interface IKindergartenStaffService extends IService<KindergartenStaff> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<KindergartenStaffBO> listPage(KindergartenStaffQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(KindergartenStaffEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(KindergartenStaffAddVO addVO);

    /**
     * 学校员工离职
     *
     * @param quitVO 离职入参
     */
    void quit(KindergartenStaffQuitVO quitVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    KindergartenStaffDetailsBO getDetailsById(Long id);

    /**
     * 根据校区ID获取集团管理员
     *
     * @param kgId 校区ID
     * @return 集团管理员信息
     */
    KindergartenStaff selectAdminByBlocId(Long kgId);

    /**
     * 查询指定校区下的在职员工数量, 除了管理员
     *
     * @param kgId 校区Id
     * @return 指定校区下的在职员工数量
     */
    int selectLiveStaffCountByKgId(Long kgId);

    /**
     * 删除指定校区下的管理员信息
     *
     * @param kgId 校区Id
     * @return
     */
    boolean deleteAdminByKgId(Long kgId);

    /**
     * 删除学校员工
     *
     * @param idVO 校区员工id入参
     */
    void deleteById(KindergartenStaffIdVO idVO);

    /**
     * 校区员工重新入职
     *
     * @param idVO 校区员工id入参
     */
    void entry(KindergartenStaffIdVO idVO);

    /**
     * 进入校区的集团人员分配岗位
     *
     * @param postVO 入参
     */
    void post(BlocStaffPostVO postVO);

    /**
     * 获取导出数据
     *
     * @param exportVO 导出入参
     */
    List<LinkedHashMap<String, Object>> export(KindergartenStaffExportVO exportVO);

    /**
     * 根据Id查询可进入校区的集团员工
     *
     * @param id id
     * @return 结果
     */
    KindergartenStaffDetailsBO getBlocStaffById(Long id);

    /**
     * 批量添加校区员工
     *
     * @param list 数据
     */
    void addBatch(List<KgStaffExcelDTO> list);

    /**
     * 重置用户密码
     *
     * @param userIdVO 入参
     * @return 新密码
     */
    String resetPassword(UserIdVO userIdVO);

    /**
     * 查询并校验当前用户是否能查看校区员工
     *
     * @param staffId 员工id
     * @return 结果
     */
    KindergartenStaff getAndVerifyKgStaff(Long staffId);

    /**
     * 校验校区员工是否都属于kgId; 并且都未离职
     *
     * @param staffIds 校区员工Id集合
     * @param kgId     校区Id
     * @return 结果
     */
    Boolean verifyKgStaffByIds(Collection<Long> staffIds, Long kgId);

    /**
     * 根据编号和校区id获取未离职的非管理员校区员工
     *
     * @param staffNumbers 校区员工编号集合
     * @param kgId         校区Id
     * @return 结果
     */
    List<KindergartenStaff> getKgStaffByNumbers(Collection<String> staffNumbers, Long kgId);

    /**
     * 查询可绑定班级的老师
     *
     * @param queryVO 入参
     * @return 结果
     */
    IPage<TeacherBO> selectedTeacherListPage(TeacherQueryVO queryVO);

    /**
     * 查询所有在职非管理员的员工
     *
     * @return 结果
     */
    List<KindergartenStaffSimpleBO> listAll();

    /**
     * 校验登录用户id集合是否都指定校区下的员工
     *
     * @param uids 登录用户id集合
     * @param kgId 校区id
     * @return 结果
     */
    Boolean verifyKgStaffByUids(List<Long> uids, Long kgId);

    /**
     * 根据用户登录id获取并校验
     *
     * @param uid 用户登录id
     * @return 结果
     */
    KindergartenStaff getAndVerifyKgStaffByUid(Long uid);

    /**
     * 判断员工是否为老师
     *
     * @param user 登录用户对象
     * @return 结果
     */
    boolean isTeacher(SysUser user);

    /**
     * 根据指定园区id获取教师角色员工id
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<KindergartenTeacherBO> getTeacherIds(Long kgId);

    /**
     * 获取校区下所有在职员工
     *
     * @param kgId 校区id
     * @return 结果
     */
    List<KindergartenStaff> getLiveStaffAllByKgId(Long kgId);
}
