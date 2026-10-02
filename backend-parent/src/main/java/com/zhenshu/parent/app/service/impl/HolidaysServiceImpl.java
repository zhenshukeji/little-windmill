package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.Holidays;
import com.zhenshu.parent.app.mapper.HolidaysMapper;
import com.zhenshu.parent.app.service.HolidaysService;
import com.zhenshu.parent.common.constant.Constants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/1/9 14:39
 * @desc serviceImpl
 */
@Service
public class HolidaysServiceImpl extends ServiceImpl<HolidaysMapper, Holidays> implements HolidaysService {
    /**
     * 获取节假日
     *
     * @param date 年月信息
     * @return 结果
     */
    @Override
    public List<Holidays> getByYM(LocalDate date) {
        LocalDate min = LocalDate.of(date.getYear(), date.getMonth(), Constants.ONE);
        LocalDate max = min.plusMonths(Constants.ONE).minusDays(Constants.ONE);
        return super.lambdaQuery()
                .ge(Holidays::getBeginDate, min)
                .le(Holidays::getEndDate, max)
                .list();
    }
}
