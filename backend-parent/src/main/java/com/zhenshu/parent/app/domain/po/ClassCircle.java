package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhenshu.parent.common.constant.enums.CircleUploadType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 班级圈表
 * </p>
 *
 * @author zch
 * @since 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_class_circle")
public class ClassCircle extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
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
