import request from '@/utils/request'

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/kg/base/advanced/post/list',
    method: 'get',
    params: query
  })
}

// 根据Id查询校区岗位
export function getRole(roleId) {
  return request({
    url: '/kg/base/advanced/post/' + roleId,
    method: 'get'
  })
}

// 添加校区岗位
export function addRole(data) {
  return request({
    url: '/kg/base/advanced/post',
    method: 'post',
    data: data
  })
}

// 修改校区岗位
export function updateRole(data) {
  return request({
    url: '/kg/base/advanced/post',
    method: 'put',
    data: data
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

// 修改校区岗位的状态
export function changeRoleStatus(postId, status) {
  const data = {
    postId,
    status
  }
  return request({
    url: '/kg/base/advanced/post/status',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delRole(data) {
  return request({
    url: '/kg/base/advanced/post',
    method: 'delete',
    data
  })
}


// 获取菜单列表
export function treeselect() {
  return request({
    url: '/kg/base/advanced/post/menu',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/bloc/base/post/' + roleId,
    method: 'get'
  })
}
