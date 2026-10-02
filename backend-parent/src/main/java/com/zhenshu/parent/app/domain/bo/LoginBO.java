package com.zhenshu.parent.app.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 登录返回数据
 * @date 2022/5/7 0007 16:19
 **/
@ApiModel
@Data
public class LoginBO {

    @ApiModelProperty("登录凭证")
    private String token;

    @ApiModelProperty("学生信息")
    private List<StudentBO> studentList;
}
