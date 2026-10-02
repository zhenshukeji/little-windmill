import request from '@/utils/request'

// 查询学生列表
export function listStudent(query) {
  return request({
    url: '/kg/base/record/student/list',
    method: 'get',
    params: query
  })
}

// 查询学生详细
export function getStudent(id) {
  return request({
    url: '/kg/base/record/student/' + id,
    method: 'get'
  })
}

// 新增学生
export function addStudent(data) {
  return request({
    url: '/kg/base/record/student',
    method: 'post',
    data: data
  })
}

// 修改学生
export function updateStudent(data) {
  return request({
    url: '/kg/base/record/student',
    method: 'put',
    data: data
  })
}

// 删除学生
export function delStudent(data) {
  return request({
    url: '/kg/base/record/student',
    method: 'delete',
    data
  })
}

// 导出学生
export function exportStudent(data) {
  return request({
    url: '/kg/base/record/student/export',
    method: 'POST',
    data
  })
}


// 获取班级列表
export function classList() {
  return request({
    url: '/kg/base/record/student/classroom',
    method: 'GET',
  })
}

// 学生离校
export function leaveSturdent(data) {
  return request({
    url: '/kg/base/record/student/leave',
    method: 'DELETE',
    data
  })
}

// 历史人员列表
export function listHistory(data) {
  return request({
    url: '/kg/base/record/student/history/list',
    method: 'GET',
    params: data
  })
}

// 返校重读
export function backToSchool(data) {
  return request({
    url: '/kg/base/record/student/backToSchool',
    method: 'POST',
    data
  })
}

// 导入学生
export function importStudent(data) {
  return request({
    url: '/kg/base/record/student/importData',
    method: 'POST',
    data,
  })
}

// 删除人脸
export function delFace(id) {
  return request({
    url: '/kg/base/record/student/face/' + id,
    method: 'DELETE',
  })
}

// 查询学生列表
export function checkIsTeacher() {
  return request({
    url: '/kg/base/record/student/isTeacher',
    method: 'post',
  })
}

// 获取日历信息
export function getCalendarInfo(data) {
  return request({
    url: '/kg/base/record/student/calendar',
    method: 'get',
    params: data
  })
}

// 获取对应考勤记录
export function getAttendanceData(data) {
  return request({
    url: '/kg/base/record/student/checking/info',
    method: 'get',
    params: data
  })
}

// 获取缴费情况
export function getPaymentData(data) {
  return request({
    url: '/kg/base/record/student/payment',
    method: 'get',
    params: data
  })
}
