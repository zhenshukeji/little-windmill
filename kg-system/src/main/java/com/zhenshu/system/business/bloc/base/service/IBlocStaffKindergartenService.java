package com.zhenshu.system.business.bloc.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffKindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.BlocStaffKindergartenEditVO;
import com.zhenshu.system.business.bloc.tokg.domain.bo.GoToKindergartenBO;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc service
 */
public interface IBlocStaffKindergartenService extends IService<BlocStaffKindergarten> {
    /**
     * 根据Id查询
     *
     * @param userId 登录用户Id
     * @return 结果
     */
    BlocStaffKindergartenDetailsBO getDetailsById(Long userId);

    /**
     * 修改集团员工进入校区的权限
     *
     * @param editVO 入参
     */
    void updateGotoKg(BlocStaffKindergartenEditVO editVO);

    /**
     * 获取登录用户可进入的校区
     *
     * @return 结果
     */
    List<GoToKindergartenBO> getUserGotoKgList();

    /**
     * 检查用户是否能进入校区
     *
     * @param userId 用户id
     * @param kgId   校区id
     * @return 结果
     */
    boolean checkUserToKg(Long userId, Long kgId);

    /**
     * 根据集团人员id查询集团人员进入校区的权限信息
     *
     * @param blocStaffId 集团人员id
     * @return 结果
     */
    List<BlocStaffKindergarten> getByBlocStaffId(Long blocStaffId);

    /**
     * 根据集团员工id和校区id获取数据
     *
     * @param staffId 集团员工id
     * @param kgId    校区id
     * @return 结果
     */
    BlocStaffKindergarten getByBlocStaffIdAndKgId(Long staffId, Long kgId);

    /**
     * 删除指定集团员工可进入的校区权限
     *
     * @param staffId 集团员工
     */
    void deleteByBlocStaffId(Long staffId);
}
