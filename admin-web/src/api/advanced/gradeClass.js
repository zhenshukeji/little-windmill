import request from '@/utils/request'

// 列表分页查询班级表
export function listClassroom(query) {
  return request({
    url: '/kg/base/advanced/classroom/list',
    method: 'get',
    params: query
  })
}

// 解除绑定老师
export function listRelieve(data) {
  return request({
    url: '/kg/base/advanced/classroom/relieve',
    method: 'DELETE',
    data: data
  })
}


// 删除班级
export function delClassroom(data) {
  return request({
    url: '/kg/base/advanced/classroom',
    method: 'DELETE',
    data: data
  })
}

// 列表分页查询班级可绑定的老师
export function listTeacher(query) {
  return request({
    url: '/kg/base/advanced/classroom/teacher/list',
    method: 'get',
    params: query
  })
}

// 班级绑定老师
export function bindClassroom(data) {
  return request({
    url: '/kg/base/advanced/classroom/bind',
    method: 'post',
    data: data
  })
}

// 添加班级
export function addClassroom(data) {
  return request({
    url: '/kg/base/advanced/classroom',
    method: 'post',
    data: data
  })
}

// 添加年级
export function addGrade(data) {
  return request({
    url: '/kg/base/advanced/grade',
    method: 'post',
    data: data
  })
}

// 根据id修改年级
export function updateGrade(data) {
  return request({
    url: '/kg/base/advanced/grade',
    method: 'put',
    data: data
  })
}

// 删除年级表
export function deleteGrade(data) {
  return request({
    url: '/kg/base/advanced/grade',
    method: 'delete',
    data: data
  })
}

// 查询可升班班级
export function listPromotion() {
  return request({
    url: '/kg/base/advanced/classroom/promotion/list',
    method: 'get',
  })
}

// 一键升班
export function litrePromotion(data) {
  return request({
    url: '/kg/base/advanced/classroom/promotion',
    method: 'post',
    data: data
  })
}


// 获取所有年级
export function listGrade() {
  return request({
    url: '/kg/base/advanced/grade/list',
    method: 'get'
  })
}

// 获取所有班级
export function listClassroomAll() {
  return request({
    url: '/kg/base/advanced/classroom/all',
    method: 'get'
  })
}

// 获取所有老师
export function listTeacherAll() {
  return request({
    url: '/kg/base/advanced/classroom/teacher/all',
    method: 'get'
  })
}
