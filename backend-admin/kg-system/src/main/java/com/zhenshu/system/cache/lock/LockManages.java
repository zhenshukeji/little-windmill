package com.zhenshu.system.cache.lock;

import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.system.cache.CachesManages;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/6 16:02
 * @desc 分布式锁管理器
 */
@Component
public class LockManages extends CachesManages {
    @Resource
    private Redisson redisson;
    @Resource
    private LockKey lockKeyManages;

    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取添加体检记录的分布式锁
     *
     * @param studentId 学生id
     * @return 分布式锁
     */
    public RLock getAddHealthExaminationLock (Long studentId) {
        String lockKey = lockKeyManages.getAddHealthExaminationLockKey(studentId);
        return redisson.getLock(lockKey);
    }

    /**
     * 获取初始化学生考勤记录分布式锁
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 分布式锁
     */
    public RLock getStudentCheckInitLock (LocalDate date, Long kgId) {
        String lockKey = lockKeyManages.getStudentCheckInitLockKey(date.format(YMD), kgId);
        return redisson.getLock(lockKey);
    }

    /**
     * 获取初始化员工考勤记录分布式锁
     *
     * @param date 日期
     * @param kgId 校区id
     * @return 分布式锁
     */
    public RLock getStaffCheckInitLock (LocalDate date, Long kgId) {
        String lockKey = lockKeyManages.getStaffCheckInitLockKey(date.format(YMD), kgId);
        return redisson.getLock(lockKey);
    }
}
