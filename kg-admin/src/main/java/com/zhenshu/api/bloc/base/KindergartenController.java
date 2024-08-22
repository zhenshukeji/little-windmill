package com.zhenshu.api.bloc.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenBO;
import com.zhenshu.system.business.bloc.base.domain.bo.KindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenAddVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenDeleteVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenEditVO;
import com.zhenshu.system.business.bloc.base.domain.vo.KindergartenQueryVO;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.kg.base.record.domain.vo.KindergartenResetPasswordVO;
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

/**
 * @author xyh
 * @version 1.0
 * @date 2022-02-11
 * @desc
 */
@RestController
@RequestMapping("/bloc/base/kg")
@PreAuthorize("@ss.hasPermi('bloc:kg:all')")
@Api(tags = "园区", value = "WEB - KindergartenController", produces = MediaType.APPLICATION_JSON_VALUE)
public class KindergartenController {
    @Resource
    private IKindergartenService kgKindergartenService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询")
    public Result<IPage<KindergartenBO>> listPage(@Validated({Select.class, Default.class}) KindergartenQueryVO kindergartenQueryVO) {
        kindergartenQueryVO.setBlocId(SecurityUtils.getUserBlocId());
        IPage<KindergartenBO> page = kgKindergartenService.listPage(kindergartenQueryVO);
        return new Result<IPage<KindergartenBO>>().success(page);
    }

    @Log(title = "删除园区", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除")
    public Result<Object> deleteById(@RequestBody @Validated KindergartenDeleteVO kindergartenDeleteVO) {
        kgKindergartenService.deleteById(kindergartenDeleteVO);
        return new Result<>().success();
    }

    @Log(title = "修改园区", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改")
    public Result<Object> updateById(@RequestBody @Validated KindergartenEditVO kindergartenEditVO) {
        kgKindergartenService.updateById(kindergartenEditVO);
        return new Result<>().success();
    }

    @Log(title = "添加园区", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Object> insert(@RequestBody @Validated KindergartenAddVO kindergartenAddVO) {
        kgKindergartenService.insert(kindergartenAddVO);
        return new Result<>().success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id查询")
    public Result<KindergartenDetailsBO> getById(@PathVariable("id") @ApiParam(required = true, value = "id") Long id) {
        return new Result<KindergartenDetailsBO>().success(kgKindergartenService.getDetailsById(id));
    }

    @Log(title = "重置校区密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置校区密码")
    public Result<Object> resetPassword(@RequestBody @Validated KindergartenResetPasswordVO restPwdVO) {
        kgKindergartenService.resetPassword(restPwdVO);
        return new Result<>().success();
    }

}
