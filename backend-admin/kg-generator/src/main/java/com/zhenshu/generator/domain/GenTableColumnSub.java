package com.zhenshu.generator.domain;

import com.zhenshu.common.constant.GenConstants;
import com.zhenshu.common.core.domain.BaseEntity;
import com.zhenshu.generator.util.GenUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/8 9:29
 * @desc
 */
@Data
public class GenTableColumnSub extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long subColumnId;

    /**
     * 父表编号
     */
    private Long tableId;

    /**
     * 归属表编号
     */
    private String subTableName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    @NotBlank(message = "Java属性不能为空")
    private String javaField;

    /**
     * 是否主键（1是）
     */
    private String isPk;

    /**
     * 是否自增（1是）
     */
    private String isIncrement;

    /**
     * 是否必填（1是）
     */
    private String isRequired;

    /**
     * 是否列表字段（1是）
     */
    private String isList;

    /**
     * 是否查询字段（1是）
     */
    private String isQuery;

    private String isDetails;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 获取列长度, 只有String类型的字段会有长度, 其他都是0
     *
     * @return
     */
    public Integer getSize() {
        String dataType = GenUtils.getDbType(this.getColumnType());
        if (GenUtils.arraysContains(GenConstants.COLUMNTYPE_STR, columnType) || GenUtils.arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            return GenUtils.getColumnLength(this.getColumnType());
        }
        return 0;
    }
}
