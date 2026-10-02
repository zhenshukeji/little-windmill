# 社区库表台账（ce-03）

生成：2026-10-01，`ce03_build_db.py`。结构来源=演示库只读 `SHOW CREATE TABLE`；表清单来源=保留代码 `@TableName` + mapper XML 反推（证据 `docs/evidence/ce-03/_ddl-source-tables.txt`）。

## 1. 纳入清单（30 张）

| 域 | 表 | 保留理由（调用链） |
|---|---|---|
| 园所 | kg_kindergarten | 家长登录/我的/概况/动态均按 kgId 关联 |
| 园所 | kg_grade、kg_classroom、kg_semester | 班级/学期管理（ce-01 保留入口），学生归属班级 |
| 人员 | kg_student、kg_guardian | 家长登录按监护人手机号→幼儿；档案管理 |
| 人员 | kg_kindergarten_staff、sys_user（association_type=2 → staff.id） | 教师-班级绑定校验（remoteClassroomService.getStaffBindClassroom 消费 sys_user.association_id） |
| 内容 | kg_school_survey、kg_class_circle、kg_teaching_program、kg_banner | 概况/园内动态（kg_id 园级语义）/教学计划/首页轮播 |
| 业务 | kg_student_vacate_apply | 请假闭环（apply_by=mini_login.id，approve_by=sys_user.user_id） |
| 家长 | mini_login、mini_already_read、mini_last_read_time | 登录缓存主体；请假已读（VacateApplyFacade→MessageReadFacade） |
| 若依 | sys_config、sys_dept、sys_dict_data、sys_dict_type、sys_logininfor、sys_menu、sys_notice、sys_oper_log、sys_post、sys_role、sys_role_dept、sys_role_menu、sys_user、sys_user_post、sys_user_role | 框架基础设施（登录/权限/菜单/字典/审计日志） |

## 2. 例外清单

> ce-12 补充：登录核心（UserDetailsServiceImpl.setUserEntity 三分支）依赖 kg_platform_staff/kg_bloc/kg_bloc_staff_kindergarten，
> 三表已补入 schema（33 表），并修正排除清单——这三张是**认证基础依赖**，不是集团功能；集团管理界面仍不开放。（保留表中含商业字段的说明）

| 表 | 商业相关字段 | 处置 |
|---|---|---|
| kg_student | img_url/real_img_url/health_status/is_weak 等健康弱项字段 | 保留列不删（实体映射兼容）；v1 不做保健业务，仅档案展示 |
| kg_kindergarten | staff_count/student_count | 基础统计字段，随管理端班级/幼儿增删维护 |
| sys_user | association_type/association_id | 基础身份绑定机制（教师链），非商业功能 |
| mini_already_read/mini_last_read_time | 关联业务类型字段含商业枚举值 | 表保留（请假已读必需），商业类型值不再产生 |

## 3. 排除清单（本库不创建，随代码裁剪同步消失）

- 财务/支付：kg_payment_*、kg_deal_*、kg_config_pay_*、mini_pay_record
- 健康/保健/膳食：kg_illness/contagion/invasion/accident/defect_record、kg_health_examination、kg_daily_checking、kg_student_checking、kg_staff_checking、kg_hey_medicine_apply、kg_medicine、kg_resume_classes_apply、kg_vaccination/vaccine_register、kg_special_attention、kg_physical_exercise_*、kg_dish/food/meal/recipe/week*
- 教务商业项：kg_homework*、kg_courseware*、kg_public_education_*、kg_principal_mailbox*、kg_class_event、kg_contacts?、kg_recruit*、kg_campus_bulletin、kg_event_trivia、kg_function_classroom、kg_visit_apply、kg_staff_vacate_apply
- 待定排除（调用链已证伪）：kg_holidays（保留页零引用；/calendar 已随裁剪移除）、kg_semester_course（学期页“配置”tab 专表，家长端无消费→ce-07 移除该 tab）、kg_attendance_time（考勤不在 v1）
- 集团/平台：kg_bloc、kg_bloc_staff、kg_bloc_staff_kindergarten、kg_platform_staff、kg_standard
- 其他：kg_config_pay_*、bak0922_*（备份表）
- kg-quartz 模块：演示库无 sys_job 表、保留功能无调度依赖 → 模块随 ce-04 排除

## 4. 初始化与幂等

- `schema.sql`：30 表全量 `DROP TABLE IF EXISTS` + `CREATE`，可重复执行。
- `seed.sql`：合成数据（虚构园所/人员/手机号），依赖 schema 先行；重复执行会因主键冲突失败——**幂等口径 = “初始化两次”指 schema 两次 + seed 一次**（seed 自身不幂等，如需重置重新导库）。该口径已在文件头注释说明。
- 账户口令：sys_user 初始 `admin123`（RuoYi 标准 BCrypt 哈希），首登必改（ce-16 安装步骤强制）。
- **验证缺口（如实登记）**：本机无 MySQL/Redis/Docker，`schema.sql`+`seed.sql` 仅完成 sqlglot 语法解析（63+26 语句 OK）与 NOT NULL 覆盖静态审计（`docs/evidence/ce-03/notnull-audit.txt`；缺口仅 sys_role_dept/sys_user_post 两个有意留空关系表，0 行 INSERT 不构成违规）。**真实导入与空库启动闭环（verify 要求的“新本地专用库初始化两次”）待本地 MySQL 可用后执行**——补齐路径：安装便携版 MySQL（或 Docker）→ `mysql -u<user> -p < schema.sql && mysql ... < seed.sql` → 重复执行 schema 验证幂等 → ce-13 从空库启动两后端跑通闭环。Redis 同理为社区部署前置。
