package com.zhenshu.common.enums.system;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.zhenshu.common.enums.base.LoginIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/6 14:38
 * @desc
 */
@Getter
@AllArgsConstructor
public enum OperatorLogAssociationType implements IEnum<Integer> {
    /**
     * 关联类型
     * 0平台
     * 1集团
     * 2园区
     */
    PLATFORM(0, "平台"),
    BLOC(1, "集团"),
    KG(2, "园区");

    private final Integer value;
    private final String info;

    /**
     * LoginIdentity转OperatorLogAssociationType
     *
     * @param identity 枚举
     * @return 结果
     */
    public static OperatorLogAssociationType convert(LoginIdentity identity) {
        if(identity == LoginIdentity.BLOC_TO_KG){
            return KG;
        } else if (identity == LoginIdentity.KG_ADMIN || identity == LoginIdentity.KG_PEOPLE) {
            return KG;
        } else if (identity == LoginIdentity.BLOC_ADMIN || identity == LoginIdentity.BLOC_PEOPLE) {
            return BLOC;
        } else if (identity == LoginIdentity.PLATFORM) {
            return PLATFORM;
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
