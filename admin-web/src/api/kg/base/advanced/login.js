import request from '@/utils/request'

// 查询登录记录 列表
export function listLogin(query) {
  return request({
    url: '/kg/base/advanced/login/list',
    method: 'get',
    params: query
  })
}

// 重置密码
export function reset(data) {
  return request({
    url: '/kg/base/advanced/login/resetPwd',
    method: 'put',
    data: data
  })
}

