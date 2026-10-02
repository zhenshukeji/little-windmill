package com.zhenshu.system.business.kg.base.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.base.record.domain.po.MiniLogin;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginQueryVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginEditVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginAddVO;
import com.zhenshu.system.business.kg.base.record.domain.vo.MiniLoginDeleteVO;
import com.zhenshu.system.business.kg.base.record.domain.bo.MiniLoginBO;

import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-07-04
 * @desc service
 */
public interface IMiniLoginService extends IService<MiniLogin> {
    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    IPage<MiniLoginBO> listPage(MiniLoginQueryVO queryVO);

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    void updateById(MiniLoginEditVO editVO);

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    void insert(MiniLoginAddVO addVO);

    /**
     * 根据id删除
     *
     * @param deleteVO 删除入参
     */
    void deleteById(MiniLoginDeleteVO deleteVO);

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    MiniLoginDetailsBO getDetailsById(Long id);

    /**
     * 条件查询
     *
     * @param queryVO 入参VO
     * @return 结果
     */
    List<MiniLoginBO> queryList(MiniLoginQueryVO queryVO);

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     * @return 结果
     */
    MiniLogin getAndVerifyMiniLogin(Long id);

    /**
     * 根据手机号查找
     * @param phones 手机号
     * @return 用户
     */
    List<MiniLogin> queryByPhoneList(List<String> phones);


}
