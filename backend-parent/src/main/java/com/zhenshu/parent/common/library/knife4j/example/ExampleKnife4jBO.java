package com.zhenshu.parent.common.library.knife4j.example;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 示例
 * </p>
 *
 * @author jing
 * @since 2021-07-14
 */
@Data
@ApiModel
public class ExampleKnife4jBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    @ApiModelProperty(value = "openid")
    private String openid;

    /**
     * 微信昵称
     */
    @ApiModelProperty(value = "微信昵称")
    private String nickName;

    /**
     * 微信头像
     */
    @ApiModelProperty(value = "微信头像")
    private String headImg;

    /**
     * 总积分数
     */
    @ApiModelProperty(value = "总积分数")
    private Integer totalIntegral;

    /**
     * 剩余积分
     */
    @ApiModelProperty(value = "剩余积分")
    private Integer remainderIntegral;

}
