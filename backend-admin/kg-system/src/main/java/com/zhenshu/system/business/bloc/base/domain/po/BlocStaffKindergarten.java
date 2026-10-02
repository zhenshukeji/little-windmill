package com.zhenshu.system.business.bloc.base.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-21
 * @desc 集团员工校区权限表实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_bloc_staff_kindergarten")
public class BlocStaffKindergarten extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校id
     */
    private Long kindergartenId;

    /**
     * 集团员工id
     */
    private Long blocStaffId;

}
