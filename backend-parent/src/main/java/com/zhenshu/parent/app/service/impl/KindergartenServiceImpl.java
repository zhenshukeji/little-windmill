package com.zhenshu.parent.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.parent.app.domain.po.Kindergarten;
import com.zhenshu.parent.app.mapper.KindergartenMapper;
import com.zhenshu.parent.app.service.KindergartenService;
import org.springframework.stereotype.Service;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/7/7 15:46
 * @desc
 */
@Service
public class KindergartenServiceImpl extends ServiceImpl<KindergartenMapper, Kindergarten> implements KindergartenService {
}
