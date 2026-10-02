package com.zhenshu.api.kg.base.advanced;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import com.zhenshu.system.business.ruoyi.domain.bo.LoginUserBO;
import com.zhenshu.system.business.ruoyi.domain.vo.LoginUserQueryVO;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @desc 校区登录管理
 */
@RestController
@RequestMapping("/kg/base/advanced/login")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:login:all')")
@Api(tags = "登录管理", value = "WEB - KgLoginController", produces = MediaType.APPLICATION_JSON_VALUE)
public class KgLoginController {
    @Resource
    private IKindergartenStaffService kindergartenStaffService;
    @Resource
    private RemoteSysUserService remoteSysUserService;

    @GetMapping("/list")
    @ApiOperation(value = "列表查询校区登录用户")
    public Result<IPage<LoginUserBO>> listPage(@Validated({Select.class, Default.class}) LoginUserQueryVO queryVO) {
        queryVO.setBlocId(SecurityUtils.getUserBlocId());
        IPage<LoginUserBO> page = remoteSysUserService.getContactsListPage(queryVO);
        return new Result<IPage<LoginUserBO>>().success(page);
    }

    @Log(title = "重置校区员工密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ApiOperation(value = "重置校区员工密码")
    @ApiResponse(code = 200, message = "重置校区员工密码")
    public Result<String> resetPassword(@RequestBody @Validated UserIdVO userIdVO) {
        return new Result<String>().success(kindergartenStaffService.resetPassword(userIdVO));
    }
}
