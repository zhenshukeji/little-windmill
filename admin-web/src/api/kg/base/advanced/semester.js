import request from '@/utils/request'

// 查询学期 列表
export function listSemester(query) {
  return request({
    url: '/kg/base/advanced/semester/list',
    method: 'get',
    params: query
  })
}

// 新增学期
export function addSemester(data) {
  return request({
    url: '/kg/base/advanced/semester',
    method: 'post',
    data: data
  })
}

// 修改学期
export function updateSemester(data) {
  return request({
    url: '/kg/base/advanced/semester',
    method: 'put',
    data: data
  })
}

// 删除学期
export function delSemester(data) {
  return request({
    url: '/kg/base/advanced/semester',
    method: 'delete',
    data: data,
  })
}

// 查询课程 列表
export function listCourse(query) {
  return request({
    url: '/kg/base/advanced/course/list',
    method: 'get',
    params: query
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/kg/base/advanced/course',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/kg/base/advanced/course',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourse(data) {
  return request({
    url: '/kg/base/advanced/course',
    method: 'delete',
    data: data,
  })
}
