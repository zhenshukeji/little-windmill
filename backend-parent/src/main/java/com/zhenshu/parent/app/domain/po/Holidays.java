package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-02
 * @desc 节假日表 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_holidays")
public class Holidays extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
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
    private Long kgId;

    /**
     * 开始日期
     */
    private LocalDate beginDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

}
