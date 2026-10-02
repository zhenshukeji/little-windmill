// 社区版 mp-weixin 构建的 vite 配置（scripts/build_mp_weixin.sh 使用）。
// 工程根取自构建脚本注入的 UNI_INPUT_DIR（vite 会把配置打包到临时文件，
// import.meta.url 不可靠）。
import { createRequire } from 'node:module'
import path from 'node:path'

const projectRoot = process.env.UNI_INPUT_DIR
const requireFromProject = createRequire(
  'file:///' + path.resolve(projectRoot).split(path.sep).join('/') + '/'
)
const uni = requireFromProject('@dcloudio/vite-plugin-uni').default

export default {
  plugins: [uni()],
}
