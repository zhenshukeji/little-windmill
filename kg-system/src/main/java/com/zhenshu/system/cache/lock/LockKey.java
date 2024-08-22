package com.zhenshu.system.cache.lock;

import com.zhenshu.common.constant.LockKeyConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/6 16:06
 * @desc 分布式锁Key管理器
 */
@Component
public class LockKey {
    @Value("${spring.redis.cache_name}")
    public String cacheName;

    /**
     * 获取添加体检记录的分布式锁缓存Key
     *
     * @param studentId 学生id
     * @return 缓存Key
     */
    public String getAddHealthExaminationLockKey(Long studentId) {
        return String.format("%s:%s:%d", cacheName, LockKeyConstants.ADD_HEALTH, studentId);
    }

    /**
     * 获取初始化学生考勤记录分布式锁缓存Key
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 缓存Key
     */
    public String getStudentCheckInitLockKey(String date, Long kgId) {
        return String.format("%s:%s:%s-%d", cacheName, LockKeyConstants.STUDENT_CHECK_INIT, date, kgId);
    }

    /**
     * 获取初始化员工考勤记录分布式锁缓存Key
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 缓存Key
     */
    public String getStaffCheckInitLockKey(String date, Long kgId) {
        return String.format("%s:%s:%s-%d", cacheName, LockKeyConstants.STAFF_CHECK_INIT, date, kgId);
    }
}
