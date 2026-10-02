import request from '@/utils/request'

// 查询登录人员列表
export function listPeople(query) {
  return request({
    url: '/bloc/base/login/list',
    method: 'get',
    params: query
  })
}


// 重置密码
export function resetPassword(data) {
  return request({
    url: '/bloc/base/login/resetPwd',
    method: 'PUT',
    data
  })
}

// 获取人员对应校区权限
export function getAuthor(id) {
  return request({
    url: '/bloc/base/login/' + id,
    method: 'get',
  })
}

// 修改集团人员进入校区的权限
export function updateAuthor(data) {
  return request({
    url: '/bloc/base/login/goto/kg',
    method: 'PUT',
    data
  })
}