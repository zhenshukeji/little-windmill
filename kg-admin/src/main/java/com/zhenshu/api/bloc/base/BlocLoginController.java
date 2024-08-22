package com.zhenshu.api.bloc.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffKindergartenDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.vo.BlocStaffKindergartenEditVO;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import com.zhenshu.system.business.ruoyi.domain.bo.LoginUserBO;
import com.zhenshu.system.business.ruoyi.domain.vo.LoginUserQueryVO;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
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
 * @date 2022/2/17 16:50
 * @desc 集团登录管理
 */
@RestController
@RequestMapping("/bloc/base/login")
@PreAuthorize("@ss.hasPermi('bloc:login:all')")
@Api(tags = "登录管理", value = "WEB - BlocLoginController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlocLoginController {
    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;
    @Resource
    private IBlocStaffService blocStaffService;
    @Resource
    private RemoteSysUserService remoteSysUserService;

    @GetMapping("/list")
    @ApiOperation(value = "列表查询集团登录用户")
    public Result<IPage<LoginUserBO>> listPage(@Validated({Select.class, Default.class}) LoginUserQueryVO queryVO) {
        queryVO.setBlocId(SecurityUtils.getUserBlocId());
        IPage<LoginUserBO> page = remoteSysUserService.getContactsListPage(queryVO);
        return new Result<IPage<LoginUserBO>>().success(page);
    }

    @Log(title = "重置集团员工密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置集团员工密码")
    @ApiResponse(code = 200, message = "重置后的密码")
    public Result<String> resetPassword(@RequestBody @Validated UserIdVO userIdVO) {
        return new Result<String>().success(blocStaffService.resetPassword(userIdVO));
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "查询集团员工进入校区的权限")
    public Result<BlocStaffKindergartenDetailsBO> getById(@PathVariable("userId") @ApiParam(required = true, value = "登录用户Id") Long userId) {
        return new Result<BlocStaffKindergartenDetailsBO>().success(blocStaffKindergartenService.getDetailsById(userId));
    }

    @Log(title = "修改集团人员进入校区的权限", businessType = BusinessType.UPDATE)
    @PutMapping("/goto/kg")
    @ApiOperation(value = "修改集团人员进入校区的权限")
    public Result<Object> updateGotoKg(@RequestBody @Validated BlocStaffKindergartenEditVO editVO) {
        blocStaffKindergartenService.updateGotoKg(editVO);
        return new Result<>().success();
    }
}
