package com.zhenshu.api.platform;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.platform.domain.bo.BlocBO;
import com.zhenshu.system.business.platform.domain.vo.*;
import com.zhenshu.system.business.platform.service.IBlocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@RestController
@RequestMapping("/platform/bloc")
@PreAuthorize("@ss.hasPermi('platform:bloc:all')")
@Api(tags = "平台-集团管理", value = "WEB - BlocController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlocController {
    @Resource
    private IBlocService kgBlocService;

    /**
     * 列表分页查询
     *
     * @param blocQueryVO 列表分页查询入参
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询")
    public Result<IPage<BlocBO>> listPage(@Validated({Select.class, Default.class}) BlocQueryVO blocQueryVO) {
        IPage<BlocBO> page = kgBlocService.listPage(blocQueryVO);
        return new Result<IPage<BlocBO>>().success(page);
    }

    /**
     * 删除
     *
     * @param blocDeleteVO 删除入参
     * @return
     */
    @Log(title = "删除集团", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除")
    public Result<Object> deleteById(@RequestBody @Validated BlocDeleteVO blocDeleteVO) {
        kgBlocService.deleteById(blocDeleteVO);
        return new Result<>().success();
    }

    /**
     * 根据ID修改
     *
     * @param blocEditVO 入参
     * @return
     */
    @Log(title = "修改集团", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改")
    public Result<Object> updateById(@RequestBody @Validated BlocEditVO blocEditVO) {
        kgBlocService.updateById(blocEditVO);
        return new Result<>().success();
    }

    /**
     * 添加
     *
     * @param blocAddVO 入参
     * @return
     */
    @Log(title = "添加集团", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Object> insert(@RequestBody @Validated BlocAddVO blocAddVO) {
        kgBlocService.insert(blocAddVO);
        return new Result<>().success();
    }

    /**
     * 重置集团密码
     *
     * @param blocResetPasswordVO 重置密码入参
     * @return
     */
    @Log(title = "重置集团密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置集团密码")
    public Result<Object> resetPassword(@RequestBody @Validated BlocResetPasswordVO blocResetPasswordVO) {
        kgBlocService.resetPassword(blocResetPasswordVO);
        return new Result<>().success();
    }
}
