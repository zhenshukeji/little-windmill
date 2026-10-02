package com.zhenshu.parent.common.utils.ip.tencent.domain;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 地理位置
 * @date 2021/8/19 0019 16:37
 **/
@Data
public class AdInfoBean {


    /**
     * 国家
     */
    private String nation;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 行政区划代码
     */
    private int adcode;
}
