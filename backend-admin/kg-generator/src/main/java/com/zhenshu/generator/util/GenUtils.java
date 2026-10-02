package com.zhenshu.generator.util;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.GenConstants;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.generator.config.GenConfig;
import com.zhenshu.generator.domain.GenTable;
import com.zhenshu.generator.domain.GenTableColumn;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;

import java.util.Arrays;

/**
 * 代码生成器 工具类
 *
 * @author ruoyi
 */
public class GenUtils {
    /**
     * 初始化表信息
     */
    public static void initTable(GenTable genTable, String operName) {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.getPackageName());
        genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(GenConfig.getAuthor());
        genTable.setGenPath("/kg-system");
        genTable.setCreateBy(operName);
        genTable.setHasInsert(Constants.TRUE);
        genTable.setHasUpdate(Constants.TRUE);
        genTable.setHasDelete(Constants.TRUE);
        genTable.setHasList(Constants.TRUE);
        genTable.setHasDetails(Constants.TRUE);
        genTable.setHasExport(Constants.TRUE);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column, GenTable table) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(StringUtils.toCamelCase(columnName));

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType) ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            if ("datetime".equals(dataType)) {
                column.setJavaType(GenConstants.TYPE_LOCALDATETIME);
            } else if("date".equals(dataType)){
                column.setJavaType(GenConstants.TYPE_LOCALDATE);
            } else if("time".equals(dataType)){
                column.setJavaType(GenConstants.TYPE_LOCALTIME);
            } else {
                column.setJavaType(GenConstants.TYPE_DATE);
            }
            column.setHtmlType(GenConstants.HTML_DATETIME);
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GenConstants.HTML_INPUT);
            // 我的逻辑部分; 没有处理好交给若依处理
            if ("int".equalsIgnoreCase(column.getColumnType())) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            } else if ("bigint".equalsIgnoreCase(column.getColumnType())) {
                column.setJavaType(GenConstants.TYPE_LONG);
            } else if ("bit".equalsIgnoreCase(dataType) || "tiyint".equalsIgnoreCase(dataType)) {
                String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
                if ("1".equals(str[0])) {
                    column.setJavaType(GenConstants.TYPE_BOOLEAN);
                } else {
                    column.setJavaType(GenConstants.TYPE_INTEGER);
                }
            } else if ("float".equalsIgnoreCase(dataType)) {
                column.setJavaType(GenConstants.TYPE_DOUBLE);
            }
            if (column.getJavaType() == null) {
                // 如果是浮点型 统一用BigDecimal
                String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
                if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                    column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
                }
                // 如果是整形
                else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                    column.setJavaType(GenConstants.TYPE_INTEGER);
                }
                // 长整形
                else {
                    column.setJavaType(GenConstants.TYPE_LONG);
                }
            }
        }
        if (column.getJavaType() == null) {
            // 设置默认类型
            column.setJavaType(GenConstants.TYPE_STRING);
        }
        String[] strings = {"kg_id", "bloc_id"};
        if(!ArrayUtils.contains(strings, column.getColumnName())){
            // 插入字段（默认所有字段都需要插入）
            if (!column.isPk() && !ArrayUtils.contains(GenConstants.FIXED, column.getColumnName())) {
                column.setIsInsert(GenConstants.REQUIRE);
                column.setIsInsertRequired(GenConstants.REQUIRE);
            }
            // 编辑字段
            if (!arraysContains(GenConstants.FIXED, columnName) || column.isPk()) {
                column.setIsEdit(GenConstants.REQUIRE);
                column.setIsUpdateRequired(GenConstants.REQUIRE);
            }
            // 列表字段
            if (!arraysContains(GenConstants.FIXED, columnName) && !column.isPk()) {
                column.setIsList(GenConstants.REQUIRE);
            }
            // 查询字段
            if (!arraysContains(GenConstants.FIXED, columnName) && !column.isPk()) {
                column.setIsQuery(GenConstants.REQUIRE);
            }
            // 详情字段
            if (!arraysContains(GenConstants.FIXED, columnName) && !column.isPk()) {
                column.setIsDetails(GenConstants.REQUIRE);
            }
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        String businessName = StringUtils.substring(tableName, lastIndex + 1, nameLength);
        return businessName;
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName) {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacementm 替换值
     * @param searchList   替换列表
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList) {
        String text = replacementm;
        for (String searchString : searchList) {
            if (replacementm.startsWith(searchString)) {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return RegExUtils.replaceAll(text, "(?:表|若依)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}
