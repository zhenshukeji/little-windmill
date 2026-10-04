# 部署说明（社区版 v1）

> ⚠️ 本模板编写于无 Docker 环境，`docker compose` 路径**未实测**；无 Docker 时可按下文“分别本地启动”。
> 完整验收以 ce-13（真实本地闭环）为准。

## 前置
1. MySQL 8 + Redis 7（本地或容器）。
2. `cp .env.example .env` 并填写：DB_PASSWORD、REDIS_PASSWORD、**TOKEN_SECRET（管理后端 JWT，必须强随机——不配置将回退到公开可知占位串，等于开放伪造 token）**、**JWT_SECRET（家长后端 JWT，同上必须强随机）**、WX_APP_ID/WX_APP_SECRET（真实家长登录必需）。
3. 数据库初始化：`mysql -u<user> -p < ../database/schema.sql && mysql -u<user> -p < ../database/seed.sql`（幂等口径见 TABLE_LEDGER.md §4）。
4. 管理前端需反向代理 `/api/ → 管理后端:8179/`（剥前缀；Vue 应用的 API 基址为同源 `/api`）。
5. **首次部署后立即修改全部账号口令**（seed 初始为 admin123）。

## 分别本地启动（无 Docker）
- 管理后端：`cd backend-admin && mvn -DskipTests package && TOKEN_SECRET=<强随机> java -jar kg-admin/target/kg-admin.jar`（8179）
- 家长后端：`cd backend-parent && mvn -DskipTests package && JWT_SECRET=<强随机> java -jar target/windmill-community-parent-api.jar`（8184）
- 管理前端：`cd admin-web && NODE_OPTIONS=--openssl-legacy-provider npm run build:prod`，dist 交给 nginx/静态服务器
- 小程序：`cd parent-mini && bash scripts/build_mp_weixin.sh build`，导入微信开发者工具

## 验证清单
- 管理端登录 → 建班/幼儿/教师 → 家长登录（需微信配置）→ 提交请假 → 管理端审批 → 家长查看（ce-13 实测项）
- 已知边界：幼儿/教职工档案列表对园所管理员 403（菜单 perms 与控制器注解错位，待 cu-05b 对齐）；教师账号权限依赖 sys_user_role.user_type=2（seed 已修复）。
