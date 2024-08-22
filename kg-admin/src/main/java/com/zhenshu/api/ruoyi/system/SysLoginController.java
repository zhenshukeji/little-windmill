package com.zhenshu.api.ruoyi.system;

import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.domain.AjaxResult;
import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.core.domain.model.LoginBody;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.framework.web.service.SysLoginService;
import com.zhenshu.framework.web.service.SysPermissionService;
import com.zhenshu.system.business.bloc.base.domain.bo.NameInfoBO;
import com.zhenshu.system.business.platform.domain.po.Bloc;
import com.zhenshu.system.business.ruoyi.domain.vo.RouterVo;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import com.zhenshu.system.remote.bloc.base.RemoteKindergartenService;
import com.zhenshu.system.remote.platform.RemoteBlocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private SysPermissionService permissionService;
    @Resource
    private RemoteKindergartenService remoteKindergartenService;
    @Resource
    private RemoteBlocService remoteBlocService;
    @Resource
    private MessageHandler messageHandler;
    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        // 获取集团名称和校区名称
        LoginIdentity identity = user.getIdentity();
        if (identity == LoginIdentity.BLOC_PEOPLE || identity == LoginIdentity.BLOC_ADMIN) {
            Bloc bloc = remoteBlocService.getById(user.getBlocId());
            ajax.put("blocName", bloc.getBlocName());
        } else if (LoginIdentity.isKg(identity)) {
            NameInfoBO kgNameAndBlocName = remoteKindergartenService.getKgNameAndBlocName(user.getKgId());
            ajax.put("blocName", kgNameAndBlocName.getBlocName());
            ajax.put("kgName", kgNameAndBlocName.getKgName());
        }
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUser(user);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 获取消息数量
     *
     * @return 消息数量
     */
    @GetMapping("getMessage")
    public Result<List<MessageHandler.MessageNotReadBO>> getMessage() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (user.getIdentity() != LoginIdentity.BLOC_TO_KG && user.getIdentity() != LoginIdentity.KG_PEOPLE && user.getIdentity() != LoginIdentity.KG_ADMIN) {
            return new Result<List<MessageHandler.MessageNotReadBO>>().success(Collections.emptyList());
        }
        List<SysMenu> menus = menuService.selectMenuTreeByUser(user);
        List<RouterVo> routers = sysMenuService.buildMenus(menus);
        return new Result<List<MessageHandler.MessageNotReadBO>>().success(messageHandler.getNotReadMessageCount(routers));
    }
}
