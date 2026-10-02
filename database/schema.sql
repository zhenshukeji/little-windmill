-- ============================================================
-- 风车智慧幼教 社区版 库结构 schema.sql（v1，ce-03 生成）
-- 30 张表 = 家长端/管理端保留链路反推（台账见同目录 TABLE_LEDGER.md）
-- 结构来源：演示库 SHOW CREATE TABLE 只读导出（仅结构，无数据）
-- 幂等：全部 DROP TABLE IF EXISTS 后重建；重复执行安全
-- 账户口令见 seed.sql 头注释；禁止连接任何既有演示/生产库
-- ============================================================
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `kg_kindergarten`;
CREATE TABLE `kg_kindergarten` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kindergarten_name` varchar(255) DEFAULT NULL,
  `principal_name` varchar(255) DEFAULT NULL,
  `principal_phone` varchar(255) DEFAULT NULL,
  `kindergarten_address` varchar(255) DEFAULT NULL,
  `staff_count` int DEFAULT NULL,
  `student_count` int DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_kindergarten';

DROP TABLE IF EXISTS `kg_grade`;
CREATE TABLE `kg_grade` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `grade_name` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_grade';

DROP TABLE IF EXISTS `kg_classroom`;
CREATE TABLE `kg_classroom` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `grade_id` bigint DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `sub_teacher_id` bigint DEFAULT NULL,
  `student_count` int DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900303 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_classroom';

DROP TABLE IF EXISTS `kg_semester`;
CREATE TABLE `kg_semester` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `semester_name` varchar(255) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=909203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_semester';

DROP TABLE IF EXISTS `kg_student`;
CREATE TABLE `kg_student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_no` varchar(255) DEFAULT NULL,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `real_img_url` varchar(255) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `blood_type` varchar(255) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `health_status` varchar(255) DEFAULT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  `studying_way` varchar(255) DEFAULT NULL,
  `enroll_date` date DEFAULT NULL,
  `place_of_birth` varchar(255) DEFAULT NULL,
  `native_place` varchar(255) DEFAULT NULL,
  `account_quality` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `account_address` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `is_weak` tinyint(1) DEFAULT NULL,
  `is_only_child` tinyint(1) DEFAULT NULL,
  `is_left` tinyint(1) DEFAULT NULL,
  `is_orphan` tinyint(1) DEFAULT NULL,
  `is_disability` tinyint(1) DEFAULT NULL,
  `is_workers` tinyint(1) DEFAULT NULL,
  `special_case` varchar(255) DEFAULT NULL,
  `is_leave_school` tinyint(1) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  `openid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_student';

DROP TABLE IF EXISTS `kg_guardian`;
CREATE TABLE `kg_guardian` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `relation` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900901 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_guardian';

DROP TABLE IF EXISTS `kg_kindergarten_staff`;
CREATE TABLE `kg_kindergarten_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `uid` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `staff_number` varchar(255) DEFAULT NULL,
  `identity_number` varchar(255) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `marriage` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `account_address` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `staff_explain` varchar(255) DEFAULT NULL,
  `is_quit` tinyint(1) DEFAULT NULL,
  `quit_date` datetime DEFAULT NULL,
  `quit_reason` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900219 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_kindergarten_staff';

DROP TABLE IF EXISTS `kg_school_survey`;
CREATE TABLE `kg_school_survey` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `survey_content` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907969 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_school_survey';

DROP TABLE IF EXISTS `kg_class_circle`;
CREATE TABLE `kg_class_circle` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `text_content` varchar(255) DEFAULT NULL,
  `upload_type` varchar(255) DEFAULT NULL,
  `attach_url` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907607 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_class_circle';

DROP TABLE IF EXISTS `kg_teaching_program`;
CREATE TABLE `kg_teaching_program` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  `plan_url` varchar(255) DEFAULT NULL,
  `begin_date` date DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907954 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_teaching_program';

DROP TABLE IF EXISTS `kg_student_vacate_apply`;
CREATE TABLE `kg_student_vacate_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  `apply_by` bigint DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `approve_by` bigint DEFAULT NULL,
  `approve_time` datetime DEFAULT NULL,
  `approve_opinion` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_student_vacate_apply';

DROP TABLE IF EXISTS `kg_banner`;
CREATE TABLE `kg_banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kg_id` bigint DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907975 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_banner';

DROP TABLE IF EXISTS `mini_login`;
CREATE TABLE `mini_login` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  `create_by` bigint DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900701 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mini_login';

DROP TABLE IF EXISTS `mini_already_read`;
CREATE TABLE `mini_already_read` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `association_id` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `last_read_time` datetime DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mini_already_read';

