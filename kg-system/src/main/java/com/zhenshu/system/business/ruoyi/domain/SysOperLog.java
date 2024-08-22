package com.zhenshu.system.business.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhenshu.common.annotation.Excel;
import com.zhenshu.common.annotation.Excel.ColumnType;
import com.zhenshu.common.core.domain.BaseEntity;
import com.zhenshu.common.enums.system.OperatorLogAssociationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作日志记录表 oper_log
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
    private Long operId;

    /**
     * 关联类型
     * 0平台
     * 1集团
     * 2园区
     */
    @Excel(name = "关联类型", cellType = ColumnType.NUMERIC)
    private OperatorLogAssociationType associationType;

    /**
     * 关联id
     * 0-平台
     * 1-bloc 集团id
     * 2-kindergarten 园区id
     */
    @Excel(name = "关联id", cellType = ColumnType.NUMERIC)
    private Long associationId;

    /**
     * 操作用户id
     */
    @Excel(name = "操作用户id", cellType = ColumnType.NUMERIC)
    private Long uid;

    /**
     * 操作功能
     */
    @Excel(name = "操作功能")
    private String function;

    /**
     * 操作菜单
     */
    @Excel(name = "操作菜单")
    private Long menu;

    /**
     * 操作模块
     */
    @Excel(name = "操作模块")
    private Long module;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    @TableField(exist = false)
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @Excel(name = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @Excel(name = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @Excel(name = "操作人员")
    private String operName;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 请求url
     */
    @Excel(name = "请求地址")
    private String operUrl;

    /**
     * 操作地址
     */
    @Excel(name = "操作地址")
    private String operIp;

    /**
     * 操作地点
     */
    @Excel(name = "操作地点")
    private String operLocation;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @Excel(name = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;

    /**
     * 错误消息
     */
    @Excel(name = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;
}
