package com.zhenshu.system.remote.ruoyi;

import com.zhenshu.system.business.ruoyi.domain.bo.MenuBO;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/2/18 18:42
 * @desc 远程菜单
 */
@Service
public class RemoteSysMenuService {
    @Resource
    private ISysMenuService menuService;

    /**
     * 获取菜单
     *
     * @return 结果
     */
    public List<MenuBO> getMenuList() {
        return menuService.getMenuList();
    }
}
