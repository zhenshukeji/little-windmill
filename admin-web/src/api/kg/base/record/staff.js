import request from '@/utils/request'

// 查询学校员工 列表
export function listStaff(query) {
  return request({
    url: '/kg/base/record/staff/list',
    method: 'get',
    params: query
  })
}

// 查询学校员工 详细
export function getStaff(id, data) {
  return request({
    url: '/kg/base/record/staff/' + id,
    method: 'get',
    params: data
  })
}

// 新增学校员工 
export function addStaff(data) {
  return request({
    url: '/kg/base/record/staff',
    method: 'post',
    data: data
  })
}

// 修改学校员工 
export function updateStaff(data) {
  return request({
    url: '/kg/base/record/staff',
    method: 'put',
    data: data
  })
}

// 删除学校员工 
export function delStaff(data) {
  return request({
    url: '/kg/base/record/staff',
    method: 'delete',
    data
  })
}

// 学校员工离职
export function fireStaff(data) {
  return request({
    url: '/kg/base/record/staff/quit',
    method: 'delete',
    data
  })
}

// 学校员工入职
export function hireStaff(data) {
  return request({
    url: '/kg/base/record/staff/entry',
    method: 'post',
    data
  })
}

// 获取岗位
export function getStation() {
  return request({
    url: '/kg/base/record/staff/post/list',
    method: 'get'
  })
}

// 给集团员工分配岗位
export function distributeJob(data) {
  return request({
    url: '/kg/base/record/staff/post',
    method: 'post',
    data
  })
}

// 导入员工
export function importStaff(data) {
  return request({
    url: '/kg/base/record/staff/importData',
    method: 'post',
    data
  })
}

// 导出员工
export function exportStaff(data) {
  return request({
    url: '/kg/base/record/staff/export',
    method: 'post',
    data
  })
}
