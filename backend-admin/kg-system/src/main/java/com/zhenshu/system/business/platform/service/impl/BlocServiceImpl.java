package com.zhenshu.system.business.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.system.business.platform.domain.po.Bloc;
import com.zhenshu.system.business.platform.mapper.BlocMapper;
import com.zhenshu.system.business.platform.service.IBlocService;
import org.springframework.stereotype.Service;

/**
 * 集团表服务实现（社区版：仅保留认证基础依赖，商业 CRUD 已移除）。
 */
@Service
public class BlocServiceImpl extends ServiceImpl<BlocMapper, Bloc> implements IBlocService {
}
