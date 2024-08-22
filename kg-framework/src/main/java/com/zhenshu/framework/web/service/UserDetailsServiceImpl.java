package com.zhenshu.framework.web.service;

import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.platform.domain.po.PlatformStaff;
import com.zhenshu.system.business.platform.service.PlatformStaffService;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.core.domain.model.LoginUser;
import com.zhenshu.common.enums.base.*;
import com.zhenshu.common.enums.system.UserStatus;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.StringUtils;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaff;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.platform.domain.po.Bloc;
import com.zhenshu.system.business.platform.service.IBlocService;
import com.zhenshu.system.business.ruoyi.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysPermissionService permissionService;
    @Resource
    private PlatformStaffService platformStaffService;
    @Resource
    private IBlocStaffService blocStaffService;
    @Resource
    private IKindergartenStaffService kindergartenStaffService;
    @Resource
    private IBlocService blocService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        setUserEntity(user);
        return createLoginUser(user);
    }


    /**
     * 关联上登录人员和对应的集团人员、平台人员、园区人员
     */
    public void setUserEntity(SysUser sysUser) {
        AssociationType associationType = sysUser.getAssociationType();
        Long associationId = sysUser.getAssociationId();
        if (AssociationType.PLATFORM.equals(associationType)) {
            PlatformStaff ps = platformStaffService.getById(associationId);
            sysUser.setNickName(ps.getStaffName());
            sysUser.setIdentity(LoginIdentity.PLATFORM);
            sysUser.setPlatformStatus(PlatformStatus.PLATFORM);
        } else if (AssociationType.BLOC.equals(associationType)) {
            BlocStaff bs = blocStaffService.getById(associationId);
            this.checkBlocInEffect(bs.getBlocId());
            sysUser.setNickName(bs.getName());
            if (UserIdentity.ADMIN.equals(bs.getIdentity())) {
                sysUser.setIdentity(LoginIdentity.BLOC_ADMIN);
            } else if (UserIdentity.PEOPLE.equals(bs.getIdentity())) {
                sysUser.setIdentity(LoginIdentity.BLOC_PEOPLE);
            } else {
                log.warn("当前登录用户集团身份既不是管理员也不是普通用户，集团人员表id是 {}", associationId);
                throw new ServiceException(ErrorEnums.LOGIN_ERROR);
            }
            sysUser.setBlocId(bs.getBlocId());
            sysUser.setBlocStaffStatus(BlocStaffStatus.BLOC);
        } else if (AssociationType.KG.equals(associationType)) {
            KindergartenStaff ks = kindergartenStaffService.getById(associationId);
            this.checkBlocInEffect(ks.getBlocId());
            sysUser.setNickName(ks.getName());
            if (UserIdentity.ADMIN.equals(ks.getIdentity())) {
                sysUser.setIdentity(LoginIdentity.KG_ADMIN);
            } else if (UserIdentity.PEOPLE.equals(ks.getIdentity())) {
                sysUser.setIdentity(LoginIdentity.KG_PEOPLE);
            } else {
                log.warn("当前登录用户园区身份既不是管理员也不是普通用户，园区人员表id是 {}", associationId);
                throw new ServiceException(ErrorEnums.LOGIN_ERROR);
            }
            sysUser.setBlocId(ks.getBlocId());
            sysUser.setKgId(ks.getKgId());
        }
    }

    /**
     * 校验集团是否在生效期间
     *
     * @param blocId 集团id
     */
    private void checkBlocInEffect(Long blocId) {
        Bloc bloc = blocService.getById(blocId);
        LocalDateTime nowTime = LocalDateTime.now();
        if (nowTime.isBefore(bloc.getEffectiveTime()) || nowTime.isAfter(bloc.getFailureTime())) {
            log.warn("当前登录集团不在生效期内，集团id是 {}", bloc.getId());
            throw new ServiceException(ErrorEnums.NOT_IN_THE_EFFECTIVE_PERIOD);
        }
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
