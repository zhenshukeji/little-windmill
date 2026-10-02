package com.zhenshu.framework.web.remote;

import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.framework.web.service.SysLoginService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/21 14:55
 * @desc 远程登录接口
 */
@Service
public class RemoteSysLoginService {
    @Resource
    private SysLoginService sysLoginService;
    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;

    /**
     * 切换用户登录身份
     *
     * @param kgId 校区Id
     * @return 新的token
     */
    public String gotoKg(Long kgId) {
        LoginIdentity identity = LoginIdentity.BLOC_TO_KG;
        // 1.校验用户是否能进入这个校区; 集团管理员不用校验
        if (SecurityUtils.getLoginIdentity() != LoginIdentity.BLOC_ADMIN) {
            boolean result = blocStaffKindergartenService.checkUserToKg(SecurityUtils.getUser().getUserId(), kgId);
            if (!result) {
                throw new ServiceException(ErrorEnums.PERMISSION_DENIED);
            }
        } else {
            // 集团管理元切换身份为校区管理员
            identity = LoginIdentity.KG_ADMIN;
        }
        // 2.切换用户身份
        return sysLoginService.switchIdentity(identity, kgId);
    }
}
