package com.zhenshu.common.enums.base;


import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Jing
 */
public enum UserRoleType implements IEnum<Integer> {
    /**
     * 用户角色类型  0集团人员  sys_user id  1集团人员在园区 kg_bloc_staff_kindergarten id  2园区人员 sys_user id
     */
    BLOC_PEOPLE(0, "集团人员"),
    BLOC_PEOPLE_INTO_KG(1, "集团人员进入园区"),
    KG_PEOPLE(2, "园区人员"),
    ;

    private final Integer value;
    private final String info;

    UserRoleType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }

    @JsonCreator
    public static UserIdentity decode(final Integer code) {
        return Stream.of(UserIdentity.values()).filter(targetEnum -> Objects.equals(code, targetEnum.getValue())).findFirst().orElse(null);
    }
}
