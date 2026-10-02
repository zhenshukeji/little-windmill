package com.zhenshu.parent.app.domain.bo.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zch
 * @version 1.0
 * @desc 轮播图 出参
 * @date 2022-06-29
 **/
@Data
@ApiModel(description = "班级圈 出参")
public class BannerBO {
    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private List<String> imgUrl;

}
