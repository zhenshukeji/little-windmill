#!/usr/bin/env bash
# 风车智慧幼教社区版 —— 小程序 mp-weixin 构建脚本（自包含、参数化）
#
# 背景：本工程是 HBuilderX 形态（package.json 仅 thorui-uni），无 npm scripts。
#       vite-plugin-uni 从 cliRoot/package.json 发现平台插件，因此构建前需临时向
#       package.json 合并 uni-app CLI 依赖清单，构建完成自动还原（trap EXIT）。
#
# 依赖：Node.js >= 18（建议 20/22，验证版本 22.22.2）+ 本仓库 node_modules 内的
#       @dcloudio 工具链（安装方式见仓库 README；版本 3.0.0-5020420260813003）。
#
# 用法：
#   bash scripts/build_mp_weixin.sh [build|dev]
#   NODE_BIN=/path/to/node bash scripts/build_mp_weixin.sh build   # 指定 node
set -u
export PATH="/usr/bin:/bin:$PATH"

MODE="${1:-build}"
# 仓库根 = 本脚本上级目录
SELF_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJ="$(cd "$SELF_DIR/.." && pwd)"
PROJ_WIN=$(cygpath -m "$PROJ" 2>/dev/null || echo "$PROJ")
NODE_BIN="${NODE_BIN:-node}"
command -v "$NODE_BIN" >/dev/null 2>&1 || { echo "!! 未找到 node，可用 NODE_BIN 指定"; exit 1; }
TMP="$PROJ/unpackage/.build-tmp"
mkdir -p "$TMP"
BAK="$TMP/package.json.orig"
CONFIG="$PROJ/scripts/vite.config.mjs"
VER="3.0.0-5020420260813003"

if [ "$MODE" = "build" ]; then
  OUT="$PROJ_WIN/unpackage/dist/build/mp-weixin"
else
  OUT="$PROJ_WIN/unpackage/dist/dev/mp-weixin"
fi

restore_pkg() {
  if [ -f "$BAK" ]; then
    cp "$BAK" "$PROJ/package.json" && echo "[restore] package.json 已还原"
  fi
}
trap restore_pkg EXIT INT TERM

export UNI_INPUT_DIR="$PROJ_WIN"
export UNI_OUTPUT_DIR="$OUT"
export UNI_PLATFORM=mp-weixin
export NODE_ENV=production

cp "$PROJ/package.json" "$BAK" || exit 1

"$NODE_BIN" -e "
const fs=require('fs');
const p=JSON.parse(fs.readFileSync(process.argv[1],'utf8'));
const v='$VER';
p.dependencies=Object.assign({},p.dependencies,{
  '@dcloudio/uni-app':v,'@dcloudio/uni-components':v,'@dcloudio/uni-mp-weixin':v,
  '@dcloudio/uni-mp-vite':v,'@dcloudio/uni-mp-compiler':v,'@dcloudio/uni-mp-vue':v});
p.devDependencies=Object.assign({},p.devDependencies,{
  '@dcloudio/uni-cli-shared':v,'@dcloudio/vite-plugin-uni':v});
fs.writeFileSync(process.argv[1],JSON.stringify(p,null,2)+'\n');
" "$PROJ/package.json" || exit 1

UNI="$PROJ_WIN/node_modules/@dcloudio/vite-plugin-uni/bin/uni.js"
cd "$PROJ" || exit 1

# cac 解析：-p/--config 必须写在子命令之前
if [ "$MODE" = "build" ]; then
  "$NODE_BIN" "$UNI" -p mp-weixin --config "$CONFIG" build
  rc=$?
else
  rm -rf "$OUT"
  "$NODE_BIN" "$UNI" -p mp-weixin --config "$CONFIG" dev > "$TMP/dev.log" 2>&1 &
  PID=$!; rc=1
  for i in $(seq 1 90); do
    sleep 2
    if [ -f "$OUT/app.json" ] && [ -f "$OUT/app.js" ] && [ -f "$OUT/app.wxss" ] && [ -d "$OUT/static" ]; then
      rc=0; break
    fi
    kill -0 "$PID" 2>/dev/null || break
  done
  kill "$PID" 2>/dev/null; sleep 1; kill -9 "$PID" 2>/dev/null; wait "$PID" 2>/dev/null
fi

echo "=== output dir: $OUT"
ls "$OUT" 2>/dev/null | head -20
exit $rc
