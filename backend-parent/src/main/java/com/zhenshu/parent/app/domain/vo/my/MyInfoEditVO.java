package com.zhenshu.parent.app.domain.vo.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/27 17:41
 * @desc 我的资料出参
 */
@Data
@ApiModel(description = "我的资料出参")
public class MyInfoEditVO {
    /**
     * 学生姓名
     */
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true, value = "学生姓名")
    private String studentName;

    /**
     * 头像
     */
    @NotBlank
    @Size(max = 200)
    @ApiModelProperty(required = true, value = "头像")
    private String headImg;

    /**
     * 性别 0男 1女 2未知
     */
    @NotNull
    @Range(min = 0, max = 2)
    @ApiModelProperty(required = true, value = "性别 0男 1女 2未知")
    private Integer gander;

    /**
     * 出生日期
     */
    @NotNull
    @ApiModelProperty(required = true, value = "出生日期")
    private LocalDate birthdate;

    /**
     * 家庭地址
     */
    @Size(max = 100)
    @ApiModelProperty(required = false, value = "家庭地址")
    private String address;

    /**
     * 监护人
     */
    @Valid
    @NotEmpty
    @Size(min = 1, max = 2)
    @ApiModelProperty(required = true, value = "监护人, 至少一个元素, 最多两个")
    private List<GuardianEditVO> guardians;
}
