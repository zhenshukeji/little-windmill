package com.zhenshu.system.remote.bloc.base;

import com.zhenshu.system.business.bloc.base.domain.bo.NameInfoBO;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.bloc.finance.domain.bo.KindergartenSimpleBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 校区
 * @date 2022/2/17 0017 16:17
 **/
@Service
public class RemoteKindergartenService {

    @Resource
    private IKindergartenService kindergartenService;

    public Kindergarten getById(Long kgId) {
        return kindergartenService.getById(kgId);
    }


    public NameInfoBO getKgNameAndBlocName(Long kgId){
        return kindergartenService.getKgNameAndBlocName(kgId);
    }

    /**
     * 获取指定集团下的所有校区
     *
     * @param blocId 集团id
     * @return 结果
     */
    public List<KindergartenSimpleBO> getListAllByBlocId(Long blocId) {
        return kindergartenService.getListAllByBlocId(blocId);
    }

    public PostRoleNameBO getUserIdAndUserName(Long kgId){
        return kindergartenService.getUidByKgId(kgId);
    }

    public List<Long> getUidByKgId(List<Long> kgId){
        return kindergartenService.getUidByKgId(kgId);
    }
}
