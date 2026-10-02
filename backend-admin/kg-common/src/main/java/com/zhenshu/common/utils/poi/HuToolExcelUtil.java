package com.zhenshu.common.utils.poi;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.AjaxResult;
import com.zhenshu.common.exception.ServiceException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/22 14:19
 * @desc HuTool工具包封装excel
 */
public class HuToolExcelUtil {
    public static AjaxResult exportExcelMap(String downloadPath, List<LinkedHashMap<String, Object>> rows) {
        if (CollectionUtils.isEmpty(rows)) {
            throw new ServiceException(ErrorEnums.EXPORT_NOT_DATA);
        }
        // 通过工具类创建writer
        String filename = encodingFilename("export");
        ExcelWriter writer = ExcelUtil.getBigWriter(getFilePath(filename, downloadPath));
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 设置第一行字体加粗
        Font font = writer.createFont();
        font.setBold(true);
        writer.getHeadCellStyle().setFont(font);
        // 设置样式
        setSizeColumn(writer.getSheet(), rows.get(Constants.ZERO).size());
        CellStyle style = writer.getCellStyle();
        style.setWrapText(Constants.TRUE);
        // 关闭writer，释放内存
        writer.close();
        return AjaxResult.success(filename);
    }

    /**
     * 自适应宽度(中文支持)
     *
     * @param sheet
     * @param size  因为for循环从0开始，size值为 列数-1
     */
    public static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum <= size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public static String getFilePath(String filename, String downloadPath) {
        String path = System.getProperty("user.dir") + File.separator + downloadPath + filename;
        File desc = new File(path);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return path;
    }

    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }
}
