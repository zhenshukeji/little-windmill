package com.zhenshu.parent.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenshu.parent.app.domain.po.Holidays;

import java.time.LocalDate;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/1/9 14:39
 * @desc service
 */
public interface HolidaysService extends IService<Holidays> {
    /**
     * 获取节假日
     *
     * @param date 年月信息
     * @return 结果
     */
    List<Holidays> getByYM(LocalDate date);
}
