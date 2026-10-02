package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.Guardian;
import com.zhenshu.parent.app.mapper.GuardianMapper;
import com.zhenshu.parent.app.service.GuardianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 监护人表  服务实现类
 * </p>
 *
 * @author jing
 * @since 2022-05-24
 */
@Service
public class GuardianServiceImpl extends ServiceImpl<GuardianMapper, Guardian> implements GuardianService {

    @Override
    public List<Guardian> getByPhone(String phone) {
        QueryWrapper<Guardian> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Guardian::getPhone, phone);
        return this.list(wrapper);
    }

    /**
     * 获取指定学生的监护人信息
     *
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public List<Guardian> getByStudentId(Long studentId) {
        return super.list(
                new QueryWrapper<Guardian>().lambda()
                        .eq(Guardian::getStudentId, studentId)
        );
    }

    /**
     * 获取学生监护人id
     *
     * @param phone     手机号
     * @param studentId 学生id
     * @return 结果
     */
    @Override
    public Long getByCondition(String phone, Long studentId) {
        Guardian one = this.getOne(new QueryWrapper<Guardian>().lambda().eq(Guardian::getPhone, phone).eq(Guardian::getStudentId, studentId));
        return Objects.nonNull(one) ? one.getId() : null;
    }
}
