package com.zhenshu.common.utils;

import com.zhenshu.common.constant.Constants;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
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
     * 计算两个时间差
     */
    public static String getDateString(long date) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        //当前时间
        long nowTime = System.currentTimeMillis();
        // 获得两个时间的毫秒时间差异
        long diff = nowTime - date;
        // 计算差多少天
        long day = diff / nd;
        if (day > 1) {
            return null;
        }
        // 计算差多少小时
        long hour = diff % nd / nh;
        if (hour > 0) {
            return hour + "小时前";
        }
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        if (min > 0) {
            return min + "分钟前";
        }
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        if (sec > 0) {
            return min + "秒前";
        }

        return null;
    }

    /**
     * 判断当前时间是否在指定时间之后
     *
     * @param localDateTime 日期
     * @return 结果
     */
    public static boolean isNowAfter(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(localDateTime);
    }

    /**
     * 当前时间戳加年月日时分秒
     *
     * @return 结果
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
     * 获取时间段内所有日期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 结果
     */
    public static List<LocalDate> getsAllDates(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> localDateList = new ArrayList<>();
        while (startDate.isBefore(endDate)) {
            localDateList.add(startDate);
            startDate = startDate.plusDays(1);
        }
        localDateList.add(endDate);
        return localDateList;
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
        int day = 1;// 所有月份从1号开始
        LocalDate of = LocalDate.of(year, month, day);
        //获取天数
        int dayCount = date.lengthOfMonth();
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        if (month < currentMonth || year < currentYear) {
            for (int i = 0; i <= (dayCount - 1); i++) {
                int size = allDate.size();
                if (size > 0 && Objects.equals(date.with(TemporalAdjusters.lastDayOfMonth()), allDate.get(size - 1))) {
                    break;
                }
                LocalDate addLocalDate = of.plusDays(i);
                //除去周末
//                DayOfWeek dayOfWeek = addLocalDate.getDayOfWeek();
//                if (!(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY)) {
//                    allDate.add(addLocalDate);
//                }
                allDate.add(addLocalDate);
            }
        } else if (year == currentYear && month == currentMonth) {
            for (int j = 0; j <= (dayCount - 1); j++) {
                int size = allDate.size();
                // 判断，如果是最后一个月份，跳出循环
                if (size > 0 && Objects.equals(now, allDate.get(size - 1))) {
                    break;
                }
                LocalDate addLocalDate = of.plusDays(j);
                //除去周末
//                DayOfWeek dayOfWeek = addLocalDate.getDayOfWeek();
//                if (!(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY)) {
//                    allDate.add(addLocalDate);
//                }
                allDate.add(addLocalDate);
            }
        }
        return allDate;
    }

    /**
     * 日期是否在指定时间范围内
     *
     * @param time      判断日期
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 结果
     */
    public static boolean isIn(LocalDate time, LocalDate beginTime, LocalDate endTime) {
        if (time.equals(beginTime) || time.equals(endTime)) {
            return true;
        }
        return beginTime.isBefore(time) && endTime.isAfter(time);
    }

    /**
     * 判断时间是上午还是下午
     *
     * @param date 指定时间
     * @return 结果
     */
    public static boolean isAm(LocalDateTime date) {
        int hour = date.getHour();
        int am = 12;
        return hour < am;
    }

    /**
     * 获取两个时间间隔
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 结果
     */
    public static long getDuration(LocalTime startTime, LocalTime endTime) {
        Duration between = Duration.between(startTime, endTime);
        return between.toMinutes();
    }

    /**
     * 获取当前时间所在周的星期一
     */
    public static LocalDate getNowWeekMonday() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    /**
     * 获取当前时间所在周的星期日
     */
    public static LocalDate getNowWeekSunday() {
        return LocalDate.now().with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取当前时间所在月的第一天
     */
    public static LocalDate getNowMonthFirst() {
        return DateUtils.getMonthFirst(LocalDate.now());
    }

    /**
     * 获取指定日期所在月的第一天
     */
    public static LocalDate getMonthFirst(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取当前时间所在月的最后一天
     */
    public static LocalDate getNowMonthLast() {
        return DateUtils.getMonthLast(LocalDate.now());
    }

    /**
     * 获取指定日期所在月的第一天
     */
    public static LocalDate getMonthLast(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取月份第一天和最后一天
     *
     * @param checkingDate 考勤月份
     */
    public static Map<String, LocalDate> getMonthFirstAndLate(Date checkingDate) {
        LocalDate localDate = toLocalDate(checkingDate);
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate now = LocalDate.now();
        //last > now
        if (last.isAfter(now)) {
            last = now;
        }
        Map<String, LocalDate> map = new HashMap<>();
        map.put("first", first);
        map.put("last", last);
        return map;
    }

    /**
     * 获取两个时间差内的所有日期, 包括头尾
     *
     * @param first 开始日期
     * @param last  结束日期
     * @return 结果
     */
    public static List<LocalDate> getRangeDate(LocalDate first, LocalDate last) {
        if (first.isAfter(last)) {
            return Collections.emptyList();
        }
        Duration between = Duration.between(LocalDateTime.of(first, LocalTime.MIN), LocalDateTime.of(last, LocalTime.MIN));
        List<LocalDate> dates = new ArrayList<>((int) between.toDays());
        do {
            dates.add(first);
            first = first.plusDays(Constants.ONE);
        } while (!Objects.equals(first, last));
        dates.add(last);
        return dates;
    }

    /**
     * 获取指定的月份内的所有日期对象
     *
     * @param date 日期
     * @return 结果
     */
    public static List<LocalDate> getMonthFullDate(LocalDate date) {
        LocalDate first = DateUtils.getMonthFirst(date);
        LocalDate last = DateUtils.getMonthLast(date);
        return DateUtils.getRangeDate(first, last);
    }
}
