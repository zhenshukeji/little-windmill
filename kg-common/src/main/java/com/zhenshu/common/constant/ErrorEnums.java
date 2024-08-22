package com.zhenshu.common.constant;

import lombok.Getter;

/**
 * @author jing
 * @version 1.0
 * @desc 返回类
 * @date 2020/12/17 0017 19:41
 **/
@Getter
public enum ErrorEnums {
    //39999 该数据已存在 zch
    EXIST_DATA(39999, "该数据已存在"),
    //39998 该数据不存在 zch
    NOT_EXIST_DATA(39998, "该数据不存在"),
    //39998 该数据不存在 zch 教学计划 班级圈
    UPLOAD_FAIL(39997, "上传失败"),
    //39996 学生考勤导入失败 学生考勤
    IMPORT_KG_STUDENT_ILLEGALITY(39996, "导入失败, 存在学生不属于本校区"),
    //学生该日期已有考勤纪律 学生考勤
    STUDENT_IN_DATE_HAS_CHECKING(39995, "学生在日期中已有考勤记录"),
    //39994 班级考勤率导入失败 班级考勤率
    IMPORT_KG_CLASS_CHECKING_RATE_ILLEGALITY(39994, "导入失败, 数据为空"),
    // 39993 存在学生请假
    HAS_STUDENT_LEAVE(39993, "有学生请假"),
    // 39994 存在学生重复
    STUDENT_ID_REPEAT(3999, "存在学生重复"),

    /**
     * 返回数据
     */
    SUCCESS(200, "成功"),
    INNER_ERROR(500, "参与人数过多，请稍后重试"),

    BIZ_ERROR_CODE(50300, "操作错误"),
    LOGIN_ERROR(50301, "当前登录用户的账号信息错误"),
    LOCK_EMPTY_PARAM(50302, "拦截锁时的参数不能为空"),
    CLASS_AND_METHOD_NOT_PRE(50323, "Log注解标注的类和方法上未使用PreAuthorize注解"),

    // 40000-40999  通用
    PERMISSION_DENIED(40000, "权限不足, 不允许操作"),
    IDENTITY_ILLEGAL(40001, "身份不合法, 不允许操作"),
    PHONE_EXISTS(40002, "手机号已存在"),
    SYS_USER_NOT_EXIST(40003, "登录用户不存在"),
    POST_ILLEGALITY(40004, "岗位Id不合法"),
    MENU_ILLEGALITY(40005, "菜单Id不合法"),
    POST_NOT_EXIST(40006, "岗位不存在"),
    POST_HAS_PEOPLE(40007, "该岗位下还存在用户, 不允许删除"),
    ID_NOT_FOUND(40008, "id查不到对应的数据"),
    DELETE_FAIL(40009, "删除失败，该数据不允许删除或不存在"),
    UPDATE_FAIL(40010, "修改失败，该数据不存在或者出现异常"),
    IMPORT_NOT_DATA(40011, "导入失败, 请填写数据"),
    IMPORT_ERROR(40012, "导入失败, 格式不正确"),
    IMPORT_SIZE_OVERFLOW(40013, "导入失败, 数据超出最大限制"),
    IMPORT_PHONE_REPEAT(40014, "导入失败, 手机号出现重复"),
    EXPORT_NOT_DATA(40015, "导出失败, 没有查询到数据"),
    USER_ID_ILLEGALITY(40016, "登录用户id不合法"),
    BEGIN_DATE_LT_END_DATE(40017, "开始时间应该小于结束时间"),
    IMPORT_DATE_ERROR(40018, "导入失败, 时间不一致"),
    IMPORT_KG_STAFF_ILLEGALITY(40019, "导入失败, 校区员工不合法"),
    IS_ANTICIPATED_VALUE(40020, "已是预期值"),

    // 43000-43999  平台
    BLOC_ONT_EXIST(43000, "集团不存在"),
    BLOC_HAS_KG(43001, "集团下还存在校区, 不允许删除"),
    BLOC_HAS_STAFF(43002, "集团下还存在在职员工, 不允许删除"),
    BLOC_ADMIN_NOT_EXIST(43003, "集团管理员不存在"),
    BLOC_STAFF_NOT_EXIST(43004, "集团员工不存在"),
    BLOC_STAFF_NOT_QUIT_UNDELETABLE(43005, "集团员工未离职不可删除"),

    // 42000-42999  集团
    KG_ONT_EXIST(42000, "校区不存在"),
    KG_HAS_STAFF(42002, "校区下还存在在职员工, 不允许删除"),
    KG_ADMIN_NOT_EXIST(42003, "校区管理员不存在"),
    KG_HAS_STUDENT(42004, "校区下还存在在读学生, 不允许删除"),
    KG_ID_ILLEGALITY(42005, "校区ID不合法"),
    KG_STAFF_NOT_EXIST(42006, "校区员工不存在"),

