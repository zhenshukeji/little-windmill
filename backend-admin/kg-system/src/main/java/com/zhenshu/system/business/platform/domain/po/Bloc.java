package com.zhenshu.system.business.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_bloc")
public class Bloc extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 集团id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团名称
     */
    private String blocName;

    /**
     * 负责人姓名
     */
    private String principalName;

    /**
     * 负责人联系电话
     */
    private String principalPhone;

    /**
     * 总部地址
     */
    private String address;

    /**
     * 校区数量
     */
    private Integer kgCount;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 失效时间
     */
    private LocalDateTime failureTime;

    /**
     * 备注
     */
    private String remark;

}
