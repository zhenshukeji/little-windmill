package com.zhenshu.system.business.bloc.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenBO;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.bo.NameInfoBO;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenAddVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenDeleteVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenEditVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenQueryVO;
import com.zhenshu.system.business.bloc.finance.domain.bo.KindergartenSimpleBO;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenResetPasswordVO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;

import java.util.Collection;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc
 */
public interface IKindergartenService extends IService<Kindergarten> {
    /**
     * 列表查询
     *
     * @param kindergartenQueryVO 列表查询入参
     * @return
     */
    IPage<KindergartenBO> listPage(KindergartenQueryVO kindergartenQueryVO);

    /**
     * 根据Id修改
     *
     * @param kindergartenEditVO 修改入参
     */
    void updateById(KindergartenEditVO kindergartenEditVO);

    /**
     * 添加
     *
     * @param kindergartenAddVO 添加入参
     */
    void insert(KindergartenAddVO kindergartenAddVO);

    /**
     * 根据id删除
     *
     * @param kindergartenDeleteVO 删除入参
     */
    void deleteById(KindergartenDeleteVO kindergartenDeleteVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return
     */
    KindergartenDetailsBO getDetailsById(Long id);

    /**
     * 查询指定集团下的校区数量
     *
     * @param blocId 集团Id
     * @return 校区数量
     */
    int selectKgCountByBlocId(Long blocId);

    /**
     * 重置密码
     *
     * @param restPwdVO 重置密码入参
     * @return 结果
     */
    void resetPassword(KindergartenResetPasswordVO restPwdVO);

    /**
     * 获取指定集团下的所有校区
     *
     * @param blocId 集团Id
     * @return 结果
     */
    List<Kindergarten> getListByBlocId(Long blocId);

    /**
     * 校验校区Id是否合法
     *
     * @param kgIds 校区id集合
     * @return 结果
     */
    boolean verifyKgIds(Collection<Long> kgIds);

    /**
     * 批量查询校区
     *
     * @param ids 校区Id结婚
     * @return 结果
     */
    List<Kindergarten> getByIds(List<Long> ids);

    /**
     * 自增学校员工数量
     *
     * @param kgId 学校id
     */
    void incrStaffCount(Long kgId);

    /**
     * 自减学校员工数量
     *
     * @param kgId 学校id
     */
    void decrStaffCount(Long kgId);

    /**
     * 查出名称
     * @param kgId 校区id
     * @return 名称
     */
    NameInfoBO getKgNameAndBlocName(Long kgId);

    /**
     * 自增学校学生数量
     *
     * @param kgId 学校id
     */
    void incrStudentCount(Long kgId);

    /**
     * 自增学校学生数量
     *
     * @param kgId 学校id
     */
    void incrStaffCount(Long kgId, int size);

    /**
     * 自减学校学生数量
     *
     * @param kgId 学校id
     */
    void decrStudentCount(Long kgId);

    /**
     * 获取指定集团下的所有校区
     *
     * @param blocId 集团id
     * @return 结果
     */
    List<KindergartenSimpleBO> getListAllByBlocId(Long blocId);

    /**
     * 获取园长userid和username
     *
     * @param kgId 校区id
     * @return 结果
     */
    PostRoleNameBO getUidByKgId(Long kgId);

    /**
     * 获取园长userid和username
     *
     * @param kgId 校区id集合
     * @return 结果
     */
    List<Long> getUidByKgId(List<Long> kgId);
}
