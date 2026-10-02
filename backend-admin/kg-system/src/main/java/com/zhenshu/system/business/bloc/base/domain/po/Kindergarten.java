package com.zhenshu.system.business.bloc.base.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_kindergarten")
public class Kindergarten extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校名称
     */
    private String kindergartenName;

    /**
     * 负责人姓名
     */
    private String principalName;

    /**
     * 负责人联系电话
     */
    private String principalPhone;

    /**
     * 学校地址
     */
    private String kindergartenAddress;

    /**
     * 员工数
     */
    private Integer staffCount;

    /**
     * 学生数
     */
    private Integer studentCount;

    /**
     * 备注
     */
    private String remark;

}
