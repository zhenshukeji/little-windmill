package com.zhenshu.system.business.bloc.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffBO;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.domain.vo.*;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc
 */
public interface IBlocStaffService extends IService<BlocStaff> {
    /**
     * 列表查询
     *
     * @param blocStaffQueryVO 列表查询入参
     * @return
     */
    IPage<BlocStaffBO> listPage(BlocStaffQueryVO blocStaffQueryVO);

    /**
     * 根据Id修改
     *
     * @param blocStaffEditVO 修改入参
     */
    void updateById(BlocStaffEditVO blocStaffEditVO);

    /**
     * 添加
     *
     * @param blocStaffAddVO 添加入参
     */
    void insert(BlocStaffAddVO blocStaffAddVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return
     */
    BlocStaffDetailsBO getDetailsById(Long id);

    /**
     * 根据Id查询集团员工
     *
     * @param id        id
     * @param queryPost 是否查询岗位
     * @return 详情
     */
    BlocStaffDetailsBO getDetailsById(Long id, boolean queryPost);

    /**
     * 根据集团ID修改集团管理员的信息
     *  @param blocStaff 实体
     * @param blocId 集团ID
     * @return
     */
    boolean updateAdminByBlocId(BlocStaff blocStaff, Long blocId);

    /**
     * 根据集团ID获取集团管理员
     *
     * @param blocId 集团ID
     * @return 集团管理员信息
     */
    BlocStaff selectAdminByBlocId(Long blocId);

    /**
     * 查询指定集团下的在职员工数量; 除了管理员
     *
     * @param blocId 集团Id
     * @return 除了管理员的在职员工数量
     */
    int selectLiveStaffCountByBlocId(Long blocId);

    /**
     * 删除指定集团下的管理员信息
     *
     * @param blocId 集团Id
     */
    void deleteAdminByBlocId(Long blocId);

    /**
     * 集团员工离职
     *
     * @param blocStaffQuitVO 集团员工离职入参
     */
    void quit(BlocStaffQuitVO blocStaffQuitVO);

    /**
     * 重置用户密码
     *
     * @param userIdVO 入参
     * @return 新密码
     */
    String resetPassword(UserIdVO userIdVO);

    /**
     * 根据登录用户Id查询集团员工
     *
     * @param userId 登录用户id
     * @return 结果
     */
    BlocStaff selectByUserId(Long userId);

    /**
     * 删除集团员工
     *
     * @param idVO id入参
     */
    void deleteById(BlocStaffIdVO idVO);

    /**
     * 集团员工重新入参
     *
     * @param idVO id入参
     */
    void entry(BlocStaffIdVO idVO);

    /**
     * 根据登录用户id查询集团员工
     *
     * @param userId 登录用户id
     * @return 结果
     */
    BlocStaff getByUid(Long userId);
}
