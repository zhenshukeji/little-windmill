package com.zhenshu.system.cache;

import com.zhenshu.common.core.domain.entity.SysMenu;
import com.zhenshu.common.enums.base.MenuCategory;
import com.zhenshu.system.business.ruoyi.domain.bo.MenuBO;
import com.zhenshu.system.business.ruoyi.service.ISysMenuService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/7 10:03
 * @desc 菜单缓存管理器
 */
@Component
public class MenuCacheManages extends CachesManages {
    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 获取指定菜单类型的所有菜单
     *
     * @param type 菜单类型
     * @return 结果
     */
    public List<SysMenu> getMenus(MenuCategory type) {
        String cacheKey = super.cacheKeyManager.getMenusKey(type);
        return super.getList(cacheKey, () -> sysMenuService.getMenusByType(type), SysMenu.class);
    }
}