    // 41000-41999  校区
    DO_NOT_INVALID(41000, "该项目不能作废"),
    CANNOT_REFUSE(41001, "只有已缴费的状态才能退款"),
    REFUSE_MORE_THAN(41002, "退款金额超出支付金额"),
    CANNOT_PAY(41003, "只有未缴费的状态才能缴费"),
    NOT_RUN(41004, "当前缴费状态不允许此操作"),
    ORIGINAL_ROAD_FAIL(41005, "只有线上缴费的才允许原路退回"),
    UNDER_LINE_FAIL(41006, "只有线下缴费的才允许线下退费"),
    GUARDIAN_STUDENT_UNRELATED(41007, "监护人和学生不是绑定关系, 不允许操作"),
    STUDENT_YET_LEVEL(41008, "学生已经是离校状态"),
    ON_SCHOOL_NOT_DELETE(41009, "在校学生不可删除"),
    STUDENT_ILLEGALITY(41010, "学生ID不合法"),
    CLASS_NOT_EXIST(41011, "班级不存在"),
    CLASS_HAS_STUDENT(41012, "班级中还有学生, 不允许删除"),
    CLASS_HAS_TEACHER(41013, "班级已经绑定了老师, 请先解绑"),
    TEACHER_BIND_CLASS(41014, "该老师已经绑定了班级, 不可重复绑定"),
    TEACHER_IS_QUIT(41015, "老师已离职, 请重新选择"),
    CLASS_ILLEGALITY(41016, "班级ID不合法"),
    TEACHER_ILLEGALITY(41017, "老师ID不合法"),
    CLASS_REPEAT(41018, "班级出现重复"),
    TEACHER_REPEAT(41019, "老师出现重复"),
    SEMESTER_HAS_COURSE(41020, "学期有课程不允许删除"),
    DATE_HAS_INTERSECTION(41021, "时间不能有交集"),
    ATTENDANCE_TIME_HAS_STAFF(41022, "考勤时间还有员工绑定"),
    STAFF_IS_BINDING_ATTENDANCE(41023, "员工已绑定了考勤时间"),
    ATTENDANCE_BIND_ID_ERROR(41024, "考勤时间绑定id有错误"),
    NOT_INVITE(41025, "只有管理员和班主任可以访问"),
    DEFAULT_ATTENDANCE_UNDELETABLE(41026, "默认考勤时间不可删除"),
    KG_STAFF_NOT_QUIT_UNDELETABLE(41027, "校区员工未离职不可删除"),
    TO_YEAR_HAS_HEALTH_EXAMINATION(41028, "该学生今年已有体检记录"),
    HAS_ENTER_SCHOOL_HEALTH_EXAMINATION(41029, "该学生已有入园体检记录"),
    STUDENT_HAS_VACCINE_REGISTER(41030, "学生已有疫苗登记"),
    SELECT_IS_WORK_DAY(41031, "所选日期已是工作日"),
    NOT_INCLUDE_HOLIDAYS(41032, "不能包含节假日"),
    APPLY_PROCESSED(41033, "已处理, 不可重复处理"),
    STAFF_IN_DATE_HAS_CHECKING(41034, "员工在日期中已有考勤记录"),
    STAFF_NUMBER_EXIST(41035, "员工编号已存在"),
    HAS_STAFF_LEAVE(41036, "有员工请假"),
    NOT_BIND_CLASS(41037, "没有绑定班级"),
    ALREADY_PUTAWAY(41038, "已是上架状态"),
    ALREADY_SOLD_OUT(41039, "已是下架状态"),
    CLASS_NOT_EQUALS(41040, "班级不一致"),
    ALREADY_APPROVE(41041, "已被审核，无需重复审核"),
    KG_STAFF_BING_CLASS_ROOM(41042, "员工已绑定班级，请先解除绑定"),
    DERATE_AMOUNT_INVALID(41043, "减免金额不能大于缴费标准"),
    CONFIG_PAY_PROJECT_ILLEGALITY(41044, "收费项目不合法"),
    CANNOT_HANDLER_OTHER_CLASS_DATA(41045, "不能操作其他班级的数据"),
    STUDENT_NOT_EXIST(41046, "学生不存在"),
    TEACHER_CANNOT_INTO(41047, "教师不能访问"),
    ONLY_TEACHER_INTO(41048, "只有管理员和教师可以访问"),
    GRADE_EXIST_CLASS(41049, "年级下存在班级"),
    STUDENT_HEALTH_EXIST_EXPORT_ERROR(41050, "学生已存在体检，请检查您的导入数据"),
    CANNOT_LOOK_OTHER_CLASS_DATA(41051, "不能查看班级的数据"),
    INIT_STUDENT_CHECK_REPEAT(41052, "学生考勤初始化中, 请稍后再试"),
    INIT_STAFF_CHECK_REPEAT(41053, "员工考勤初始化中, 请稍后再试"),

    // 50000-50999  通用
    // 51000-51999  校区
    // 52000-52999  集团
    // 53000-53999  平台
    NOT_IN_THE_EFFECTIVE_PERIOD(40302, "集团不在生效期间"),
    CHARGES_MISTAKE(51000, "缴费统计金额与传过来的不一致，请联系系统管理员"),
    STUDENT_EXIST(51001, "缴费学生有重复，请去掉重复学员，或者联系系统管理员"),
    EMPTY_PARAM(40301, "参数不能为空"),
    FREQUENT_OPERATION(40312, "您操作得太快了");


    ErrorEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final Integer code;

    public final String msg;
}
