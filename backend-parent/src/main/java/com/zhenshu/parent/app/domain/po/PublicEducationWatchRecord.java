package com.zhenshu.parent.app.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公共教育观看表
 * </p>
 *
 * @author zch
 * @since 2022-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kg_public_education_watch_record")
public class PublicEducationWatchRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 公告教育视频id
     */
    private Long videoId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 最后观看时间
     */
    private LocalDateTime lastWatchTime;

    /**
     * 累计观看次数
     */
    private Integer watchCount;

}
