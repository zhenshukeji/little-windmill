package com.zhenshu.api.kg.base.advanced;

import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.SchoolSurveyDetailsBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.SchoolSurveyEditVO;
import com.zhenshu.system.business.kg.base.advanced.service.ISchoolSurveyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022-03-01
 * @desc
 */
@RestController
@RequestMapping("/kg/base/advanced/survey")
@PreAuthorize("@ss.hasPermi('kg:base:advanced:survey:all')")
@Api(tags = "园区概况 ", value = "WEB - SchoolSurveyController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SchoolSurveyController {
    @Resource
    private ISchoolSurveyService schoolSurveyService;

    @Log(title = "修改园区概况 ", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据ID修改园区概况表 ")
    public Result<Object> updateById(@RequestBody @Validated SchoolSurveyEditVO editVO) {
        schoolSurveyService.updateById(editVO);
        return new Result<>().success();
    }

    @GetMapping
    @ApiOperation(value = "查询园区概况表 ")
    public Result<List<SchoolSurveyDetailsBO>> getDetailsById() {
        return new Result<List<SchoolSurveyDetailsBO>>().success(schoolSurveyService.getList());
    }

}

