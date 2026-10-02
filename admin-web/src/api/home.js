import request from '@/utils/request'


// 首页全量数据
export function getInfo() {
  return request({
    url: '/bloc/home',
    method: 'get'
  })
}
