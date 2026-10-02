package com.zhenshu.api.kg.base.advanced;

import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.GradeBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeAddVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeDeleteVO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.GradeEditVO;
import com.zhenshu.system.business.kg.base.advanced.service.IGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xxx
 * @version 1.0
 * @date 2022-02-16
 * @desc 年级接口
 */
@RestController
@RequestMapping("/kg/base/advanced/grade")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:gradeClass:all')")
@Api(tags = "年级 ", value = "WEB - GradeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class GradeController {
    @Resource
    private IGradeService gradeService;

    @GetMapping("/list")
    @ApiOperation(value = "获取所有年级")
    public Result<List<GradeBO>> listPage() {
        return new Result<List<GradeBO>>().success(gradeService.getList());
    }

    @Log(title = "删除年级 ", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除年级表 ")
    public Result<Object> deleteById(@RequestBody @Validated GradeDeleteVO deleteVO) {
        gradeService.deleteById(deleteVO);
        return new Result<>().success();
    }

    @Log(title = "修改年级 ", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改年级表 ")
    public Result<Object> updateById(@RequestBody @Validated GradeEditVO editVO) {
        gradeService.updateById(editVO);
        return new Result<>().success();
    }

    @Log(title = "添加年级 ", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加年级表 ")
    public Result<Object> insert(@RequestBody @Validated GradeAddVO addVO) {
        gradeService.insert(addVO);
        return new Result<>().success();
    }

}