DROP TABLE IF EXISTS `mini_last_read_time`;
CREATE TABLE `mini_last_read_time` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mini_last_read_time';

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT,
  `config_name` varchar(255) DEFAULT NULL,
  `config_key` varchar(255) DEFAULT NULL,
  `config_value` varchar(500) DEFAULT NULL,
  `config_type` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_config';

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `ancestors` varchar(500) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `order_num` varchar(255) DEFAULT NULL,
  `leader` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `del_flag` varchar(255) NOT NULL DEFAULT '0',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_dept';

DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT,
  `dict_sort` varchar(255) DEFAULT NULL,
  `dict_label` varchar(500) DEFAULT NULL,
  `dict_value` varchar(500) DEFAULT NULL,
  `dict_type` varchar(255) DEFAULT NULL,
  `css_class` varchar(255) DEFAULT NULL,
  `list_class` varchar(255) DEFAULT NULL,
  `is_default` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=900012 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_dict_data';

DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) DEFAULT NULL,
  `dict_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_dict_type';

DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ipaddr` varchar(255) DEFAULT NULL,
  `login_location` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_logininfor';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `order_num` varchar(255) DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `component` varchar(500) DEFAULT NULL,
  `query` varchar(500) DEFAULT NULL,
  `is_frame` varchar(255) DEFAULT NULL,
  `is_cache` varchar(255) DEFAULT NULL,
  `menu_type` varchar(255) DEFAULT NULL,
  `visible` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `perms` varchar(500) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=915098 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_menu';

DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(255) DEFAULT NULL,
  `notice_type` varchar(255) DEFAULT NULL,
  `notice_content` varchar(500) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_notice';

DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT,
  `function` varchar(255) DEFAULT NULL,
  `business_type` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `request_method` varchar(255) DEFAULT NULL,
  `operator_type` varchar(255) DEFAULT NULL,
  `oper_name` varchar(255) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `oper_url` varchar(255) DEFAULT NULL,
  `oper_ip` varchar(255) DEFAULT NULL,
  `oper_location` varchar(255) DEFAULT NULL,
  `oper_param` varchar(500) DEFAULT NULL,
  `json_result` varchar(500) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `error_msg` varchar(500) DEFAULT NULL,
  `oper_time` datetime DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `menu` varchar(255) DEFAULT NULL,
  `uid` bigint DEFAULT NULL,
  `association_type` int DEFAULT NULL,
  `association_id` bigint DEFAULT NULL,
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_oper_log';

DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `post_code` varchar(255) DEFAULT NULL,
  `post_name` varchar(255) DEFAULT NULL,
  `post_sort` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_post';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role_key` varchar(255) DEFAULT NULL,
  `role_sort` varchar(255) DEFAULT NULL,
  `data_scope` varchar(255) DEFAULT NULL,
  `menu_check_strictly` varchar(255) DEFAULT NULL,
  `dept_check_strictly` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `del_flag` varchar(255) NOT NULL DEFAULT '0',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `role_type` int DEFAULT NULL COMMENT '角色类型 0系统 1集团 2园区',
  `association_id` bigint DEFAULT NULL COMMENT '归属 集团id/园所id',
  `bind_count` int DEFAULT '0' COMMENT '已绑定人数',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_role';

DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_role_dept';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_role_menu';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `avatar` varchar(500) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `del_flag` varchar(255) NOT NULL DEFAULT '0',
  `login_ip` varchar(255) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `association_type` varchar(255) DEFAULT NULL,
  `association_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_user';

DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_user_post';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `user_type` int DEFAULT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sys_user_role';


-- ce-12 补充：认证链依赖的基础表（平台人员/集团/员工-多园绑定），商业管理界面不开放
DROP TABLE IF EXISTS `kg_platform_staff`;
DROP TABLE IF EXISTS `kg_bloc`;
DROP TABLE IF EXISTS `kg_bloc_staff_kindergarten`;

CREATE TABLE `kg_platform_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staff_name` varchar(255) DEFAULT NULL,
  `staff_phone` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_platform_staff';

CREATE TABLE `kg_bloc` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_name` varchar(255) DEFAULT NULL,
  `principal_name` varchar(255) DEFAULT NULL,
  `principal_phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `kg_count` int DEFAULT NULL,
  `effective_time` datetime DEFAULT NULL,
  `failure_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_bloc';

CREATE TABLE `kg_bloc_staff_kindergarten` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `kindergarten_id` bigint DEFAULT NULL,
  `bloc_staff_id` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_bloc_staff_kindergarten';

DROP TABLE IF EXISTS `kg_bloc_staff`;
CREATE TABLE `kg_bloc_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bloc_id` bigint DEFAULT NULL,
  `uid` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `staff_number` varchar(255) DEFAULT NULL,
  `identity_number` varchar(255) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `marriage` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `account_address` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `staff_explain` varchar(255) DEFAULT NULL,
  `is_quit` tinyint(1) DEFAULT NULL,
  `quit_date` datetime DEFAULT NULL,
  `quit_reason` varchar(255) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删标记 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=900999 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='kg_bloc_staff';

SET FOREIGN_KEY_CHECKS = 1;