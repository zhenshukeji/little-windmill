package com.zhenshu.api.platform;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.platform.domain.vo.PlatformLogQueryVO;
import com.zhenshu.system.business.ruoyi.domain.bo.LogBO;
import com.zhenshu.system.business.ruoyi.service.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/6 14:53
 * @desc 平台日志
 */
@RestController
@RequestMapping("/platform/log")
@PreAuthorize("@ss.hasPermi('platform:log:all')")
@Api(tags = "日志管理", value = "WEB - PlatformLogController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlatformLogController {
    @Autowired
    private ISysOperLogService operLogService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询平台日志")
    public Result<IPage<LogBO>> list(@Validated({Select.class, Default.class}) PlatformLogQueryVO queryVO) {
        return new Result<IPage<LogBO>>().success(
                operLogService.listPage(
                        queryVO.getPageNum(),
                        queryVO.getPageSize(),
                        queryVO.getStartTime(),
                        queryVO.getEndTime(),
                        null,
                        null
                )
        );
    }
}
