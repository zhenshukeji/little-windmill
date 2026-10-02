package com.zhenshu.common.utils;

import java.time.LocalDate;

/**
 * @author zch
 * @version 1.0
 * @date 2022-06-10
 * @desc 编号自动生成
 */
public class AutoGenerateNoUtil {
    /**
     * 生成学生学号 -> 当前年份 + 校区id + 班级id + 学生在班级顺序
     *
     * @param clazzId          班级id
     * @param studentInClassNo 学生在班级顺序
     * @return 结果
     */
    public static String generateSn(Long clazzId, Long studentInClassNo) {
        // 年份
        int year = LocalDate.now().getYear();
        String sn;
        //学生在班级编号转string
        String classNo = longToString(studentInClassNo);
        //校区id转string
        String kgId = longToString(SecurityUtils.getUserKgId());
        //获取学号
        sn = year + kgId + clazzId + classNo;
        return sn;
    }

    /**
     * 获取教师编号
     *
     * @param clazzId 班级id
     * @return 结果
     */
    public static String generateTeacherSn(Long clazzId) {
        String sn;
        sn = "T" + clazzId + System.currentTimeMillis();
        return sn;
    }

    /**
     * 小于10补零
     *
     * @param value 参数
     * @return 结果
     */
    private static String longToString(Long value) {
        Long count = 10L;
        String valueStr;
        //若为单数，补零
        String s = String.valueOf(value);
        if (value < count) {
            valueStr = "0" + s;
        } else {
            valueStr = s;
        }

        return valueStr;
    }
}
