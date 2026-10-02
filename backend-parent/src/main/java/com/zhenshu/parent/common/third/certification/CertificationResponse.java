package com.zhenshu.parent.common.third.certification;

import lombok.Data;

/**
 * @author jing
 * @version 1.0
 * @desc 实名认证
 * @date 2022/4/6 0006 14:26
 **/
@Data
public class CertificationResponse {
    /**
     * status : 01
     * msg : 实名认证通过！
     * idCard : 43************7021
     * name : 杨*
     * sex : 女
     * area : 湖南省湘潭市湘潭县
     * province : 湖南省
     * city : 湘潭市
     * prefecture : 湘潭县
     * birthday : 1998-04-19
     * addrCode :
     * lastCode :
     * traceId : 20220406140202_bc82ic_55cf3935
     */
    private String status;
    private String msg;
    private String idCard;
    private String name;
    private String sex;
    private String area;
    private String province;
    private String city;
    private String prefecture;
    private String birthday;
    private String addrCode;
    private String lastCode;
    private String traceId;
}
