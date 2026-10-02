package com.zhenshu.system.business.kg.work.educational.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.system.business.kg.work.educational.domain.bo.ClassCircleBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.ClassCircle;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc ClassCircleMapper接口
 */
public interface ClassCircleMapper extends BaseMapper<ClassCircle> {
    /**
     * 列表查询
     *
     * @param queryVO 查询入参(是否为管理员)
     * @return 结果
     */
    IPage<ClassCircleBO> listQuery(IPage<ClassCircleBO> page, @Param("queryVO") ClassCircleQueryVO queryVO);

    /**
     * 删除班级圈
     *
     * @param deleteVO 查询入参(是否为管理员)
     * @return
     */
    void deleteOne(@Param("deleteVO") ClassCircleDeleteVO deleteVO);
}
