package com.zhenshu.parent.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 计算两个时间段
     */
    public static String getTimeDivision(LocalDateTime endDate) {
        LocalDateTime nowDate = LocalDateTime.now();
        //间隔天数
        int i = nowDate.compareTo(endDate);
        int num = 1;
        if (i < num) {
            return "今天";
        } else if (i < (num++)) {
            return "昨天";
        } else if (i <= (num++)) {
            return "2天前";
        } else if (i < (num++)) {
            return "3天前";
        } else {
            return endDate.toLocalDate().toString();
        }
    }

    /**
     * 计算两个时间段
     */
    public static String getTimeDay(LocalDateTime endDate) {
        LocalDateTime nowDate = LocalDateTime.now();
        //间隔天数
        int i = nowDate.compareTo(endDate);
        int num = 1;
        if (i < num) {
            return "刚刚";
        } else {
            return i + "天前";
        }
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor)
    {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor)
    {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 当前时间戳加年月日时分秒
     *
     * @return
     */
    public static String getTimeNO() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdfTime.format(new Date());
    }

    /**
     * 根据出生日期返回年龄; 格式:y岁m月
     *
     * @param birthdate 出生日期
     * @return 年龄
     */
    public static String getAge(LocalDate birthdate, LocalDate date) {
        long y = ChronoUnit.YEARS.between(birthdate, date);
        long m = ChronoUnit.MONTHS.between(birthdate, date) % 12;
        return String.format("%d岁%d个月", y, m);
    }


    /**
     * Date转LocalDate
     *
     * @param date 时间
     * @return 结果
     */
    public static LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 获取指定年月内所有工作日日期
     *
     * @param date 年月入参
     * @return 结果
     */
    public static List<LocalDate> getMonthFullDayWorkingDay(LocalDate date) {
        List<LocalDate> allDate = new ArrayList<>(31);
        int year = date.getYear();
        int month = date.getMonthValue();
        // 所有月份从1号开始
        int day = 1;
        LocalDate of = LocalDate.of(year, month, day);
        //获取天数
        int dayCount = date.lengthOfMonth();
        for (int i = 0; i <= (dayCount - 1); i++) {
            LocalDate addLocalDate = of.plusDays(i);
            allDate.add(addLocalDate);
        }
        return allDate;
    }

    /**
     * 获取几岁几个月
     *
     * @param birthdate 出生日期
     * @return 结果
     */
    public static String getYearMonth(LocalDate birthdate) {
        Period until = birthdate.until(LocalDate.now());
        return until.getYears() + "岁" + until.getMonths() + "月";
    }
}
