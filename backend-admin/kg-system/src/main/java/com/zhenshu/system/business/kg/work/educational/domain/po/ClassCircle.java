package com.zhenshu.system.business.kg.work.educational.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.common.domain.BasePO;
import com.zhenshu.common.enums.kg.work.educational.CircleUploadType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc 班级圈实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kg_class_circle")
public class ClassCircle extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级圈id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团id
     */
    private Long blocId;

    /**
     * 学校id
     */
    private Long kgId;

    /**
     * 发布的文字
     */
    private String textContent;

    /**
     * 上传附件类型(0 文件 1视频)
     */
    private CircleUploadType uploadType;

    /**
     * 附件地址
     */
    private String attachUrl;

}
