package com.zhenshu.api.kg.work.educational;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.work.educational.domain.bo.ClassCircleBO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleQueryVO;
import com.zhenshu.system.business.kg.work.educational.service.IClassCircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 班级圈Controller
 */
@RestController
@RequestMapping("/kg/work/educational/circle")
@PreAuthorize("@ss.hasPermi('kg:work:educational:circle:all')")
@Api(tags = "班级圈", value = "WEB - ClassCircleController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassCircleController {
    @Resource
    private IClassCircleService classCircleService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询班级圈")
    public Result<IPage<ClassCircleBO>> listPage(@Validated({Select.class, Default.class}) ClassCircleQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<ClassCircleBO> page = classCircleService.listQuery(queryVO);
        return new Result<IPage<ClassCircleBO>>().success(page);
    }

    @Log(title = "删除班级圈动态", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除班级圈动态")
    public Result<Object> deleteById(@RequestBody @Validated ClassCircleDeleteVO deleteVO) {
        classCircleService.deleteById(deleteVO);
        return new Result<>().success();
    }


    @Log(title = "发布班级圈动态", businessType = BusinessType.INSERT)
    @PostMapping("/publish")
    @ApiOperation(value = "发布班级圈动态")
    public Result<Object> publish(@RequestBody @Validated ClassCircleAddVO addVO) {
        classCircleService.insertOne(addVO);
        return new Result<>().success();
    }
}
