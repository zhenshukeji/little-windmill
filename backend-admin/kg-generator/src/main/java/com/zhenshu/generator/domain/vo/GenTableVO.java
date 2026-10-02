package com.zhenshu.generator.domain.vo;

import com.zhenshu.generator.domain.GenTable;
import com.zhenshu.generator.domain.GenTableColumnSub;
import lombok.Data;

import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/8 10:18
 * @desc
 */
@Data
public class GenTableVO {
    private GenTable table;

    private List<GenTableColumnSub> columns;
}
