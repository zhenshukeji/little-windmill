package com.zhenshu.api.kg.base.advanced;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.remote.ruoyi.RemoteSysMenuService;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import com.zhenshu.system.business.ruoyi.domain.bo.MenuBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostBO;
import com.zhenshu.system.business.ruoyi.domain.bo.PostDetailsBO;
import com.zhenshu.system.business.ruoyi.domain.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/3/1
 * @desc 校区端-岗位管理接口
 */
@RestController
@RequestMapping("/kg/base/advanced/post")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:post:all')")
@Api(tags = "岗位管理", value = "WEB - KgPostController", produces = MediaType.APPLICATION_JSON_VALUE)
public class KgPostController {
    @Resource
    private RemoteSysPostService remoteSysPostService;
    @Resource
    private RemoteSysMenuService remoteSysMenuService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询")
    public Result<IPage<PostBO>> listPage(@Validated({Select.class, Default.class}) PostQueryVO queryVO) {
        queryVO.setBlocId(SecurityUtils.getUserBlocId());
        IPage<PostBO> page = remoteSysPostService.blocListPage(queryVO);
        return new Result<IPage<PostBO>>().success(page);
    }

    @Log(title = "添加校区岗位 ", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加校区岗位")
    public Result<Object> insert(@RequestBody @Validated PostAddVO addVO) {
        remoteSysPostService.insert(addVO);
        return new Result<>().success();
    }

    @Log(title = "修改校区岗位 ", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改校区岗位")
    public Result<Object> updateById(@RequestBody @Validated PostEditVO editVO) {
        remoteSysPostService.updateById(editVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询校区岗位")
    public Result<PostDetailsBO> getById(@PathVariable("id") @ApiParam(required = true, value = "id") Long id) {
        return new Result<PostDetailsBO>().success(remoteSysPostService.getDetailsById(id));
    }

    @Log(title = "删除岗位", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除岗位")
    public Result<Object> deleteById(@RequestBody @Validated PostDeleteVO deleteVO) {
        remoteSysPostService.deleteById(deleteVO);
        return new Result<>().success();
    }

    @Log(title = "修改校区岗位的状态 ", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    @ApiOperation(value = "修改校区岗位的状态")
    public Result<Object> updateStatusById(@RequestBody @Validated PostStatusVO statusVO) {
        remoteSysPostService.updateStatusById(statusVO);
        return new Result<>().success();
    }

    @GetMapping("/menu")
    @ApiOperation(value = "获取菜单列表")
    public Result<List<MenuBO>> getMenuList() {
        return new Result<List<MenuBO>>().success(remoteSysMenuService.getMenuList());
    }
}
