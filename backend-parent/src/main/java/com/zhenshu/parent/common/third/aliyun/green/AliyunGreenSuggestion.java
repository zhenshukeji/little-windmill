package com.zhenshu.parent.common.third.aliyun.green;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/4/25 15:09
 * @desc
 */
@Getter
@AllArgsConstructor
public enum AliyunGreenSuggestion {
    /**
     * pass：结果正常，无需进行其余操作。
     * review：结果不确定，需要进行人工审核。
     * block：结果违规，建议直接删除或者限制公开。
     */
    PASS(0, "pass：结果正常，无需进行其余操作。"),
    REVIEW(1, "review：结果不确定，需要进行人工审核。"),
    BLOCK(2, "block：结果违规，建议直接删除或者限制公开。");

    private final int code;

    private final String msg;
}
