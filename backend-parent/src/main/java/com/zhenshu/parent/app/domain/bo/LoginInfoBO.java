package com.zhenshu.parent.app.domain.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author jing
 * @version 1.0
 * @desc 学生信息
 * @date 2022/5/7 0007 16:32
 **/
@Data
@ApiModel
public class LoginInfoBO {

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别 0女 1男
     */
    private Integer gender;

    /**
     * 学生头像url
     */
    private String imgUrl;

    /**
     * 学生真实头像
     */
    private String realImgUrl;

    /**
     * 民族
     */
    private String nation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 出生日期 年月日
     */
    private LocalDate birthdate;

    /**
     * 现住址
     */
    private String address;

    /**
     * 是否离校
     */
    private Boolean isLeaveSchool;


}
