import request from '@/utils/request'

// 查询登录用户 平台、机构、学校人员的登录信息列表
export function listInfo(query) {
  return request({
    url: '/base/info/list',
    method: 'get',
    params: query
  })
}

// 查询登录用户 平台、机构、学校人员的登录信息详细
export function getInfo(id) {
  return request({
    url: '/base/info/' + id,
    method: 'get'
  })
}

// 新增登录用户 平台、机构、学校人员的登录信息
export function addInfo(data) {
  return request({
    url: '/base/info',
    method: 'post',
    data: data
  })
}

// 修改登录用户 平台、机构、学校人员的登录信息
export function updateInfo(data) {
  return request({
    url: '/base/info',
    method: 'put',
    data: data
  })
}

// 删除登录用户 平台、机构、学校人员的登录信息
export function delInfo(id) {
  return request({
    url: '/base/info/' + id,
    method: 'delete'
  })
}
