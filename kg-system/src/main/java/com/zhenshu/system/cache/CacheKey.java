package com.zhenshu.system.cache;

import com.zhenshu.common.enums.base.MenuCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author jing
 * @version 1.0
 * @desc redis缓存key类
 * @date 2020/6/11 0011 13:50
 **/
@Component
public class CacheKey {

    @Value("${spring.redis.cache_name}")
    public String cacheName;

    /**
     * 获取菜单缓存Key
     *
     * @param type 菜单类型
     * @return 缓存Key
     */
    public String getMenusKey(MenuCategory type) {
        return String.format("%s:menus-type:%s", cacheName, type.getCode());
    }

    /**
     * 是否初始化了学生考勤记录缓存Key
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 缓存Key
     */
    public String getHasInitStudentCheckKey(LocalDate date, Long kgId) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.format("%s:init-studentCheck:%s-%d", cacheName, dateStr, kgId);
    }

    /**
     * 是否初始化了员工考勤记录缓存Key
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 缓存Key
     */
    public String getHasInitStaffCheckKey(LocalDate date, Long kgId) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.format("%s:init-staffCheck:%s-%d", cacheName, dateStr, kgId);
    }
}
