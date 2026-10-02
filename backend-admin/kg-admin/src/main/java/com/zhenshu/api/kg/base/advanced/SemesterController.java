package com.zhenshu.api.kg.base.advanced;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.constant.HttpStatus;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SemesterBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterEditVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SemesterQueryVO;
import com.zhenshu.system.business.kg.base.advanced.service.ISemesterService;
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
 * @date 2022-03-01
 * @desc
 */
@RestController
@RequestMapping("/kg/base/advanced/semester")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:semester:all')")
@Api(tags = "学期", value = "WEB - SemesterController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SemesterController {
    @Resource
    private ISemesterService semesterService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询学期")
    public Result<IPage<SemesterBO>> listPage(@Validated({Select.class, Default.class}) SemesterQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<SemesterBO> page = semesterService.listPage(queryVO);
        return new Result<IPage<SemesterBO>>().success(page);
    }

    @Log(title = "删除学期 ", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除学期")
    public Result<Object> deleteById(@RequestBody @Validated SemesterDeleteVO deleteVO) {
        semesterService.deleteById(deleteVO);
        return new Result<>().success();
    }

    @Log(title = "修改学期 ", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改学期")
    public Result<Object> updateById(@RequestBody @Validated SemesterEditVO editVO) {
        // 开始时间需要小于结束时间
        if (editVO.getBeginDate().after(editVO.getEndDate())) {
            throw new ServiceException("开始时间不能大于结束时间", HttpStatus.BAD_REQUEST);
        }
        semesterService.updateById(editVO);
        return new Result<>().success();
    }

    @Log(title = "添加学期 ", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加学期")
    public Result<Object> insert(@RequestBody @Validated SemesterAddVO addVO) {
        // 开始时间需要小于结束时间
        if (addVO.getBeginDate().after(addVO.getEndDate())) {
            throw new ServiceException("开始时间不能大于结束时间", HttpStatus.BAD_REQUEST);
        }
        semesterService.insert(addVO);
        return new Result<>().success();
    }
}
