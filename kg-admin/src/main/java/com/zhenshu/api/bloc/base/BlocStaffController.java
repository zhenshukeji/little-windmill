package com.zhenshu.api.bloc.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.LockFunction;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffBO;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.vo.*;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import com.zhenshu.system.business.ruoyi.domain.bo.RoleBO;
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
 * @date 2022-02-11
 * @desc
 */
@RestController
@RequestMapping("/bloc/base/staff")
@PreAuthorize("@ss.hasPermi('bloc:staff:all')")
@Api(tags = "集团员工", value = "WEB - BlocStaffController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlocStaffController {
    @Resource
    private IBlocStaffService blocStaffService;
    @Resource
    private RemoteSysPostService remoteSysPostService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询")
    public Result<IPage<BlocStaffBO>> listPage(@Validated({Select.class, Default.class}) BlocStaffQueryVO blocStaffQueryVO) {
        blocStaffQueryVO.setBlocId(SecurityUtils.getUserBlocId());
        IPage<BlocStaffBO> page = blocStaffService.listPage(blocStaffQueryVO);
        return new Result<IPage<BlocStaffBO>>().success(page);
    }

    @Log(title = "修改集团员工", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改")
    public Result<Object> updateById(@RequestBody @Validated BlocStaffEditVO blocStaffEditVO) {
        blocStaffService.updateById(blocStaffEditVO);
        return new Result<>().success();
    }

    @Log(title = "添加集团员工", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Object> insert(@RequestBody @Validated BlocStaffAddVO blocStaffAddVO) {
        blocStaffService.insert(blocStaffAddVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询")
    public Result<BlocStaffDetailsBO> getById(@PathVariable("id") @ApiParam(required = true, value = "id") Long id) {
        return new Result<BlocStaffDetailsBO>().success(blocStaffService.getDetailsById(id));
    }

    @LockFunction(className = "BlocStaff", methodName = "quit", keyName = "id")
    @Log(title = "集团员工离职", businessType = BusinessType.DELETE)
    @DeleteMapping("/quit")
    @ApiOperation(value = "集团员工离职")
    public Result<Object> quit(@RequestBody @Validated BlocStaffQuitVO blocStaffQuitVO) {
        blocStaffService.quit(blocStaffQuitVO);
        return new Result<>().success();
    }

    @Log(title = "集团员工入职", businessType = BusinessType.INSERT)
    @PostMapping("/entry")
    @ApiOperation(value = "集团员工入职")
    public Result<Object> entry(@RequestBody @Validated BlocStaffIdVO idVO) {
        blocStaffService.entry(idVO);
        return new Result<>().success();
    }

    @GetMapping("/post/list")
    @ApiOperation(value = "查询登录用户所在集团的岗位")
    public Result<List<RoleBO>> getRoleList() {
        return new Result<List<RoleBO>>().success(remoteSysPostService.getPostList());
    }

    @Log(title = "删除集团员工", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除集团员工")
    public Result<Object> deleteById(@RequestBody @Validated BlocStaffIdVO idVO) {
        blocStaffService.deleteById(idVO);
        return new Result<>().success();
    }

}
