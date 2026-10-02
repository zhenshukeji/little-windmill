package com.zhenshu.parent.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/1 16:05
 * @desc UUID工具类
 */
public class UUIDUtils {
    /**
     * 时间戳 + 5位数的随机数字
     *
     * @return 结果
     */
    public static String timeRandom() {
        return DateUtils.getTimeNO() + RandomUtil.randomNumbers(5);
    }

    /**
     * 雪花算法生成唯一id
     *
     * @return 字符串
     */
    public static String snowflake() {
        return IdUtil.getSnowflake().nextIdStr();
    }
}
