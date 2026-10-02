package com.zhenshu.api.kg.work.educational;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.common.annotation.Log;
import com.zhenshu.common.enums.system.BusinessType;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.web.Result;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.ClassroomPartBO;
import com.zhenshu.system.business.kg.work.educational.domain.bo.TeachingProgramBO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.TeachingProgramQueryVO;
import com.zhenshu.system.business.kg.work.educational.service.ITeachingProgramService;
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
 * @date 2022-05-09
 * @desc 教学计划 Controller
 */
@RestController
@RequestMapping("/kg/work/educational/program")
@PreAuthorize("@ss.hasPermi('kg:work:educational:program:all')")
@Api(tags = "教学计划 ", value = "WEB - TeachingProgramController", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeachingProgramController {

    @Resource
    private ITeachingProgramService iTeachingProgramService;

    @GetMapping("/list")
    @ApiOperation(value = "列表分页查询教学计划")
    public Result<IPage<TeachingProgramBO>> listPage(@Validated({Select.class, Default.class}) TeachingProgramQueryVO queryVO) {
        queryVO.setKgId(SecurityUtils.getUserKgId());
        IPage<TeachingProgramBO> page = iTeachingProgramService.listPage(queryVO);
        return new Result<IPage<TeachingProgramBO>>().success(page);
    }

    @Log(title = "删除教学计划 ", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除教学计划表 ")
    public Result<Object> deleteById(@RequestBody @Validated TeachingProgramDeleteVO deleteVO) {
        iTeachingProgramService.deleteById(deleteVO);
        return new Result<>().success();
    }

    @Log(title = "添加教学计划 ", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加教学计划表 ")
    public Result<Object> insert(@RequestBody @Validated TeachingProgramAddVO addVO) {
        iTeachingProgramService.insert(addVO);
        return new Result<>().success();
    }

    /**
     * 获取该校区所有班级
     *
     * @return 结果
     */
    @GetMapping("/classroom")
    @ApiOperation(value = "获取班级信息 ")
    public Result<List<ClassroomPartBO>> getClassroom() {
        return new Result<List<ClassroomPartBO>>().success(iTeachingProgramService.getClassroomByKgId());
    }
}
